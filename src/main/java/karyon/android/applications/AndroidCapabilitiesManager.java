package karyon.android.applications;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import karyon.applications.CapabilitiesManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AndroidCapabilitiesManager extends CapabilitiesManager
{
    private Boolean m_lDebuggable;

    /**
     * Checks if the application is capable of going online
     * @return true if capable of going online
     */
    public boolean isOnline()
    {
        // TODO: Refactor this to the karyon capabilities manager
        ConnectivityManager loManager = AndroidApplicationAdaptor.getSystemService(Context.CONNECTIVITY_SERVICE, ConnectivityManager.class);
        NetworkInfo loInfo = loManager.getActiveNetworkInfo();
        return loInfo != null && loInfo.isConnected();
    }

    /**
     * Checking if the current application is debuggable
     * @return true if this application is in debug mode
     */
    public boolean isDebuggable()
    {
        // TODO: refactor this to the karyon capabilities manger
        if (m_lDebuggable == null)
        {
            boolean llReturn = false;
            PackageManager loPM = AndroidApplicationAdaptor.getInstance().getApplicationContext().getPackageManager();
            try
            {
                ApplicationInfo loInfo = loPM.getApplicationInfo(AndroidApplicationAdaptor.getInstance().getApplicationContext().getPackageName(), 0);
                llReturn = (0 != (loInfo.flags &= ApplicationInfo.FLAG_DEBUGGABLE));
            }
            catch (Throwable ex)
            {
            }
            m_lDebuggable = llReturn;
        }
        return m_lDebuggable;
    }

    /**
     * Checks if we are running on a tablet or mobile device
     * @return true if we are on a tablet, false otherwise
     */
    public static boolean isTablet()
    {
        return (AndroidApplicationAdaptor.getInstance().getApplicationContext().getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;

    }
}
