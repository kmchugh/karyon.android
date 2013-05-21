package karyon.Android.Activities;

import com.xtremelabs.robolectric.Robolectric;
import android.widget.TextView;
import karyon.Android.Applications.AndroidApplication;
import karyon.Android.Applications.Application;
import karyon.Android.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import karyon.Android.CustomRoboTestRunner;

/**
 * Created with IntelliJ IDEA.
 * User: YC
 * Date: 9/1/13
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */

// Test class for SplashActivity
@RunWith(CustomRoboTestRunner.class)
public class SplashActivityTest {

    private SplashActivity m_oActivity;
    private TextView m_oSplashText;
    private TextView m_oSplashVersion;


    @Before
    public void beforeTest()
    {
        Robolectric.setDefaultHttpResponse(200, "OK");
        if (!Application.isCreated())
        {
            android.app.Application loApp = Robolectric.application;
            loApp.onCreate();
        }
        m_oActivity = new SplashActivity();
        m_oActivity.onCreate(null);
    }

    @Test
    public void testStart() throws Exception
    {
        assertTrue(Application.isCreated());
        assertTrue(Application.isInitialised());
    }

  @Test
    public void testSetVersionText() throws Exception {
       assertEquals("TestAndroidApp, v1.0.0.0", Application.getInstance().getVersion().toString());

    }

    @Test
    public void testSetIDText() throws Exception {
       String loSplashText =  Application.getInstance().getInstanceGUID();
        assertTrue(loSplashText.length() != 0);
    }

}
