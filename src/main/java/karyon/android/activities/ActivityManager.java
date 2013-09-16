package karyon.android.activities;

import karyon.Utilities;
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
     * Gets the instance of the controller manager
     * @return the controller manager
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
     * A controller manager is not publicly creatable
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
    public boolean push(IActivity toActivity)
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
        if (!m_oActivities.contains(toActivity))
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
     * Notifies the behaviour that a major lifecycle event has
     * occurred in this activity
     * @param toType the type of event
     * @param toActivity the activity it occurred in
     * @return true if the notification and notification tasks were successful
     */
    public boolean notify(NotificationType toType, IActivity toActivity)
    {
        return true;
    }



























    /**
     * Registers the specified behaviour so it will be used with the controller class
     * @param toControllerClass the class to use this behaviour with
     * @param toBehaviour the behaviour to use
     * @param <K> the type of the controller
     * @return true if the list of controllers changed as a result of this call
     */
    public final <K extends IActivity<K>> boolean addBehaviour(Class<K> toControllerClass, Behaviour<K> toBehaviour)
    {
        Utilities.checkParameterNotNull("toControllerClass", toControllerClass);
        Utilities.checkParameterNotNull("toBehaviour", toBehaviour);

        if (m_oBehavours == null)
        {
            m_oBehavours = new HashMap<Class<? extends IActivity>, List<Behaviour<? extends IActivity>>>();
        }

        if (!m_oBehavours.containsKey(toControllerClass))
        {
            m_oBehavours.put(toControllerClass, new List<Behaviour<? extends IActivity>>());
        }

        if (!m_oBehavours.get(toControllerClass).contains(toBehaviour))
        {
            return m_oBehavours.get(toControllerClass).add(toBehaviour);
        }
        return false;
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
    protected final <K extends IActivity<K>> boolean removeBehaviour(Class<K> toClass, Behaviour<K> toBehaviour)
    {
        if (m_oBehavours == null || !m_oBehavours.containsKey(toClass))
        {
            return false;
        }

        return m_oBehavours.get(toClass).remove(toBehaviour);
    }

    /**
     * Tells the controller manager to manage the controller using the
     * behaviour that was registered.  This will not set the actiivty,
     * the assumption is the activity is already set
     * @param toController the controller to manage
     */
    public final void manageController(IActivity toController)
    {
        Behaviour loBehaviour = getBehaviour(toController);
        if (loBehaviour != null)
        {

        }
    }

    /**
     * Sets the Activity and prepares to manage the activity
     * @param toController
     */
    public final void setCurrentController(IActivity toController)
    {

    }

}
