package karyon.android;

import android.view.Window;
import karyon.android.applications.AndroidApplicationAdaptor;
import karyon.collections.HashMap;
import karyon.dynamicCode.Java;
import android.content.res.Configuration;
import android.os.Bundle;

import java.lang.reflect.Method;

/**
 * Utility functions specifically related to the Android applications
 * @author kmchugh
 */
public class Utilities
{
    /**
     * Creates a Bundle from a parameter hashmap.
     * @param toHashMap the hashmap to create the bundle from
     * @return the Bundle created or null if the hashmap was null or had no keys
     */
    public static Bundle createBundle(HashMap<String, ?> toHashMap)
    {
        if (toHashMap != null && toHashMap.size() > 0)
        {
            Bundle loReturn = new Bundle(toHashMap == null ? 0 : toHashMap.size());
            for (String lcKey : toHashMap.keySet())
            {
                Object loValue = toHashMap.get(lcKey);

                // For the moment, only primitives are supported
                if (loValue == null)
                {
                    // Do nothing for nulls
                }
                else if (Java.isPrimitive(loValue))
                {
                    Class loPrimitive = Java.getPrimitiveClass(loValue.getClass());
                    if (loPrimitive == String.class)
                    {
                        loReturn.putString(lcKey, (String) loValue);
                    }
                    else if(loPrimitive == boolean.class)
                    {
                        loReturn.putBoolean(lcKey, ((Boolean) loValue).booleanValue());
                    }
                    else if(loPrimitive == double.class)
                    {
                        loReturn.putDouble(lcKey, ((Double) loValue).doubleValue());
                    }
                    else if(loPrimitive == float.class)
                    {
                        loReturn.putFloat(lcKey, ((Float) loValue).floatValue());
                    }
                    else if(loPrimitive == int.class)
                    {
                        loReturn.putInt(lcKey, ((Integer) loValue).intValue());
                    }
                    else if(loPrimitive == long.class)
                    {
                        loReturn.putLong(lcKey, ((Long) loValue).longValue());
                    }
                    else if(loPrimitive == short.class)
                    {
                        loReturn.putShort(lcKey, ((Short) loValue).shortValue());
                    }
                    else
                    {
                        throw new UnsupportedOperationException("Class " + loValue.getClass().getName() + " not yet supported");
                    }
                }
                else
                {
                    // TODO: Support non primitives with JSON
                    throw new UnsupportedOperationException("Non primitives are not yet supported");
                }
            }
            return loReturn;
        }
        return null;
    }

    /**
     * Checks if a window has the specified feature flag set
     * @param toWindow the window object to check
     * @param tnFeatureID the ID of the feature to check for
     * @return true if the feature flag is set, false otherwise
     */
    public static synchronized boolean hasFeature(Window toWindow, int tnFeatureID)
    {
        Method loMethod = Java.getMethod(toWindow.getClass(), "getFeatures", null);
        boolean llAccessible = loMethod.isAccessible();
        if (!llAccessible)
        {
            loMethod.setAccessible(true);
        }
        int lnFeatures = 0;
        try
        {
            lnFeatures = ((Integer)loMethod.invoke(toWindow)).intValue();
        }
        catch (Throwable ex)
        {
            // Do nothing here
        }

        loMethod.setAccessible(llAccessible);

        return (lnFeatures & (1 << tnFeatureID)) != 0;
    }
    
    // Not createable
    private Utilities()
    {}
    
}
