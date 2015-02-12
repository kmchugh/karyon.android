package karyon.android;

/**
 * Configuration class used to help configure robolectric
 */
public class Config
{
    private int m_nMinSDKVersion = 11;
    private int m_nTargetSDKVersion = 18;

    /**
     * Gets the minimum SDK version for robolectric
     * @return the min sdk version as set by gradle
     */
    public int getMinSDKVersion()
    {
        return m_nMinSDKVersion;
    }

    /**
     * Gets the target SDK version for robolectric
     * @return the target sdk version as set by gradle
     */
    public int getTargetSDKVersion()
    {
        return m_nTargetSDKVersion;
    }
}
