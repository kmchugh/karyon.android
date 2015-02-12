package karyon.android.behaviours;

import karyon.android.CustomRoboTestRunner;
import karyon.android.activities.NotificationType;
import karyon.android.activities.SplashActivity;
import karyon.testing.KaryonTest;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 17/9/13
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(CustomRoboTestRunner.class)
public class BehaviourTest
    extends KaryonTest
{
    public static class TestBehaviour
        extends Behaviour<SplashActivity>
    {
        private boolean m_lFinish;
        private boolean m_lInit;
        private boolean m_lContentReady;
        private boolean m_lDestroy;
        private boolean m_lLowMemory;
        private boolean m_lPause;
        private boolean m_lRestart;
        private boolean m_lResume;
        private boolean m_lStart;
        private boolean m_lStop;
        private boolean m_lUnknown;

        @Override
        public boolean onFinish(SplashActivity toActivity)
        {
            m_lFinish = true;
            return m_lFinish;
        }

        @Override
        public boolean onInit(SplashActivity toActivity)
        {
            m_lInit = true;
            return m_lInit;
        }

        @Override
        public boolean onContentReady(SplashActivity toActivity)
        {
            m_lContentReady = true;
            return m_lContentReady;
        }

        @Override
        public boolean onDestroy(SplashActivity toActivity)
        {
            m_lDestroy = true;
            return m_lDestroy;
        }

        @Override
        public boolean onLowMemory(SplashActivity toActivity)
        {
            m_lLowMemory = true;
            return m_lLowMemory;
        }

        @Override
        public boolean onPause(SplashActivity toActivity)
        {
            m_lPause = true;
            return m_lPause;
        }

        @Override
        public boolean onRestart(SplashActivity toActivity)
        {
            m_lRestart = true;
            return m_lRestart;
        }

        @Override
        public boolean onResume(SplashActivity toActivity)
        {
            m_lResume = true;
            return m_lResume;
        }

        @Override
        public boolean onStart(SplashActivity toActivity)
        {
            m_lStart = true;
            return m_lStart;
        }

        @Override
        public boolean onStop(SplashActivity toActivity)
        {
            m_lStop = true;
            return m_lStop;
        }

        @Override
        public boolean onUnknownNotification(NotificationType toType, SplashActivity toActivity)
        {
            m_lUnknown = true;
            return m_lUnknown;
        }
    }

    @Test
    public void testIsValid() throws Exception
    {
        startMarker();
        Behaviour<SplashActivity> loBehaviour = new TestBehaviour();
        assertTrue(loBehaviour.isValid(new SplashActivity()));
    }

    @Test
    public void testCanShow() throws Exception
    {
        startMarker();
        Behaviour<SplashActivity> loBehaviour = new TestBehaviour();
        assertTrue(loBehaviour.canShow(new SplashActivity()));
    }

    @Test
    public void testNotify() throws Exception
    {
        startMarker();
        TestBehaviour loBehaviour = new TestBehaviour();

        assertFalse(loBehaviour.m_lUnknown);
        loBehaviour.notify(new NotificationType(), new SplashActivity());
        assertTrue(loBehaviour.m_lUnknown);
    }

    @Test
    public void testOnFinish() throws Exception
    {
        startMarker();
        TestBehaviour loBehaviour = new TestBehaviour();

        assertFalse(loBehaviour.m_lFinish);
        loBehaviour.notify(NotificationType.FINISH, new SplashActivity());
        assertTrue(loBehaviour.m_lFinish);
    }

    @Test
    public void testOnInit() throws Exception
    {
        startMarker();
        TestBehaviour loBehaviour = new TestBehaviour();

        assertFalse(loBehaviour.m_lInit);
        loBehaviour.notify(NotificationType.INIT, new SplashActivity());
        assertTrue(loBehaviour.m_lInit);
    }

    @Test
    public void testOnContentReady() throws Exception
    {
        startMarker();
        TestBehaviour loBehaviour = new TestBehaviour();

        assertFalse(loBehaviour.m_lContentReady);
        loBehaviour.notify(NotificationType.CONTENT_READY, new SplashActivity());
        assertTrue(loBehaviour.m_lContentReady);
    }

    @Test
    public void testOnDestroy() throws Exception
    {
        startMarker();
        TestBehaviour loBehaviour = new TestBehaviour();

        assertFalse(loBehaviour.m_lDestroy);
        loBehaviour.notify(NotificationType.DESTROY, new SplashActivity());
        assertTrue(loBehaviour.m_lDestroy);
    }

    @Test
    public void testOnLowMemory() throws Exception
    {
        startMarker();
        TestBehaviour loBehaviour = new TestBehaviour();

        assertFalse(loBehaviour.m_lLowMemory);
        loBehaviour.notify(NotificationType.LOW_MEMORY, new SplashActivity());
        assertTrue(loBehaviour.m_lLowMemory);
    }

    @Test
    public void testOnPause() throws Exception
    {
        startMarker();
        TestBehaviour loBehaviour = new TestBehaviour();

        assertFalse(loBehaviour.m_lPause);
        loBehaviour.notify(NotificationType.PAUSE, new SplashActivity());
        assertTrue(loBehaviour.m_lPause);
    }

    @Test
    public void testOnRestart() throws Exception
    {
        startMarker();
        TestBehaviour loBehaviour = new TestBehaviour();

        assertFalse(loBehaviour.m_lRestart);
        loBehaviour.notify(NotificationType.RESTART, new SplashActivity());
        assertTrue(loBehaviour.m_lRestart);
    }

    @Test
    public void testOnResume() throws Exception
    {
        startMarker();
        TestBehaviour loBehaviour = new TestBehaviour();

        assertFalse(loBehaviour.m_lResume);
        loBehaviour.notify(NotificationType.RESUME, new SplashActivity());
        assertTrue(loBehaviour.m_lResume);
    }

    @Test
    public void testOnStart() throws Exception
    {
        startMarker();
        TestBehaviour loBehaviour = new TestBehaviour();

        assertFalse(loBehaviour.m_lStart);
        loBehaviour.notify(NotificationType.START, new SplashActivity());
        assertTrue(loBehaviour.m_lStart);
    }

    @Test
    public void testOnStop() throws Exception
    {
        startMarker();
        TestBehaviour loBehaviour = new TestBehaviour();

        assertFalse(loBehaviour.m_lStop);
        loBehaviour.notify(NotificationType.STOP, new SplashActivity());
        assertTrue(loBehaviour.m_lStop);
    }

    @Test
    public void testOnUnknownNotification() throws Exception
    {
        startMarker();
        TestBehaviour loBehaviour = new TestBehaviour();

        assertFalse(loBehaviour.m_lUnknown);
        loBehaviour.notify(new NotificationType(), new SplashActivity());
        assertTrue(loBehaviour.m_lUnknown);
    }
}
