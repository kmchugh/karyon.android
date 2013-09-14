package karyon.android.controllers;

import karyon.testing.KaryonTest;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 13/9/13
 * Time: 9:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class IControllerTest
    extends KaryonTest
{
    private static class TestController
        extends Controller<TestController>
    {
        @Override
        public int getPortraitViewResourceID()
        {
            return 0;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }


    @Test
    public void testPolymorphism() throws Exception
    {
        startMarker();
        IController loController = new TestController();
    }
}
