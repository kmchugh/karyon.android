package karyon.android1.Activities;

import karyon.collections.HashMap;
import karyon.collections.List;
import karyon.android1.Applications.Application;
import karyon.android1.Behaviours.ControllerBehaviour;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Manages which activities are loaded and displayed, also manages a history
 * for activity flow
 * @author kmchugh
 */
public class ControllerManager
{
    private static ControllerManager g_oInstance;
    public static ControllerManager getInstance()
    {
        if (g_oInstance == null)
        {
            g_oInstance = new ControllerManager();
        }
        return g_oInstance;
    }

    // TODO: Remove behaviours that have not been used in a while
    
    
    private List<IController> m_oActivityHistory;
    private HashMap<Class, ControllerBehaviour> m_oBehaviours;
    private int m_nActivityPointer;
    
    /**
     * not publicly createable
     */
    private ControllerManager()
    {
    }
    
    /**
     * Attempts to start the specified activity.  The activity starting will end up calling push(Controller)
     * @param toActivityClass the activity to start
     * @return true if the manager attempted to start the activity
     */
    public void start(Class<? extends android.app.Activity> toActivityClass)
    {
       this.start(toActivityClass, null);
    }
    
    /**
     * Starts the activity specified using toParent as the parent context
     * @param toActivityClass the activity class to start
     * @param toParent the parent activity
     */
    public void start(Class<? extends  android.app.Activity> toActivityClass, android.app.Activity toParent)
    {
        Application.getInstance().startActivity(toActivityClass, toParent);
    }

    /**
     * Adds the Behaviour for the activity, for the moment, this is intended to be a one to one for activity and behaviour.
     * In the future this will be extended to allow rules on subclasses, rule chaining, and conditional rules
     * @param <K> the type of the activity
     * @param toActivityClass the activity class
     * @param toBehaviour the behaviour to apply to the activity
     * @return true if the behaviour collection was changed as a result of this call
     */
    public <K extends IController> boolean addActivityBehaviour(Class<K> toActivityClass, ControllerBehaviour<K> toBehaviour)
    {
        if (m_oBehaviours == null)
        {
            m_oBehaviours = new HashMap<Class, ControllerBehaviour>();
        }
        if (!m_oBehaviours.containsKey(toActivityClass))
        {
            m_oBehaviours.put(toActivityClass, toBehaviour);
            return true;
        }
        return false;
    }
    
    /**
     * Gets the behaviour for the specified activity, this will return null if no behaviour exists
     * @param <K> the type of activity
     * @param toActivityClass the activity class
     * @return the behaviour for the activity specified
     */
    protected <K extends IController> ControllerBehaviour<K> getBehaviour(Class<K> toActivityClass)
    {
        // Check if we need to attempt a behaviour load
        if (m_oBehaviours == null || !m_oBehaviours.containsKey(toActivityClass))
        {
            ControllerBehaviour<K> loBehaviour = null;
            Method loMethod = null;
            try
            {
                loMethod = toActivityClass.getMethod("get" + toActivityClass.getSimpleName() + "Behaviour");
            }
            catch (NoSuchMethodException ex)
            {
                // Not dangerous, just means no dynamic Controller Behaviour
            }
            // Lets check if there is a loadable behaviour
            if (loMethod != null && Modifier.isStatic(loMethod.getModifiers()))
            {
                try
                {
                    loBehaviour = (ControllerBehaviour)loMethod.invoke(toActivityClass, null);
                }
                catch(Throwable ex)
                {
                    Application.log(ex);
                }
            }
            addActivityBehaviour(toActivityClass, Application.getInstance(Application.class).getBehaviourFor((Class<Controller>)toActivityClass, loBehaviour));
        }
        return m_oBehaviours.get(toActivityClass);
    }
    
