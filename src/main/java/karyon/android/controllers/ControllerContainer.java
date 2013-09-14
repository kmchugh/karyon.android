package karyon.android.controllers;

import android.support.v4.app.FragmentActivity;

/**
 * The Controller Container is the base activity class.
 * All activity classes should extend from here.
 *
 * A Controller Container can contain one or more fragments
 */
public class ControllerContainer
    extends FragmentActivity
{

    /**
     * Allows setting of window features
     * @param tnFeature the feature to set
     * @return true if the feature has been correctly set
     */
    public final boolean setWindowFeature(int tnFeature)
    {
        return requestWindowFeature(tnFeature);
    }
}
