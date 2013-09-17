package karyon.android.applications;

import karyon.applications.propertyManagers.IPropertyManager;
import karyon.text.Utilities;
import android.app.Activity;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * Stores properties in the Shared Preferences, property names and paths are case insensitive in this implementation
 */
public class SharedPreferencesPropertyManager
    extends karyon.Object
    implements IPropertyManager
{
    private SharedPreferences m_oPreferences;
    private Map<String, ?> m_oValues;
    private String m_cPathSeparator;

    /**
     * Initialises the shared preferences, this will use a default separator of '.'
     */
    public SharedPreferencesPropertyManager()
    {
        this(".");
    }

    /**
     * Initialises the shared preferences
     */
    public SharedPreferencesPropertyManager(String tcPathSeparator)
    {
        m_oPreferences = AndroidApplicationAdaptor.getInstance().getSharedPreferences("app.preferences", Activity.MODE_PRIVATE);
        m_cPathSeparator = tcPathSeparator;
        m_oPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences toSharedPreferences, String tcPreference)
            {
                // We don't particularly care what happened, we just clear the values until to notify a change
                m_oValues = null;
            }
        });
    }

    /**
     * Make sure we have the latest values
     */
    private synchronized Map<String, ?> getValues()
    {
        if (m_oValues == null)
        {
            m_oValues = m_oPreferences.getAll();
        }
        return m_oValues;
    }

    @Override
    public <K> K getProperty(String tcPath)
    {
        tcPath = Utilities.normalise(tcPath);
        Map<String, ?> loValues = getValues();
        if (loValues.containsKey(tcPath))
        {
            return (K)loValues.get(tcPath);
        }
        return null;
    }

    @Override
    public <K> K getProperty(String tcPath, K toDefault)
    {
        K loReturn = getProperty(tcPath);
        if (loReturn == null)
        {
            setProperty(tcPath, toDefault);
            loReturn = toDefault;
        }
        return loReturn;
    }

    @Override
    public boolean hasProperty(String tcPath)
    {
        return getValues().containsKey(Utilities.normalise(tcPath));
    }

    @Override
    public <K> K setProperty(String tcPath, K toValue)
    {
        return setProperty(tcPath, toValue, null, getValues());
    }

    @Override
    public boolean setProperties(String tcPath, Map<String, Object> toValues)
    {
        boolean llReturn = false;
        SharedPreferences.Editor loEditor = m_oPreferences.edit();
        Map<String, ?> loValues = getValues();
        for (String lcProperty : toValues.keySet())
        {
            llReturn = (setProperty(
                    ((tcPath == null || tcPath.length() == 0) ? "" : tcPath+m_cPathSeparator) + lcProperty,
                    toValues.get(lcProperty),
                    loEditor, loValues) != null) || llReturn;
        }
        loEditor.commit();
        m_oValues = null;
        return llReturn;
    }

    @Override
    public boolean clearProperty(String tcPath)
    {
        return setProperty(tcPath, null) != null;
    }

    /**
     * Sets the property in the SharedPreferences
     * @param tcPath The path and name of the property to set
     * @param toValue the value of the property
     * @param toEditor the editor that is being used to set the property
     * @param toValues the parameters that are currently in the collection, this is so we don't have to keep looking up
     * @param <K> the type of the property
     * @return the old value of the property if there was one
     */
    private <K> K setProperty(String tcPath, K toValue, SharedPreferences.Editor toEditor, Map<String, ?> toValues)
    {
        tcPath = Utilities.normalise(tcPath);
        K loReturn = toValues.containsKey(tcPath) ? (K)toValues.get(tcPath) : null;
        SharedPreferences.Editor loEditor = toEditor == null ? m_oPreferences.edit() : toEditor;
        if (toValue == null)
        {
            loEditor.remove(tcPath);
        }
        else
        {
            if (toValue.getClass().equals(Integer.class))
            {
                loEditor.putInt(tcPath, (Integer) toValue);
            }
            else if (toValue.getClass().equals(Boolean.class))
            {
                loEditor.putBoolean(tcPath, (Boolean) toValue);
            }
            else if (toValue.getClass().equals(Float.class))
            {
                loEditor.putFloat(tcPath, (Float) toValue);
            }
            else if (toValue.getClass().equals(Long.class))
            {
                loEditor.putLong(tcPath, (Long) toValue);
            }
            else
            {
                loEditor.putString(tcPath, toValue.toString());
            }
        }
        // Need to commit if this was a single set
        if (toEditor == null)
        {
            loEditor.commit();
            m_oValues = null;
        }
        return loReturn;
    }
}
