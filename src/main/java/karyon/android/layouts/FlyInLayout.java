package karyon.android.layouts;

import android.content.Context;
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

/**
 * The fly in layout is a layout that
 * allows for a fly in menu
 */
public class FlyInLayout
    extends LinearLayout
{
    public static abstract class FlyInHelper
    {
        /**
         * When setCurrentView is called, this method will be called to discover which view
         * should actually be displayed.  This allows for logic when selecting the view.
         * returning tnStringID would simply display the requested view, returning an alternate view
         * id would display that view.  If the view id has not been added, then tnStringID will be returned
         * @param tnStringID the view id that we are being asked to display
         * @return the view id that we are going to display
         */
        protected abstract int translateView(int tnStringID);
    }

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
    private HashMap<Integer, View> m_oViews;
    private int m_nCurrentView;
    private int m_nMenuMargin;
    private int m_nContentOffset;
    private MenuState m_nMenuState;
    private Scroller m_oMenuScroller;
    private Runnable m_oMenuRunnable;
    private Handler m_oMenuHandler;
    private int m_nAnimationDuration;
    private int m_nPollingInterval;
    private FlyInHelper m_oHelper;

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
        m_oViews = new HashMap<Integer, View>();
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
        if (m_oViews.size() == 0)
        {
            // No views have been added yet so just create an empty view
            m_nCurrentView = -1;
            addView(m_nCurrentView, new View(getContext()));
        }
        return m_oViews.get(m_nCurrentView);
    }

    /**
     * Gets the id of the view that is currently being displayed as the content
     * @return the content view id
     */
    public int getCurrentViewID()
    {
        if (m_oViews.size() == 0)
        {
            getCurrentView();
        }
        return m_nCurrentView;
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
     * Adds a view to be available for the content area.  This will not add a menu item
     * but one the view has been added, it is the possible to navigate to the view using
     * setCurrentView.  It is best to use a resource string id or layout id for the identifier as they
     * are generated and unique
     * @param tnViewIdentifier the identifier for the view, use this in setCurrentView to set the content view to toView
     * @param tnLayoutID the layout to inflate and add
     * @return true if the list of views has changed as a result of this call
     */
    public boolean addView(int tnViewIdentifier, int tnLayoutID)
    {
        LayoutInflater loInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return addView(tnViewIdentifier, loInflater.inflate(tnLayoutID, null, false));
    }

    /**
     * Adds a view to be available for the content area.  This will not add a menu item
     * but one the view has been added, it is the possible to navigate to the view using
     * setCurrentView.  It is best to use a resource string id or layout id for the identifier as they
     * are generated and unique
     * @param tnViewIdentifier the identifier for the view, use this in setCurrentView to set the content view to toView
     * @param toView the view that we are adding
     * @return true if the list of views has changed as a result of this call
     */
    public boolean addView(int tnViewIdentifier, View toView)
    {
        if (!m_oViews.containsKey(tnViewIdentifier))
        {
            m_oViews.put(tnViewIdentifier, toView);
            toView.setVisibility(View.GONE);
            addView(toView);
            return true;
        }
        return false;
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
        // Adds a menu item for the view specified
        if (!m_oViews.containsKey(tnStringID))
        {
            View loMenu = getMenu();

            if (addView(tnStringID, tnViewID))
            {
                // If there is no current view, then make sure everything is clean and set the new view to the current view
                if (m_nCurrentView == 0 || m_nCurrentView == -1)
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
                loButton.setText(getResources().getText(tnStringID));
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
        if (m_oHelper != null)
        {
            int lnTranslatedID = m_oHelper.translateView(tnStringID);
            if (m_oViews.containsKey(lnTranslatedID))
            {
                tnStringID = lnTranslatedID;
            }
        }
        if (m_oViews.containsKey(tnStringID) && tnStringID != m_nCurrentView)
        {
            if (m_nCurrentView != 0)
            {
                getCurrentView().setVisibility(View.GONE);
            }
            m_nCurrentView = tnStringID;
            getCurrentView().setVisibility(View.VISIBLE);
            onLayout(true, getLeft(), getTop(), getRight(), getBottom());
            return true;
        }
        return false;
    }

    /**
     * Sets the flyin helper that is being used by this layout
     * @param toHelper the helper that is being used
     */
    public void setFlyInHelper(FlyInHelper toHelper)
    {
        m_oHelper = toHelper;
    }
}
