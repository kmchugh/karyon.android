package karyon.Android.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;
import karyon.Android.R;

/**
 * A progress Controller is an activity which has the ability
 * to display a progress indicator
 * @author kmchugh
 */
public abstract class ProgressActivity<T extends ProgressActivity<T>>
    extends Controller<T>
{
    private boolean m_lProgressVisible;
    private String m_cProgressMessage;
    private ProgressDialog m_oDialog;

    
    @Override
    public boolean onInit(Bundle toSavedInstanceState)
    {
        boolean llReturn = super.onInit(toSavedInstanceState);
        if (llReturn)
        {
            getWindow().requestFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        }
        return llReturn;
    }

    @Override
    public void onUpdateUI()
    {
        if (m_lProgressVisible)
        {
            // Display the progress
            if (m_oDialog == null)
            {
                m_oDialog = new ProgressDialog(this);
                m_oDialog.setIndeterminate(true);
                m_oDialog.setCancelable(true);
                m_oDialog.setOnCancelListener(new DialogInterface.OnCancelListener() 
                    {
                        public void onCancel(DialogInterface arg0)
                        {
                            onProgressCancelled();
                        }
                    });
            }
            m_oDialog.setMessage(m_cProgressMessage == null ? getString(R.string.please_wait) : m_cProgressMessage);
            m_oDialog.show();
        }
        else
        {
            // Hide the progress
            if (m_oDialog != null)
            {
                m_oDialog.dismiss();
            }
        }
    }
    
    /**
     * Handles the cancellation of the progress dialog
     */
    protected void onProgressCancelled()
    {
        finish();
    }
    
    
    
    
    /**
     * Shows the progress dialog
     */
    public void showProgress()
    {
        showProgress(m_cProgressMessage);
    }
    
    /**
     * Displays the progress dialog with the specified message
     * @param tcMessage the message to display in the progress dialog
     */
    public void showProgress(String tcMessage)
    {
        m_cProgressMessage = tcMessage;
        if (!m_lProgressVisible)
        {
            m_lProgressVisible = true;
            invalidate();
        }
    }
    
    /**
     * Hides the progress dialog
     */
    public void hideProgress()
    {
        if (m_lProgressVisible)
        {
            m_lProgressVisible = false;
            invalidate();
        }
    }
    
}
