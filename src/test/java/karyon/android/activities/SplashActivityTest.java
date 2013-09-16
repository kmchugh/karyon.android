package karyon.android.activities;

import android.widget.ImageView;
import android.widget.TextView;
import karyon.android.CustomRoboTestRunner;
import karyon.android.R;
import karyon.applications.Application;
import karyon.testing.KaryonTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 14/9/13
 * Time: 11:08 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(CustomRoboTestRunner.class)
public class SplashActivityTest
    extends KaryonTest
{
    @Test
    public void testOnContentReady() throws Exception
    {
        startMarker();
        ActivityController<SplashActivity> loActivityCon = Robolectric.buildActivity(SplashActivity.class);
        SplashActivity loActivity = loActivityCon.get();

        loActivityCon.create();
        loActivityCon.start();

        assertEquals(Application.getInstance().getName(), ((TextView) loActivity.findViewById(R.id.splash_content)).getText());
        assertEquals(Application.getInstance().getVersion().toString(), ((TextView) loActivity.findViewById(R.id.splash_version)).getText());
        assertEquals(Application.getInstance().getInstanceGUID(), ((TextView) loActivity.findViewById(R.id.splash_id)).getText());

        loActivityCon.destroy();
    }

    @Test
    public void testSetVersionText() throws Exception
    {
        startMarker();
        ActivityController<SplashActivity> loActivityCon = Robolectric.buildActivity(SplashActivity.class);
        SplashActivity loActivity = loActivityCon.get();

        loActivityCon.create();
        loActivityCon.start();

        assertEquals(Application.getInstance().getVersion().toString(), ((TextView) loActivity.findViewById(R.id.splash_version)).getText());

        loActivity.setVersionText("A TEST");

        assertEquals("A TEST", ((TextView) loActivity.findViewById(R.id.splash_version)).getText());

        loActivityCon.destroy();
    }

    @Test
    public void testSetContentText() throws Exception
    {
        startMarker();
        ActivityController<SplashActivity> loActivityCon = Robolectric.buildActivity(SplashActivity.class);
        SplashActivity loActivity = loActivityCon.get();

        loActivityCon.create();
        loActivityCon.start();

        assertEquals(Application.getInstance().getName(), ((TextView) loActivity.findViewById(R.id.splash_content)).getText());

        loActivity.setContentText("A TEST");

        assertEquals("A TEST", ((TextView) loActivity.findViewById(R.id.splash_content)).getText());

        loActivityCon.destroy();
    }

    @Test
    public void testSetIDText() throws Exception
    {
        startMarker();
        ActivityController<SplashActivity> loActivityCon = Robolectric.buildActivity(SplashActivity.class);
        SplashActivity loActivity = loActivityCon.get();

        loActivityCon.create();
        loActivityCon.start();

        assertEquals(Application.getInstance().getInstanceGUID(), ((TextView) loActivity.findViewById(R.id.splash_id)).getText());

        loActivity.setIDText("A TEST");

        assertEquals("A TEST", ((TextView) loActivity.findViewById(R.id.splash_id)).getText());

        loActivityCon.destroy();
    }

    @Test
    public void testSetImage() throws Exception
    {
        startMarker();
        ActivityController<SplashActivity> loActivityCon = Robolectric.buildActivity(SplashActivity.class);
        SplashActivity loActivity = loActivityCon.get();

        loActivityCon.create();
        loActivityCon.start();

        assertNull(((ImageView) loActivity.findViewById(R.id.splash_screen)).getDrawable());

        loActivity.setImage(R.drawable.attention);

        assertNotNull(((ImageView) loActivity.findViewById(R.id.splash_screen)).getDrawable());

        loActivityCon.destroy();
    }

    @Test
    public void testGetPortraitViewResourceID() throws Exception
    {
        startMarker();
        ActivityController<SplashActivity> loActivityCon = Robolectric.buildActivity(SplashActivity.class);
        SplashActivity loActivity = loActivityCon.get();

        loActivityCon.create();
        loActivityCon.start();

        assertEquals(R.layout.splash, loActivity.getPortraitViewResourceID());
        assertEquals(R.layout.splash, loActivity.getContentViewID());
    }

    @Test
    public void testOnSplashClicked() throws Exception
    {
        startMarker();
        ActivityController<SplashActivity> loActivityCon = Robolectric.buildActivity(SplashActivity.class);
        SplashActivity loActivity = loActivityCon.get();

        loActivityCon.create();
        loActivityCon.start();

        assertFalse(loActivity.isFinishing());

        loActivity.onSplashClicked(null);

        assertTrue(loActivity.isFinishing());

        loActivityCon.destroy();
    }
}
