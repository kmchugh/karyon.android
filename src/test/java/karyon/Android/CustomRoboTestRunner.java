package karyon.Android;

import Karyon.Data.IDataManager;
import Karyon.ISessionManager;
import Karyon.SessionManager;
import Karyon.Version;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricConfig;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.res.RobolectricPackageManager;
import com.xtremelabs.robolectric.shadows.ShadowApplication;
import com.xtremelabs.robolectric.util.DatabaseConfig;
import karyon.Android.Applications.AndroidApplication;
import karyon.Android.Applications.Application;
import karyon.Android.Data.SQLiteDataManager;
import org.junit.runners.model.InitializationError;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            return new SessionManager();
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
        super(toTestClass, getManifestDirectory());
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

    @Override
    protected android.app.Application createApplication()
    {
        return new TestAndroidApp();
    }

    @Override
    public void setupApplicationState(RobolectricConfig toConfig)
    {
        super.setupApplicationState(toConfig);
        ShadowApplication loApp = Robolectric.shadowOf(Robolectric.application);
        loApp.setPackageName(toConfig.getPackageName());
        loApp.setPackageManager(new RobolectricPackageManager(Robolectric.application, toConfig));
    }
}