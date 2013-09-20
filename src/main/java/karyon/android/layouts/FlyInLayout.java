package karyon.android.layouts;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;
import karyon.android.controls.Button;
import karyon.collections.HashMap;
import karyon.collections.List;

/**
 * The fly in layout is a layout that
 * allows for a fly in menu
 */
public class FlyInLayout
    extends LinearLayout
{
    /**
     * Interpolator for the menu animation
     */
    private class SmoothInterpolator
            implements Interpolator
    {
        @Override
        public float getInterpolation(float tnInput)
        {
            return (float)Math.pow(tnInput-1, 5)+1;
        }
    }

    /**
     * Animation executable
     */
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

    public enum MenuState
    {
        CLOSED,
        OPEN,
        CLOSING,
        OPENING
    };

    private View m_oMenu;
    private HashMap<String, View> m_oContentViews;
    private String m_cCurrentView;
    private int m_nMenuMargin;
    private int m_nContentOffset;
    private MenuState m_nMenuState;
    private Scroller m_oMenuScroller;
    private Runnable m_oMenuRunnable;
    private Handler m_oMenuHandler;
    private int m_nAnimationDuration;
    private int m_nPollingInterval;

    /**
     * Creates a new instance of the FlyInLayout
     * @param toContext the context of the layout
     */
    public FlyInLayout(Context toContext)
    {
        super(toContext);
        initialiseComponent();
    }

    /**
     * Creates a new instance of the FlyInLayout
     * @param toContext the context of the layout
     * @param toAttributes the attributes to use to configure the layout
     */
    public FlyInLayout(Context toContext, AttributeSet toAttributes)
    {
        super(toContext, toAttributes);
        initialiseComponent();
    }

    /**
     * Initialises the component
     */
    private void initialiseComponent()
    {
        m_nMenuMargin = 50;
        m_nMenuState = MenuState.CLOSED;
        m_oMenuScroller = new Scroller(this.getContext(), new SmoothInterpolator());
        m_oMenuRunnable = new AnimationRunnable();
        m_oMenuHandler = new Handler();
        m_nAnimationDuration = 500;
        m_nPollingInterval = 16;
        m_oContentViews = new HashMap<String, View>();
    }

    /**
     * Toggles the menu opened or closed
     */
    public void toggleMenu()
    {
        toggleMenu(true);
    }

    /**
     * Toggles the menu opened or closed
     * @param tlAnimate true to animate the transition change
     */
    public void toggleMenu(boolean tlAnimate)
    {
        switch (m_nMenuState)
        {
            case CLOSED:
                m_nMenuState = MenuState.OPENING;
                getMenu().setVisibility(View.VISIBLE);
                m_oMenuScroller.startScroll(0, 0, getMenuWidth(), 0, m_nAnimationDuration);
                break;
            case OPEN:
                m_nMenuState = MenuState.CLOSING;
                m_oMenuScroller.startScroll(m_nContentOffset, 0, -m_nContentOffset, 0, m_nAnimationDuration);
                break;
            default:
                return;
        }

        if (tlAnimate)
        {
            m_oMenuHandler.postDelayed(m_oMenuRunnable, m_nPollingInterval);
        }
        else
        {
            onMenuTransitionComplete();
        }
    }

    /**
     * Gets the current state of the menu
     * @return the state of the menu
     */
    public MenuState getState()
    {
        return m_nMenuState;
    }

    /**
     * Helper function to get the width of the menu
     * @return the width of the menu
     */
    private int getMenuWidth()
    {
        return getMenu().getLayoutParams().width;
    }

    /**
     * Helper function to get the current content view
     * @return the current content view
     */
    private View getCurrentView()
    {
        if (m_oContentViews.size() == 0)
        {
            // No views have been added yet so just create an empty view
            View loView = new View(getContext());
            m_cCurrentView = "empty";
            m_oContentViews.append(m_cCurrentView, loView);
            addView(loView);
        }
        return m_oContentViews.get(m_cCurrentView);
    }

    /**
     * Calculates teh content and menu dimensions
     */
    private void calculateChildDimensions()
    {
        View loContent = getCurrentView();
        loContent.getLayoutParams().height = getHeight();
        loContent.getLayoutParams().width = getWidth();

        View loMenu = getMenu();
        loMenu.getLayoutParams().width = getWidth() - m_nMenuMargin;
        loMenu.getLayoutParams().height = getHeight();
    }

    /**
     * Moves the content view during the animation
     * @param tlIsOngoing true if this is during the animation
     */
    private void adjustContentPosition(boolean tlIsOngoing)
    {
        int lnOffset = m_oMenuScroller.getCurrX();
        getCurrentView().offsetLeftAndRight(lnOffset - m_nContentOffset);

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

    /**
     * Completes the menu transition from closed to open, or visa versa
     */
    private void onMenuTransitionComplete()
    {
        switch (m_nMenuState)
        {
            case OPENING:
                m_nMenuState = MenuState.OPEN;
                break;
            case CLOSING:
                m_nMenuState = MenuState.CLOSED;
                getMenu().setVisibility(View.GONE);
                break;
            default:
                return;
        }
    }

    /**
     * Adds an item to the menu.  Only unique stringIDs can be added
     * @param tnIconResourceID the icon resource id
     * @param tnStringID the string to display for the menu item
     * @param tnViewID the view to display
     * @return true if the item is actually added.
     */
    public boolean addItem(int tnIconResourceID, int tnStringID, int tnViewID)
    {
        String lcKey = getResources().getString(tnStringID);
        if (!m_oContentViews.containsKey(lcKey))
        {
            View loMenu = getMenu();
            LayoutInflater loInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View loView = loInflater.inflate(tnViewID, null, false);
            loView.setVisibility(View.GONE);
            addView(loView);

            m_oContentViews.append(lcKey, loView);

            if (m_cCurrentView == null || m_cCurrentView.equalsIgnoreCase("empty"))
            {
                View loCurrentView = getCurrentView();
                for (int i=0, lnLength = getChildCount(); i<lnLength; i++)
                {
                    View loChild = getChildAt(i);
                    int lnVisibility = loChild == loMenu || loChild == loCurrentView ? View.VISIBLE : View.GONE;
                    if (loChild.getVisibility() != lnVisibility)
                    {
                        loChild.setVisibility(lnVisibility);
                    }
                }
                setCurrentView(tnStringID);
            }

            Button loButton = new Button(getContext());
            loButton.setId(tnStringID);
            loButton.setText(lcKey);
            loButton.setCompoundDrawablesWithIntrinsicBounds(tnIconResourceID, 0, 0, 0);
            loButton.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View toView)
                {
                    FlyInLayout.this.setCurrentView(toView.getId());
                    FlyInLayout.this.toggleMenu();
                }
            });
            ((ViewGroup)loMenu).addView(loButton);
            return true;
        }
        return false;
    }

    /**
     * Helper method to get the menu view
     * @return
     */
    private View getMenu()
    {
        if (m_oMenu == null)
        {
            m_oMenu = this.getChildCount() == 0 ?
                    new LinearLayout(getContext()) : this.getChildAt(0);
            if (this.getChildCount() == 0)
            {
                addView(m_oMenu);
            }
        }
        return m_oMenu;
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();

        getMenu().setVisibility(View.GONE);
    }

    @Override
    protected void onLayout(boolean tlChanged, int tnLeft, int tnTop, int tnRight, int tnBottom)
    {
        if (tlChanged)
        {
            calculateChildDimensions();
        }

        getMenu().layout(tnLeft, tnTop, tnRight - m_nMenuMargin, tnBottom);

        getCurrentView().layout(tnLeft + m_nContentOffset, tnTop, tnRight + m_nContentOffset, tnBottom);
    }

    /**
     * Sets the current view to the view specified by the string id.
     * This is the same string id that was used to add the view
     * @param tnStringID the id of the string the view maps to
     * @return true if the view was changed, false otherwise
     */
    public boolean setCurrentView(int tnStringID)
    {
        String lcKey = getResources().getString(tnStringID);
        if (m_oContentViews.containsKey(lcKey) && !lcKey.equals(m_cCurrentView))
        {
            if (m_cCurrentView != null)
            {
                getCurrentView().setVisibility(View.GONE);
            }
            m_cCurrentView = lcKey;
            getCurrentView().setVisibility(View.VISIBLE);
            onLayout(true, getLeft(), getTop(), getRight(), getBottom());
            return true;
        }
        return false;
    }
}
