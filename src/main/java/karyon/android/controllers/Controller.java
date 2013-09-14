package karyon.android.controllers;

import android.app.Activity;
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

    private boolean m_lPaused;
    private boolean m_lInitialised;

    /**
     * Initialises the activity with any parameters that were used to create the activity.
     * Initialisation occurs during the construction of the activity so the activity will
     * not yet be created or visible
     */
    private void initialise()
    {
        if (!m_lInitialised)
        {
            m_lInitialised = true;
            Bundle loArgs = getArguments();
            if (!onInit(loArgs))
            {
                Application.log("Failed to initialise controller " + getClass().getName(), LogType.WARNING);
                finish();
            }
        }
    }

    /**
     * Occurs during the constructon of the controller.  The controller is not created or visible
     * at this stage.  In most cases when overriding onInit, the return value from super.onInit should be
     * evaluated before attempting anything else
     * @param toArgs the arguments passed to the controller for creation, null if no arguments were passed
     * @return true if initialisation was successful
     */
    protected boolean onInit(Bundle toArgs)
    {
        return true;
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
        return toInflater.inflate(getContentViewID(), toContainer, false);
    }

    /**
     * Creates the view from a layout
     * @param toCreateArgs the previously saved instance state
     * @return the view for the fragments UI, or null if there is not view
     */
    @Override
    public final void onCreate(Bundle toCreateArgs)
    {
        initialise();
        super.onCreate(toCreateArgs);
        onCreating(toCreateArgs);
    }

    /**
     * Hook to allow interaction with the view creation cycle.
     * Called after onCreate but before onActivityCreated.  At this stage
     * the view hierarchy is created but the view is not yet attached to it's parent
     * @param toView the view that was inflated for this controller
     * @param toBundle the bundle of arguments
     * @return the view to be used by this controller
     */
    @Override
    public void onViewCreated(View toView, Bundle toBundle)
    {
    }

    /**
     * Hook method to allow interaction with the create process
     * @param toCreateArgs the saved instance state
     * @return true if the view has been created correctly
     */
    protected void onCreating(Bundle toCreateArgs)
    {
    }

    @Override
    public final void onActivityCreated(Bundle toBundle)
    {
        super.onActivityCreated(toBundle);
        onContentReady(toBundle);
    }

    @Override
    public View findViewById(int tnID)
    {
        return getView() != null ? getView().findViewById(tnID) : null;
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
    public void runOnUiThread(Runnable toAction)
    {
        getActivity().runOnUiThread(toAction);
    }

    @Override
    public boolean isFinishing()
    {
        return this.getActivity() != null ? this.getActivity().isFinishing() : this.m_lInitialised && !this.isDetached();
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * This is called before onStart.  At this stage the view hierarchy is complete, the
     * view is not made visible until onStart
     * @param toBundle the bundle of parameters
     */
    protected void onContentReady(Bundle toBundle)
    {
    }

    /**
     * Notifies the activity that it should close.  The activity will check with the activity manager before
     * closing
     */
    public final void finish()
    {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    @Override
    public Context getContext()
    {
        return getView().getContext();
    }

    @Override
    public Window getWindow()
    {
        return getActivity().getWindow();
    }

    /**
     * Checks if this view is paused
     * @return true if paused
     */
    public boolean isPaused()
    {
        return m_lPaused;
    }

    // TODO: All of the on<Action> methods from the activity should fire events that we can listen to with an event manager
    @Override
    public final void onPause()
    {
        m_lPaused = true;
        super.onPause();
    }

    @Override
    public final void onResume()
    {
        m_lPaused = false;
        super.onResume();
    }
}
