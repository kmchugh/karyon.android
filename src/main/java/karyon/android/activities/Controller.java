package karyon.android.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * Core activity class.  All android activities should extend from this activity
 * @author kmchugh
 */
public abstract class Controller<T extends Controller<T>>
    extends android.app.Activity
    implements IController
{
    private ControllerImpl<T> m_oControllerImpl;

    /**
     * Creates a new instance of controller, controller
     * is not publicly creatable.
     * This will also initialise the Controller Implementation
     */
    protected Controller()
    {
        m_oControllerImpl=new ControllerImpl(this);
    }

    /**
     * Allows customising the implementation classes
     * @param toImplementation the implementation of this controller
     */
    protected Controller(ControllerImpl<T> toImplementation)
    {
        m_oControllerImpl = toImplementation;
    }

    /**
     * Helper method for getting this object from a callback, runnable, or anonymous class
     */
    protected final T getSelf()
    {
        return (T)this;
    }

    /**
     * Initialises the activity when it is being created, if this is the first initialisation, toSavedInstanceState will be
     * null.  If this is being recreated then toSavedInstanceState will contain the most recent copy of the savedInstanceState
     * @param toSavedInstanceState the saved instance state, or null if there is no saved state
     */
    @Override
    protected void onCreate(Bundle toSavedInstanceState)
    {
        super.onCreate(toSavedInstanceState);
        m_oControllerImpl.onCreate(toSavedInstanceState);
    }

    @Override
    public int getCustomTitleDrawable()
    {
        return 0;
    }

    @Override
    public boolean canShowTitle()
    {
        return true;
    }

    @Override
    public Context getContext()
    {
        return this;
    }

    /**
     * Occurs when the activity configuration has been changed, e.g. orientation
     * @param toConfig the new configuration
     */
    @Override
    public final void onConfigurationChanged(Configuration toConfig)
    {
        super.onConfigurationChanged(toConfig);
        m_oControllerImpl.updateContentView();
    }
    
    @Override
    public final boolean setWindowFeature(int tnFeature)
    {
        return requestWindowFeature(tnFeature);
    }

    /**
     * Notifies the UI that it should be refreshed.
     */
    public final void invalidate()
    {
        m_oControllerImpl.updateUI();
    }

    @Override
    public void onUpdateUI()
    {
    }

    @Override
    public final void onContentChanged()
    {
        super.onContentChanged();
        m_oControllerImpl.onContentChanged();
    }

    @Override
    public void onContentReady()
    {
        invalidate();
    }

    @Override
    public final void finish()
    {
        if (!m_oControllerImpl.isFinishing())
        {
            m_oControllerImpl.finish();
        }
        else
        {
            onFinish();
            super.finish();
        }
    }

    public void onFinish()
    {
    }

    @Override
    public boolean onInit(Bundle toSavedInstanceState)
    {
        return true;
    }

    @Override
    public boolean isPaused()
    {
        return m_oControllerImpl.isPaused();
    }

    @Override
    public final void onDestroy()
    {
        m_oControllerImpl.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory()
    {
        m_oControllerImpl.onLowMemory();
    }

    @Override
    public final void onPause()
    {
        m_oControllerImpl.onPause();
        super.onPause();
    }

    @Override
    public final void onRestart()
    {
        m_oControllerImpl.onRestart();
        super.onRestart();
    }

    @Override
    public final void onResume()
    {
        m_oControllerImpl.onResume();
        super.onResume();
    }

    @Override
    public final void onStart()
    {
        m_oControllerImpl.onStart();
        super.onStart();
    }

    @Override
    public void onStop()
    {
        m_oControllerImpl.onStop();
        super.onStop();
    }


    @Override
    public abstract int getPortraitViewResourceID();

    @Override
    public int getLandscapeViewResourceID()
    {
        return getPortraitViewResourceID();
    }

    
    /*

    // TODO: This all needs to be pushed in to a behaviour

    protected boolean authenticationRequired(boolean tlRequire)
    {
        if (tlRequire && !SessionManager.getInstance().getCurrentSession().isAuthenticated())
        {
            finish();
            return false;
        }
        else
        {
            return true;        	
        }
    }
    
    public boolean startActivityWhenAuthenticated(Intent toIntent)
    {
        if (!SessionManager.getInstance().getCurrentSession().isAuthenticated())
        {
            // TODO: This code exists more than once, needs to be refactored
            Intent loIntent = new Intent(getBaseContext(), WebActivity.class);
            loIntent.putExtra("com.youcommentate.authenticationURL", YouCommentate.getInstance().getURL("AUTHENTICATEURL"));
            startActivity(loIntent);
        }
        
        if (SessionManager.getInstance().getCurrentSession().isAuthenticated())
        {
            startActivity(toIntent);
            return true;
        }
        return false;
    }
    * 
    */

    
    
    
    
}
