package karyon.android.activities;

import karyon.android.CustomRoboTestRunner;
import karyon.android.behaviours.Behaviour;
import karyon.testing.KaryonTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 16/9/13
 * Time: 8:46 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(CustomRoboTestRunner.class)
public class ActivityManagerTest
    extends KaryonTest
{
    @Test
    public void testGetInstance() throws Exception
    {
        startMarker();
        assertSame(ActivityManager.getInstance(), ActivityManager.getInstance());
    }

    @Test
    public void testAdd_activity() throws Exception
    {
        startMarker();
        ActivityManager.getInstance().clearActivities();

        assertNull(ActivityManager.getInstance().getCurrentActivity());

        ActivityManager.getInstance().add(new SplashActivity());

        assertSame(SplashActivity.class, ActivityManager.getInstance().getCurrentActivity().getClass());
    }

    @Test
    public void testAdd_activityclass_behaviour() throws Exception
    {
        startMarker();

        ActivityManager.getInstance().clearBehaviours();

        Behaviour<SplashActivity> loBehaviour = new Behaviour<SplashActivity>(){};

        ActivityManager.getInstance().add(SplashActivity.class, loBehaviour);

        assertSame(loBehaviour, ActivityManager.getInstance().getBehaviour(new SplashActivity()));
    }

    @Test
    public void testGetBehaviour() throws Exception
    {
        startMarker();

        ActivityManager.getInstance().clearBehaviours();

        Behaviour<SplashActivity> loBehaviour = new Behaviour<SplashActivity>(){
            @Override
            public boolean isValid(SplashActivity toController)
            {
                return false;
            }
        };

        Behaviour<SplashActivity> loBehaviour1 = new Behaviour<SplashActivity>(){};

        ActivityManager.getInstance().add(SplashActivity.class, loBehaviour);

        assertNull(ActivityManager.getInstance().getBehaviour(new SplashActivity()));

        ActivityManager.getInstance().add(SplashActivity.class, loBehaviour1);

        assertSame(loBehaviour1, ActivityManager.getInstance().getBehaviour(new SplashActivity()));
    }

    @Test
    public void testClearBehaviours() throws Exception
    {
        startMarker();

        ActivityManager.getInstance().clearBehaviours();

        Behaviour<SplashActivity> loBehaviour = new Behaviour<SplashActivity>(){
            @Override
            public boolean isValid(SplashActivity toController)
            {
                return false;
            }
        };

        Behaviour<SplashActivity> loBehaviour1 = new Behaviour<SplashActivity>(){};

        ActivityManager.getInstance().add(SplashActivity.class, loBehaviour);

        assertNull(ActivityManager.getInstance().getBehaviour(new SplashActivity()));

        ActivityManager.getInstance().add(SplashActivity.class, loBehaviour1);

        assertSame(loBehaviour1, ActivityManager.getInstance().getBehaviour(new SplashActivity()));

        ActivityManager.getInstance().clearBehaviours();

        assertNull(ActivityManager.getInstance().getBehaviour(new SplashActivity()));
    }

    @Test
    public void testRemove_class_behaviour() throws Exception
    {
        startMarker();

        ActivityManager.getInstance().clearBehaviours();

        Behaviour<SplashActivity> loBehaviour = new Behaviour<SplashActivity>(){
            @Override
            public boolean isValid(SplashActivity toController)
            {
                return false;
            }
        };

        Behaviour<SplashActivity> loBehaviour1 = new Behaviour<SplashActivity>(){};

        ActivityManager.getInstance().add(SplashActivity.class, loBehaviour);

        assertNull(ActivityManager.getInstance().getBehaviour(new SplashActivity()));

        ActivityManager.getInstance().add(SplashActivity.class, loBehaviour1);

        assertSame(loBehaviour1, ActivityManager.getInstance().getBehaviour(new SplashActivity()));

        ActivityManager.getInstance().remove(SplashActivity.class, loBehaviour1);

        assertNull(ActivityManager.getInstance().getBehaviour(new SplashActivity()));
    }

    @Test
    public void testRemove() throws Exception
    {
        startMarker();

        ActivityManager.getInstance().clearActivities();

        SplashActivity loAct1 = Robolectric.buildActivity(SplashActivity.class).get();
        ActivityImplTest.Splash2Activity loAct2 = Robolectric.buildActivity(ActivityImplTest.Splash2Activity.class).get();

        ActivityManager.getInstance().add(loAct1);

        ActivityManager.getInstance().add(loAct2);

        assertSame(loAct2, ActivityManager.getInstance().getCurrentActivity());

        ActivityManager.getInstance().remove(loAct2);

        assertSame(loAct1, ActivityManager.getInstance().getCurrentActivity());
    }

    @Test
    public void testClearActivities() throws Exception
    {
        startMarker();

        ActivityManager.getInstance().clearActivities();

        SplashActivity loAct1 = Robolectric.buildActivity(SplashActivity.class).get();
        ActivityImplTest.Splash2Activity loAct2 = Robolectric.buildActivity(ActivityImplTest.Splash2Activity.class).get();

        ActivityManager.getInstance().add(loAct1);

        ActivityManager.getInstance().add(loAct2);

        ActivityManager.getInstance().clearActivities();

        assertNull(ActivityManager.getInstance().getCurrentActivity());
    }

    @Test
    public void testGetCurrentActivity() throws Exception
    {
        startMarker();

        ActivityManager.getInstance().clearActivities();
        ActivityManager.getInstance().clearBehaviours();

        SplashActivity loAct1 = Robolectric.buildActivity(SplashActivity.class).get();
        ActivityImplTest.Splash2Activity loAct2 = Robolectric.buildActivity(ActivityImplTest.Splash2Activity.class).get();

        ActivityManager.getInstance().add(loAct1);

        ActivityManager.getInstance().add(loAct2);

        assertSame(loAct2, ActivityManager.getInstance().getCurrentActivity());

        ActivityManager.getInstance().remove(loAct2);

        assertSame(loAct1, ActivityManager.getInstance().getCurrentActivity());
    }


    @Test
    public void testHasActivity_class() throws Exception
    {
        startMarker();

        ActivityManager.getInstance().clearActivities();

        SplashActivity loAct1 = Robolectric.buildActivity(SplashActivity.class).get();
        ActivityImplTest.Splash2Activity loAct2 = Robolectric.buildActivity(ActivityImplTest.Splash2Activity.class).get();

        ActivityManager.getInstance().add(loAct1);

        ActivityManager.getInstance().add(loAct2);

        assertTrue(ActivityManager.getInstance().hasActivity(loAct1.getClass()));
        assertTrue(ActivityManager.getInstance().hasActivity(loAct2.getClass()));

        ActivityManager.getInstance().remove(loAct1);

        assertFalse(ActivityManager.getInstance().hasActivity(loAct1.getClass()));
        assertTrue(ActivityManager.getInstance().hasActivity(loAct2.getClass()));

        ActivityManager.getInstance().remove(loAct2);

        assertFalse(ActivityManager.getInstance().hasActivity(loAct1.getClass()));
        assertFalse(ActivityManager.getInstance().hasActivity(loAct2.getClass()));
    }

    @Test
    public void testHasActivity_activity() throws Exception
    {
        startMarker();

        ActivityManager.getInstance().clearActivities();

        SplashActivity loAct1 = Robolectric.buildActivity(SplashActivity.class).get();
        ActivityImplTest.Splash2Activity loAct2 = Robolectric.buildActivity(ActivityImplTest.Splash2Activity.class).get();

        ActivityManager.getInstance().add(loAct1);

        ActivityManager.getInstance().add(loAct2);

        assertTrue(ActivityManager.getInstance().hasActivity(loAct1));
        assertTrue(ActivityManager.getInstance().hasActivity(loAct2));

        ActivityManager.getInstance().remove(loAct1);

        assertFalse(ActivityManager.getInstance().hasActivity(loAct1));
        assertTrue(ActivityManager.getInstance().hasActivity(loAct2));

        ActivityManager.getInstance().remove(loAct2);

        assertFalse(ActivityManager.getInstance().hasActivity(loAct1));
        assertFalse(ActivityManager.getInstance().hasActivity(loAct2));
    }

    @Test
    public void testStart_activityclass() throws Exception
    {
        // TODO: Full lifecycle test

    }

    @Test
    public void testStart_activityclass_parent() throws Exception
    {
        // TODO: Full lifecycle test
    }

    @Test
    public void testNotify() throws Exception
    {
        // TODO: Notify test
    }
}
