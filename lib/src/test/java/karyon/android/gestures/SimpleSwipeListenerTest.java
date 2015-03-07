package karyon.android.gestures;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import karyon.Date;
import karyon.android.CustomRoboTestRunner;
import karyon.android.applications.AndroidApplicationAdaptor;
import karyon.testing.KaryonTest;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 20/9/13
 * Time: 8:33 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(CustomRoboTestRunner.class)
public class SimpleSwipeListenerTest
    extends KaryonTest
{

    // TODO: Test that events are outputting to the correct handler event

    @Test
    public void testOnTouch() throws Exception
    {
        startMarker();
        SimpleSwipeListener loListener = new SimpleSwipeListener(AndroidApplicationAdaptor.getInstance().getBaseContext())
        {
            @Override
            public boolean onSwipeRight()
            {
                return true;
            }
        };

        assertTrue(loListener.onTouch(new View(AndroidApplicationAdaptor.getInstance().getBaseContext()), MotionEvent.obtain(100L, Date.getCurrentLong(),MotionEvent.ACTION_CANCEL, 10, 10, 0)));
        assertTrue(loListener.onTouch(new View(AndroidApplicationAdaptor.getInstance().getBaseContext()), MotionEvent
                .obtain(100L, Date.getCurrentLong(), MotionEvent.ACTION_DOWN, 10, 10, 0)));
    }
}
