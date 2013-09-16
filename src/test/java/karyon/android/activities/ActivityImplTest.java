package karyon.android.activities;

import android.os.Bundle;
import karyon.android.CustomRoboTestRunner;
import karyon.dynamicCode.Java;
import karyon.testing.KaryonTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 16/9/13
 * Time: 7:24 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(CustomRoboTestRunner.class)
public class ActivityImplTest
    extends KaryonTest
{
    public static class TestImpl
        extends ActivityImpl<SplashActivity>
    {
        private boolean m_lInitialised;
        private boolean m_lIsRestarted;
        private boolean m_lIsStarted;
        private boolean m_lIsResumed;
        private boolean m_lIsPaused;
        private boolean m_lIsStopped;
        private boolean m_lIsDestroyed;
        private boolean m_lLowMemory;
        private boolean m_lContentViewUpdated;
        private boolean m_lContentChanged;
        private boolean m_lIsUpdated;

        public boolean isUpdated()
        {
            return m_lIsUpdated;
        }

        private boolean isContentChanged()
        {
            return m_lContentChanged;
        }

        public boolean isContentViewUpdated()
        {
            return m_lContentViewUpdated;
        }

        public boolean isInitialised()
        {
            return m_lInitialised;
        }

        public boolean isRestarted()
        {
            return m_lIsRestarted;
        }

        public boolean isStarted()
        {
            return m_lIsStarted;
        }

        public boolean isResumed()
        {
            return m_lIsResumed;
        }

        public boolean hasPaused()
        {
            return m_lIsPaused;
        }

        public boolean isStopped()
        {
            return m_lIsStopped;
        }

        public boolean isDestroyed()
        {
            return m_lIsDestroyed;
        }

        private boolean isLowMemory()
        {
            return m_lLowMemory;
        }

        @Override
        public void onCreate(Bundle toSavedState)
        {
            m_lInitialised = true;
            super.onCreate(toSavedState);
        }

        @Override
        public void onRestart()
        {
            m_lIsRestarted = true;
            super.onRestart();
        }

        @Override
        public void onStart()
        {
            m_lIsStarted = true;
            super.onStart();
        }

        @Override
        public void onResume()
        {
            m_lIsResumed = true;
            super.onResume();
        }

        @Override
        public void onPause()
        {
            m_lIsPaused = true;
            super.onPause();
        }

        @Override
        public void onStop()
        {
            m_lIsStopped = true;
            super.onStop();
        }

        @Override
        public void onLowMemory()
        {
            m_lLowMemory = true;
            super.onLowMemory();
        }

        @Override
        public void onDestroy()
        {
            m_lIsDestroyed = true;
            super.onDestroy();
        }

        @Override
        public void updateContentView()
        {
            m_lContentViewUpdated = true;
            super.updateContentView();
        }

        @Override
        public void onContentChanged()
        {
            m_lContentChanged = true;
            super.onContentChanged();
        }
    }

    public static class Splash2Activity
        extends SplashActivity
    {
        private boolean m_lUIUpdated;

        public boolean isUIUpdated()
        {
            return m_lUIUpdated;
        }

        @Override
        public void onUpdateUI()
        {
            m_lUIUpdated = true;
            super.onUpdateUI();
        }

        public Splash2Activity()
        {
            super(new TestImpl());
            getImpl().setActivity(this);


        }
    }

    @Test
    public void testConstructor() throws Exception
    {
        startMarker();

        ActivityImpl<SplashActivity> loActivity = new ActivityImpl<SplashActivity>();
        assertNull(loActivity.getActivity());
    }

    @Test
    public void testConstructor_activity() throws Exception
    {
        startMarker();

        ActivityImpl<SplashActivity> loActivity = new ActivityImpl<SplashActivity>(new SplashActivity());
        assertNotNull(loActivity.getActivity());
    }

    @Test
    public void testGetActivity() throws Exception
    {
        startMarker();

        ActivityImpl<SplashActivity> loActivity = new ActivityImpl<SplashActivity>();
        assertNull(loActivity.getActivity());

        SplashActivity loSplash = new SplashActivity();

        loActivity.setActivity(loSplash);

        assertSame(loSplash, loActivity.getActivity());
    }




    @Test
    public void testGetContentViewID() throws Exception
    {
        startMarker();

        ActivityImpl<SplashActivity> loActivity = new ActivityImpl<SplashActivity>(new SplashActivity());
        assertEquals(loActivity.getContentViewID(), loActivity.getActivity().getContentViewID());
    }



    @Test
    public void testSetActivity() throws Exception
    {
        startMarker();

        ActivityImpl<SplashActivity> loActivity = new ActivityImpl<SplashActivity>();
        assertNull(loActivity.getActivity());

        SplashActivity loSplash = new SplashActivity();

        loActivity.setActivity(loSplash);

        assertSame(loSplash, loActivity.getActivity());
    }

    @Test
    public void testOnCreate() throws Exception
    {
        startMarker();

        ActivityController<Splash2Activity> loActivityCon = Robolectric.buildActivity(Splash2Activity.class);
        Splash2Activity loActivity = loActivityCon.get();
        TestImpl loImpl = (TestImpl)loActivity.getImpl();


        assertFalse(loImpl.isInitialised());

        loActivityCon.create();

        assertTrue(loImpl.isInitialised());


        // TODO: Test with no title
        // TODO: Test with custom title

        // TODO: Test with saved state bundle
    }

    @Test
    public void testOnRestart() throws Exception
    {
        startMarker();

        ActivityController<Splash2Activity> loActivityCon = Robolectric.buildActivity(Splash2Activity.class);
        Splash2Activity loActivity = loActivityCon.get();
        TestImpl loImpl = (TestImpl)loActivity.getImpl();


        assertFalse(loImpl.isRestarted());

        loActivityCon.create();
        loActivityCon.start();
        loActivityCon.pause();
        loActivityCon.restart();

        // robolectric doesn't seem to call restart, so we do it ourselves
        Method loMethod = Java.getMethod(Splash2Activity.class, "onRestart", null);
        if (loMethod != null)
        {
            loMethod.invoke(loActivity);
        }

        assertTrue(loImpl.isRestarted());

    }

    @Test
    public void testOnStart() throws Exception
    {
        startMarker();

        ActivityController<Splash2Activity> loActivityCon = Robolectric.buildActivity(Splash2Activity.class);
        Splash2Activity loActivity = loActivityCon.get();
        TestImpl loImpl = (TestImpl)loActivity.getImpl();


        assertFalse(loImpl.isStarted());

        loActivityCon.create();
        loActivityCon.start();

        assertTrue(loImpl.isStarted());

    }

    @Test
    public void testOnResume() throws Exception
    {
        startMarker();

        ActivityController<Splash2Activity> loActivityCon = Robolectric.buildActivity(Splash2Activity.class);
        Splash2Activity loActivity = loActivityCon.get();
        TestImpl loImpl = (TestImpl)loActivity.getImpl();


        assertFalse(loImpl.isResumed());

        loActivityCon.create();
        loActivityCon.start();
        loActivityCon.pause();
        loActivityCon.resume();

        assertTrue(loImpl.isResumed());

    }

    @Test
    public void testOnPause() throws Exception
    {
        startMarker();

        ActivityController<Splash2Activity> loActivityCon = Robolectric.buildActivity(Splash2Activity.class);
        Splash2Activity loActivity = loActivityCon.get();
        TestImpl loImpl = (TestImpl)loActivity.getImpl();


        assertFalse(loImpl.hasPaused());

        loActivityCon.create();
        loActivityCon.start();
        loActivityCon.pause();

        assertTrue(loImpl.hasPaused());

    }

    @Test
    public void testOnStop() throws Exception
    {
        startMarker();

        ActivityController<Splash2Activity> loActivityCon = Robolectric.buildActivity(Splash2Activity.class);
        Splash2Activity loActivity = loActivityCon.get();
        TestImpl loImpl = (TestImpl)loActivity.getImpl();


        assertFalse(loImpl.isStopped());

        loActivityCon.create();
        loActivityCon.start();
        loActivityCon.stop();

        assertTrue(loImpl.isStopped());

    }

    @Test
    public void testOnLowMemory() throws Exception
    {
        startMarker();

        ActivityController<Splash2Activity> loActivityCon = Robolectric.buildActivity(Splash2Activity.class);
        Splash2Activity loActivity = loActivityCon.get();
        TestImpl loImpl = (TestImpl)loActivity.getImpl();


        assertFalse(loImpl.isLowMemory());

        loActivityCon.create();
        loActivityCon.start();

        Method loMethod = Java.getMethod(Splash2Activity.class, "onLowMemory", null);
        if (loMethod != null)
        {
            loMethod.invoke(loActivity);
        }

        assertTrue(loImpl.isLowMemory());
    }

    @Test
    public void testOnDestroy() throws Exception
    {
        startMarker();

        ActivityController<Splash2Activity> loActivityCon = Robolectric.buildActivity(Splash2Activity.class);
        Splash2Activity loActivity = loActivityCon.get();
        TestImpl loImpl = (TestImpl)loActivity.getImpl();


        assertFalse(loImpl.isDestroyed());

        loActivityCon.create();
        loActivityCon.start();
        loActivityCon.stop();
        loActivityCon.destroy();

        assertTrue(loImpl.isDestroyed());

    }

    @Test
    public void testUpdateContentView() throws Exception
    {
        startMarker();

        ActivityController<Splash2Activity> loActivityCon = Robolectric.buildActivity(Splash2Activity.class);
        Splash2Activity loActivity = loActivityCon.get();
        TestImpl loImpl = (TestImpl)loActivity.getImpl();


        assertFalse(loImpl.isContentViewUpdated());

        loActivityCon.create();

        assertTrue(loImpl.isContentViewUpdated());
    }

    @Test
    public void testFinish() throws Exception
    {
        startMarker();

        startMarker();

        ActivityController<Splash2Activity> loActivityCon = Robolectric.buildActivity(Splash2Activity.class);
        Splash2Activity loActivity = loActivityCon.get();
        TestImpl loImpl = (TestImpl)loActivity.getImpl();


        assertFalse(loImpl.isFinishing());

        loActivityCon.create();
        loActivityCon.start();

        loImpl.finish();

        assertTrue(loImpl.isFinishing());
        assertTrue(loActivity.isFinishing());

    }

    @Test
    public void testIsFinishing() throws Exception
    {
        startMarker();

        startMarker();

        ActivityController<Splash2Activity> loActivityCon = Robolectric.buildActivity(Splash2Activity.class);
        Splash2Activity loActivity = loActivityCon.get();
        TestImpl loImpl = (TestImpl)loActivity.getImpl();


        assertFalse(loImpl.isFinishing());

        loActivityCon.create();
        loActivityCon.start();

        loImpl.finish();

        assertTrue(loImpl.isFinishing());
        assertTrue(loActivity.isFinishing());
    }

    @Test
    public void testIsPaused() throws Exception
    {
        startMarker();

        ActivityController<Splash2Activity> loActivityCon = Robolectric.buildActivity(Splash2Activity.class);
        Splash2Activity loActivity = loActivityCon.get();
        TestImpl loImpl = (TestImpl)loActivity.getImpl();


        assertFalse(loImpl.isPaused());

        loActivityCon.create();
        loActivityCon.start();
        loActivityCon.pause();

        assertTrue(loImpl.isPaused());

        loActivityCon.resume();

        assertFalse(loImpl.isPaused());
    }

    @Test
    public void testIsVisible() throws Exception
    {
        startMarker();

        ActivityController<Splash2Activity> loActivityCon = Robolectric.buildActivity(Splash2Activity.class);
        Splash2Activity loActivity = loActivityCon.get();
        TestImpl loImpl = (TestImpl)loActivity.getImpl();


        assertFalse(loImpl.isVisible());

        loActivityCon.create();

        assertFalse(loImpl.isVisible());

        loActivityCon.start();

        assertTrue(loImpl.isVisible());

        loActivityCon.stop();

        assertFalse(loImpl.isPaused());

    }

    @Test
    public void testOnContentChanged() throws Exception
    {
        startMarker();

        ActivityController<Splash2Activity> loActivityCon = Robolectric.buildActivity(Splash2Activity.class);
        Splash2Activity loActivity = loActivityCon.get();
        TestImpl loImpl = (TestImpl)loActivity.getImpl();


        assertFalse(loImpl.isContentChanged());

        loActivityCon.create();

        assertTrue(loImpl.isContentChanged());
    }

    @Test
    public void testUpdateUI() throws Exception
    {
        startMarker();

        ActivityController<Splash2Activity> loActivityCon = Robolectric.buildActivity(Splash2Activity.class);
        Splash2Activity loActivity = loActivityCon.get();
        TestImpl loImpl = (TestImpl)loActivity.getImpl();


        assertFalse(loActivity.isUIUpdated());

        loActivityCon.create();
        loActivityCon.start();

        assertFalse(loActivity.isUIUpdated());

        loActivityCon.pause();

        loActivity.invalidate();

        assertFalse(loActivity.isUIUpdated());

        loActivityCon.resume();

        loActivity.invalidate();

        assertTrue(loActivity.isUIUpdated());
    }

}
