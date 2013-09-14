package karyon.android.activities;

import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;
import karyon.android.CustomRoboTestRunner;
import karyon.android.R;
import karyon.android.controllers.Controller;
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
        ActivityController<FragmentActivity> loActivity = Robolectric.buildActivity(FragmentActivity.class);
        SplashActivity loSplash = Controller.instantiate(SplashActivity.class);

        loActivity.create();

        loActivity.get().getSupportFragmentManager().beginTransaction().add(loSplash, "FRAGMENT").commit();
        assertNull(loSplash.findViewById(R.id.splash_content));
        loActivity.start();

        assertEquals(Application.getInstance().getName(), ((TextView) loSplash.findViewById(R.id.splash_content)).getText());
        assertEquals(Application.getInstance().getVersion().toString(), ((TextView) loSplash.findViewById(R.id.splash_version)).getText());
        assertEquals(Application.getInstance().getInstanceGUID(), ((TextView) loSplash.findViewById(R.id.splash_id)).getText());

        loActivity.destroy();
    }

    @Test
    public void testSetVersionText() throws Exception
    {
        startMarker();
        ActivityController<FragmentActivity> loActivity = Robolectric.buildActivity(FragmentActivity.class);
        SplashActivity loSplash = Controller.instantiate(SplashActivity.class);

        loActivity.create();

        loActivity.get().getSupportFragmentManager().beginTransaction().add(loSplash, "FRAGMENT").commit();
        assertNull(loSplash.findViewById(R.id.splash_content));
        loActivity.start();

        assertEquals(Application.getInstance().getVersion().toString(), ((TextView) loSplash.findViewById(R.id.splash_version)).getText());

        loSplash.setVersionText("A TEST");

        assertEquals("A TEST", ((TextView) loSplash.findViewById(R.id.splash_version)).getText());

        loActivity.destroy();
    }

    @Test
    public void testSetContentText() throws Exception
    {
        startMarker();
        ActivityController<FragmentActivity> loActivity = Robolectric.buildActivity(FragmentActivity.class);
        SplashActivity loSplash = Controller.instantiate(SplashActivity.class);

        loActivity.create();

        loActivity.get().getSupportFragmentManager().beginTransaction().add(loSplash, "FRAGMENT").commit();
        assertNull(loSplash.findViewById(R.id.splash_content));
        loActivity.start();

        assertEquals(Application.getInstance().getName(), ((TextView) loSplash.findViewById(R.id.splash_content)).getText());

        loSplash.setContentText("A TEST");

        assertEquals("A TEST", ((TextView) loSplash.findViewById(R.id.splash_content)).getText());

        loActivity.destroy();
    }

    @Test
    public void testSetIDText() throws Exception
    {
        startMarker();
        ActivityController<FragmentActivity> loActivity = Robolectric.buildActivity(FragmentActivity.class);
        SplashActivity loSplash = Controller.instantiate(SplashActivity.class);

        loActivity.create();

        loActivity.get().getSupportFragmentManager().beginTransaction().add(loSplash, "FRAGMENT").commit();
        assertNull(loSplash.findViewById(R.id.splash_content));
        loActivity.start();

        assertEquals(Application.getInstance().getInstanceGUID(), ((TextView) loSplash.findViewById(R.id.splash_id)).getText());

        loSplash.setIDText("A TEST");

        assertEquals("A TEST", ((TextView) loSplash.findViewById(R.id.splash_id)).getText());

        loActivity.destroy();
    }

    @Test
    public void testSetImage() throws Exception
    {
        startMarker();
        ActivityController<FragmentActivity> loActivity = Robolectric.buildActivity(FragmentActivity.class);
        SplashActivity loSplash = Controller.instantiate(SplashActivity.class);

        loActivity.create();

        loActivity.get().getSupportFragmentManager().beginTransaction().add(loSplash, "FRAGMENT").commit();
        assertNull(loSplash.findViewById(R.id.splash_content));
        loActivity.start();

        assertNull(((ImageView) loSplash.findViewById(R.id.splash_screen)).getDrawable());

        loSplash.setImage(R.drawable.attention);

        assertNotNull(((ImageView) loSplash.findViewById(R.id.splash_screen)).getDrawable());

        loActivity.destroy();
    }

    @Test
    public void testGetPortraitViewResourceID() throws Exception
    {
        startMarker();
        ActivityController<FragmentActivity> loActivity = Robolectric.buildActivity(FragmentActivity.class);
        SplashActivity loSplash = Controller.instantiate(SplashActivity.class);

        loActivity.create();

        loActivity.get().getSupportFragmentManager().beginTransaction().add(loSplash, "FRAGMENT").commit();

        assertEquals(R.layout.splash, loSplash.getPortraitViewResourceID());
        assertEquals(R.layout.splash, loSplash.getContentViewID());
    }

    @Test
    public void testOnSplashClicked() throws Exception
    {
        startMarker();
        ActivityController<FragmentActivity> loActivity = Robolectric.buildActivity(FragmentActivity.class);
        SplashActivity loSplash = Controller.instantiate(SplashActivity.class);

        loActivity.create();

        loActivity.get().getSupportFragmentManager().beginTransaction().add(loSplash, "FRAGMENT").commit();
        assertNull(loSplash.findViewById(R.id.splash_content));
        loActivity.start();

        assertFalse(loSplash.isFinishing());

        loSplash.onSplashClicked(null);

        assertTrue(loSplash.isFinishing());

        loActivity.destroy();
    }
}
