package karyon.android.activities;

import karyon.DynamicEnum;

/**
 * The notification type for events occurring on the activity
 * that need to be forwarded to the behaviour
 */
public class NotificationType
    extends DynamicEnum
{
    // Used to notify the Activity Manager that an Activity is being initialised
    public static NotificationType INIT = new NotificationType();

    // Used to notify the Activity Manager that an Activity is being finished
    public static NotificationType FINISH = new NotificationType();

    // Used to notify the Activity Manager that an Activity is being restarted
    public static NotificationType RESTART = new NotificationType();

    // Used to notify the Activity Manager that an Activity is being started
    public static NotificationType START = new NotificationType();

    // Used to notify the Activity Manager that an Activity is being stopped
    public static NotificationType STOP = new NotificationType();

    // Used to notify the Activity Manager that an Activity is being paused
    public static NotificationType PAUSE = new NotificationType();

    // Used to notify the Activity Manager that an Activity is being resumed
    public static NotificationType RESUME = new NotificationType();

    // Used to notify the Activity Manager that an Activity has run low on memory
    public static NotificationType LOW_MEMORY = new NotificationType();

    // Used to notify the Activity Manager that an Activity is being destroyed
    public static NotificationType DESTROY = new NotificationType();

    // Used to notify the Activity Manager that an Activitys content has been upated
    public static NotificationType CONTENT_READY = new NotificationType();

    static
    {
        register();
    }
}
