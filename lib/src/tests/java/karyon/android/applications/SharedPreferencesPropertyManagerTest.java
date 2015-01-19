package karyon.android.applications;

import karyon.android.CustomRoboTestRunner;
import karyon.collections.HashMap;
import karyon.testing.KaryonTest;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 17/9/13
 * Time: 1:10 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(CustomRoboTestRunner.class)
public class SharedPreferencesPropertyManagerTest
    extends KaryonTest
{
    private static class TestValue
    {
        int m_nValue;

        public TestValue()
        {}

        public TestValue(int tnValue)
        {
            m_nValue = tnValue;
        }

        public int getValue()
        {
            return m_nValue;
        }
    }

    @Test
    public void testGetProperty_string() throws Exception
    {
        startMarker();

        SharedPreferencesPropertyManager loPM = new SharedPreferencesPropertyManager();
        loPM.setProperty("Test", "TEST1");

        assertNull(loPM.getProperty("Invalid Property"));
        assertEquals("TEST1", loPM.getProperty("Test"));
    }

    @Test
    public void testGetProperty_string_default() throws Exception
    {
        startMarker();

        SharedPreferencesPropertyManager loPM = new SharedPreferencesPropertyManager();

        if(loPM.hasProperty("Test"))
        {
            loPM.clearProperty("Test");
        }

        assertEquals("test", loPM.getProperty("Test", "test"));
        loPM.setProperty("Test", "TEST1");
        assertEquals("TEST1", loPM.getProperty("Test", "test"));
    }

    @Test
    public void testHasProperty() throws Exception
    {
        startMarker();

        SharedPreferencesPropertyManager loPM = new SharedPreferencesPropertyManager();

        if(loPM.hasProperty("Test"))
        {
            loPM.clearProperty("Test");
        }

        assertFalse(loPM.hasProperty("Test"));

        loPM.setProperty("Test", "TEST1");

        assertTrue(loPM.hasProperty("Test"));
    }

    @Test
    public void testSetProperty() throws Exception
    {
        startMarker();

        SharedPreferencesPropertyManager loPM = new SharedPreferencesPropertyManager();

        if(loPM.hasProperty("Test"))
        {
            loPM.clearProperty("Test");
        }

        assertEquals("test", loPM.getProperty("Test", "test"));
        loPM.setProperty("Test", "TEST1");
        assertEquals("TEST1", loPM.getProperty("Test", "test"));

        loPM.setProperty("Test2", new TestValue(999));

        assertEquals(999, loPM.<TestValue>getProperty("Test2", TestValue.class).getValue());
    }

    @Test
    public void testSetProperties() throws Exception
    {
        startMarker();

        SharedPreferencesPropertyManager loPM = new SharedPreferencesPropertyManager();

        HashMap loProperties = new HashMap()
                .append("Property1", "Value1")
                .append("Property2", "Value2")
                .append("Property3", "Value3");

        loPM.setProperties("", loProperties);

        assertTrue(loPM.hasProperty("Property1"));
        assertTrue(loPM.hasProperty("Property2"));
        assertTrue(loPM.hasProperty("Property3"));

        loPM.setProperties("application", loProperties);

        assertTrue(loPM.hasProperty("application.Property1"));
        assertTrue(loPM.hasProperty("application.Property2"));
        assertTrue(loPM.hasProperty("application.Property3"));

        loPM.setProperties("application.test", loProperties);

        assertTrue(loPM.hasProperty("application.test.Property1"));
        assertTrue(loPM.hasProperty("application.test.Property2"));
        assertTrue(loPM.hasProperty("application.test.Property3"));
    }

    @Test
    public void testClearProperty() throws Exception
    {
        startMarker();

        SharedPreferencesPropertyManager loPM = new SharedPreferencesPropertyManager();

        loPM.setProperty("Test", "test");

        assertTrue("test", loPM.hasProperty("Test"));

        loPM.clearProperty("Test");

        assertFalse("test", loPM.hasProperty("Test"));
    }
}
