package karyon.android.controls;

import karyon.collections.HashMap;
import karyon.collections.List;
import karyon.SessionManager;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebViewClient;
import karyon.android.activities.WebActivity;
import karyon.android.applications.Application;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;

/**
 * Base DefaultWebViewClient shares cookies with DefaultHTTPClient
 */
public class DefaultWebViewClient
    extends WebViewClient
{
    private WebActivity m_oWebActivity;
    private boolean m_lUpdateCookies;
    private String m_cDomain;
    private HashMap<String, String> m_oCookies;

    /**
     * Creates a new instance of the WebViewClient and copies cookies
     * from the DefaultHTTPClient
     * @param toWebActivity
     */
    public DefaultWebViewClient(WebActivity toWebActivity, String tcDomain)
    {
        m_oWebActivity = toWebActivity;
        m_cDomain = tcDomain;
        m_oCookies = new HashMap<String, String>();
        DefaultHttpClient loClient = Application.getInstance().getDefaultHttpClient();
        if (loClient != null)
        {
            List<Cookie> loCookies = new List<Cookie>(loClient.getCookieStore().getCookies());
            if (loCookies.size()>0)
            {
                CookieSyncManager.createInstance(Application.getInstance().getApplicationContext());
                CookieManager loManager = CookieManager.getInstance();
                loManager.removeAllCookie();
                CookieSyncManager.getInstance().sync();

                loManager.setAcceptCookie(true);
                for (Cookie loCookie : loCookies)
                {
                    String lcName = loCookie.getName();
                    String lcValue = loCookie.getValue();

                    // Make sure the Session ID cookie matches the Session
                    // TODO: Make the session cookie identifier configurable
                    if (loCookie.getName().equalsIgnoreCase("phpsessid"))
                    {
                        lcValue = SessionManager.getInstance().getCurrentSession().getSessionID();
                    }

                    String lcCookie = lcName + "=" + lcValue;// + "; domain=" + loCookie.getDomain();

                    m_oCookies.put(lcName, lcValue);

                    loManager.setCookie(tcDomain, lcCookie);
                    CookieSyncManager.getInstance().sync();
                }
            }
        }
    }

    @Override
    public void onLoadResource(android.webkit.WebView toView, String tcUrl)
    {
        m_lUpdateCookies = true;
        m_oWebActivity.onLoadedResource((WebView)toView, tcUrl);
    }

    @Override
    public void onPageFinished(android.webkit.WebView toView, String tcUrl)
    {
        m_lUpdateCookies = true;
        m_oWebActivity.onFinishedPage((WebView)toView, tcUrl);
        m_oWebActivity.hideProgress();
    }

    @Override
    public void onPageStarted(android.webkit.WebView toView, String tcUrl, Bitmap toFavIcon)
    {
        m_oWebActivity.showProgress();
        m_oWebActivity.onStartedPage((WebView)toView, tcUrl, toFavIcon);
    }

    @Override
    public void onReceivedError(android.webkit.WebView toView, int tnError, String tcDescription, String tcUrl)
    {
        m_oWebActivity.onErrorReceived((WebView)toView, tnError, tcDescription, tcUrl);
    }

    @Override
    public void onReceivedSslError(android.webkit.WebView toView, SslErrorHandler toHandler, SslError toError)
    {
        toHandler.proceed(); // Ignore SSL certificate errors
    }

    @Override
    public boolean shouldOverrideUrlLoading(android.webkit.WebView toView, String tcURL)
    {
        return m_oWebActivity.onOverrideUrlLoading((WebView)toView, tcURL);
    }

    /**
     * Gets a list of the names of cookies that have been loaded, if no cookies have been loaded
     * this will return an empty list
     * @return the list of cookies, or and empty list if no cookies
     */
    public List<String> getCookies()
    {
        extractCookies();
        return m_oCookies != null ? new List(m_oCookies.keySet()) : new List<String>(0);
    }

    /**
     * Gets the value of a specific cookie, the cookie name is case sensitive
     * @param tcCookie the name of the cookie to retrieve
     * @return the value of the cookie, or null if the cookie did not exist
     */
    public String getCookieValue(String tcCookie)
    {
        extractCookies();
        return m_oCookies != null ? m_oCookies.get(tcCookie.trim()) : null;
    }

    /**
     * Extracts cookies from the web view and updates them in the HttpClient
     */
    private void extractCookies()
    {
        if (m_lUpdateCookies)
        {
            CookieManager loManager = CookieManager.getInstance();
            loManager.setAcceptCookie(true);
            String lcCookieString = loManager.getCookie(m_cDomain);
            BasicHttpContext loContext = new BasicHttpContext();
            DefaultHttpClient loClient = Application.getInstance().getDefaultHttpClient();
            loContext.setAttribute(ClientContext.COOKIE_STORE, loClient.getCookieStore());
            String[] laParts = null;
            String[] laCookies = null;


            laCookies = lcCookieString != null ? lcCookieString.split(";") : new String[0];

            for (int i=0, lnLength = laCookies.length; i<lnLength; i++)
            {
                laParts = laCookies[i].split("=");
                BasicClientCookie loCookie = new BasicClientCookie(laParts[0].trim(), laParts[1].trim());
                m_oCookies.put(laParts[0].trim(), laParts[1].trim());
                loCookie.setDomain(m_cDomain);
                loClient.getCookieStore().addCookie(loCookie);
            }
            m_lUpdateCookies = false;
        }
    }
}
