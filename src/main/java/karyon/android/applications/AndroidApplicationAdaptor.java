package karyon.android.applications;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import karyon.Version;
import karyon.android.logging.AndroidLogger;
import karyon.applications.ApplicationOptions;
import karyon.applications.ICapabilitiesManager;
import karyon.applications.propertyManagers.IPropertyManager;
import karyon.dynamicCode.Java;
import karyon.exceptions.CriticalException;
import karyon.logging.ILogger;

/**
 * The Android Application Adaptor is used
 * to adapt applications so they will integrate with the
 * android lifecycle
 */
public abstract class AndroidApplicationAdaptor<T extends karyon.applications.Application<T>>
    extends android.app.Application
{
    private static AndroidApplicationAdaptor g_oApp;

    /**
     * Gets the singleton instance of the android application.  This will
     * throw a critical exception if the android application has not yet been created
     * @return the android application class
     */
    public static AndroidApplicationAdaptor getInstance()
    {
        if (g_oApp == null)
        {
            throw new CriticalException("Android Application class has not yet been created");
        }
        return g_oApp;
    }

    /**
     * Helper method to get the requested system service specific to the Application context
     * @param tcService the service to get
     * @param toReturnType the type of object that will be returned
     * @param <K> the type of object that is being requested
     * @return the object or null if unable to retrieve the requested service
     */
    public static <K> K getSystemService(String tcService, Class<K> toReturnType)
    {
        return (K)AndroidApplicationAdaptor.getInstance().getApplicationContext().getSystemService(tcService);
    }


    private T m_oApplication;

    /**
     * Creates a new instance of the Android Appliation Adaptor.
     * If a previous android adaptor was created this will throw a critical
     * exception
     */
    protected AndroidApplicationAdaptor()
    {
        if (g_oApp != null)
        {
            throw new CriticalException("An Android Application has already been created");
        }
        g_oApp = this;
    }

    /**
     * Creates the Application object
     * @return the application that is used as the controller
     */
    protected abstract Class<T> getApplicationClass();


    @Override
    public final void onCreate()
    {
        super.onCreate();
        try
        {
            PackageInfo loInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            String lcName = loInfo.packageName;
            String lcVersion = loInfo.versionName;
            try
            {
                lcName = getString(loInfo.applicationInfo.labelRes);
            }
            catch (Throwable ex)
            {
                // No need to do anything here
            }
            lcVersion = lcVersion == null ? "0.0.0.1" : lcVersion.replace("-SNAPSHOT", "");

            // Create the karyon framework application
            m_oApplication = Java.createObject(getApplicationClass(), new Version(lcName + " " + lcVersion));

            m_oApplication.setOptions(new ApplicationOptions(){

                @Override
                public ILogger getDefaultLogger(karyon.applications.Application toApplication)
                {
                    return new AndroidLogger();
                }

                @Override
                public IPropertyManager getDefaultPropertyManager(karyon.applications.Application toApplication)
                {
                    return new SharedPreferencesPropertyManager();
                }

                @Override
                public ICapabilitiesManager getDefaultCapabilitiesManager(karyon.applications.Application toApplication)
                {
                    return new AndroidCapabilitiesManager();
                }
            });

            // Start up the application
            m_oApplication.start();
        }
        catch (PackageManager.NameNotFoundException ex)
        {
            // This should never be able to happen as it is the application package being used
            // This means if it does happen something has gone horribly wrong
            throw new CriticalException("Unable to create Android Application");
        }
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
        g_oApp = null;
        if (m_oApplication != null)
        {
            m_oApplication.stop();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration toConfiguration)
    {
        super.onConfigurationChanged(toConfiguration);
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        if (m_oApplication != null)
        {
            m_oApplication.notifyLowMemory();
        }
    }

    /**
     * Gets the application instance with the correct type
     * @return the application instance
     */
    public T getApplicationInstance()
    {
        return m_oApplication;
    }


    // TODO: Determine where everything below here should be implemented
//    private DefaultHttpClient m_oHTTPClient;
//
//
//    /**
//     * Gets the host name of the web server
//     */
//    public String getWebServerHost()
//    {
//        return getPropertyManager().<String>getProperty("application.webHost");
//    }
//
//    /**
//     * Gets a behaviour for the specified activity, should be overridden in
//     * @param toActivityClass the class that we are attempting to get a behaviour for
//     * @param toCurrent the system selected behaviour if any
//     * @param <K> the type of activity that the behaviour is for
//     * @return the behaviour to use, or null for no behaviour
//     */
//    public <L extends Controller, K extends ControllerBehaviour<L>> K getBehaviourFor(Class<L> toActivityClass, K toCurrent)
//    {
//        return toCurrent;
//    }
//
//    /**
//     * Starts an activity of the specified type
//     * @param toActivityClass the activity class to start
//     * @param toParent the parent activity to use
//     */
//    public void startActivity(Class<? extends Activity>toActivityClass, Activity toParent)
//    {
//        this.startActivity(toActivityClass, toParent, null);
//    }
//
//    /**
//     * Starts an activity of the specified type
//     * @param toActivityClass the activity class to start
//     * @param toParent the parent activity to use
//     * @param toParameters the parameters to pass to the activity
//     */
//    public void startActivity(Class<? extends Activity>toActivityClass, Activity toParent, HashMap<String, ?> toParameters)
//    {
//        Intent loIntent = new Intent(toParent == null ? getApplicationContext() : toParent.getBaseContext(), toActivityClass);
//        if (toParent == null)
//        {
//            loIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        }
//        Bundle loParameters = karyon.android.Utilities.createBundle(toParameters);
//        if (loParameters != null)
//        {
//            loIntent.putExtras(loParameters);
//        }
//        if (toParent != null)
//        {
//            toParent.startActivity(loIntent);
//        }
//        else
//        {
//            getApplicationContext().startActivity(loIntent);
//        }
//    }

    // TODO: This should be refactored into a HTTP manager

//    /**
//     * Gets the DefaultHttpClient for all internet transactions
//     * @return the default http client
//     */
//    public DefaultHttpClient getDefaultHttpClient()
//    {
//        if (m_oHTTPClient == null)
//        {
//            SchemeRegistry loRegistry = new SchemeRegistry();
//            loRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//            loRegistry.register(new Scheme("http", SSLSocketFactory.getSocketFactory(), 443));
//
//            BasicHttpParams loParams = new BasicHttpParams();
//
//            HttpConnectionParams.setStaleCheckingEnabled(loParams, true);
//            HttpConnectionParams.setTcpNoDelay(loParams, true);
//
//            m_oHTTPClient = new DefaultHttpClient(loParams);
//            m_oHTTPClient.getCookieStore().getCookies();
//
//            String lcDomain = getWebServerHost().replaceAll("^(https?://)?[^\\.]+", "");
//            lcDomain = lcDomain.indexOf("/") >= 0  ? lcDomain.substring(0, lcDomain.indexOf("/")) : lcDomain;
//
//            // Always add the appID and instance cookies
//            BasicClientCookie loCookie = new BasicClientCookie("applicationid", getApplicationGUID());
//            loCookie.setDomain(lcDomain);
//            loCookie.setPath("/");
//            m_oHTTPClient.getCookieStore().addCookie(loCookie);
//
//            loCookie = new BasicClientCookie("applicationinstanceid", getInstanceGUID());
//            loCookie.setDomain(lcDomain);
//            loCookie.setPath("/");
//            m_oHTTPClient.getCookieStore().addCookie(loCookie);
//
//            loCookie = new BasicClientCookie(Application.getInstance().getPropertyManager().getProperty("application.network.sessionCookie", "PHPSESSID"), getInstanceGUID());
//            loCookie.setDomain(lcDomain);
//            loCookie.setPath("/");
//            m_oHTTPClient.getCookieStore().addCookie(loCookie);
//        }
//        return m_oHTTPClient;
//    }
//
//
//    // Everything below here needs refactoring
//
//
//
//
//    // TODO: Refactor this to track notifications and allow more flexibility
//    public Notification createNotification(int tnIconID, String tcTicker, String tcTitle, String tcContent, int tnARGB, Class<? extends Activity> toActivityClass)
//    {
//        NotificationManager loManager = getSystemService(Context.NOTIFICATION_SERVICE, NotificationManager.class);
//
//        Notification loNotification = new Notification(tnIconID, tcTicker, System.currentTimeMillis());
//
//        loNotification.ledARGB = tnARGB;
//        loNotification.ledOnMS = 500;
//        loNotification.ledOffMS = 500;
//        loNotification.flags |= Notification.FLAG_SHOW_LIGHTS;
//        loNotification.flags |= Notification.FLAG_ONGOING_EVENT;
//        loNotification.flags |= Notification.FLAG_AUTO_CANCEL;
//
//        loNotification.setLatestEventInfo(getApplicationContext(),
//                                          tcTitle, tcContent, null);
//
//        Intent loIntent = new Intent(getApplicationContext(), toActivityClass);
//        loIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//        PendingIntent loNotificationIntent = PendingIntent.getActivity(getApplicationContext(), 0, loIntent,  0);
//
//        loNotification.contentIntent = loNotificationIntent;
//
//        loManager.notify(1, loNotification);
//
//        return loNotification;
//    }
//
//    public void updateNotification(Notification toNotification, int tnIconID, String tcTicker, String tcTitle, String tcContent, int tnARGB)
//    {
//        NotificationManager loManager = getSystemService(Context.NOTIFICATION_SERVICE, NotificationManager.class);
//
//        toNotification.icon = tnIconID;
//        toNotification.tickerText = tcTicker;
//
//        toNotification.ledARGB = tnARGB;
//        toNotification.ledOnMS = 500;
//        toNotification.ledOffMS = 500;
//
//        toNotification.setLatestEventInfo(getApplicationContext(), tcTitle, tcContent, null);
//
//        loManager.notify(1, toNotification);
//    }
//
//    public void cancelNotification(Notification toNotification)
//    {
//        NotificationManager loManager = getSystemService(Context.NOTIFICATION_SERVICE, NotificationManager.class);
//        loManager.cancelAll();
//    }
//
//    public final void showError(int tnResourceID)
//    {
//        showError(m_oAndroidApp.getString(tnResourceID));
//    }
//
//    public final void showError(final String tcError)
//    {
//
//        Intent loAlertDialog = new Intent(m_oAndroidApp.getBaseContext(), ErrorActivity.class);
//        loAlertDialog.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        loAlertDialog.putExtra("com.youcommentate.message", tcError);
//        m_oAndroidApp.startActivity(loAlertDialog);
//    }
//
//    public FileOutputStream openFileOutput(String name, int mode) throws FileNotFoundException
//    {
//        return m_oAndroidApp.openFileOutput(name, mode);
//    }
//
//    public FileInputStream openFileInput(String name) throws FileNotFoundException
//    {
//        return m_oAndroidApp.openFileInput(name);
//    }
//
//    // TODO: Move the following to a FlurryManager (UserInfo Manager??)
//
//    /**
//     * Gets the flurry API key for this app
//     * @return the flurry api key, or null if there is no key
//     */
//    public String flurryAPIKey()
//    {
//        return null;
//    }
//
//    /**
//     * Checks if this application uses Flurry for analytics
//     * @return true if flurry is used
//     */
//    public final boolean usesFlurry()
//    {
//        return flurryAPIKey() != null;
//    }
}
