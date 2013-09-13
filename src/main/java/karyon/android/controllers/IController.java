package karyon.android.controllers;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/**
 * Base interface for Controller classes, a controller is the equivalent to
 * an Activity or Fragment
 */
public interface IController
{
    /**
     * Pass through to requestWindowFeature()
     * @param tnFeature the id of the feature to request
     * @return true if the feature was successfully requested
     */
    boolean setWindowFeature(int tnFeature);

    /**
     * Gets the drawable id for the custom title if there is one
     * @return the custom title drawable or 0 if there is not one
     */
    int getCustomTitleDrawable();

    /**
     * Gets the window this control is current on
     * @return the window for this control
     */
    Window getWindow();

    /**
     * Sets the content view of the controller
     * @param tnResourceViewID the content resource id
     */
    void setContentView(int tnResourceViewID);

    /**
     * Gets the resources associated with this controller
     * @return the resources
     */
    Resources getResources();

    /**
     * Gets the resource view for the portrait version of this view.
     * @return the view resource ID
     */
    int getPortraitViewResourceID();

    /**
     * Gets the resource view for the Landscape version of this view.  By default will return the
     * Portrait view
     * @return the view resource ID
     */
    int getLandscapeViewResourceID();

    /**
     * Checks if this Controller should display a title or not
     * @return true if a title should be shown
     */
    boolean canShowTitle();

    /**
     * Hook to interact with UI Updates, this will always be called in the UI Thread,
     * this should not be called manually, to force this code call invalidate on the
     * controller
     */
    void onUpdateUI();

    /**
     * Hook for when the content has been set on the activity
     */
    void onContentReady();

    /**
     * Occurs when the activity has been notified its content has changed
     */
    void onContentChanged();

    /**
     * Notifies the activity that it should close.  The activity will check with the activity manager before
     * closing
     */
    void finish();

    /**
     * Checks if this view is paused
     * @return true if paused
     */
    boolean isPaused();

    /**
     * Notifies the ControllerManager that we are low on memory
     */
    void onLowMemory();

    /**
     * Notifies the controller manater that this activity has been paused
     */
    void onPause();

    /**
     * Notifies the controller manater that this activity has been restarted
     */
    void onRestart();

    /**
     * Notifies the controller manater that this activity has been resumed
     */
    void onResume();

    /**
     * Notifies the controller manater that this activity has been started
     */
    void onStart();

    /**
     * Notifies the controller manater that this activity has been stopped
     */
    void onStop();

    /**
     * Notifies the controller manater that this activity has been destroyed
     */
    void onDestroy();

    /**
     * Determines if this controller is in the process of finishing
     * @return true if finishing, false otherwise
     */
    public boolean isFinishing();

    /**
     * Executes the specified action on the UI Thread
     * @param tcAction the action to run
     */
    public void runOnUiThread(java.lang.Runnable tcAction);

    /**
     * Gets the view specified by thte ID if it exists on the Activity.
     * @param tnID the id of the view to get
     * @return the view if it is found, null otherwise
     */
    public View findViewById(int tnID);

    /**
     * Gets the context for this Controller
     * @return the context for the controller
     */
    public Context getContext();
}
