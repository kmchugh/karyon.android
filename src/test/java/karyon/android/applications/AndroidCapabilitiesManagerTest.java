package karyon.android.applications;

import android.content.res.Configuration;
import karyon.android.AndroidTestApplication;
import karyon.android.CustomRoboTestRunner;
import karyon.testing.KaryonTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static  org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 17/9/13
 * Time: 12:53 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(CustomRoboTestRunner.class)
public class AndroidCapabilitiesManagerTest
    extends KaryonTest
{
    @Before
    public void setUp() throws Exception
    {
        AndroidTestApplication.getInstance();

    }

    @Test
    public void testIsOnline() throws Exception
    {
        startMarker();

        assertTrue(new AndroidCapabilitiesManager().isOnline());

        // TODO: Figure out how to turn of wifi to test for false result
    }

    @Test
    public void testIsDebuggable() throws Exception
    {
        startMarker();

        assertTrue(new AndroidCapabilitiesManager().isOnline());

        // TODO: See if it is possible to switch the debug flag on the fly
    }

    @Test
    public void testIsTablet() throws Exception
    {
        startMarker();

        AndroidApplicationAdaptor.getInstance().getApplicationContext().getResources().getConfiguration().screenLayout = Configuration.SCREENLAYOUT_SIZE_SMALL;
        assertFalse(new AndroidCapabilitiesManager().isTablet());

        AndroidApplicationAdaptor.getInstance().getApplicationContext().getResources().getConfiguration().screenLayout = Configuration.SCREENLAYOUT_SIZE_NORMAL;
        assertFalse(new AndroidCapabilitiesManager().isTablet());

        AndroidApplicationAdaptor.getInstance().getApplicationContext().getResources().getConfiguration().screenLayout = Configuration.SCREENLAYOUT_SIZE_LARGE;

        assertTrue(new AndroidCapabilitiesManager().isTablet());
    }
}
