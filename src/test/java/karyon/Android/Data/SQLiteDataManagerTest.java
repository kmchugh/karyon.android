package karyon.Android.Data;

import Karyon.Data.DataManager;
import Karyon.Data.IDataManager;
import com.xtremelabs.robolectric.Robolectric;
import karyon.Android.CustomRoboTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(CustomRoboTestRunner.class)
public class SQLiteDataManagerTest
{


    @Before
    public void setUp() throws Exception
    {
        android.app.Application loApp = Robolectric.application;
        loApp.onCreate();
    }

    @Test
    public void testIsDataStoreCreated() throws Exception
    {
        IDataManager loManager = DataManager.getInstance();
        assertTrue(loManager.isDataStoreCreated());
    }

    @Test
    public void testCreateDataStore() throws Exception
    {
    }

    @Test
    public void testDeleteDataStore() throws Exception
    {
    }
}
