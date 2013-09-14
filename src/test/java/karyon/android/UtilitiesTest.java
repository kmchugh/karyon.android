package karyon.android;

import android.os.Bundle;
import android.view.Window;
import karyon.IRunnable;
import karyon.android.controllers.ControllerContainer;
import karyon.collections.HashMap;
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
 * Time: 11:25 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(CustomRoboTestRunner.class)
public class UtilitiesTest
    extends KaryonTest
{
    @Test
    public void testCreateBundle() throws Exception
    {
        startMarker();

        final HashMap<String, Object> loMap = new HashMap<String, Object>()
            .append("Item1", "Value1")
            .append("Item2", "Value2")
            .append("Item3", "Value3")
            .append("Item4", null);

        Bundle loBundle = Utilities.createBundle(loMap);

        assertEquals("Value1", loBundle.getString("Item1"));
        assertEquals("Value2", loBundle.getString("Item2"));
        assertEquals("Value3", loBundle.getString("Item3"));
        assertFalse(loBundle.containsKey("Item4"));

        loMap.put("Item1", false);
        loBundle = Utilities.createBundle(loMap);
        assertFalse(loBundle.getBoolean("Item1"));

        loMap.put("Item1", 1.2);
        loBundle = Utilities.createBundle(loMap);
        assertEquals(1.2, loBundle.getDouble("Item1"), 0);

        loMap.put("Item1", 1.2f);
        loBundle = Utilities.createBundle(loMap);
        assertEquals(1.2f, loBundle.getFloat("Item1"), 0);

        loMap.put("Item1", 1);
        loBundle = Utilities.createBundle(loMap);
        assertEquals(1, loBundle.getInt("Item1"));

        loMap.put("Item1", 1L);
        loBundle = Utilities.createBundle(loMap);
        assertEquals(1L, loBundle.getLong("Item1"));

        loMap.put("Item1", (short)1);
        loBundle = Utilities.createBundle(loMap);
        assertEquals((short)1, loBundle.getShort("Item1"));


        assertWillThrow(UnsupportedOperationException.class, new IRunnable()
        {
            @Override
            public void run() throws Throwable
            {
                loMap.put("Item1", new byte[4]);

                Bundle loBundle = Utilities.createBundle(loMap);
            }
        });

        assertWillThrow(UnsupportedOperationException.class, new IRunnable()
        {
            @Override
            public void run() throws Throwable
            {
                loMap.put("Item1", new IRunnable()
                {
                    @Override
                    public void run() throws Throwable
                    {
                    }
                });

                Bundle loBundle = Utilities.createBundle(loMap);
            }
        });
    }

    @Test
    public void testHasFeature() throws Exception
    {
        startMarker();

        ActivityController<ControllerContainer> loActivity = Robolectric.buildActivity(ControllerContainer.class);

        assertFalse(Utilities.hasFeature(loActivity.get().getWindow(), Window.FEATURE_NO_TITLE));

        assertTrue(loActivity.get().setWindowFeature(Window.FEATURE_NO_TITLE));

        // This is currently not testable with the robolectric shadow classes

        // assertTrue(Utilities.hasFeature(loActivity.get().getWindow(), Window.FEATURE_NO_TITLE));
    }
}
