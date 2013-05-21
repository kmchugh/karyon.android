package karyon.Android.Controls;

import Karyon.Collections.List;
import Karyon.Date;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import karyon.Android.R;

public class ListView extends android.widget.ListView
{

    private static final int WAITING_FOR_REFRESH = 1;
    private static final int PULL_TO_REFRESH = 2;
    private static final int RELEASE_TO_REFRESH = 3;
    private static final int REFRESHING = 4;

    public static interface RefreshListener
    {
        public void onRefresh(ListView toView);
    }


    private boolean m_lPullToRefresh;
    private TextView m_oPullToRefreshText;
    private TextView m_oLastUpdatedText;
    private ProgressBar m_oProgress;
    private ImageView m_oImage;
    private View m_oHeaderView;
    private int m_nViewState;
    private Date m_dLastRefresh;
    private int m_nHeaderHeight;
    private List<RefreshListener> m_oRefreshListeners;

    public ListView(Context toContext)
    {
        super(toContext);
        initialiseComponent(toContext, null);
    }

    public ListView(Context toContext, AttributeSet toAttributes)
    {
        super(toContext, toAttributes);
        initialiseComponent(toContext, toAttributes);
    }

    public ListView(Context toContext, AttributeSet toAttributes, int tnStyle)
    {
        super(toContext, toAttributes, tnStyle);
        initialiseComponent(toContext, toAttributes);
    }

    public boolean addRefreshListener(RefreshListener toListener)
    {
        if (m_oRefreshListeners == null)
        {
            m_oRefreshListeners = new List<RefreshListener>();
        }

        return !m_oRefreshListeners.contains(toListener) ? m_oRefreshListeners.add(toListener) : false;
    }

    public boolean removeRefreshListener(RefreshListener toListener)
    {
        return m_oRefreshListeners != null ? m_oRefreshListeners.remove(toListener) : false;
    }

