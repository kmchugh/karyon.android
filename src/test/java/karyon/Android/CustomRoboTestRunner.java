package karyon.Android;

import Karyon.ISessionManager;
import Karyon.Version;
import karyon.Android.Applications.AndroidApplication;
import karyon.Android.Applications.Application;
import org.junit.runners.model.InitializationError;
import org.robolectric.*;

import java.lang.reflect.Method;

/**
 * This is the test runner that any tests should be executed using
 */
public class CustomRoboTestRunner extends RobolectricTestRunner
{
    /**
     * LifeCycle Class to override the Application being executed during the tests
     */
    public static class KaryonTestLifeCycle
        extends DefaultTestLifecycle
    {
        @Override
        public android.app.Application createApplication(Method toMethod, AndroidManifest toManifest)
        {
            return new TestAndroidApp();
        }
    }

    /**
     * The Android Application Launcher hook.  this will create and execute TestApplication
     */
    public static class TestAndroidApp
            extends AndroidApplication
    {
        @Override
        protected Application createApplication(Version toVersion)
        {
            Application.clearApplication();
            return new TestApplication(toVersion, this);
        }
    }


    /**
     * The Application Class that is used to test the functionality of Karyon.Android
     */
    public static class TestApplication
            extends Application
    {
        private TestApplication(Version toVersion, AndroidApplication toApp)
        {
            super(toVersion, toApp);
        }

        @Override
        protected boolean onInit()
        {
            // Do nothing with the initialisation
            return true;
        }

        @Override
        protected boolean onStart()
        {
            // Do nothing with start
            return true;
        }

        @Override
        public ISessionManager createSessionManager()
        {
            return null;
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