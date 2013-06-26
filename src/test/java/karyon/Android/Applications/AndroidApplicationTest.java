package karyon.Android.Applications;

import Karyon.Testing.KaryonTest;
import karyon.Android.CustomRoboTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 6/26/13
 * Time: 2:58 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(CustomRoboTestRunner.class)
public class AndroidApplicationTest
        extends KaryonTest
{
    @Test
    public void testCreateApplication() throws Exception
    {
        startMarker();
    }

    @Test
    public void testOnCreate() throws Exception
    {
        startMarker();

    }

    @Test
    public void testOnLowMemory() throws Exception
    {
        startMarker();
    }

    @Test
    public void testOnTerminate() throws Exception
    {
        startMarker();
    }
}
