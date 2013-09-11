package karyon.android1.logging1;

import karyon.constants.LogType;
import karyon.logging.Logger;
import android.util.Log;
import karyon.android1.applications1.Application;

/**
 * Logging for the android
 */
public class AndroidLogger
    extends Logger
{
    private String m_cTag;

    @Override
    public void log(String tcMessage, LogType toType)
    {
        if (m_cTag == null)
        {
            m_cTag = Application.getInstance().getName();
        }

        if (toType.getLogLevel() <= LogType.ERROR.getLogLevel())
        {
            Log.e(m_cTag, tcMessage);
        }
        else if (toType == LogType.WARNING)
        {
            Log.w(m_cTag, tcMessage);
        }
        else
        {
            Log.i(m_cTag, tcMessage);
        }
    }
}
