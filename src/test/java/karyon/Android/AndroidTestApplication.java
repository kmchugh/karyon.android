package karyon.Android;

import Karyon.ISessionManager;
import Karyon.Version;
import karyon.Android.Applications.Application;

/**
 * The AndroidTestApplication is the AndroidApplication that
 * is used for all of the Android Tests
 */
public class AndroidTestApplication
    extends Application
{
    public AndroidTestApplication(Version toVersion, AndroidApplication toApp)
    {
        super(toVersion, toApp);
    }

    @Override
    public ISessionManager createSessionManager()
    {
        return null;
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
