package karyon.android.behaviours;

import karyon.android.activities.SplashActivity;
import karyon.testing.KaryonTest;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 17/9/13
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class TimedBehaviourTest
    extends KaryonTest
{
    public static class TestTimedBehaviour
        extends TimedBehaviour<SplashActivity>
    {
        private int m_nTicks;
        private boolean m_lEvent;

        public TestTimedBehaviour(long tnTick)
        {
            super(tnTick);
        }

        public TestTimedBehaviour(long tnMillis, long tnTick)
        {
            super(tnMillis, tnTick);
        }

        @Override
        public long tick(SplashActivity toActivity, long tnRemaining)
        {
            m_nTicks++;
            return tnRemaining;
        }

        @Override
        public void timerEvent(SplashActivity toActivity)
        {
            m_lEvent = true;
        }
    }

    @Test
    public void testTick() throws Exception
    {
        startMarker();
        TestTimedBehaviour loBehaviour = new TestTimedBehaviour(100L);

        loBehaviour.onInit(null);

        Thread.sleep(500);

        assertTrue(loBehaviour.m_nTicks > 3);
    }

    @Test
    public void testTimerEvent() throws Exception
    {
        startMarker();

        startMarker();
        TestTimedBehaviour loBehaviour = new TestTimedBehaviour(400L, 100L);

        loBehaviour.onInit(null);

        Thread.sleep(500);

        assertTrue(loBehaviour.m_nTicks > 3);
        assertTrue(loBehaviour.m_lEvent);
    }

    @Test
    public void testOnStop() throws Exception
    {
        startMarker();

        startMarker();
        TestTimedBehaviour loBehaviour = new TestTimedBehaviour(400L, 100L);

        loBehaviour.onInit(null);

        Thread.sleep(500);

        loBehaviour.onFinish(null);
        long lnTicks = loBehaviour.m_nTicks;

        Thread.sleep(500);

        assertEquals(lnTicks, loBehaviour.m_nTicks);
    }

    @Test
    public void testOnFinish() throws Exception
    {
        startMarker();

        startMarker();
        TestTimedBehaviour loBehaviour = new TestTimedBehaviour(1000L, 100L);

        loBehaviour.onInit(null);

        Thread.sleep(500);

        loBehaviour.onFinish(null);
        long lnTicks = loBehaviour.m_nTicks;

        Thread.sleep(500);

        assertEquals(lnTicks, loBehaviour.m_nTicks);
    }

    @Test
    public void testOnInit() throws Exception
    {
        startMarker();

        startMarker();
        TestTimedBehaviour loBehaviour = new TestTimedBehaviour(1000L, 100L);

        loBehaviour.onInit(null);

        Thread.sleep(500);

        loBehaviour.onFinish(null);
        long lnTicks = loBehaviour.m_nTicks;

        loBehaviour.onResume(null);

        Thread.sleep(500);

        assertTrue(lnTicks < loBehaviour.m_nTicks);
    }

    @Test
    public void testOnResume() throws Exception
    {
        startMarker();

        startMarker();
        TestTimedBehaviour loBehaviour = new TestTimedBehaviour(1000L, 100L);

        loBehaviour.onInit(null);

        Thread.sleep(500);

        loBehaviour.onFinish(null);
        long lnTicks = loBehaviour.m_nTicks;

        Thread.sleep(500);

        assertEquals(lnTicks, loBehaviour.m_nTicks);

        loBehaviour.onResume(null);
    }
}
