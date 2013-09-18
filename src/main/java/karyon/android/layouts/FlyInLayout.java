package karyon.android.layouts;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * The fly in layout is a layout that
 * allows for a fly in menu
 */
public class FlyInLayout
    extends LinearLayout
{
    private View m_oMenu;
    private View m_oContent;

    private int m_nMenuMargin;
    private int m_nContentOffset;
    private MenuState m_nMenuState;

    public enum MenuState
    {
        CLOSED,
        OPEN,
        CLOSING,
        OPENING
    };

    public FlyInLayout(Context toContext)
    {
        super(toContext);
        initialiseComponent();
    }

    public FlyInLayout(Context toContext, AttributeSet toAttributes)
    {
        super(toContext, toAttributes);
        initialiseComponent();
    }

    private Scroller m_oMenuScroller;
    private Runnable m_oMenuRunnable;
    private Handler m_oMenuHandler;
    private int m_nAnimationDuration;
    private int m_nPollingInterval;

    private void initialiseComponent()
    {
        m_nMenuMargin = 50;
        m_nMenuState = MenuState.CLOSED;
        m_oMenuScroller = new Scroller(this.getContext(), new SmoothInterpolator());
        m_oMenuRunnable = new AnimationRunnable();
        m_oMenuHandler = new Handler();
        m_nAnimationDuration = 500;
        m_nPollingInterval = 16;
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();

        m_oMenu = this.getChildAt(0);
        m_oContent = this.getChildAt(1);

        m_oMenu.setVisibility(View.GONE);
    }

    @Override
    protected void onLayout(boolean tlChanged, int tnLeft, int tnTop, int tnRight, int tnBottom)
    {
        if (tlChanged)
        {
            calculateChildDimensions();
        }

        m_oMenu.layout(tnLeft, tnTop, tnRight - m_nMenuMargin, tnBottom);

        m_oContent.layout(tnLeft + m_nContentOffset, tnTop, tnRight + m_nContentOffset, tnBottom);
    }

    public void toggleMenu()
    {
        switch (m_nMenuState)
        {
            case CLOSED:
                m_nMenuState = MenuState.OPENING;
                m_oMenu.setVisibility(View.VISIBLE);
                m_oMenuScroller.startScroll(0, 0, getMenuWidth(), 0, m_nAnimationDuration);
                break;
            case OPEN:
                m_nMenuState = MenuState.CLOSING;
                m_oMenuScroller.startScroll(m_nContentOffset, 0, -m_nContentOffset, 0, m_nAnimationDuration);
                break;
            default:
                return;
        }

        m_oMenuHandler.postDelayed(m_oMenuRunnable, m_nPollingInterval);
    }

    private int getMenuWidth()
    {
        return m_oMenu.getLayoutParams().width;
    }

    private void calculateChildDimensions()
    {
        m_oContent.getLayoutParams().height = getHeight();
        m_oContent.getLayoutParams().width = getWidth();

        m_oMenu.getLayoutParams().width = getWidth() - m_nMenuMargin;
        m_oMenu.getLayoutParams().height = getHeight();
    }

    private class SmoothInterpolator
        implements Interpolator
    {
        @Override
        public float getInterpolation(float tnInput)
        {
            return (float)Math.pow(tnInput-1, 5)+1;
        }
    }

    private class AnimationRunnable
        implements Runnable
    {
        @Override
        public void run()
        {
            boolean llOngoing = FlyInLayout.this.m_oMenuScroller.computeScrollOffset();

            FlyInLayout.this.adjustContentPosition(llOngoing);
        }
    }

    private void adjustContentPosition(boolean tlIsOngoing)
    {
        int lnOffset = m_oMenuScroller.getCurrX();
        m_oContent.offsetLeftAndRight(lnOffset - m_nContentOffset);

        m_nContentOffset = lnOffset;

        this.invalidate();

        if (tlIsOngoing)
        {
            m_oMenuHandler.postDelayed(m_oMenuRunnable, m_nPollingInterval);
        }
        else
        {
            onMenuTransitionComplete();
        }
    }

    private void onMenuTransitionComplete()
    {
        switch (m_nMenuState)
        {
            case OPENING:
                m_nMenuState = MenuState.OPEN;
                break;
            case CLOSING:
                m_nMenuState = MenuState.CLOSED;
                m_oMenu.setVisibility(View.GONE);
                break;
            default:
                return;
        }
    }
}
