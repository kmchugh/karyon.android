package karyon.Android.Applications;

import Karyon.Collections.HashMap;
import com.xtremelabs.robolectric.Robolectric;
import karyon.Android.CustomRoboTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

@RunWith(CustomRoboTestRunner.class)
public class AndroidApplicationTest
{
    @Test
    public void testOnCreate() throws Exception
    {
        android.app.Application loApp = Robolectric.application;
        loApp.onCreate();
        assertTrue(Application.isCreated());
        assertTrue(Application.isInitialised());
        assertSame(Karyon.Applications.Application.getInstance().getClass(), CustomRoboTestRunner.TestApplication.class);
    }

    @Test
    public void testOnTerminate() throws Exception
    {
        android.app.Application loApp = Robolectric.application;
        loApp.onTerminate();
    }

    @Test
    public void testGetParameter() throws Exception
    {
        Application loApp = Application.getInstance();
        Object loValue = 5;
        loApp.getPropertyManager().setProperty("TestParameter", loValue);
        assertEquals(loValue, loApp.getPropertyManager().getProperty("TestParameter"));
    }

    @Test
    public void testSetParameters() throws Exception
    {
        Application loApp = Application.getInstance();
        HashMap<String, Object> loValues = new HashMap<String, Object>(2);
        loValues.put("Test1", 1);
        loValues.put("Test2", 2);
        loApp.getPropertyManager().setProperties("",loValues);
        assertEquals(loApp.getPropertyManager().getProperty(".Test1"), loValues.get("Test1"));
        assertEquals(loApp.getPropertyManager().getProperty(".Test2"), loValues.get("Test2"));
    }

    @Test
    public void testSetParameter() throws Exception
    {
        Application loApp = Application.getInstance();
        Object loValue = 11;
        loApp.getPropertyManager().setProperty("TestParameter", loValue);
        assertEquals(loValue, loApp.getPropertyManager().getProperty("TestParameter"));
    }
}
