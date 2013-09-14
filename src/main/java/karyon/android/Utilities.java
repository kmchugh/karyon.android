package karyon.android;

import karyon.android.applications.AndroidApplicationAdaptor;
import karyon.collections.HashMap;
import karyon.dynamicCode.Java;
import android.content.res.Configuration;
import android.os.Bundle;

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
                        loReturn.putString(lcKey, (String)loValue);
                    }
                    else if(loPrimitive == boolean.class)
                    {
                        loReturn.putBoolean(lcKey, ((Boolean)loValue).booleanValue());
                    }
                    else if(loPrimitive == double.class)
                    {
                        loReturn.putDouble(lcKey, ((Double)loValue).doubleValue());
                    }
                    else if(loPrimitive == float.class)
                    {
                        loReturn.putFloat(lcKey, ((Float)loValue).floatValue());
                    }
                    else if(loPrimitive == int.class)
                    {
                        loReturn.putInt(lcKey, ((Integer)loValue).intValue());
                    }
                    else if(loPrimitive == long.class)
                    {
                        loReturn.putLong(lcKey, ((Long)loValue).longValue());
                    }
                    else if(loPrimitive == short.class)
                    {
                        loReturn.putShort(lcKey, ((Short)loValue).shortValue());
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
    
    // Not createable
    private Utilities()
    {}
    
}
