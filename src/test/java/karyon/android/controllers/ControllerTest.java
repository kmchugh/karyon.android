package karyon.android.controllers;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import karyon.android.CustomRoboTestRunner;
import karyon.android.R;
import karyon.testing.KaryonTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 13/9/13
 * Time: 10:09 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(CustomRoboTestRunner.class)
public class ControllerTest
        extends KaryonTest
{
    public static class TestController
            extends Controller<TestController>
    {
        private String m_cValue;
        private boolean m_lIsCreated;
        private boolean m_lIsViewCreated;
        private boolean m_lIsCreating;

        @Override
        public int getPortraitViewResourceID()
        {
            return R.layout.splash;
        }

        @Override
        public int getLandscapeViewResourceID()
        {
            return R.layout.error;
        }

        public String getStringValue()
        {
            return m_cValue;
        }

        public boolean isCreated()
        {
            return m_lIsCreated;
        }

        public boolean isViewCreated()
        {
            return m_lIsViewCreated;
        }

        public boolean isCreating()
        {
            return m_lIsCreating;
        }

        @Override
        public boolean onInit(Bundle toArgs)
        {
            boolean llReturn = super.onInit(toArgs);
            if (llReturn && toArgs != null)
            {
                m_cValue = toArgs.getString("Test");
            }
            return llReturn;
        }


        @Override
        protected void onCreating(Bundle toSavedInstanceState)
        {
            m_lIsCreating = true;
            super.onCreating(toSavedInstanceState);

            if (toSavedInstanceState != null)
            {
                m_cValue = toSavedInstanceState.getString("Test");
            }
        }

        @Override
        protected void onContentReady(Bundle toSavedInstanceState)
        {
            m_lIsCreated = true;
            super.onCreating(toSavedInstanceState);

            if (toSavedInstanceState != null)
            {
                m_cValue = "Created: " + toSavedInstanceState.getString("Test");
            }
        }

        @Override
        public void onViewCreated(View toView, Bundle toBundle)
        {
            m_lIsViewCreated = true;
            super.onViewCreated(toView, toBundle);

            if (toBundle != null)
            {
                m_cValue = toBundle.getString("Test");
            }
        }
    }

    @Test
    public void testInstantiate_context_class() throws Exception
    {
        startMarker();
        TestController loController = Controller.instantiate(TestController.class);
        assertNotNull(loController);
        loController.onCreate(null);
        assertEquals(TestController.class, loController.getClass());
        assertNull(loController.getStringValue());
    }

    @Test
    public void testInstantiate_context_class_args() throws Exception
    {
        startMarker();
        Bundle loBundle = new Bundle();
        loBundle.putString("Test", "Test String Value");

        TestController loController = Controller.instantiate(TestController.class, loBundle);
        assertNotNull(loController);
        loController.onCreate(null);
        assertEquals(TestController.class, loController.getClass());
        assertEquals("Test String Value", loController.getStringValue());
    }

    @Test
    public void testGetSelf() throws Exception
    {
        startMarker();
        TestController loController = Controller.instantiate(TestController.class);
        assertSame(loController, loController.getSelf());
    }


    @Test
    public void testOnCreate() throws Exception
    {
        startMarker();
        TestController loController = Controller.instantiate(TestController.class);
        assertFalse(loController.isCreated());
        loController.onCreate(null);
        assertTrue(loController.isCreating());
        assertNull(loController.getStringValue());

        Bundle loBundle = new Bundle();
        loBundle.putString("Test", "Test String Value");

        loController = Controller.instantiate(TestController.class);
        assertFalse(loController.isCreated());
        loController.onCreate(loBundle);
        assertTrue(loController.isCreating());
        assertEquals("Test String Value", loController.getStringValue());
    }

    @Test
    public void testOnCreated() throws Exception
    {
        startMarker();
        TestController loController = Controller.instantiate(TestController.class);
        assertFalse(loController.isCreated());
        loController.onCreate(null);
        loController.onActivityCreated(null);
        assertTrue(loController.isCreated());
        assertNull(loController.getStringValue());

        Bundle loBundle = new Bundle();
        loBundle.putString("Test", "Test String Value");

        loController = Controller.instantiate(TestController.class);
        assertFalse(loController.isCreated());
        loController.onCreate(loBundle);
        loController.onActivityCreated(loBundle);
        assertTrue(loController.isCreated());
        assertEquals("Created: Test String Value", loController.getStringValue());
    }


    @Test
    public void testOnCreateView() throws Exception
    {
        startMarker();
        ActivityController<FragmentActivity> loActivity = Robolectric.buildActivity(FragmentActivity.class);
        TestController loController = Controller.instantiate(TestController.class);

        loActivity.create();
        assertFalse(loController.isViewCreated());
        loActivity.get().getSupportFragmentManager().beginTransaction().add(loController, "FRAGMENT").commit();
        loActivity.start();

        assertTrue(loController.isViewCreated());

        assertNull(loController.getStringValue());
        loActivity.destroy();

        Bundle loBundle = new Bundle();
        loBundle.putString("Test", "Test String Value");

        loActivity = Robolectric.buildActivity(FragmentActivity.class);
        loController = Controller.instantiate(TestController.class, loBundle);

        assertFalse(loController.isViewCreated());

        loActivity.create();
        loActivity.get().getSupportFragmentManager().beginTransaction().add(loController, "FRAGMENT").commit();
        loActivity.start();

        assertTrue(loController.isViewCreated());
        assertEquals("Test String Value", loController.getStringValue());
        loActivity.destroy();
    }

    @Test
    public void testOnViewCreated() throws Exception
    {
        startMarker();
        ActivityController<FragmentActivity> loActivity = Robolectric.buildActivity(FragmentActivity.class);
        TestController loController = Controller.instantiate(TestController.class);

        assertFalse(loController.isCreated());

        loActivity.create();
        loActivity.get().getSupportFragmentManager().beginTransaction().add(loController, "FRAGMENT").commit();
        loActivity.start();

        assertTrue(loController.isCreated());

        assertNull(loController.getStringValue());
        loActivity.destroy();

        Bundle loBundle = new Bundle();
        loBundle.putString("Test", "Test String Value");

        loActivity = Robolectric.buildActivity(FragmentActivity.class);
        loController = Controller.instantiate(TestController.class, loBundle);

        assertFalse(loController.isViewCreated());

        loActivity.create();
        loActivity.get().getSupportFragmentManager().beginTransaction().add(loController, "FRAGMENT").commit();
        loActivity.start();

        assertTrue(loController.isViewCreated());
        assertEquals("Test String Value", loController.getStringValue());
        loActivity.destroy();
    }

    @Test
    public void testGetContentViewID_landscape() throws Exception
    {
        startMarker();
        ActivityController<FragmentActivity> loActivity = Robolectric.buildActivity(FragmentActivity.class);
        TestController loController = Controller.instantiate(TestController.class);
        loActivity.get().getResources().getConfiguration().orientation = Configuration.ORIENTATION_LANDSCAPE;
        loActivity.create();
        loActivity.get().getSupportFragmentManager().beginTransaction().add(loController, "FRAGMENT").commit();
        assertEquals(loController.getLandscapeViewResourceID(), loController.getContentViewID());
    }

    @Test
    public void testGetContentViewID_portrait() throws Exception
    {
        startMarker();
        ActivityController<FragmentActivity> loActivity = Robolectric.buildActivity(FragmentActivity.class);
        TestController loController = Controller.instantiate(TestController.class);
        loActivity.get().getResources().getConfiguration().orientation = Configuration.ORIENTATION_PORTRAIT;
        loActivity.create();

        loActivity.get().getSupportFragmentManager().beginTransaction().add(loController, "FRAGMENT").commit();
        assertEquals(loController.getPortraitViewResourceID(), loController.getContentViewID());
    }

    @Test
    public void testGetPortraitViewResourceID() throws Exception
    {
        startMarker();
        TestController loController = Controller.instantiate(TestController.class);
        assertEquals(R.layout.splash, loController.getPortraitViewResourceID());
    }

    @Test
    public void testGetLandscapeViewResourceID() throws Exception
    {
        startMarker();
        TestController loController = Controller.instantiate(TestController.class);
        assertEquals(R.layout.error, loController.getLandscapeViewResourceID());
    }


    @Test
    public void testFindViewById() throws Exception
    {
        startMarker();
        ActivityController<FragmentActivity> loActivity = Robolectric.buildActivity(FragmentActivity.class);
        TestController loController = Controller.instantiate(TestController.class);
        loActivity.get().getResources().getConfiguration().orientation = Configuration.ORIENTATION_PORTRAIT;

        loActivity.create();
        loActivity.start();
        loActivity.get().getSupportFragmentManager().beginTransaction().add(loController, "FRAGMENT").commit();

        assertNotNull(loController.findViewById(R.id.splash_version));
        assertNull(loController.findViewById(R.id.error_title));
    }

    private boolean m_lComplete;
    private Thread m_oThread;
    @Test
    public void testRunOnUiThread() throws Exception
    {
        startMarker();

        final Runnable loTest = new Runnable()
        {
            @Override
            public void run()
            {
                m_lComplete = true;
                assertNotEquals(m_oThread, Thread.currentThread());
            }
        };

        Runnable loAltThread = new Runnable()
        {
            @Override
            public void run()
            {
                m_oThread = Thread.currentThread();
                try
                {
                    ActivityController<FragmentActivity> loActivity = Robolectric.buildActivity(FragmentActivity.class);
                    TestController loController = Controller.instantiate(TestController.class);
                    loActivity.get().getResources().getConfiguration().orientation = Configuration.ORIENTATION_PORTRAIT;

                    loActivity.create();
                    loActivity.start();
                    loActivity.get().getSupportFragmentManager().beginTransaction().add(loController, "FRAGMENT").commit();

                    loController.runOnUiThread(loTest);
                }
                catch (Exception ex)
                {}
            }
        };

        Thread loThread = new Thread(loAltThread, "Non UI Thread");
        loThread.start();

        int lnTime = 0;
        while (!m_lComplete && lnTime < 1000)
        {
            lnTime+=100;
            Thread.sleep(100);
        }

    }

    @Test
    public void testIsFinishing() throws Exception
    {
        ActivityController<FragmentActivity> loActivity = Robolectric.buildActivity(FragmentActivity.class);
        TestController loController = Controller.instantiate(TestController.class);
        loActivity.get().getResources().getConfiguration().orientation = Configuration.ORIENTATION_PORTRAIT;

        loActivity.create();
        loActivity.start();
        loActivity.get().getSupportFragmentManager().beginTransaction().add(loController, "FRAGMENT").commit();

        assertFalse(loController.isFinishing());

        loActivity.destroy();

        assertTrue(loController.isFinishing());
    }

    @Test
    public void testFinish() throws Exception
    {
        ActivityController<FragmentActivity> loActivity = Robolectric.buildActivity(FragmentActivity.class);
        TestController loController = Controller.instantiate(TestController.class);
        loActivity.get().getResources().getConfiguration().orientation = Configuration.ORIENTATION_PORTRAIT;

        loActivity.create();
        loActivity.start();
        loActivity.get().getSupportFragmentManager().beginTransaction().add(loController, "FRAGMENT").commit();

        assertFalse(loController.isFinishing());

        loController.finish();

        assertTrue(loController.isFinishing());
    }

    @Test
    public void testGetContext() throws Exception
    {
        ActivityController<FragmentActivity> loActivity = Robolectric.buildActivity(FragmentActivity.class);
        TestController loController = Controller.instantiate(TestController.class);
        loActivity.get().getResources().getConfiguration().orientation = Configuration.ORIENTATION_PORTRAIT;

        loActivity.create();
        loActivity.start();
        loActivity.get().getSupportFragmentManager().beginTransaction().add(loController, "FRAGMENT").commit();

        assertSame(loController.getView().getContext(), loController.getContext());
    }

    @Test
    public void testGetWindow() throws Exception
    {
        ActivityController<FragmentActivity> loActivity = Robolectric.buildActivity(FragmentActivity.class);
        TestController loController = Controller.instantiate(TestController.class);
        loActivity.get().getResources().getConfiguration().orientation = Configuration.ORIENTATION_PORTRAIT;

        loActivity.create();
        loActivity.start();
        loActivity.get().getSupportFragmentManager().beginTransaction().add(loController, "FRAGMENT").commit();

        assertSame(loActivity.get().getWindow(), loController.getWindow());
    }

    @Test
    public void testOnPause() throws Exception
    {
        ActivityController<FragmentActivity> loActivity = Robolectric.buildActivity(FragmentActivity.class);
        TestController loController = Controller.instantiate(TestController.class);
        loActivity.get().getResources().getConfiguration().orientation = Configuration.ORIENTATION_PORTRAIT;

        loActivity.create();
        loActivity.start();
        loActivity.get().getSupportFragmentManager().beginTransaction().add(loController, "FRAGMENT").commit();
        loActivity.pause();

        loController.onPause();

        assertTrue(loController.isPaused());
    }

    @Test
    public void testIsPaused() throws Exception
    {
        ActivityController<FragmentActivity> loActivity = Robolectric.buildActivity(FragmentActivity.class);
        TestController loController = Controller.instantiate(TestController.class);
        loActivity.get().getResources().getConfiguration().orientation = Configuration.ORIENTATION_PORTRAIT;

        loActivity.create();
        loActivity.start();
        loActivity.get().getSupportFragmentManager().beginTransaction().add(loController, "FRAGMENT").commit();
        loActivity.pause();

        loController.onPause();

        assertTrue(loController.isPaused());
    }

    @Test
    public void testOnResume() throws Exception
    {
        ActivityController<FragmentActivity> loActivity = Robolectric.buildActivity(FragmentActivity.class);
        TestController loController = Controller.instantiate(TestController.class);
        loActivity.get().getResources().getConfiguration().orientation = Configuration.ORIENTATION_PORTRAIT;

        loActivity.create();
        loActivity.start();
        loActivity.get().getSupportFragmentManager().beginTransaction().add(loController, "FRAGMENT").commit();
        loActivity.pause();

        loController.onPause();

        assertTrue(loController.isPaused());

        loActivity.resume();

        assertFalse(loController.isPaused());
    }

}
