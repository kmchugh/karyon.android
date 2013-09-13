package karyon.android.controllers;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import karyon.applications.Application;
import karyon.constants.LogType;

/**
 * A Controller is intended to control a
 * single view in the system.  A view is based around
 * a fragment which means that views can contain other views.
 */
public abstract class Controller<T extends Controller<T>>
    extends Fragment
    implements IController
{
    /**
     * Helper method for creating a controller
     * @param toClass the class to load
     * @param <K> the return type of the controller
     * @return the Controller that was created
     */
    public static <K extends Controller<K>> K instantiate(Class<K> toClass)
            throws java.lang.InstantiationException, IllegalAccessException
    {
        return Controller.instantiate(toClass, null);
    }

    /**
     * Helper method for creating a controller
     * @param toClass the class to load
     * @param toArgs the bundle arguments to pass to the controller
     * @param <K> the return type of the controller
     * @return the Controller that was created
     */
    public static <K extends Controller<K>> K instantiate(Class<K> toClass, Bundle toArgs)
        throws java.lang.InstantiationException, IllegalAccessException
    {
        K loController = toClass.newInstance();
        loController.setArguments(toArgs);
        return loController;
    }

    private Runnable m_oOutstandingUpdate;
    private boolean m_lPaused;
    private boolean m_lInitialised;

    /**
     * Initialises the activity with any parameters that were used to create the activity.
     * Initialisation occurs during the construction of the activity so the activity will
     * not yet be attached or visible
     */
    private void initialise()
    {
        if (!m_lInitialised)
        {
            m_lInitialised = true;
            Bundle loArgs = getArguments();
            //setArguments(null);

            if (!onInit(loArgs))
            {
                Application.log("Failed to initialise controller " + getClass().getName(), LogType.WARNING);
                finish();
            }
        }
    }

    /**
     * Occurs during the constructon of the controller.  The controller is not attached or visible
     * at this stage.  In most cases when overriding onInit, the return value from super.onInit should be
     * evaluated before attempting anything else
     * @param toArgs the arguments passed to the controller for creation, null if no arguments were passed
     * @return true if initialisation was successful
     */
    protected boolean onInit(Bundle toArgs)
    {
        return true;
    }

    @Override
    public void setArguments(Bundle toArguments)
    {
        super.setArguments(toArguments);
        initialise();
    }













    /**
     * Helper method to get this fragment from a callback
     * @return this object
     */
    protected final T getSelf()
    {
        return (T)this;
    }

    /**

     * @param toSavedInstanceState the saved instance state, or null if there is no saved state
     */

    /**
     * Initialises the activity when it is being created, if this is the first initialisation, toSavedInstanceState will be
     * null.  If this is being recreated then toSavedInstanceState will contain the most recent copy of the savedInstanceState
     * @param toInflater The LayoutInflater object that can be used to inflate any views in the controller
     * @param toContainer If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param toSavedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return the view for the fragments UI, or null if there is no view
     */
    @Override
    public final View onCreateView(LayoutInflater toInflater, ViewGroup toContainer, Bundle toSavedInstanceState)
    {
        View loView = toContainer == null ? null : onCreate(toSavedInstanceState, toInflater, toContainer);
        if (loView != null)
        {
            //this.initialise(toSavedInstanceState, loView);
        }
        return loView;
    }

    /**
     * Creates the view from a layout
     * @param toSavedInstanceState the previously saved instance state
     * @param toInflater The LayoutInflater object that can be used to inflate any views in the fragment
     * @param toContainer If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @return the view for the fragments UI, or null if there is not view
     */
    protected final View onCreate(Bundle toSavedInstanceState, LayoutInflater toInflater, ViewGroup toContainer)
    {
        // TODO: Load from toSavedInstanceState
        return toInflater.inflate(getContentViewID(), toContainer, false);
    }

    @Override
    public View findViewById(int tnID)
    {
        return getActivity().findViewById(tnID);
    }



    /**
     * Notifies the UI that it should be refreshed.
     */
    public final void invalidate()
    {
        updateUI();
    }

    /**
     * Forces a UI update by calling onUpdateUI on the correct thread.  If this is called multiple times and there
     * is still an outstanding update the additional calls will be considered a no op.
     */
    protected final void updateUI()
    {
        if (!m_lPaused && m_oOutstandingUpdate == null)
        {
            final Controller<T> loSelf = this;
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
    public void runOnUiThread(Runnable toAction)
    {
        getActivity().runOnUiThread(toAction);
    }

    @Override
    public boolean isFinishing()
    {
        return getActivity().isFinishing();
    }

    @Override
    public void onUpdateUI()
    {
        // By default we do nothing here
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



    @Override
    public void onContentReady()
    {
        // TODO: implement this
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

    @Override
    public abstract int getPortraitViewResourceID();

    @Override
    public int getLandscapeViewResourceID()
    {
        return getPortraitViewResourceID();
    }

    @Override
    public boolean setWindowFeature(int tnFeature)
    {
        // TODO: Implement this using the controller container
        // TODO: Implement a getContainer to get the parent controller container
        return false;
    }

    @Override
    public int getCustomTitleDrawable()
    {
        // TODO: Implement this using the controller container
        return 0;
    }

    @Override
    public Window getWindow()
    {
        return getActivity().getWindow();
    }

    @Override
    public void setContentView(int tnResourceViewID)
    {
        getActivity().setContentView(tnResourceViewID);
    }

    @Override
    public boolean canShowTitle()
    {
        // TODO: Implement this using the controller container
        return false;
    }

    @Override
    public Context getContext()
    {
        // TODO: Implement this using the controller container
        return null;
    }
}
