package karyon.android;

import karyon.android.applications.AndroidApplicationAdaptor;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 12/9/13
 * Time: 5:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class AndroidTestApplicationAdaptor
    extends AndroidApplicationAdaptor<AndroidTestApplication>
{
    @Override
    protected Class<AndroidTestApplication> getApplicationClass()
    {
        return AndroidTestApplication.class;
    }
}
