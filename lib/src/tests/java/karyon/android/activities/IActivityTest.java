package karyon.android.activities;

import karyon.android.CustomRoboTestRunner;
import karyon.android.R;
import karyon.testing.KaryonTest;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 16/9/13
 * Time: 3:05 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(CustomRoboTestRunner.class)
public class IActivityTest
    extends KaryonTest
{
    private static class TestController
            extends BaseActivity<TestController>
    {
        @Override
        public int getPortraitViewResourceID()
        {
            return R.id.splash_id;
        }
    }


    @Test
    public void testPolymorphism() throws Exception
    {
        startMarker();
        IActivity loActivity = new TestController();
    }
}
