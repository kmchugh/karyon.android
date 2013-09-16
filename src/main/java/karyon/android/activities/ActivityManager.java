package karyon.android.activities;

import karyon.Utilities;
import karyon.android.applications.AndroidApplicationAdaptor;
import karyon.android.behaviours.Behaviour;
import karyon.collections.HashMap;
import karyon.collections.List;
import karyon.dynamicCode.Java;

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
    private HashMap<Class<? extends IActivity>, List<Behaviour<? extends IActivity>>> m_oBehavours;
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
     * Registers the specified behaviour so it will be used with the controller class
     * @param toActivityClass the class to use this behaviour with
     * @param toBehaviour the behaviour to use
     * @param <K> the type of the controller
     * @return true if the list of controllers changed as a result of this call
     */
    public final <K extends IActivity<K>> boolean add(Class<K> toActivityClass, Behaviour<K> toBehaviour)
    {
        Utilities.checkParameterNotNull("toControllerClass", toActivityClass);
        Utilities.checkParameterNotNull("toBehaviour", toBehaviour);

        if (m_oBehavours == null)
        {
            m_oBehavours = new HashMap<Class<? extends IActivity>, List<Behaviour<? extends IActivity>>>();
        }

        if (!m_oBehavours.containsKey(toActivityClass))
        {
            m_oBehavours.put(toActivityClass, new List<Behaviour<? extends IActivity>>());
        }

        if (!m_oBehavours.get(toActivityClass).contains(toBehaviour))
        {
            return m_oBehavours.get(toActivityClass).add(toBehaviour);
        }
        return false;
    }



    /**
     * Checks if it is okay to display the activity
     * @param toActivity the activity to display
     * @return true if it is okay to display the activity
     */
    private boolean canShow(IActivity toActivity)
    {
        Behaviour loBehaviour = getBehaviour(toActivity);
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
     * Gets the behaviour for the controller specified
     * @param toActivity the controller to get the behaviour for
     * @param <K> the type of the controller
     * @return the behaviour for the controller
     */
    protected final <K extends IActivity<K>> Behaviour<K> getBehaviour(K toActivity)
    {
        if (m_oBehavours == null)
        {
            return null;
        }

        Class loClass = toActivity.getClass();
        while (loClass != null && Java.isEqualOrAssignable(IActivity.class, loClass))
        {
            if (m_oBehavours.containsKey(loClass))
            {
                List<Behaviour<? extends IActivity>> loBehaviours = m_oBehavours.get(loClass);
                for (Behaviour loBehaviour : loBehaviours)
                {
                    if (loBehaviour.isValid(toActivity))
                    {
                        return (Behaviour<K>)loBehaviour;
                    }
                }
            }
            loClass = loClass.getSuperclass();
        }
        return null;
    }


    /**
     * Clears out all of the behaviours from the manager
     */
    protected final void clearBehaviours()
    {
        m_oBehavours = null;
    }

    /**
     * Removes the specified behaviour from the behaviour list.
     * @param toClass the class the behaviour is registered to
     * @param toBehaviour the behaviour to remove
     * @param <K> the type of the controller
     * @return true if the behaviour collections were changed as a result of this call
     */
    protected final <K extends IActivity<K>> boolean remove(Class<K> toClass, Behaviour<K> toBehaviour)
    {
        if (m_oBehavours == null || !m_oBehavours.containsKey(toClass))
        {
            return false;
        }

        return m_oBehavours.get(toClass).remove(toBehaviour);
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
        Behaviour loBehaviour = getBehaviour(toActivity);
        if (loBehaviour != null)
        {
            return loBehaviour.notify(toType, toActivity);
        }
        return true;
    }
}
