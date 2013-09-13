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
     * Gets the window this control is current on
     * @return the window for this control
     */
    Window getWindow();

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
