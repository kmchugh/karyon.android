package karyon.android.activities;

import karyon.testing.KaryonTest;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 16/9/13
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class NotificationTypeTest
    extends KaryonTest
{
    @Test
    public void testTypes() throws Exception
    {
        assertSame(NotificationType.CONTENT_READY, NotificationType.CONTENT_READY);
        assertSame(NotificationType.INIT, NotificationType.INIT);
        assertSame(NotificationType.FINISH, NotificationType.FINISH);
        assertSame(NotificationType.RESTART, NotificationType.RESTART);
        assertSame(NotificationType.START, NotificationType.START);
        assertSame(NotificationType.STOP, NotificationType.STOP);
        assertSame(NotificationType.PAUSE, NotificationType.PAUSE);
        assertSame(NotificationType.RESUME, NotificationType.RESUME);
        assertSame(NotificationType.LOW_MEMORY, NotificationType.LOW_MEMORY);
        assertSame(NotificationType.DESTROY, NotificationType.DESTROY);
    }
}
