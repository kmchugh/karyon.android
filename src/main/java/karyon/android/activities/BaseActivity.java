package karyon.android.activities;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * Base Activity is the base activity class.  All
 * pure activities should inherit from this class
 */
public abstract class BaseActivity<T extends IActivity>
    extends Activity
    implements IActivity<T>
{
    /**
     * Helper method for creating an activity
     * @param toClass the class to load
     * @param <K> the return type of the activity
     * @return the Activity that was created
     */
    public static <K extends BaseActivity> K instantiate(Class<K> toClass)
            throws java.lang.InstantiationException, IllegalAccessException
    {
        return toClass.newInstance();
    }

    private ActivityImpl<T> m_oImpl;

    /**
     * Creates a new instance of the base activity
     */
    protected BaseActivity()
    {
        m_oImpl = new ActivityImpl<T>((T)this);
    }

    /**
     * Allows for customisation of the implementation
     * @param toImplementation the implementation to use with this instance
     */
    protected BaseActivity(ActivityImpl<T> toImplementation)
    {
        m_oImpl = toImplementation;
    }

    /**
     * Gets the implementation that is being used for this activity
     * @return the activity that is being used for the activity
     */
    protected ActivityImpl<T> getImpl()
    {
        return m_oImpl;
    }

    @Override
    public final int getContentViewID()
    {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ?
                getLandscapeViewResourceID() : getPortraitViewResourceID();
    }

    @Override
    public int getLandscapeViewResourceID()
    {
        return getPortraitViewResourceID();
    }

    @Override
    public abstract int getPortraitViewResourceID();

    @Override
    public boolean canShowTitle()
    {
        return true;
    }

    @Override
    public int getCustomTitleDrawable()
    {
        return 0;
    }

    @Override
    public final boolean setWindowFeature(int tnFeature)
    {
        return requestWindowFeature(tnFeature);
    }

    /**
     * This is always called when the activity is first created.
     * This is the "entry point" to the activity life cycle
     */
    @Override
    protected final void onCreate(Bundle toPreviousState)
    {
        super.onCreate(toPreviousState);
        m_oImpl.onCreate(toPreviousState);
    }

    @Override
    public boolean onInit(Bundle toSavedState)
    {
        return true;
    }

    /**
     * This is called after the activity has been stopped, before it is being started again.
     * Called before onStart
     */
    @Override
    protected final void onRestart()
    {
        m_oImpl.onRestart();
        super.onRestart();
    }

    @Override
    public void onRestarted()
    {
    }

    /**
     * Called when the activity is becoming visible to the user, after either onCreate or onRestart.
     */
    @Override
    protected final void onStart()
    {
        m_oImpl.onStart();
        super.onStart();
    }

    @Override
    public void onStarted()
    {
    }

    /**
     * Called when the activity will start interacting with the user.  At this point your activity
     * is at the top of the activity stack, with the user input going to your activity
     */
    @Override
    protected final void onResume()
    {
        m_oImpl.onResume();
        super.onResume();
    }

    @Override
    public void onResumed()
    {
    }

    /**
     * Called when the system is about to start resuming a previous activity.  This is typically
     * used to commit unsaved changes to persistent data, stop animations and other things that may be consuming CPU.
     * Implementations of this method should be very quick as the next activity will not be resumed until this
     * method returns
     */
    @Override
    protected final void onPause()
    {
        m_oImpl.onPause();
        super.onPause();
    }

    @Override
    public void onPaused()
    {
    }

    /**
     * Called when the activity is no longer visible to the user, because another activity has been resumed and is
     * covering this one.  This may happen either because a new activity is being started, an existing activity is
     * being brought in front of this one, or this one is being destroyed
     */
    @Override
    protected final void onStop()
    {
        m_oImpl.onStop();
        super.onStop();
    }

    @Override
    public void onStopped()
    {
    }

    /**
     * The final call you receive before your activity is destroyed.  this can happen either because the activity
     * is finishing or because the system is temporarily destroying this instance of the activity to save
     * space.  You can distinguish between these two scenarios with the isFinishing method
     */
    @Override
    protected final void onDestroy()
    {
        m_oImpl.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onDestroyed()
    {
    }

    /**
     * This is called when the overall system is running low on memory and would like actively running processes to try
     * to clear up memory.  The system will perform a gc after returning from the method
     */
    @Override
    public final void onLowMemory()
    {
        m_oImpl.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onMemoryLow()
    {
    }

    /**
     * This hook method is always called when there is a content view change, either by setContentView or by addContentView
     */
    @Override
    public final void onContentChanged()
    {
        super.onContentChanged();
        m_oImpl.onContentChanged();
    }

    @Override
    public void onContentReady()
    {
        // By default, force an update
        invalidate();
    }

    /**
     * Notifies the UI that a refresh is needed
     */
    public final void invalidate()
    {
        m_oImpl.updateUI();
    }

    @Override
    public void onUpdateUI()
    {
    }
}
