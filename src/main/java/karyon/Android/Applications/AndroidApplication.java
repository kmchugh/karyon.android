package karyon.Android.Applications;

import Karyon.Exceptions.CriticalException;
import Karyon.Version;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.webkit.CookieSyncManager;

/**
 * The core Android Application Class.
 * Android Apps should be based on this class in order to hook in
 * to the Karyon Application Framework
 */
public abstract class AndroidApplication 
    extends android.app.Application
{

    /**
     * Hook class to create the Karyon application
     * @return the Karyon application
     */
    protected abstract Application createApplication(Version toVersion);


    /**
     * Occurs when the Android Application is created.
     * To interact with this method, override onBeforeCreate and
     * onAfterCreate.  After onAfterCreate is called, the Karyon 
     * Application will be started
     */
    @Override
    public final void onCreate()
    {
        super.onCreate();
        try
        {
            PackageInfo loPackageInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            // Create the Karyon Application instance
            createApplication(new Version(this.getClass().getSimpleName() + " " + loPackageInfo.versionName));
        }
        catch (PackageManager.NameNotFoundException ex)
        {
            // This should never be able to happen as it is the application package being used
            // If it does happen it is a critical error
            throw new CriticalException("Unable to create Android Application");
        }
        
        // Force the cookie synch manager to be created
        CookieSyncManager.createInstance(this);
        // TODO: refactor this END----------------
        
        Application.getInstance().start();
    }
    
    /**
     * Occurs when the app is notified that the JVM is running low on
     * memory
     */
    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        Application.getInstance().notifyLowMemory();
    }
    
    /**
     * Lets the application know that it is being terminated.
     */
    @Override
    public void onTerminate()
    {
        super.onTerminate();
        Application.getInstance().stop();
    }
}
