package karyon.Android;

import Karyon.ISessionManager;
import Karyon.SessionManager;
import Karyon.Version;
import karyon.Android.Applications.AndroidApplication;
import karyon.Android.Applications.Application;
import org.junit.runners.model.InitializationError;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricConfigs;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.res.builder.RobolectricPackageManager;
import org.robolectric.shadows.ShadowApplication;

import java.io.File;

/**
 * Allows customisation of the location of the manifest file
 */
public class CustomRoboTestRunner extends RobolectricTestRunner
{
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
            return true;
        }

        @Override
        protected boolean onStart()
        {
            return true;
        }

        @Override
        public ISessionManager createSessionManager()
        {
            //return new SessionManager();
            return null;
        }
    }


    public static class TestAndroidApp
            extends AndroidApplication
    {
        @Override
        protected Application createApplication(Version toVersion)
        {
            return new TestApplication(toVersion, this);
        }
    }

    public CustomRoboTestRunner(Class<?> toTestClass)
            throws InitializationError
    {
        //super(toTestClass, getManifestDirectory());
        super(toTestClass);
    }

    // Attempts to find the Karyon.Android Manifest file
    private static File getManifestDirectory()
    {
        Application.clearApplication();
        File loRoot = new File(Application.class.getClassLoader().
                getResource("karyon/Android/Applications/Application.class").getFile()).
                getParentFile().getParentFile().getParentFile().getParentFile().
                getParentFile().getParentFile();
        if (new File(loRoot.getPath() + "/AndroidManifest.xml").exists())
        {
            return loRoot;
        }
        return new File(".");
    }

    /*

    @Override
    protected android.app.Application createApplication()
    {
        return new TestAndroidApp();
    }

    @Override
    public void setupApplicationState(RobolectricConfigs toConfig)
    {
        super.setupApplicationState(toConfig);
        ShadowApplication loApp = Robolectric.shadowOf(Robolectric.application);
        loApp.setPackageName(toConfig.getPackageName());
        loApp.setPackageManager(new RobolectricPackageManager(Robolectric.application, toConfig));
    }
    */
}