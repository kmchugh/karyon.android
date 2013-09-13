package karyon.android.behaviours;

import android.widget.TextView;
import karyon.android.controllers.IController;

/**
 * Class to contain the rules around the lifecycle of an activity
 * @author kmchugh
 */
public abstract class ControllerBehaviour<T extends IController>
{
    /**
     * Checks if this activity can be shown, if false is returned, the activity will be closed
     * @param toActivity the activity to check
     * @return true if the activity can be displayed to the user
     */
    public boolean canShow(T toActivity)
    {
        return true;
    }

    /**
     * Occurs when the content view on the Controller has been set, at this point all UI is available
     * for interaction.  If true is returned, then the activity default onContentReady handler will also be run
     * if false is returned then the activities onContentReady handler will not be run
     * @param toActivity the activity that the content view has been set on
     * @return true if the default handler should be run
     */
    public boolean onContentReady(T toActivity)
    {
        return true;
    }

    /**
     * Occurs after the Controller is created, but before the content view is set.  At this stage the UI will not
     * be available
     * @param toActivity  the activity being initialised
     */
    public void onInit(T toActivity)
    {
    }

    /**
     * Occurs when the activity is started either from a restart or from the initial
     * creation
     * @param toActivity  the activity being started
     */
    public void onStart(T toActivity)
    {
    }


    /**
     * Occurs when the activity is restarted after having being stopped.  This usually occurs
     * due to a user navigating back to an activity that had previously been finished.
     * @param toActivity  the activity being restarted
     */
    public void onRestart(T toActivity)
    {

    }

    /**
     * Occurs when the activity is paused.  This occurs when an activity takes the "back seat" but is still
     * on screen and visible.  This could be beause it is not a full screen view and has lost the focus
     * or becuase it is the full screen view but a dialog or another view that is not full screen has been given the 
     * focus
     * @param toActivity  the activity being restarted
     */
    public void onPause(T toActivity)
    {

    }

    /**
     * Occurs when an activity that was previously paused is receiving the focus again, this could be due to the
     * closing of a dialong, or the user interacting when the screen is in the process of turning off
     * @param toActivity  the activity being resumed
     */
    public void onResume(T toActivity)
    {
    }

    /**
     * Occurs when an activity is being removed from memory.  This is usually due to the system deciding that
     * the activity is no longer required either because it is consuming needed memory or because it has been
     * in the closed state for enough time to be sure the user will not reactivate
     */
    public void onDestroy(T toActivity)
    {
    }

    /**
     * Occurs when an activity has been asked to finish, if true is returned the Controller will eventually call
     * finish.  If false is returned, the finish process will be interrupted.
     * @param toActivity the activity that is preparing to finish
     * @return true to allow the activity to finish, false otherwise
     */
    public boolean onFinishing(T toActivity)
    {
        return true;
    }

    /**
     * Occurs when the activity has been notified the memory is getting low
     * @param toActivity the activity that has been notified
     */
    public void onLowMemory(T toActivity)
    {

    }

    /**
     * Occurs when the Controller is being stopped, this could be because the activity was finished due to a user
     * or programmatic interaction, or it could be because the display has turned off meaning this activity is
     * no longer required to be focused.  If toActivity.isPaused() is true then this activity is in the paused state.
     * If toActivity.isFinishing() is true then this activity has been told to finish
     * @param toActivity the activity that is being stopped
     */
    public void onStop(T toActivity)
    {

    }

    /**
     *
     * @param toActivity the activity that contains the text view
     * @param tnID the id of the textview
     * @param tcText the text to set on the text view
     * @return true if the textview was found and set
     */
    protected final boolean setTextViewText(T toActivity, int tnID, String tcText)
    {
        TextView loView = (TextView)toActivity.findViewById(tnID);
        if (loView != null)
        {
            loView.setText(tcText);
            return true;
        }
        return false;
    }
}