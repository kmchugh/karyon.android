package karyon.android.controllers;

import android.content.pm.ActivityInfo;
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
import org.robolectric.annotation.Config;
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
        protected void onCreated(Bundle toSavedInstanceState)
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
        TestController loController = Controller.instantiate(TestController.class);
        assertFalse(loController.isCreated());
        loController.onCreate(null);
        loController.onCreateView(null, null, null);
        assertNull(loController.getStringValue());

        Bundle loBundle = new Bundle();
        loBundle.putString("Test", "Test String Value");

        loController = Controller.instantiate(TestController.class);
        assertFalse(loController.isCreated());
        loController.onCreate(loBundle);
        assertEquals("Test String Value", loController.getStringValue());
    }

    @Test
    public void testOnViewCreated() throws Exception
    {
        startMarker();
        TestController loController = Controller.instantiate(TestController.class);
        assertFalse(loController.isCreated());
        loController.onCreate(null);
        loController.onViewCreated(loController.onCreateView(null, null, null), null);
        assertTrue(loController.isViewCreated());
        assertNull(loController.getStringValue());

        Bundle loBundle = new Bundle();
        loBundle.putString("Test", "Test String Value");

        loController = Controller.instantiate(TestController.class);
        assertFalse(loController.isCreated());
        loController.onCreate(null);
        loController.onViewCreated(loController.onCreateView(null, null, null), loBundle);
        assertTrue(loController.isViewCreated());
        assertEquals("Test String Value", loController.getStringValue());
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
        TestController loController = Controller.instantiate(TestController.class);

    }

    @Test
    public void testInvalidate() throws Exception
    {

    }

    @Test
    public void testUpdateUI() throws Exception
    {

    }

    @Test
    public void testRunOnUiThread() throws Exception
    {

    }

    @Test
    public void testIsFinishing() throws Exception
    {

    }

    @Test
    public void testOnUpdateUI() throws Exception
    {

    }

    @Test
    public void testOnContentChanged() throws Exception
    {

    }

    @Test
    public void testOnInit_bundle() throws Exception
    {

    }

    @Test
    public void testOnInit_bundle_view() throws Exception
    {

    }

    @Test
    public void testOnContentReady() throws Exception
    {

    }

    @Test
    public void testFinish() throws Exception
    {

    }

    @Test
    public void testIsPaused() throws Exception
    {

    }

    @Test
    public void testOnDestroy() throws Exception
    {

    }

    @Test
    public void testOnLowMemory() throws Exception
    {

    }

    @Test
    public void testNotifyLowMemory() throws Exception
    {

    }

    @Test
    public void testOnPause() throws Exception
    {

    }

    @Test
    public void testOnRestart() throws Exception
    {

    }

    @Test
    public void testOnResume() throws Exception
    {

    }

    @Test
    public void testOnStart() throws Exception
    {

    }

    @Test
    public void testOnStop() throws Exception
    {

    }

    @Test
    public void testNotifyStop() throws Exception
    {

    }

    @Test
    public void testSetWindowFeature() throws Exception
    {

    }

    @Test
    public void testGetCustomTitleDrawable() throws Exception
    {

    }

    @Test
    public void testGetWindow() throws Exception
    {

    }

    @Test
    public void testSetContentView() throws Exception
    {

    }

    @Test
    public void testCanShowTitle() throws Exception
    {

    }

    @Test
    public void testGetContext() throws Exception
    {

    }
}
