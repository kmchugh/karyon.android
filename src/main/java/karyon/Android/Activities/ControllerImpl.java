package karyon.Android.Activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import com.flurry.android.FlurryAgent;
import karyon.Android.Applications.Application;

/**
 * The implementation rules of a Controller
 * @param <T> the controller type
 */
public class ControllerImpl<T extends IController>
    extends Karyon.Object
{
    private boolean m_lPaused;
    private Runnable m_oOutstandingUpdate;
    private boolean m_lIsFinishing;
    private T m_oThis;

    /**
     * Creates a new instance of ControllerImpl
     * @param toActivity the activity that this implementation represents
     */
    public ControllerImpl(T toActivity)
    {
        m_oThis = toActivity;
    }

    /**
     * Gets the Controller that this implementation is functioning over
     * @return the Controller
     */
    protected T getSelf()
    {
        return m_oThis;
    }

    /**
     * Creates and initialise the controller
     * @param toSavedInstanceState the previous controller state if there was one
     */
    public final void onCreate(Bundle toSavedInstanceState)
    {
        boolean llCustomTitle = false;
        if (!m_oThis.canShowTitle())
        {
            m_oThis.setWindowFeature(Window.FEATURE_NO_TITLE);
        }
        else
        {
            if (m_oThis.getCustomTitleDrawable() != 0)
            {
                llCustomTitle = m_oThis.setWindowFeature(Window.FEATURE_CUSTOM_TITLE);
            }
        }
        initialise(toSavedInstanceState);

        if (llCustomTitle)
        {
            m_oThis.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, m_oThis.getCustomTitleDrawable());

            try
            {
                // Remove the padding from the title
                int lnTitleContainer = (Integer)Class.forName("com.android.internal.R$id").getField("title_container").get(null);
                ViewGroup loGroup = ((ViewGroup)m_oThis.getWindow().findViewById(lnTitleContainer));
                if (loGroup != null)
                {
                    loGroup.setPadding(0,0,0,0);
                }
            }
            catch (Throwable ex)
            {
                // This may happen in future versions of the android sdk
            }
        }
    }

    /**
     * Initialises the activity, template method to ensure flexibility in inheritance
     * @param toSavedInstanceState the state that is being used to restore the activity
     */
    private void initialise(Bundle toSavedInstanceState)
    {
        if (ControllerManager.getInstance().push(m_oThis) &&
                m_oThis.onInit(toSavedInstanceState))
        {
            ControllerManager.getInstance().notifyInit(m_oThis);
            updateContentView();
        }
        else
        {
            finish();
        }
    }

    /**
     * Called to update the content view, sets the view to the specified resource
     */
    public final void updateContentView()
    {
        int lnContentID = getContentViewID();
        if (lnContentID != 0)
        {
            m_oThis.setContentView(lnContentID);
        }
    }

    /**
     * Hook to allow overriding of the default view resources
     * @return the integer ID of the layout resource to initialise the view with
     */
    public final int getContentViewID()
    {
        return m_oThis.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ?
                m_oThis.getLandscapeViewResourceID() : m_oThis.getPortraitViewResourceID();
    }

    /**
     * Forces a UI update by calling onUpdateUI on the correct thread.  If this is called multiple times and there
     * is still an outstanding update the additional calls will be considered a no op.
     */
    public final void updateUI()
    {
        if (!m_lPaused && m_oOutstandingUpdate == null)
        {
            final ControllerImpl loSelf = this;
            m_oOutstandingUpdate = new Runnable()
            {
                public void run()
                {
                    try
                    {
                        m_oThis.onUpdateUI();
                    }
                    catch (Throwable ex)
                    {}
                    finally
                    {
                        loSelf.m_oOutstandingUpdate = null;
                    }
                }
            };
            m_oThis.runOnUiThread(m_oOutstandingUpdate);
        }
    }

    /**
     * Occurs when the activity has been notified its content has changed
     */
    public final void onContentChanged()
    {
        if (ControllerManager.getInstance().notifyContentReady(m_oThis))
        {
            m_oThis.onContentReady();
        }
    }

    /**
     * Notifies the activity that it should close.  The activity will check with the activity manager before
     * closing
     */
    public final void finish()
    {
        if (!m_lIsFinishing)
        {
            m_lIsFinishing = true;
            if (ControllerManager.getInstance().notifyFinishing(m_oThis))
            {
                m_oThis.finish();
            }
        }
    }

    /**
     * Checks if the Implementation has started the process of finishing the activity
     * @return true if we are already in the process of finishing
     */
    public boolean isFinishing()
    {
        return m_lIsFinishing;
    }

    /**
     * Checks if this view is paused
     * @return true if paused
     */
    public final boolean isPaused()
    {
        return m_lPaused;
    }

    /**
     * Notifies the ControllerManager that we are low on memory
     */
    public final void onLowMemory()
    {
        ControllerManager.getInstance().notifyLowMemory(m_oThis);
    }

    /**
     * Notifies the controller manater that this activity has been paused
     */
    public final void onPause()
    {
        m_lPaused = true;
        ControllerManager.getInstance().notifyPause(m_oThis);
    }

    /**
     * Notifies the controller manater that this activity has been restarted
     */
    public final void onRestart()
    {
        ControllerManager.getInstance().notifyRestart(m_oThis);
    }

    /**
     * Notifies the controller manater that this activity has been resumed
     */
    public final void onResume()
    {
        m_lPaused = false;
        ControllerManager.getInstance().notifyResume(m_oThis);
    }

    /**
     * Notifies the controller manater that this activity has been started
     */
    public final void onStart()
    {
        // Start up Flurry
        if (Application.getInstance().usesFlurry())
        {
            FlurryAgent.onStartSession(m_oThis.getContext(), Application.getInstance().flurryAPIKey());
        }
        ControllerManager.getInstance().notifyStart(m_oThis);
    }

    /**
     * Notifies the controller manater that this activity has been stopped
     */
    public final void onStop()
    {
        // Stop the Flurry session
        if (Application.getInstance().usesFlurry())
        {
            FlurryAgent.onEndSession(m_oThis.getContext());
        }
        ControllerManager.getInstance().notifyStop(m_oThis);
    }

    /**
     * Notifies the controller manager that this activity has been destroyed
     */
    public final void onDestroy()
    {
        ControllerManager.getInstance().notifyDestroy(m_oThis);
    }
}

