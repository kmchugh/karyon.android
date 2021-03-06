package karyon.android.activities;

import karyon.android.applications.AndroidApplicationAdaptor;
import karyon.android.behaviours.Behaviour;
import karyon.android.behaviours.BehaviourManager;
import karyon.collections.List;

/**
 * The Activity Manager is the workflow controller
 * for the application.  The Activity manager
 * is used to attach behaviours to views and
 * fragments
 */
public class ActivityManager
    extends karyon.Object
{
    // TODO: Look at having the internals (remove, register, etc.) work of the behaviour class rather than the instance
    // TODO: Remove behaviors that have not been used in a while

    private static ActivityManager g_oInstance;

    /**
     * Gets the instance of the Activity manager
     * @return the Activity manager
     */
    public static ActivityManager getInstance()
    {
        if (g_oInstance == null)
        {
            g_oInstance = new ActivityManager();
        }
        return g_oInstance;
    }

    private List<IActivity> m_oActivities;
    private int m_nActivityPointer;

    /**
     * An Activity manager is not publicly creatable
     */
    private ActivityManager()
    {
    }

    /**
     * Pushes the activity on to the stack if the activity can be shown, if the activity can not
     * be shown it is finished and removed from the stack.  Generally this should be called automatically by
     * the activity when it is created.
     * @param toActivity the activity to show
     * @return true if the activity is displayed after this call
     */
    public boolean add(IActivity toActivity)
    {
        if (canShow(toActivity))
        {
            show(toActivity);
        }
        return m_oActivities != null && m_oActivities.get(m_nActivityPointer) == toActivity;
    }

    /**
     * Checks if it is okay to display the activity
     * @param toActivity the activity to display
     * @return true if it is okay to display the activity
     */
    private boolean canShow(IActivity toActivity)
    {
        Behaviour loBehaviour = BehaviourManager.getInstance().getBehaviourFor(toActivity);
        return loBehaviour == null || loBehaviour.canShow(toActivity);
    }

    /**
     * Add the specified activity to the Controller Stack
     * @param toActivity the activity to add to the stack
     */
    private void show(IActivity toActivity)
    {
        // If passed all the rules, push the activity to the stack
        if (m_oActivities == null)
        {
            m_oActivities = new List<IActivity>();
        }
        if (!hasActivity(toActivity))
        {
            if (m_oActivities.add(toActivity))
            {
                m_nActivityPointer = m_oActivities.size()-1;
            }
        }
    }

    /**
     * Removes the specified activity from the activity stack
     * @param toActivity the activity to remove
     * @return true if the activity stack was adjusted as a result of this call
     */
    protected final <K extends IActivity> boolean remove(K toActivity)
    {
        if(m_oActivities != null)
        {
            int lnIndex = m_oActivities.indexOf(toActivity);
            if (lnIndex >= 0)
            {
                if (m_oActivities.remove(toActivity))
                {
                    if (lnIndex <= m_nActivityPointer)
                    {
                        m_nActivityPointer--;
                        if (m_nActivityPointer < 0)
                        {
                            m_nActivityPointer = 0;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Removes all of the activities from the stack, this will
     * finish any activities that were placed on the stack
     */
    public final void clearActivities()
    {
        if (m_oActivities != null)
        {
            for (IActivity loActivity : m_oActivities)
            {
                loActivity.finish();
            }
            m_oActivities.clear();
        }
    }

    /**
     * Gets the activity that is marked as the current activity
     * @return the current activity or null if there is none
     */
    public IActivity getCurrentActivity()
    {
        return m_oActivities == null || m_oActivities.size() <= m_nActivityPointer ?
                null :
                m_oActivities.get(m_nActivityPointer);
    }

    /**
     * Attempts to start the specified activity.  The activity starting will end up calling push(Controller)
     * @param toActivityClass the activity to start
     * @return true if the manager attempted to start the activity
     */
    public void start(Class<? extends IActivity> toActivityClass)
    {
        this.start(toActivityClass, null);
    }

    /**
     * Starts the activity specified using toParent as the parent context
     * @param toActivityClass the activity class to start
     * @param toParent the parent activity
     */
    public void start(Class<? extends IActivity> toActivityClass, IActivity toParent)
    {
        AndroidApplicationAdaptor.getInstance().startActivity(toActivityClass, toParent);
    }

    /**
     * Checks if the activity specified in in the view stack
     * @param <K> the type of the activity
     * @param toActivityClass the class to check for
     * @return true if there is an activity in the stack of the specified type
     */
    public <K extends IActivity> boolean hasActivity(Class<K> toActivityClass)
    {
        if (m_oActivities != null && m_oActivities.size() > 0)
        {
            for (IActivity loActivity : m_oActivities)
            {
                if (loActivity.getClass().isAssignableFrom(toActivityClass))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the specified activity is in the activity stack
     * @param toActivity the activity to check for
     * @param <K> the type of the activity
     * @return true if the activity is in the stack, false otherwise
     */
    public <K extends IActivity> boolean hasActivity(K toActivity)
    {
        if (m_oActivities != null && m_oActivities.size() > 0)
        {
            return m_oActivities.contains(toActivity);
        }
        return false;
    }

    /**
     * Notifies the behaviour that a major lifecycle event has
     * occurred in this activity
     * @param toType the type of event
     * @param toActivity the activity it occurred in
     * @return true if the notification and notification tasks were successful
     */
    public <K extends IActivity> boolean notify(NotificationType toType, K toActivity)
    {
        Behaviour loBehaviour = BehaviourManager.getInstance().getBehaviourFor(toActivity);
        boolean llReturn = true;
        if (loBehaviour != null)
        {
            llReturn = loBehaviour.notify(toType, toActivity);
        }

        // If the return type is to destroy then also clean out the behaviour
        if (toType == NotificationType.DESTROY)
        {
            BehaviourManager.getInstance().removeBehaviourFor(toActivity);
        }
        return llReturn;
    }
}
