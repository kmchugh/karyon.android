package karyon.android.activities;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import karyon.android.R;
import karyon.applications.Application;

/**
 * The splash screen activity for the application, by default this is the 
 * startup activity
 * @author kmchugh
 */
public class SplashActivity
        extends BaseActivity<SplashActivity>
{
    /**
     * Creates a new instance of the splash activity
     */
    public SplashActivity()
    {
    }

    @Override
    public boolean canShowTitle()
    {
        return false;
    }

    /**
     * Creates a new instance of the splash activity
     * @param toImplementation the implementation to use
     */
    protected SplashActivity(ActivityImpl<SplashActivity> toImplementation)
    {
        super(toImplementation);
    }

    @Override
    public void onContentReady()
    {
        super.onContentReady();
        setContentText(Application.getInstance().getName());
        setVersionText(Application.getInstance().getVersion().toString());
        setIDText(Application.getInstance().getInstanceGUID());
    }

    
    /**
     * Sets the text of the version text area in the splash screen
     * @param tcText the text to set the version to
     */
    public void setVersionText(String tcText)
    {
        TextView loTextView = (TextView)findViewById(R.id.splash_version);
        if (loTextView != null)
        {
            loTextView.setText(tcText);
        }
    }
    
    /**
     * Sets the text of the content text area in the splash screen
     * @param tcText the text to set the content to
     */
    public void setContentText(String tcText)
    {
        TextView loTextView = (TextView)findViewById(R.id.splash_content);
        if (loTextView != null)
        {
            loTextView.setText(tcText);
        }
    }

    public void setIDText(String tcText)
    {
        TextView loTextView = (TextView)findViewById(R.id.splash_id);
        if (loTextView != null)
        {
            loTextView.setText(tcText);
        }
    }
    
    /**
     * Sets the image that is used for the splash screen
     */
    public void setImage(int tnImageResourceID)
    {
        ImageView loImageView = (ImageView)findViewById(R.id.splash_screen);
        loImageView.setImageResource(tnImageResourceID);
    }

    @Override
    public int getPortraitViewResourceID()
    {
        return R.layout.splash;
    }
    
    /**
     * Occurs when the splash screen has been clicked
     */
    public void onSplashClicked(View toView)
    {
        finish();
    }
}
