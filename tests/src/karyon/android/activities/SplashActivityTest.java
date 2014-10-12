package karyon.android;

import android.test.ActivityInstrumentationTestCase2;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class karyon.android.activities.SplashActivityTest \
 * karyon.android.tests/android.test.InstrumentationTestRunner
 */
public class SplashActivityTest extends ActivityInstrumentationTestCase2<SplashActivity> {

    public SplashActivityTest() {
        super("karyon.android", SplashActivity.class);
    }

}