    private void initialiseComponent(Context toContext, AttributeSet toAttributes)
    {
        TypedArray loAttributes = this.getContext().obtainStyledAttributes(toAttributes, R.styleable.ListView);
        int lnHeaderView = loAttributes.getResourceId(R.styleable.ListView_pullToRefreshHeaderResource, R.layout.listview_refresh_header);
        m_lPullToRefresh = loAttributes.getBoolean(R.styleable.ListView_pullToRefresh, false);

        loAttributes.recycle();

        if (m_lPullToRefresh)
        {
            LayoutInflater loInflator = (LayoutInflater) toContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            m_oHeaderView = loInflator.inflate(lnHeaderView, this, false);

            m_oImage = (ImageView)m_oHeaderView.findViewById(R.id.pullHandle);
            m_oPullToRefreshText = (TextView)m_oHeaderView.findViewById(R.id.pullToRefreshText);
            m_oLastUpdatedText = (TextView)m_oHeaderView.findViewById(R.id.lastUpdatedText);
            m_oProgress = (ProgressBar)m_oHeaderView.findViewById(R.id.progressBar);

            addHeaderView(m_oHeaderView);
            updateHeader(WAITING_FOR_REFRESH);

            setOnScrollListener(new OnScrollListener()
            {
                @Override
                public void onScrollStateChanged(AbsListView toView, int tnScrollState)
                {
                    if (m_nHeaderHeight <= 0)
                    {
                        m_nHeaderHeight = m_oHeaderView.getMeasuredHeight();
                    }

                    if (tnScrollState == SCROLL_STATE_TOUCH_SCROLL && m_nViewState != REFRESHING)
                    {
                        m_oImage.setVisibility(View.VISIBLE);
                        if ((m_oHeaderView.getBottom() >= m_nHeaderHeight - 10
                                || m_oHeaderView.getTop() >= 0))
                        {
                            updateHeader(RELEASE_TO_REFRESH);
                        }
                        else if (m_oHeaderView.getBottom() < m_nHeaderHeight - 10)
                        {
                            updateHeader(PULL_TO_REFRESH);
                        }
                    }
                }

                @Override
                public void onScroll(AbsListView toView, int tnFirstVisibleItem, int tnVisibleItemCount, int tnTotalItemCount)
                {
                    if (m_lPullToRefresh && tnFirstVisibleItem == 0 && m_nViewState != REFRESHING)
                    {
                        m_oImage.setVisibility(View.VISIBLE);
                        if ((m_oHeaderView.getBottom() >= m_nHeaderHeight - 10
                                || m_oHeaderView.getTop() >= 0))
                        {
                            updateHeader(RELEASE_TO_REFRESH);
                        }
                        else if (m_oHeaderView.getBottom() < m_nHeaderHeight - 10)
                        {
                            updateHeader(PULL_TO_REFRESH);
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void handleDataChanged()
    {
        super.handleDataChanged();
        setSelection(1);
        if (getCount() > 0)
        {
            m_dLastRefresh = new Date();
            updateHeader(WAITING_FOR_REFRESH);
        }
    }

    @Override
    protected void onAttachedToWindow()
    {
        // Hide the pull to refresh view by selecting the first item in the list
        super.onAttachedToWindow();
        setSelection(1);
    }

    @Override
    public void setAdapter(ListAdapter toAdapter)
    {
        // Hide the pull to refresh view by selecting the first item in the list
        super.setAdapter(toAdapter);
        setSelection(1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent toEvent)
    {
        if (m_lPullToRefresh)
        {
            switch (toEvent.getAction())
            {
                case MotionEvent.ACTION_UP:
                    if (!isVerticalScrollBarEnabled())
                    {
                        setVerticalScrollBarEnabled(true);
                    }
                    if (getFirstVisiblePosition() == 0 && m_nViewState != REFRESHING)
                    {
                        if ((m_oHeaderView.getBottom() >= m_nHeaderHeight
                                || m_oHeaderView.getTop() >= 0)
                                && m_nViewState == RELEASE_TO_REFRESH)
                        {
                            updateHeader(REFRESHING);
                            notifyRefresh();
                        }
                        else if (m_oHeaderView.getBottom() < m_nHeaderHeight
                                || m_oHeaderView.getTop() <= 0)
                        {
                            updateHeader(WAITING_FOR_REFRESH);
                            setSelection(1);
                        }
                    }
                    break;
            }
        }
        return super.onTouchEvent(toEvent);
    }

    private String getLastUpdateText()
    {
        return m_dLastRefresh == null ? "" : m_dLastRefresh.toString();
    }

    private void updateHeader(int tnUpdatedState)
    {
        if (m_nViewState == tnUpdatedState)
        {
            return;
        }

        m_nViewState = tnUpdatedState;

        m_oImage.clearAnimation();

        if (this.getCount() <= 1)
        {
            m_nViewState = REFRESHING;
        }

        m_oLastUpdatedText.setText(getLastUpdateText());

        switch (m_nViewState)
        {
            case WAITING_FOR_REFRESH:
                // Same visual state as PULL_TO_REFRESH
            case PULL_TO_REFRESH:
                m_oPullToRefreshText.setText(R.string.pull_to_refresh);
                m_oProgress.setVisibility(GONE);
                m_oImage.setVisibility(VISIBLE);
                //m_oImage.startAnimation(mReverseFlipAnimation);
                break;
            case RELEASE_TO_REFRESH:
                m_oPullToRefreshText.setText(R.string.release_to_update);
                m_oProgress.setVisibility(GONE);
                m_oImage.setVisibility(VISIBLE);
                // //m_oImage.startAnimation(mFlipAnimation);
                break;
            case REFRESHING:
                m_oPullToRefreshText.setText(R.string.refreshing);
                m_oProgress.setVisibility(VISIBLE);
                m_oImage.setVisibility(GONE);
                break;
        }

        m_oHeaderView.invalidate();
    }

    private void notifyRefresh()
    {
        for (RefreshListener loListener : m_oRefreshListeners)
        {
            loListener.onRefresh(this);
        }
    }
}