package karyon.android.activities;

import karyon.DynamicEnum;

/**
 * The notification type for events occurring on the activity
 * that need to be forwarded to the behaviour
 */
public class NotificationType
    extends DynamicEnum
{
    public static NotificationType INIT = new NotificationType();
    public static NotificationType FINISH = new NotificationType();
    public static NotificationType RESTART = new NotificationType();
    public static NotificationType START = new NotificationType();
    public static NotificationType STOP = new NotificationType();
    public static NotificationType PAUSE = new NotificationType();
    public static NotificationType RESUME = new NotificationType();
    public static NotificationType LOW_MEMORY = new NotificationType();
    public static NotificationType DESTROY = new NotificationType();
    public static NotificationType CONTENT_READY = new NotificationType();

    static
    {
        register();
    }
}