    /**
     * Checks if the View on the top of the stack is of the specified class
     * @param <K> the type to check
     * @param toActivityClass the class to check for
     * @return true if the current activity is the specified activity type
     */
    public <K extends android.app.Activity> boolean hasActivity(Class<K> toActivityClass)
    {
        if (m_oActivityHistory != null && m_oActivityHistory.size() > 0)
        {
            for (IController loActivity : m_oActivityHistory)
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
     * Pushes the activity on to the stack if the activity can be shown, if the activity can not
     * be shown it is finished and removed from the stack.  Generally this should be called automatically by
     * the activity when it is created.
     * @param toActivity the activity to show
     * @return true if the activity is displayed after this call
     */
    public boolean push(IController toActivity)
    {
       if (canShow(toActivity))
        {
            show(toActivity);
        }
        return m_oActivityHistory != null && m_oActivityHistory.get(m_nActivityPointer) == toActivity;
    }
    
    /**
     * Removes the specified activity from the activity stack
     * @param toActivity the activity to remove
     * @return true if the activity stack was adjusted as a result of this call
     */
    private boolean remove(IController toActivity)
    {
        if(m_oActivityHistory != null)
        {
            int lnIndex = m_oActivityHistory.indexOf(toActivity);
            if (lnIndex >= 0)
            {
                if (m_oActivityHistory.remove(toActivity))
                {
                    if (lnIndex <= m_nActivityPointer)
                    {
                        m_nActivityPointer--;
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    
    // TODO: This needs to be refactored to allow dynamic notifications notify(TYPE.INIT) rather than notifyInit()
    
    /**
     * Used to notify the activity manager that an Controller has been initialised, generally called by the
     * activity.
     * @param toActivity the activity that has initialised
     */
    public void notifyInit(IController toActivity)
    {
        ControllerBehaviour loBehaviour = getBehaviour(toActivity.getClass());
        if (loBehaviour != null)
        {
            loBehaviour.onInit(toActivity);
        }
    }
    
    /**
     * Occurs before finish on an activity, if false is returned, the activity will not finish, if true is returned
     * finish will eventually occur
     * @param toActivity the activity that is preparing to finish
     * @return true to finish, false to interrupt the finish call
     */
    public boolean notifyFinishing(IController toActivity)
    {
        if (!toActivity.isFinishing())
        {
            ControllerBehaviour loBehaviour = getBehaviour(toActivity.getClass());
            if (loBehaviour != null)
            {
                return loBehaviour.onFinishing(toActivity);
            }
        }
        return true;
    }
    
    /**
     * The specified activity has been destroyed and should be removed from the stack
     * @param toActivity the activity
     */
    public void notifyDestroy(IController toActivity)
    {
        if (remove(toActivity))
        {
            ControllerBehaviour loBehaviour = getBehaviour(toActivity.getClass());
            if (loBehaviour != null)
            {
                loBehaviour.onDestroy(toActivity);
            }
        }
    }
    
    /**
     * The specified activity has been paused which means it is still active, but there is another activity above or
     * the screen has turned off
     * @param toActivity the activity that has been paused
     */
    public void notifyPause(IController toActivity)
    {
        ControllerBehaviour loBehaviour = getBehaviour(toActivity.getClass());
        if (loBehaviour != null)
        {
            loBehaviour.onPause(toActivity);
        }
    }
    
    /**
     * The specified activity has been made the active activity
     * @param toActivity the activity that has been started
     */
    public void notifyStart(IController toActivity)
    {
        ControllerBehaviour loBehaviour = getBehaviour(toActivity.getClass());
        if (loBehaviour != null)
        {
            loBehaviour.onStart(toActivity);
        }
    }
    
    /**
     * The specified activity has been resumed, the screen has been turned back on, or the partial view
     * has been removed and this activity has become the focus again
     * @param toActivity the activity that has been resumed
     */
    public void notifyResume(IController toActivity)
    {
        ControllerBehaviour loBehaviour = getBehaviour(toActivity.getClass());
        if (loBehaviour != null)
        {
            loBehaviour.onResume(toActivity);
        }
    }
    
    /**
     * The specified activity has been stopped, the activity has finished
     * @param toActivity the activity that has been finished
     */
    public void notifyStop(IController toActivity)
    {
        if (remove(toActivity))
        {
            ControllerBehaviour loBehaviour = getBehaviour(toActivity.getClass());
            if (loBehaviour != null)
            {
                loBehaviour.onStop(toActivity);
            }
        }
    }
    
    /**
     * The activity has been restarted, which means it was stopped and the user has navigated back to it
     * before the GC destroyed it
     * @param toActivity the activity that is restarted
     */
    public void notifyRestart(IController toActivity)
    {
        ControllerBehaviour loBehaviour = getBehaviour(toActivity.getClass());
        if (loBehaviour != null)
        {
            loBehaviour.onRestart(toActivity);
        }
    }
    
    /**
     * The activity has been told the system is running low on memory.
     * @param toActivity the activity that is being informed memory is getting low
     */
    public void notifyLowMemory(IController toActivity)
    {
        ControllerBehaviour loBehaviour = getBehaviour(toActivity.getClass());
        if (loBehaviour != null)
        {
            loBehaviour.onLowMemory(toActivity);
        }
    }
    
    /**
     * Occurs when the view has been set on an activity
     * @param toActivity the activity the view has been set for
     */
    public boolean notifyContentReady(IController toActivity)
    {
        ControllerBehaviour loBehaviour = getBehaviour(toActivity.getClass());
        if (loBehaviour != null)
        {
            return loBehaviour.onContentReady(toActivity);
        }
        return true;
    }
    
    /**
     * Checks if it is okay to display the activity
     * @param toActivity the activity to display
     * @return true if it is okay to display the activity
     */
    private boolean canShow(IController toActivity)
    {
        ControllerBehaviour loBehaviour = getBehaviour(toActivity.getClass());
        return loBehaviour == null || loBehaviour.canShow(toActivity);
    }
    
    /**
     * Add the specified activity to the Controller Stack
     * @param toActivity the activity to add to the stack
     */
    private void show(IController toActivity)
    {

        // If passed all the rules, push the activity to the stack
        if (m_oActivityHistory == null)
        {
            m_oActivityHistory = new List<IController>();
        }
        if (!m_oActivityHistory.contains(toActivity))
        {

            if (m_oActivityHistory.add(toActivity))
            {
                m_nActivityPointer = m_oActivityHistory.size()-1;
            }
        }


    }
}
