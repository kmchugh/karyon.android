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
    public final boolean notify(NotificationType toType, T toActivity)
    {
        if (toType == NotificationType.FINISH)
        {
            return onFinish(toActivity);
        }
        else if (toType == NotificationType.INIT)
        {
            return onInit(toActivity);
        }
        else if (toType == NotificationType.CONTENT_READY)
        {
            return onContentReady(toActivity);
        }
        else if (toType == NotificationType.DESTROY)
        {
            return onDestroy(toActivity);
        }
        else if (toType == NotificationType.LOW_MEMORY)
        {
            return onLowMemory(toActivity);
        }
        else if (toType == NotificationType.PAUSE)
        {
            return onPause(toActivity);
        }
        else if (toType == NotificationType.RESTART)
        {
            return onRestart(toActivity);
        }
        else if (toType == NotificationType.RESUME)
        {
            return onResume(toActivity);
        }
        else if (toType == NotificationType.START)
        {
            return onStart(toActivity);
        }
        else if (toType == NotificationType.STOP)
        {
            return onStop(toActivity);
        }
        else
        {
            return onUnknownNotification(toType, toActivity);
        }
    }

    /**
     * Occurs when an activity is finishing
     * @param toActivity the activity that we are acting upon
     * @return true if everything was successful, false otherwise
     */
    public boolean onFinish(T toActivity)
    {
        return true;
    }

    /**
     * Occurs when an activity is initialising
     * @param toActivity the activity that we are acting upon
     * @return true if everything was successful, false otherwise
     */
    public boolean onInit(T toActivity)
    {
        return true;
    }

    /**
     * Occurs when an activitys content is ready
     * @param toActivity the activity that we are acting upon
     * @return true if everything was successful, false otherwise
     */
    public boolean onContentReady(T toActivity)
    {
        return true;
    }

    /**
     * Occurs when an activity is being destroyed
     * @param toActivity the activity that we are acting upon
     * @return true if everything was successful, false otherwise
     */
    public boolean onDestroy(T toActivity)
    {
        return true;
    }

    /**
     * Occurs when an activity is low on memory
     * @param toActivity the activity that we are acting upon
     * @return true if everything was successful, false otherwise
     */
    public boolean onLowMemory(T toActivity)
    {
        return true;
    }

    /**
     * Occurs when an activity is pausing
     * @param toActivity the activity that we are acting upon
     * @return true if everything was successful, false otherwise
     */
    public boolean onPause(T toActivity)
    {
        return true;
    }

    /**
     * Occurs when an activity is restarting
     * @param toActivity the activity that we are acting upon
     * @return true if everything was successful, false otherwise
     */
    public boolean onRestart(T toActivity)
    {
        return true;
    }

    /**
     * Occurs when an activity is resuming
     * @param toActivity the activity that we are acting upon
     * @return true if everything was successful, false otherwise
     */
    public boolean onResume(T toActivity)
    {
        return true;
    }

    /**
     * Occurs when an activity is starting
     * @param toActivity the activity that we are acting upon
     * @return true if everything was successful, false otherwise
     */
    public boolean onStart(T toActivity)
    {
        return true;
    }

    /**
     * Occurs when an activity is stopping
     * @param toActivity the activity that we are acting upon
     * @return true if everything was successful, false otherwise
     */
    public boolean onStop(T toActivity)
    {
        return true;
    }

    /**
     * Occurs when a notification happens that the system does not know about
     * @param toType the notification that occurred
     * @param toActivity the activity that we are acting upon
     * @return true if everything was successful, false otherwise
     */
    public boolean onUnknownNotification(NotificationType toType, T toActivity)
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
}
