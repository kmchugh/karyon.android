package karyon.android.controllers;

import android.os.Bundle;
import android.view.View;
import karyon.android.AndroidTestApplicationAdaptor;
import karyon.android.CustomRoboTestRunner;
import karyon.testing.KaryonTest;
import org.junit.Test;
import org.junit.runner.RunWith;
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

        @Override
        public int getPortraitViewResourceID()
        {
            return 0;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public String getStringValue()
        {
            return m_cValue;
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
    }

    @Test
    public void testInstantiate_context_class() throws Exception
    {
        startMarker();
        TestController loController = Controller.instantiate(TestController.class);
        assertNotNull(loController);
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
        assertEquals(TestController.class, loController.getClass());
        assertEquals("Test String Value", loController.getStringValue());
    }

    @Test
    public void testGetSelf() throws Exception
    {

    }

    @Test
    public void testOnCreateView() throws Exception
    {

    }

    @Test
    public void testOnCreate() throws Exception
    {

    }

    @Test
    public void testFindViewById() throws Exception
    {

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
    public void testGetContentViewID() throws Exception
    {

    }

    @Test
    public void testGetPortraitViewResourceID() throws Exception
    {

    }

    @Test
    public void testGetLandscapeViewResourceID() throws Exception
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
