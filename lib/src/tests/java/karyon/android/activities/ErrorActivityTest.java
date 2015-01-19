package karyon.android.activities;

import android.content.Intent;
import android.widget.TextView;
import karyon.android.CustomRoboTestRunner;
import karyon.android.R;
import karyon.android.applications.AndroidApplicationAdaptor;
import karyon.applications.Application;
import karyon.testing.KaryonTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 17/9/13
 * Time: 10:39 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(CustomRoboTestRunner.class)
public class ErrorActivityTest
    extends KaryonTest
{
    @Test
    public void testGetPortraitViewResourceID() throws Exception
    {
        startMarker();

        ActivityController<ErrorActivity> loActivityCon = Robolectric.buildActivity(ErrorActivity.class);
        ErrorActivity loActivity = loActivityCon.get();

        loActivityCon.create();

        assertEquals(R.layout.error, loActivity.getPortraitViewResourceID());
        assertEquals(R.layout.error, loActivity.getContentViewID());
    }

    @Test
    public void testOnContentReady() throws Exception
    {
        startMarker();

        ActivityController<ErrorActivity> loActivityCon = Robolectric.buildActivity(ErrorActivity.class);
        ErrorActivity loActivity = loActivityCon.get();

        Intent loIntent = new Intent();
        loIntent.putExtra("error.message", "An error message");
        loIntent.putExtra("error.title", "An error title");

        loActivityCon.create();

        loActivity.setIntent(loIntent);

        loActivity.onContentReady();

        assertEquals("An error message", ((TextView) loActivity.findViewById(R.id.error_message)).getText());
        assertEquals("An error title", ((TextView) loActivity.findViewById(R.id.error_title)).getText());

    }

    @Test
    public void testOnOkayClicked() throws Exception
    {
        startMarker();
        ActivityController<ErrorActivity> loActivityCon = Robolectric.buildActivity(ErrorActivity.class);
        ErrorActivity loActivity = loActivityCon.get();

        loActivityCon.create();

        assertFalse(loActivity.isFinishing());

        loActivity.onOkayClicked(null);

        assertTrue(loActivity.isFinishing());

        loActivityCon.destroy();
    }
}
