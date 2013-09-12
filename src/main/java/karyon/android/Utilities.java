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
     * Creates a Bundle from a parameter hashmap.  All of the keys
     * of the hashmap will be converted to lower case in the returned bundle
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
                    if (loValue.getClass().isAssignableFrom(String.class))
                    {
                        loReturn.putString(lcKey, (String)loValue);
                    }
                    else
                    {
                        throw new UnsupportedOperationException("Class " + loValue.getClass().getName() + " not yet supported");
                    }
                }
                else
                {
                    throw new UnsupportedOperationException("Non primitives are not yet supported");
                }
            }
            return loReturn;
        }
        return null;
    }

    /**
     * Checks if we are running on a tablet or mobile device
     * @return true if we are on a tablet, false otherwise
     */
    public static boolean isTablet()
    {
        // TODO: refactor to capabilities
        return (AndroidApplicationAdaptor.getInstance().getApplicationContext().getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;

    }
    
    
    // Not createable
    private Utilities()
    {}
    
}
