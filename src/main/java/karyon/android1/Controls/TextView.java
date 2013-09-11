package karyon.android1.Controls;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import karyon.android1.R;

/**
 * Base textview for Karyon
 */
public class TextView
    extends android.widget.TextView
    implements IControl
{
    /**
     * Creates a new instance of TextView
     * @param toContext the context of the class
     */
    public TextView(Context toContext)
    {
        super(toContext);
    }

    /**
     * Creates a new instance of TextView
     * @param toContext the context of the class
     * @param toAttributes the control attributes
     */
    public TextView(Context toContext, AttributeSet toAttributes)
    {
        super(toContext, toAttributes);
        CustomFontBehaviour.getInstance().setFont(this, toAttributes, R.styleable.TextView, R.styleable.TextView_customFont);
    }

    /**
     * Creates a new instance of TextView
     * @param toContext the context of the class
     * @param toAttributes the control attributes
     * @param tnStyle the control style
     */
    public TextView(Context toContext, AttributeSet toAttributes, int tnStyle)
    {
        super(toContext, toAttributes, tnStyle);
        CustomFontBehaviour.getInstance().setFont(this, toAttributes, R.styleable.TextView, R.styleable.TextView_customFont);
    }

    @Override
    public void setFont(Typeface toTypeface)
    {
        this.setTypeface(toTypeface);
    }
}
