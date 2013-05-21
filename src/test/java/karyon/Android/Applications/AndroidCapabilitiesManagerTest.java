package karyon.Android.Applications;

import static junit.framework.Assert.*;

import Karyon.Applications.ICapabilitiesManager;
import com.xtremelabs.robolectric.Robolectric;
import karyon.Android.CustomRoboTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CustomRoboTestRunner.class)
public class AndroidCapabilitiesManagerTest
{
    @Test
    public void testIsOnline()
    {
        if (!Application.isCreated())
        {
            Robolectric.application.onCreate();
        }
        ICapabilitiesManager loManager = Application.getInstance().getCapabilitiesManager();
        if (loManager.getClass().equals(AndroidCapabilitiesManager.class))
        {
            assertTrue(((AndroidCapabilitiesManager)loManager).isOnline());
        }

    }

}
