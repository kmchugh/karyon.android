package karyon.android.behaviours;

import karyon.android.activities.IActivity;
import karyon.android.activities.NotificationType;

/**
 * A Behaviour is how an activity should behave under
 * different situations.  Basically the concept of
 * a controller
 * @param <T>
 */
public class Behaviour<T extends IActivity>
    extends karyon.Object
{
    /**
     * Creates a new instance of the Controller Behaviour
     */
    protected Behaviour()
    {
    }

    /**
     * Determines if this behaviour is okay to use with the
     * controller specified
     * @param toActivity the controller that we are finding a behaviour for
     */
    public boolean isValid(T toActivity)
    {
        return true;
    }

    /**
     * Determines if it is okay to show the activity specified
     * @param toActivity the activity to check
     * @return true if it is okay to display the activity
     */
    public boolean canShow(T toActivity)
    {
        return true;
    }

    /**
     * Notifies the behaviour that something has happened in the activity
     * @param toType the type of notification
     * @param toActivity the activity that is being notified
     * @return true if everything worked correctly
     */
    public boolean notify(NotificationType toType, T toActivity)
    {
        return true;
    }

    /*

    // TODO: This all needs to be pushed in to a behaviour

    protected boolean authenticationRequired(boolean tlRequire)
    {
        if (tlRequire && !SessionManager.getInstance().getCurrentSession().isAuthenticated())
        {
            finish();
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean startActivityWhenAuthenticated(Intent toIntent)
    {
        if (!SessionManager.getInstance().getCurrentSession().isAuthenticated())
        {
            // TODO: This code exists more than once, needs to be refactored
            Intent loIntent = new Intent(getBaseContext(), WebActivity.class);
            loIntent.putExtra("com.youcommentate.authenticationURL", YouCommentate.getInstance().getURL("AUTHENTICATEURL"));
            startActivity(loIntent);
        }

        if (SessionManager.getInstance().getCurrentSession().isAuthenticated())
        {
            startActivity(toIntent);
            return true;
        }
        return false;
    }
    *
    */


//       /**
//     * Used to notify the activity manager that an Controller has been initialised, generally called by the
//     * activity.
//     * @param toActivity the activity that has initialised
//     */
//    public void notifyInit(IActivity toActivity)
//    {
//        Behaviour loBehaviour = getBehaviour(toActivity.getClass());
//        if (loBehaviour != null)
//        {
//            //loBehaviour.onInit(toActivity);
//        }
//    }
//
//    /**
//     * Occurs before finish on an activity, if false is returned, the activity will not finish, if true is returned
//     * finish will eventually occur
//     * @param toActivity the activity that is preparing to finish
//     * @return true to finish, false to interrupt the finish call
//     */
//    public boolean notifyFinishing(IActivity toActivity)
//    {
//        /*
//        if (!toActivity.isFinishing())
//        {
//            Behaviour loBehaviour = getBehaviour(toActivity.getClass());
//            if (loBehaviour != null)
//            {
//                //return loBehaviour.onFinishing(toActivity);
//            }
//        }
//        */
//        return true;
//
//    }
//
//    /**
//     * The specified activity has been destroyed and should be removed from the stack
//     * @param toActivity the activity
//     */
//    public void notifyDestroy(IActivity toActivity)
//    {
//        if (remove(toActivity))
//        {
//            Behaviour loBehaviour = getBehaviour(toActivity.getClass());
//            if (loBehaviour != null)
//            {
//                //loBehaviour.onDestroy(toActivity);
//            }
//        }
//    }
//
//    /**
//     * The specified activity has been paused which means it is still active, but there is another activity above or
//     * the screen has turned off
//     * @param toActivity the activity that has been paused
//     */
//    public void notifyPause(IActivity toActivity)
//    {
//        Behaviour loBehaviour = getBehaviour(toActivity.getClass());
//        if (loBehaviour != null)
//        {
//            //loBehaviour.onPause(toActivity);
//        }
//    }
//
//    /**
//     * The specified activity has been made the active activity
//     * @param toActivity the activity that has been started
//     */
//    public void notifyStart(IActivity toActivity)
//    {
//        Behaviour loBehaviour = getBehaviour(toActivity.getClass());
//        if (loBehaviour != null)
//        {
//            //loBehaviour.onStart(toActivity);
//        }
//    }
//
//    /**
//     * The specified activity has been resumed, the screen has been turned back on, or the partial view
//     * has been removed and this activity has become the focus again
//     * @param toActivity the activity that has been resumed
//     */
//    public void notifyResume(IActivity toActivity)
//    {
//        Behaviour loBehaviour = getBehaviour(toActivity.getClass());
//        if (loBehaviour != null)
//        {
//            //loBehaviour.onResume(toActivity);
//        }
//    }
//
//    /**
//     * The specified activity has been stopped, the activity has finished
//     * @param toActivity the activity that has been finished
//     */
//    public void notifyStop(IActivity toActivity)
//    {
//        if (remove(toActivity))
//        {
//            Behaviour loBehaviour = getBehaviour(toActivity.getClass());
//            if (loBehaviour != null)
//            {
//                //loBehaviour.onStop(toActivity);
//            }
//        }
//    }
//
//    /**
//     * The activity has been restarted, which means it was stopped and the user has navigated back to it
//     * before the GC destroyed it
//     * @param toActivity the activity that is restarted
//     */
//    public void notifyRestart(IActivity toActivity)
//    {
//        Behaviour loBehaviour = getBehaviour(toActivity.getClass());
//        if (loBehaviour != null)
//        {
//            //loBehaviour.onRestart(toActivity);
//        }
//    }
//
//    /**
//     * The activity has been told the system is running low on memory.
//     * @param toActivity the activity that is being informed memory is getting low
//     */
//    public void notifyLowMemory(IActivity toActivity)
//    {
//        Behaviour loBehaviour = getBehaviour(toActivity.getClass());
//        if (loBehaviour != null)
//        {
//            //loBehaviour.onLowMemory(toActivity);
//        }
//    }
//
//    /**
//     * Occurs when the view has been set on an activity
//     * @param toActivity the activity the view has been set for
//     */
//    public boolean notifyContentReady(IActivity toActivity)
//    {
//        Behaviour loBehaviour = getBehaviour(toActivity.getClass());
//        if (loBehaviour != null)
//        {
//            //return loBehaviour.onContentReady(toActivity);
//        }
//        return true;
//    }
}
