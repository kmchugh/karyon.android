package karyon.android1.Controls;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;
import karyon.android1.R;

/**
 * Base textbox class used for user input, all application textboxes should be derived from this class
 */
public class Textbox
    extends EditText
    implements IControl
{
    /**
     * Creates a new instance of Textbox
     * @param toContext the context of the class
     */
    public Textbox(Context toContext)
    {
        super(toContext);
    }

    /**
     * Creates a new instance of Textbox
     * @param toContext the context of the class
     * @param toAttributes the control attributes
     */
    public Textbox(Context toContext, AttributeSet toAttributes)
    {
        super(toContext, toAttributes);
        CustomFontBehaviour.getInstance().setFont(this, toAttributes, R.styleable.TextView, R.styleable.TextView_customFont);
    }

    /**
     * Creates a new instance of Textbox
     * @param toContext the context of the class
     * @param toAttributes the control attributes
     * @param tnStyle the control style
     */
    public Textbox(Context toContext, AttributeSet toAttributes, int tnStyle)
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
