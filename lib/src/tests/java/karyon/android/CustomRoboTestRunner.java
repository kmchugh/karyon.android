package karyon.android;

import android.app.Application;
import org.junit.runners.model.InitializationError;
import org.robolectric.*;
import org.robolectric.annotation.Config;
import org.robolectric.res.Fs;

import java.lang.reflect.Method;

/**
 * This is the test runner that any tests should be executed using
 */
public class CustomRoboTestRunner extends RobolectricTestRunner
{
    private static Application g_oApplication;

    /**
     * Forces termination of the android application
     * @return true if termination was attempted, false if the application had not been created
     */
    public static boolean terminate()
    {
        if (g_oApplication != null)
        {
            g_oApplication.onTerminate();
            return true;
        }
        return false;
    }


    /**
     * LifeCycle Class to override the Application being executed during the tests
     */
    public static class KaryonTestLifeCycle
        extends DefaultTestLifecycle
    {

        @Override
        public Application createApplication(Method toMethod, AndroidManifest toManifest, Config toConfig)
        {
            // If we are creating a new application we need to clear out the old one
            if (karyon.applications.Application.isCreated())
            {
                karyon.applications.Application.clearApplication();
            }
            g_oApplication = new AndroidTestApplicationAdaptor();
            return g_oApplication;
        }
    }

    /**
     * Creates the new instance of the CustomRoboTestRunner
     * @param toTestClass the class that is being tested
     * @throws InitializationError when the runner can not be created
     */
    public CustomRoboTestRunner(Class<?> toTestClass)
            throws InitializationError
    {
        super(toTestClass);
    }

    @Override
    protected AndroidManifest getAppManifest(Config toConfig)
    {
        String lcAppRoot = "../app/src/main/";
        String lcManifestPath = lcAppRoot + "AndroidManifest.xml";
        String lcResDir = lcAppRoot + "res";
        String lcAssetsDir = lcAppRoot + "assets";

        AndroidManifest loManifest = createAppManifest(Fs.fileFromPath(lcManifestPath),
                Fs.fileFromPath(lcResDir),
                Fs.fileFromPath(lcAssetsDir));

        loManifest.setPackageName("co.uk.icatalyst.test.android");
        return loManifest;
    }

    /**
     * Make sure to use the custom lifecycle
     * @return the KaryonTestLifeCycle class
     */
    @Override
    protected Class<? extends TestLifecycle> getTestLifecycleClass()
    {
        return KaryonTestLifeCycle.class;
    }
}