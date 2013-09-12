package karyon.android.applications;

import android.content.Context;
import android.net.ConnectivityManager;
import karyon.android.AndroidTestApplication;
import karyon.android.CustomRoboTestRunner;
import karyon.applications.Application;
import karyon.testing.KaryonTest;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 12/9/13
 * Time: 5:18 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(CustomRoboTestRunner.class)
public class AndroidApplicationAdaptorTest
        extends KaryonTest
{
    @Test
    public void testGetInstance() throws Exception
    {
        startMarker();
        assertNotNull(AndroidApplicationAdaptor.getInstance());
    }

    @Test
    public void testGetApplicationClass() throws Exception
    {
        startMarker();
        assertEquals(AndroidTestApplication.class, AndroidApplicationAdaptor.getInstance().getApplicationClass());
    }

    @Test
    public void testOnCreate() throws Exception
    {
        startMarker();
        assertTrue(Application.getInstance().isRunning());

        assertEquals(SharedPreferencesPropertyManager.class, Application.getInstance().getPropertyManager().getClass());
    }

    @Test
    public void testOnTerminate() throws Exception
    {
        startMarker();

        CustomRoboTestRunner.terminate();
        assertFalse(Application.getInstance().isRunning());
    }

    @Test
    public void testOnConfigurationChanged() throws Exception
    {
        startMarker();

        // Nothing to test here yet
    }

    @Test
    public void testOnLowMemory() throws Exception
    {
        startMarker();

        // Nothing to test here yet
    }

    @Test
    public void testGetSystemService() throws Exception
    {
        startMarker();

        ConnectivityManager loManager = AndroidApplicationAdaptor.getSystemService(Context.CONNECTIVITY_SERVICE,
                                                                                   ConnectivityManager.class);
        assertNotNull(loManager);
    }

    @Test
    public void testGetApplicationInstance() throws Exception
    {
        startMarker();

        assertSame(Application.getInstance(), AndroidApplicationAdaptor.getInstance().getApplicationInstance());
    }
}
