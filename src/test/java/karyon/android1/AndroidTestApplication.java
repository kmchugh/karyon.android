package karyon.android1;

import karyon.ISessionManager;
import karyon.Version;
import karyon.android1.applications1.Application;

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
