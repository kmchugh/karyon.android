package karyon.android1.controls1;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import karyon.android1.R;

/**
 * Base webview for Karyon Activities
 */
public class WebView
    extends android.webkit.WebView
    implements IControl
{
    /**
     * Creates a new instance of TextView
     * @param toContext the context of the class
     */
    public WebView(Context toContext)
    {
        super(toContext);
    }

    /**
     * Creates a new instance of TextView
     * @param toContext the context of the class
     * @param toAttributes the control attributes
     */
    public WebView(Context toContext, AttributeSet toAttributes)
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
    public WebView(Context toContext, AttributeSet toAttributes, int tnStyle)
    {
        super(toContext, toAttributes, tnStyle);
        CustomFontBehaviour.getInstance().setFont(this, toAttributes, R.styleable.TextView, R.styleable.TextView_customFont);
    }

    @Override
    public void setFont(Typeface toTypeface)
    {
        // Does nothing
    }
}
