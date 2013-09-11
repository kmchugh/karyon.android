package karyon.android1.controls1;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Karyon Control Interface for Android Controls
 */
public interface IControl
{
    /**
     * Sets the typeface of the control to the font specified
     * @param toTypeface the typeface
     */
    void setFont(Typeface toTypeface);

    /**
     * Gets the context for this control
     * @return the controls context
     */
    Context getContext();

    /**
     * Determines if the control is in edit mode.  Edit mode
     * would usually mean the control is being designed in
     * a designer
     * @return true if in edit mode, false otherwise
     */
    boolean isInEditMode();
}
