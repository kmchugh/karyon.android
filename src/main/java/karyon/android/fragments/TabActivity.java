/**
 * FragmentTabHost is in the android support libraries V13,
 * It is required for a tabview but can't be included at the
 * moment because v13 isn't available through maven
 */

package karyon.android.fragments;

import karyon.android.controllers.Controller;
import karyon.collections.HashMap;
import android.content.Context;
//import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.TabHost;
import karyon.android.R;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 12/31/12
 * Time: 1:54 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class TabActivity
        extends FragmentController
        implements TabHost.OnTabChangeListener
{
    protected class TabDefinition<T extends Controller>
    {
        private String m_cID;
        private String m_cTitle;
        private Class<T> m_oActivity;
        private int m_nIconDrawable;
        private T m_oFragment;
        private String m_cTitleText;
        private int m_nTitleIcon;

        private TabDefinition(String tcID, String tcTitle, Class<T> toActivity, int tnIconDrawable, String tcTitleText, int tnTitleIcon)
        {
            m_cID = tcID;
            m_cTitle = tcTitle;
            m_oActivity = toActivity;
            m_nIconDrawable = tnIconDrawable;
            m_cTitleText = tcTitleText;
            m_nTitleIcon = tnTitleIcon;
        }

        public String getID()
        {
            return m_cID;
        }

        public String getTitle()
        {
            return m_cTitle;
        }

        public Class<T> getFragmentClass()
        {
            return m_oActivity;
        }

        public T getFragment()
        {
            return m_oFragment;
        }

        public void setFragment(T toFragment)
        {
            m_oFragment = toFragment;
        }

        public int getIconDrawable()
        {
            return m_nIconDrawable;
        }

        public int getTitleIcon()
        {
            return m_nTitleIcon;
        }

        public String getTitleText()
        {
            return m_cTitleText;
        }
    }

    private class TabFactory implements TabHost.TabContentFactory
    {
        private final Context m_oContext;

        private TabFactory(Context toContext)
        {
            m_oContext = toContext;
        }

        @Override
        public View createTabContent(String tcTag)
        {
            View loView = new View(m_oContext);
            loView.setMinimumHeight(0);
            loView.setMinimumWidth(0);
            return loView;
        }
    }

    private TabHost m_oTabHost;
    private Map<String, TabDefinition> m_oTabs;
    private TabDefinition m_oLastTab;

    private TabHost getTabHost()
    {
        if (m_oTabHost == null)
        {
            m_oTabHost = (TabHost)findViewById(android.R.id.tabhost);
            m_oTabHost.setup();
            m_oTabHost.setOnTabChangedListener(this);
        }
        return m_oTabHost;
    }


    @Override
    public int getPortraitViewResourceID()
    {
        return R.layout.tabview;
    }

    private Map<String, TabDefinition> getTabs()
    {
        if (m_oTabs == null)
        {
            m_oTabs = new HashMap<String, TabDefinition>();
        }
        return m_oTabs;
    }

    public final TabDefinition getSelectedTab()
    {
        String lcTag = getTabHost().getCurrentTabTag();
        return m_oTabs != null && m_oTabs.containsKey(lcTag) ? m_oTabs.get(lcTag) : null;
    }

    public final <K extends Controller> TabDefinition<K> addTab(String tcTabID, String tcTabText, int tnIcon, Class<K> toActivityClass)
    {
        return this.addTab(tcTabID, tcTabText, tnIcon, toActivityClass, tcTabText, 0);
    }

    public final <K extends Controller> TabDefinition<K> addTab(String tcTabID, String tcTabText, int tnIcon, Class<K> toActivityClass, String tcTitleText, int tnTitleIcon)
    {
        TabDefinition<K> loDef = new TabDefinition(tcTabID, tcTabText, toActivityClass, tnIcon, tcTitleText, tnTitleIcon);
        getTabs().put(tcTabID, loDef);

        TabHost loHost = getTabHost();
        if (loHost != null)
        {
            addTab(loHost, loDef);
        }
        return loDef;
    }

    private void addTab(TabHost toHost, TabDefinition toDef)
    {
        // TODO: Add this back in when FragmentTabHost is available through maven or write our own

        /*
        FragmentTabHost.TabSpec loSpec = toHost.newTabSpec(toDef.getID());
        loSpec.setIndicator(toDef.getTitle(), getResources().getDrawable(toDef.getIconDrawable()));
        loSpec.setContent(new TabFactory(this));
        String lcTag = loSpec.getTag();

        toDef.setFragment((Fragment)getSupportFragmentManager().findFragmentByTag(lcTag));
        Fragment loFragment = toDef.getFragment();
        if (loFragment != null && !loFragment.isDetached())
        {
            FragmentTransaction loTransaction = getSupportFragmentManager().beginTransaction();
            loTransaction.detach(loFragment);
            loTransaction.commit();
            getSupportFragmentManager().executePendingTransactions();
        }
        toHost.addTab(loSpec);

        if (m_oTabs.size() == 1)
        {
            afterTabChanged(toDef);
        }
        */
    }

    @Override
    public void onTabChanged(String tcTag)
    {
        // TODO: Add this back in when FragmentTabHost is available through maven or write our own
        /*
        TabDefinition loTab = m_oTabs.get(tcTag);
        if (m_oLastTab != loTab)
        {
            FragmentTransaction loTransaction = getSupportFragmentManager().beginTransaction();
            if (m_oLastTab != null && m_oLastTab.getFragment() != null)
            {
                loTransaction.detach(m_oLastTab.getFragment());
            }

            if (loTab != null)
            {
                if (loTab.getFragment() == null)
                {
                    Fragment loFragment = Fragment.instantiate(this, loTab.getFragmentClass().getName());
                    loTab.setFragment(loFragment);
                    loTransaction.add(R.id.realtabcontent, loFragment, loTab.getID());
                }
                else
                {
                    loTransaction.attach(loTab.getFragment());
                }
            }

            m_oLastTab = loTab;
            loTransaction.commit();
            getSupportFragmentManager().executePendingTransactions();
        }
        afterTabChanged(loTab);
        */
    }

    protected void afterTabChanged(TabDefinition toDefinition)
    {

    }
}
