package karyon.os;

import karyon.testing.KaryonTest;
import org.junit.Test;
import org.omg.CORBA.Environment;

import static org.junit.Assert.*;

public class OSTest
    extends KaryonTest
{

    @Test
    public void testGetOS() throws Exception
    {
        startMarker();
        OS loOS = OS.getOS();
        assertEquals(loOS, karyon.Environment.OS());
    }

    @Test
    public void testIsAndroid() throws Exception
    {
        startMarker();
        OS loOS = OS.getOS();
        assertEquals("Android", loOS.getName());
    }

    @Test
    public void testGetDeviceID() throws Exception
    {
        startMarker();
        OS loOS = OS.getOS();
        assertEquals("NO ACCESS TO MAC", loOS.getDeviceID());
    }
}