package karyon.Android.Fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import karyon.Android.Activities.IController;


// TODO : refactor Fragment to use an IMPL pattern, Fragment is different from FragmentController,
// A fragment controller is an activity that can contain fragments

public abstract class Fragment<T extends Fragment<T>> extends android.support.v4.app.Fragment
        implements IController
{

    private Runnable m_oOutstandingUpdate;
    private boolean m_lPaused;

    protected Fragment()
    {
    }

    /**
     * Helper method for getting this object from a callback, runnable, or anonymous class
     */
    protected final T getSelf()
    {
        return (T)this;
    }

    /**
     * Initialises the activity when it is being created, if this is the first initilaisation, toSavedInstanceState will be
     * null.  If this is being recreated then toSavedInstanceState will contain the most recent copy of the savedInstanceState
     * @param toSavedInstanceState the saved instance state, or null if there is no saved state
     */
    @Override
    public final View onCreateView(LayoutInflater toInflator, ViewGroup toContainer, Bundle toSavedInstanceState)
    {
        View loView = toContainer == null ? null : onCreate(toSavedInstanceState, toInflator, toContainer);
        if (loView != null)
        {
            this.initialise(toSavedInstanceState, loView);
        }
        return loView;
    }

    protected final View onCreate(Bundle toSavedInstanceState, LayoutInflater toInflator, ViewGroup toContainer)
    {
        // TODO: Load from toSavedInstanceState
        return toInflator.inflate(getContentViewID(), toContainer, false);
    }

    @Override
    public void onCreate(Bundle toSavedInstanceState)
    {
        super.onCreate(toSavedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration toConfig)
    {
        super.onConfigurationChanged(toConfig);
        // TODO: Need to check what happens when the configuration changes
    }

    public final View findViewById(int tnID)
    {
        return getActivity().findViewById(tnID);
    }

    /**
     * Initialises the activity, template method to ensure flexibility in inheritance
     * @param toSavedInstanceState the state that is being used to restore the activity
     */
    private void initialise(Bundle toSavedInstanceState, View toView)
    {
        if (!onInit(toSavedInstanceState, toView))
        {
            finish();
        }
        /*
        // TODO: Implement ControllerManager coverage for Fragments
        if (ControllerManager.getInstance().push(this) &&
                onInit(toSavedInstanceState))
        {
            ControllerManager.getInstance().notifyInit(this);
            updateContentView();
        }
        else
        {
            finish();
        }
        */
    }

    /**
     * Notifies the UI that it should be refreshed.
     */
    public final void invalidate()
    {
        //m_oFragmentControllerImpl.updateUI();
    }

    /**
     * Forces a UI update by calling onUpdateUI on the correct thread.  If this is called multiple times and there
     * is still an outstanding update the additional calls will be considered a no op.
     */
    protected final void updateUI()
    {
        if (!m_lPaused && m_oOutstandingUpdate == null)
        {
            final Fragment<T> loSelf = this;
            m_oOutstandingUpdate = new Runnable()
            {
                public void run()
                {
                    try
                    {
                        loSelf.onUpdateUI();
                    }
                    catch (Throwable ex)
                    {}
                    finally
                    {
                        loSelf.m_oOutstandingUpdate = null;
                    }
                }
            };
            getActivity().runOnUiThread(m_oOutstandingUpdate);
        }
    }

    @Override
    public void runOnUiThread(Runnable action) {
        getActivity().runOnUiThread(m_oOutstandingUpdate);
    }

    @Override
    public boolean isFinishing() {
        return getActivity().isFinishing();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onUpdateUI()
    {
    }

    @Override
    public void onContentChanged()
    {
        // TODO: Implement activity manager for content changing?  May not need to do this depending on how fragments work
        /*
        super.onContentChanged();
        if (ControllerManager.getInstance().notifyContentReady(this))
        {
            onContentReady();
        }*/
    }

    /**
     * Occurs when the activity is being created, at this stage the ControllerManager has already checked if the activity can be displayed.
     * If toSavedInstanceState is null then this is a fresh instance, if it is not null then we are attempting to create the instance from stored data.
     * If true is returned from this method then the content view will be updated and the activity manager will be notified of initialisation
     * @param toSavedInstanceState the instance date to restore from, or null if a clean instance
     * @return true if initialisation was successful
     */
    protected boolean onInit(Bundle toSavedInstanceState, View toView)
    {
        return false; // m_oFragmentControllerImpl.onInit();
    }

    @Override
    public void onContentReady()
    {
        //m_oFragmentControllerImpl.onContentReady();
    }

    /**
     * Notifies the activity that it should close.  The activity will check with the activity manager before
     * closing
     */
    public final void finish()
    {
        // TODO : Implement finishing with the activity manager
        /*
        if (ControllerManager.getInstance().notifyFinishing(this))
        {
            super.finish();
        }*/
        // For now just remove the Fragment
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    /**
     * Checks if this view is paused
     * @return true if paused
     */
    public boolean isPaused()
    {
        // TODO: Test this, it may be possible to simply use isFinishing for this same data
        return m_lPaused;
    }

    @Override
    public final void onDestroy()
    {
        // TODO : Implement destory in ControllerManager for Fragments
        // ControllerManager.getInstance().notifyDestroy(this);
        super.onDestroy();
    }

    @Override
    public final void onLowMemory()
    {
        notifyLowMemory();
        super.onLowMemory();
    }

    /**
     * Notifies the Controller manager that memory is low.
     * If this is overridden, super should always be called.
     */
    public void notifyLowMemory()
    {
        // TODO: Implement notifications on ActvityManager for Fragments
        //ControllerManager.getInstance().notifyLowMemory(this);
    }

    @Override
    public final void onPause()
    {
        m_lPaused = true;
        // TODO: Implement notifications on ActvityManager for Fragments
        //ControllerManager.getInstance().notifyPause(this);
        super.onPause();
    }

    //@Override
    public final void onRestart()
    {
        // TODO: Implement notifications on ActvityManager for Fragments
        //ControllerManager.getInstance().notifyRestart(this);
        //super.onRestart();
    }

    @Override
    public final void onResume()
    {
        m_lPaused = false;
        // TODO: Implement notifications on ActvityManager for Fragments
        //ControllerManager.getInstance().notifyResume(this);
        super.onResume();
    }

    @Override
    public final void onStart()
    {
        // TODO: Implement notifications on ActvityManager for Fragments
        //ControllerManager.getInstance().notifyStart(this);
        super.onStart();
    }

    @Override
    public final void onStop()
    {
        notifyStop();
        super.onStop();
    }

    /**
     * Notifies the Controller manager that the view is stopping
     * If this is overridden, super should always be called.
     */
    public void notifyStop()
    {
        // TODO: Implement notifications on ActvityManager for Fragments
        //ControllerManager.getInstance().notifyStop(this);
    }

    /**
     * Hook to allow overriding of the default view resources
     * @return the integer ID of the layout resource to initialise the view with
     */
    public int getContentViewID()
    {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ?
                getLandscapeViewResourceID() :getPortraitViewResourceID();
    }

    /**
     * Gets the resource view for the portrait version of this view.
     * @return the view resource ID
     */
    public abstract int getPortraitViewResourceID();

    /**
     * Gets the resource view for the Landscape version of this view.  By default will return the
     * Portrait view
     * @return the view resource ID
     */
    public int getLandscapeViewResourceID()
    {
        return getPortraitViewResourceID();
    }

    public static Fragment instantiate(Context toContext, String tcClassName)
    {
        return (Fragment)android.support.v4.app.Fragment.instantiate(toContext, tcClassName, null);
    }

    public static Fragment instantiate(Context toContext, String tcClassName, Bundle toArgs)
    {
        return (Fragment)android.support.v4.app.Fragment.instantiate(toContext, tcClassName, null);
    }
}
