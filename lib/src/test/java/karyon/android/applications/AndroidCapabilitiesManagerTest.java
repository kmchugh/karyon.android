package karyon.android.applications;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import karyon.android.AndroidTestApplication;
import karyon.android.CustomRoboTestRunner;
import karyon.testing.KaryonTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

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
    public void testIsInternetReachable() throws Exception
    {
        startMarker();

        assertTrue(new AndroidCapabilitiesManager().isInternetReachable());

        // TODO: Figure out how to turn off wifi to test for false result
    }

    @Test
    public void testGetTempFileDirectory() throws Exception
    {
        startMarker();

        assertEquals(AndroidApplicationAdaptor.getInstance().getApplicationContext().getCacheDir(), new AndroidCapabilitiesManager().getTempFileDirectory());
    }

    @Test
    public void testGetFileStoreDirectory() throws Exception
    {
        startMarker();

        assertEquals(AndroidApplicationAdaptor.getInstance().getApplicationContext().getFilesDir(), new AndroidCapabilitiesManager().getFileStoreDirectory());
    }

    @Test
    public void testIsDebuggable() throws Exception
    {
        startMarker();

        PackageManager loPM = AndroidApplicationAdaptor.getInstance().getApplicationContext().getPackageManager();
        ApplicationInfo loInfo = loPM.getApplicationInfo(AndroidApplicationAdaptor.getInstance().getApplicationContext().getPackageName(), 0);
        assertEquals(0 != (loInfo.flags &= ApplicationInfo.FLAG_DEBUGGABLE),
                     new AndroidCapabilitiesManager().isDebuggable());
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
