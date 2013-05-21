package karyon.Android.Activities;

import android.app.Activity;
import com.xtremelabs.robolectric.Robolectric;
import karyon.Android.Applications.Application;
import karyon.Android.Behaviours.ControllerBehaviour;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import karyon.Android.CustomRoboTestRunner;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: YC
 * Date: 10/1/13
 * Time: 1:08 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(CustomRoboTestRunner.class)
public class ControllerManagerTest
{
    private Activity m_oActivity;
   // private IController m_oConroller;
    private ControllerBehaviour m_oBehaviours;
    ControllerManager m_oManager = ControllerManager.getInstance();

    @Before
    public void beforeTest()
    {
        Robolectric.setDefaultHttpResponse(200, "OK");
        m_oActivity = new Activity();
    }

    @Test
    public void testStart() throws Exception
    {
        android.app.Application loApp = Robolectric.application;
        loApp.onCreate();
        assertTrue(Application.isCreated());
        assertTrue(Application.isInitialised());
    }

    @Test
    public void testGetInstance()
    {
      assertSame(m_oManager, ControllerManager.getInstance());
    }

    @Test
    public void testHasActivity()
    {
        m_oManager.hasActivity(m_oActivity.getClass());
        assertTrue(true);
    }

    @Test
    public void testGetBehaviour()
    {
        assertNull(m_oManager.getBehaviour(IController.class));
       // loManager.addActivityBehaviour(m_oActivity,m_oBehaviours);
    }


}
