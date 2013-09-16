package karyon.android.controllers;

import karyon.Utilities;
import karyon.android.activities.IActivity;
import karyon.android.behaviours.Behaviour;
import karyon.collections.HashMap;
import karyon.collections.List;
import karyon.dynamicCode.Java;

/**
 * The Controller Manager is the workflow controller
 * for the application.  The Controller manager
 * is used to attach behaviours to views and
 * fragments
 */
public class ControllerManager
    extends karyon.Object
{
    // TODO: Look at having the internals (remove, register, etc.) work of the behaviour class rather than the instance
    private static ControllerManager g_oInstance;

    /**
     * Gets the instance of the controller manager
     * @return the controller manager
     */
    public static ControllerManager getInstance()
    {
        if (g_oInstance == null)
        {
            g_oInstance = new ControllerManager();
        }
        return g_oInstance;
    }

    private HashMap<Class<? extends IActivity>, List<Behaviour<? extends IActivity>>> m_oBehavours;

    /**
     * A controller manager is not publicly creatable
     */
    private ControllerManager()
    {
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
     * Gets the behaviour for the controller specified
     * @param toController the controller to get the behaviour for
     * @param <K> the type of the controller
     * @return the behaviour for the controller
     */
    protected final <K extends IActivity<K>> Behaviour<K> getBehaviour(K toController)
    {
        if (m_oBehavours == null)
        {
            return null;
        }

        Class loClass = toController.getClass();
        while (loClass != null && Java.isEqualOrAssignable(IActivity.class, loClass))
        {
            if (m_oBehavours.containsKey(loClass))
            {
                List<Behaviour<? extends IActivity>> loBehaviours = m_oBehavours.get(loClass);
                for (Behaviour loBehaviour : loBehaviours)
                {
                    if (loBehaviour.isValid(toController))
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
