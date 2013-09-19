package karyon.android.behaviours;

import karyon.android.activities.IActivity;
import karyon.applications.Application;
import karyon.collections.HashMap;
import karyon.collections.List;

/**
 * The behaviour factory is used to control which
 * behaviours are created for which activities
 */
public class BehaviourManager
    extends karyon.Object
{
    private static BehaviourManager g_oInstance;

    /**
     * Gets the public instance of the behaviour manager
     * @return the behaviour manager
     */
    public static BehaviourManager getInstance()
    {
        if (g_oInstance == null)
        {
            g_oInstance = new BehaviourManager();
        }
        return g_oInstance;
    }

    private HashMap<Class<? extends IActivity>, List<Class<? extends Behaviour<? extends IActivity>>>> m_oBehaviourMap;
    private HashMap<IActivity, Behaviour<? extends IActivity>> m_oActivityMap;

    /**
     * Not publicly creatable
     */
    private BehaviourManager()
    {
    }

    /**
     * Get the behaviour for the activity specified
     * @param toActivity the activity to get the activity for
     * @param <K> the type of the activity
     * @return the behaviour or null if no behaviour was found
     */
    public <K extends IActivity> Behaviour<K> getBehaviourFor(K toActivity)
    {
        // First check for a live behaviour
        if (m_oActivityMap != null && m_oActivityMap.containsKey(toActivity))
        {
            return (Behaviour<K>)m_oActivityMap.get(toActivity);
        }

        if (m_oBehaviourMap != null && m_oBehaviourMap.containsKey(toActivity.getClass()))
        {
            List<Class<? extends Behaviour<? extends IActivity>>> loBehaviours = m_oBehaviourMap.get(toActivity.getClass());
            for (Class<? extends Behaviour<? extends IActivity>> loBehaviourClass : loBehaviours)
            {
                try
                {
                    Behaviour<K> loBehaviour = (Behaviour<K>)loBehaviourClass.newInstance();
                    if (loBehaviour.isValid(toActivity))
                    {
                        if (m_oActivityMap == null)
                        {
                            m_oActivityMap = new HashMap<IActivity, Behaviour<? extends IActivity>>();
                        }
                        m_oActivityMap.put(toActivity, loBehaviour);
                        return loBehaviour;
                    }
                }
                catch (InstantiationException ex)
                {
                    Application.log(ex);
                }
                catch (IllegalAccessException ex)
                {
                    Application.log(ex);
                }
            }
        }
        return null;
    }

    /**
     * Adds the behaviour to the list of behaviours for the activity type
     * @param toActivityClass the activity class to map the behaviour to
     * @param toBehaviourClass the behaviour class to map
     * @param <K> the type of the activity
     * @return true if the behaviour list changes as a result of this call
     */
    public <K extends IActivity> boolean addBehaviour(Class<K> toActivityClass, Class<? extends Behaviour<K>> toBehaviourClass)
    {
        if (m_oBehaviourMap == null)
        {
            m_oBehaviourMap = new HashMap<Class<? extends IActivity>, List<Class<? extends Behaviour<? extends IActivity>>>>();
        }

        if (!m_oBehaviourMap.containsKey(toActivityClass))
        {
            m_oBehaviourMap.append(toActivityClass, new List<Class<? extends Behaviour<? extends IActivity>>>());
        }

        if (!m_oBehaviourMap.get(toActivityClass).contains(toBehaviourClass))
        {
            return m_oBehaviourMap.get(toActivityClass).add(toBehaviourClass);
        }
        return false;
    }

    /**
     * Removes the behaviour that has been attached to the specified activity
     * @param toActivity the activity to remove the behaviour for
     * @return true if a behaviour was removed, false otherwise
     */
    public boolean removeBehaviourFor(IActivity toActivity)
    {
        return m_oActivityMap != null && m_oActivityMap.remove(toActivity) != null;
    }
}
