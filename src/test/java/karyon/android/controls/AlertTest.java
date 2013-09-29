package karyon.android.controls;

import android.app.Dialog;
import karyon.android.CustomRoboTestRunner;
import karyon.android.R;
import karyon.android.activities.SplashActivity;
import karyon.testing.KaryonTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 29/9/13
 * Time: 11:03 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(CustomRoboTestRunner.class)
public class AlertTest
    extends KaryonTest
{
    @Test
    public void testOnCreateDialog() throws Exception
    {
        startMarker();

        ActivityController<SplashActivity> loActivityCon = Robolectric.buildActivity(SplashActivity.class);
        SplashActivity loActivity = loActivityCon.get();

        loActivityCon.create();
        loActivityCon.start();

        Alert loAlert = new Alert(loActivity, R.string.app_name);
        Dialog loDialog = loAlert.onCreateDialog(null);
        assertNotNull(loDialog);
    }

    @Test
    public void testShow() throws Exception
    {
        startMarker();

        ActivityController<SplashActivity> loActivityCon = Robolectric.buildActivity(SplashActivity.class);
        SplashActivity loActivity = loActivityCon.get();

        loActivityCon.create();
        loActivityCon.start();

        Alert loAlert = Alert.createAlert(R.string.app_name, new Alert.ButtonInfo(R.string.ok), new Alert.ButtonInfo(R.string.cancel));
        String lcTag = loAlert.show(loActivity);
        assertNotNull(lcTag);

        assertNotNull(loActivity.getSupportFragmentManager().findFragmentByTag(lcTag));
    }
}
