package karyon.android1.Controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Allows the setting of custom fonts on a view
 */
public class CustomFontBehaviour
    extends karyon.Object
{
    private static CustomFontBehaviour g_oInstance;

    /**
     * Gets the Singleton instance of CustomFontBehaviour
     * @return the CustomFontBehaviour
     */
    public static CustomFontBehaviour getInstance()
    {
        if (g_oInstance == null)
        {
            g_oInstance = new CustomFontBehaviour();
        }
        return g_oInstance;
    }

    /**
     * No publicly createable
     */
    private CustomFontBehaviour()
    {
    }

    /**
     * Sets the font on the control by determining the font from the XML styleable properties
     * @param toControl The control to set the font on
     * @param toAttributes The properties that have been set for this control
     * @param tnStyleable The resource ID of the type being styled
     * @param tnFontProperty The resource ID of the font property
     * @param <K> The type of control
     * @return true if the font has been set as a result of this call
     */
    public <K extends IControl> boolean setFont(K toControl, AttributeSet toAttributes, int[] tnStyleable, int tnFontProperty)
    {
        TypedArray loAttributes = toControl.getContext().obtainStyledAttributes(toAttributes, tnStyleable);
        String lcFont = loAttributes.getString(tnFontProperty);
        loAttributes.recycle();
        return setFont(toControl, toControl.getContext(), lcFont);
    }

    /**
     * Sets the font on the control by determining the font from the XML styleable properties
     * @param toControl The control to set the font on
     * @param toContext The context to use to load the font
     * @param tcFontAsset The string resource location of the font
     * @param <K> The type of control
     * @return true if the font has been set as a result of this call
     */
    public <K extends IControl> boolean setFont(K toControl, Context toContext, String tcFontAsset)
    {
        if (tcFontAsset != null && !toControl.isInEditMode())
        {
            try
            {
                Typeface loTypeFace = Typeface.createFromAsset(toContext.getAssets(), tcFontAsset);
                toControl.setFont(loTypeFace);
                return true;
            }
            catch (Throwable ex)
            {
                Log.e(toControl.getClass().getSimpleName(), "Could not get typeface (" + tcFontAsset + "): " + ex.getLocalizedMessage());
            }
        }
        return false;
    }
}
