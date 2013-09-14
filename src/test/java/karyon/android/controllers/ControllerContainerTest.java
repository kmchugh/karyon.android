package karyon.android.controllers;

import android.view.Window;
import karyon.android.CustomRoboTestRunner;
import karyon.android.Utilities;
import karyon.testing.KaryonTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 14/9/13
 * Time: 12:00 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(CustomRoboTestRunner.class)
public class ControllerContainerTest
    extends KaryonTest
{
    @Test
    public void testSetWindowFeature() throws Exception
    {
        startMarker();

        ActivityController<ControllerContainer> loActivity = Robolectric.buildActivity(ControllerContainer.class);

        assertFalse(Utilities.hasFeature(loActivity.get().getWindow(), Window.FEATURE_NO_TITLE));

        assertTrue(loActivity.get().setWindowFeature(Window.FEATURE_NO_TITLE));

        // This is currently not testable with the robolectric shadow classes

        // assertTrue(Utilities.hasFeature(loActivity.get().getWindow(), Window.FEATURE_NO_TITLE));
    }
}
