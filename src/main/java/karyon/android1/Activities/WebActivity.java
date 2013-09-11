package karyon.android1.Activities;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.widget.ProgressBar;
import karyon.android1.Applications.Application;
import karyon.android1.Controls.DefaultWebViewClient;
import karyon.android1.Controls.WebView;
import karyon.android1.R;

/**
 * A web activity is an activity which opens a URL or displays a HTML snippet.
 * @author kmchugh
 */
public abstract class WebActivity<T extends WebActivity<T>>
    extends Controller<T>
{
    private ProgressBar m_oProgress;
    private WebView m_oWebView;
    private View m_oCoverView;
    private CookieSyncManager m_oSyncManager;
    private DefaultWebViewClient m_oDefaultClient;

    // Portrait and landscape are the same view
    @Override
    public int getPortraitViewResourceID()
    {
        return R.layout.webview;
    }

    @Override
    public void onContentReady()
    {
        super.onContentReady();

        if (m_oCoverView == null)
        {
            m_oCoverView = this.findViewById(R.id.fadeCover);
            m_oCoverView.setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View toView, MotionEvent toEvent)
                {
                    // We return true to cancel any events, we don't want the user to click the buttons under
                    return true;
                }
            });
        }
        if (m_oProgress == null)
        {
            m_oProgress = (ProgressBar)this.findViewById(R.id.progressBar);
        }

        if (m_oWebView == null)
        {
            m_oWebView = (WebView)this.findViewById(R.id.webview);
            if (m_oWebView == null)
            {
                // Web View control is required
                throw new UnsupportedOperationException("A control with the id 'webview' was not found in the activity");
            }
            // TODO: See if it is worth storing the client as a lazy load singleton
            m_oDefaultClient = createWebViewClient();
            m_oWebView.setWebViewClient(m_oDefaultClient);
            m_oWebView.getSettings().setJavaScriptEnabled(true);
            m_oWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            m_oWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            m_oSyncManager = CookieSyncManager.createInstance(Application.getInstance().getApplicationContext());
            m_oSyncManager.sync();
            String lcURL = getURL();
            Application.log("Opening Webview for " + lcURL);
            m_oWebView.loadUrl(lcURL);
        }
    }

    public String getCookieValue(String tcCookieName)
    {
        return m_oDefaultClient.getCookieValue(tcCookieName);
    }

    @Override
    public void onLowMemory()
    {
        if (m_oWebView != null)
        {
            m_oWebView.freeMemory();
        }
        super.onLowMemory();
    }

    @Override
    public void onFinish()
    {
        if (m_oWebView != null)
        {
            // Load a blank page to ensure the webview does not continue to execute
            runOnUiThread(new Runnable(){
                @Override
                public void run()
                {
                    m_oWebView.loadUrl("");
                }
            });
        }
    }

    /**
     * Gets the URL that this activity is browsing to
     * @return the activity url
     */
    protected abstract String getURL();

    /**
     * Shows the progress notification
     */
    public void showProgress()
    {
        if (m_oCoverView != null)
        {
            m_oCoverView.setVisibility(View.VISIBLE);
        }

        if (m_oProgress != null)
        {
            m_oProgress.setVisibility(View.VISIBLE);
        }
        else if (m_oProgress == null)
        {
            m_oProgress = new ProgressBar(getApplicationContext());
            addContentView(m_oProgress, m_oWebView.getLayoutParams());
        }
    }

    /**
     * Hides the progress notification
     */
    public void hideProgress()
    {
        if (m_oCoverView != null)
        {
            m_oCoverView.setVisibility(View.GONE);
        }

        if (m_oProgress != null)
        {
            m_oProgress.setVisibility(View.GONE);
        }
    }

    /**
     * Creates a web view client that allows a WebActivityBehaviour to interact
     * with the page load activity
     * @return the web view client
     */
    private DefaultWebViewClient createWebViewClient()
    {
        String lcDomain = getURL().replaceAll("^(https?://)?[^\\.]+", "");
        return new DefaultWebViewClient(this, lcDomain.indexOf("/") >= 0 ? lcDomain.substring(0, lcDomain.indexOf("/")) : lcDomain);
    }

    public boolean onOverrideUrlLoading(WebView toView, String tcURL)
    {

        return false;
    }
    
    public void onLoadedResource(WebView toView, String tcURL)
    {
        // TODO: Call the Controller manager to notify the behaviour
    }

    public void onFinishedPage(WebView toView, String tcURL)
    {
        // TODO: Call the Controller manager to notify the behaviour
    }

    public void onStartedPage(WebView toView, String tcURL, Bitmap toFavIcon)
    {
        // TODO: Call the Controller manager to notify the behaviour
    }

    public void onErrorReceived(WebView toView, int tnError, String tcDescription, String tcURL)
    {
        // TODO: Call the Controller manager to notify the behaviour
    }
    
    
    
    
    
    
}
