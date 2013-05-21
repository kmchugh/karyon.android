package karyon.Android.Applications;

import Karyon.Applications.CapabilitiesManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AndroidCapabilitiesManager extends CapabilitiesManager
{
    public boolean isOnline()
    {
        ConnectivityManager loManager = (ConnectivityManager)Application.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE, ConnectivityManager.class);
        NetworkInfo loInfo = loManager.getActiveNetworkInfo();
        return loInfo != null && loInfo.isConnected();
    }
}
