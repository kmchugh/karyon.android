package karyon.android.behaviours;

import karyon.android.activities.IActivity;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 16/9/13
 * Time: 4:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class Behaviour<T extends IActivity<T>>
    extends karyon.Object
{
    /**
     * Creates a new instance of the Controller Behaviour
     */
    protected Behaviour()
    {
    }

    /**
     * Determines if this behaviour is okay to use with the
     * controller specified
     * @param toController the controller that we are finding a behaviour for
     */
    public boolean isValid(T toController)
    {
        return true;
    }

    /**
     * Determines if it is okay to show the activity specified
     * @param toActivity the activity to check
     * @return true if it is okay to display the activity
     */
    public boolean canShow(T toActivity)
    {
        return true;
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
