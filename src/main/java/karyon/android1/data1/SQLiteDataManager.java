package karyon.android1.data1;

import Karyon.Data.DataConnector;
import Karyon.Data.DataManager;
import Karyon.Data.DataObject;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import karyon.android1.applications1.Application;

/**
 * Data Manager for SQLite data stores on an Android Device
 */
public abstract class SQLiteDataManager
    extends DataConnector
{
    /**
     * SQLite Helper class
     */
    private class SQLHelper extends SQLiteOpenHelper
    {
        private DataManager m_oManager;
        private Context m_oContext;
        private boolean m_lSkipUpdate;

        private SQLHelper(Context toContext, float tnVersion, DataManager toDataManager)
        {
            super(toContext, Application.getInstance().getApplicationGUID(),
                    null, (int)(tnVersion*1000f));
            m_oManager = toDataManager;
            m_oContext = toContext;
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            //To change body of implemented methods use File | Settings | File Templates.
        }
        /*

        private SQLHelper(Context toContext, float tnVersion, IDataManager toDataManager)
        {
            super(toContext, Application.getInstance().getApplicationGUID(),
                    null, (int)(tnVersion*1000f));
            m_oManager = toDataManager;
            m_oContext = toContext;
        }

        @Override
        public void onCreate(SQLiteDatabase toDB)
        {
            m_oManager.initialise();

            // Mark that the database has been newly created
            m_lSkipUpdate = true;
        }

        @Override
        public void onUpgrade(SQLiteDatabase toDB, int tnOldVersion, int tnNewVersion)
        {
            // Check if we need to upgrade
            if (!m_lSkipUpdate)
            {
                m_lSkipUpdate = true;
                m_oManager.initialise();
            }
        }

        public boolean exists()
        {
            File loDB = Application.getInstance().getApplicationContext().getDatabasePath(Application.getInstance().getApplicationGUID());
            return loDB != null && loDB.exists();
        }
        */


    }

    private SQLHelper m_oHelper;


    /**
     * Creates a new instance of SQLiteDataManager.
     * The underlying SQLHelper is shared through all instances of SQLiteDataManager
     */
    protected SQLiteDataManager()
    {
    }

    /**
     * Gets the helper object for this database
     * @return the database helper object
     */
    private SQLHelper getHelper()
    {
        if (m_oHelper == null)
        {
            m_oHelper = null;//new SQLHelper(Application.getInstance().getApplicationContext(), getCodedVersion(), this);
        }
        return m_oHelper;
    }

    /*
    @Override
    public boolean isDataStoreCreated()
    {
        return getHelper().exists();
    }

    @Override
    public boolean createDataStore()
    {
        return getHelper().getWritableDatabase() != null;
    }

    @Override
    public boolean deleteDataStore()
    {
        Application.getInstance().getApplicationContext().deleteDatabase(Application.getInstance().getApplicationGUID());
        File loDB = Application.getInstance().getApplicationContext().getDatabasePath(Application.getInstance().getApplicationGUID());
        boolean llReturn = true;
        if (loDB.exists())
        {
            llReturn = loDB.delete();
        }
        return llReturn;
    }

    @Override
    public <K extends DataObject> boolean addEntity(Class<K> toEntityType)
    {
        return false;
    }

    @Override
    public <K extends DataObject> boolean removeEntity(Class<K> toEntityType)
    {
        return false;
    }
    */
}
