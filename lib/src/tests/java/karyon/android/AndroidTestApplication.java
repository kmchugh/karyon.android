package karyon.android;

import karyon.Version;
import karyon.applications.Application;

/**
 * The AndroidTestApplication is the AndroidApplication that
 * is used for all of the Android Tests
 */
public class AndroidTestApplication
    extends Application<AndroidTestApplication>
{
    public AndroidTestApplication(Version toVersion)
    {
        super(toVersion);
    }

    @Override
    protected boolean onInit()
    {
        return true;
    }

    @Override
    protected boolean onStart()
    {
        return true;
    }
}
