package karyon.android.behaviours;

import karyon.android.CustomRoboTestRunner;
import karyon.android.activities.SplashActivity;
import karyon.testing.KaryonTest;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 17/9/13
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(CustomRoboTestRunner.class)
public class BehaviourManagerTest
    extends KaryonTest
{
    public static class TestBehaviour
        extends Behaviour<SplashActivity>
    {

    }

    @Test
    public void testGetInstance() throws Exception
    {
        startMarker();

        assertSame(BehaviourManager.getInstance(), BehaviourManager.getInstance());
    }

    @Test
    public void testAddBehaviour() throws Exception
    {
        startMarker();

        BehaviourManager.getInstance().addBehaviour(SplashActivity.class, TestBehaviour.class);

        assertEquals(TestBehaviour.class, BehaviourManager.getInstance().getBehaviourFor(new SplashActivity()).getClass());
    }

    @Test
    public void testGetBehaviourFor() throws Exception
    {
        startMarker();

        BehaviourManager.getInstance().addBehaviour(SplashActivity.class, TestBehaviour.class);

        assertEquals(TestBehaviour.class, BehaviourManager.getInstance().getBehaviourFor(new SplashActivity()).getClass());
    }
}
