package karyon.android1.controls1;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import karyon.android1.R;

/**
 * Base button class, all buttons controls should extend
 * from this class.
 */
public class Button
    extends android.widget.Button
    implements IControl
{
    /**
     * Creates a new instance of Button
     * @param toContext the context of the class
     */
    public Button(Context toContext)
    {
        super(toContext);
    }

    /**
     * Creates a new instance of Button
     * @param toContext the context of the class
     * @param toAttributes the button attributes
     */
    public Button(Context toContext, AttributeSet toAttributes)
    {
        super(toContext, toAttributes);
        CustomFontBehaviour.getInstance().setFont(this, toAttributes, R.styleable.Button, R.styleable.Button_customFont);
    }

    /**
     * Creates a new instance of Button
     * @param toContext the context of the class
     * @param toAttributes the button attributes
     * @param tnStyle the button style
     */
    public Button(Context toContext, AttributeSet toAttributes, int tnStyle)
    {
        super(toContext, toAttributes, tnStyle);
        CustomFontBehaviour.getInstance().setFont(this, toAttributes, R.styleable.Button, R.styleable.Button_customFont);
    }

    @Override
    public void setFont(Typeface toTypeface)
    {
        this.setTypeface(toTypeface);
    }
}
