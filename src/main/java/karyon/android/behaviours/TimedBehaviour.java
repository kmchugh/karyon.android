package karyon.android.behaviours;


import karyon.android.activities.IActivity;

/**
 * A timed behaviour will cause timerEvent to occur after the specified timeout.  A tick notification will be
 * given on the specified tick count
 * @author kmchugh
 */
public abstract class TimedBehaviour<T extends IActivity<T>>
        extends Behaviour<T>
{
    private long m_nTimeout;
    private long m_nTick;
    private boolean m_lRunning;

    // Not using AsyncTask because there are problems with using multiples
    private Thread m_oTimer;

    /**
     * Creates an instance of TimedBehaviour which will cause a tick event
     * @param tnTick the number of millis between each tick event
     */
    public TimedBehaviour(long tnTick)
    {
        this(-1, tnTick);
    }

    /**
     * Creates a new instance of Timed Behaviour which will fire timerEvent after tnMillis.  tnTick is the
     * notification interval
     * @param tnMillis the number of milliseconds before calling timerEvent
     * @param tnTick the interval between calling tick
     */
    public TimedBehaviour(long tnMillis, long tnTick)
    {
        m_nTimeout = tnMillis;
        m_nTick = tnTick;
    }

    /**
     * Occurs at the tick interval allowing interaction with the timer.  The return expected is the number of 
     * milliseconds remaining after this call, usually tnRemaining.  Return a value of 0 or less to fire the 
     * timer event immediately
     * @param toActivity the activity that the tick occured on
     * @param tnRemaining the remaining number of milliseconds until the timer event
     * @return the number of milliseconds remaining after this call
     */
    public abstract long tick(T toActivity, long tnRemaining);

    /**
     * The timer event that will occur after the timer expires
     * @param toActivity the activity the timer event occured on
     */
    public abstract void timerEvent(T toActivity);

    /**
     * Stops the timer task
     */
    private void stopTimer()
    {
        m_lRunning = false;
        m_oTimer = null;
    }

    @Override
    public boolean onInit(T toActivity)
    {
        startTimer(toActivity);
        return true;
    }

    @Override
    public boolean onResume(T toActivity)
    {
        startTimer(toActivity);
        return true;
    }

    @Override
    public boolean onStop(T toActivity)
    {
        if (toActivity.isFinishing())
        {
            stopTimer();
        }
        return true;
    }

    @Override
    public boolean onFinish(T toActivity)
    {
        stopTimer();
        return !m_lRunning;
    }

    /**
     * Starts up the timer if it has not already been started
     */
    protected final void startTimer(final T toActivity)
    {
        if (!m_lRunning && (m_oTimer == null || !m_oTimer.isAlive()))
        {
            m_lRunning = true;
            m_oTimer = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        long lnProgress = 0;
                        while (m_lRunning)
                        {
                            if (m_lRunning)
                            {
                                lnProgress = tick(toActivity, lnProgress);
                            }

                            try
                            {
                                Thread.sleep(m_nTick);
                            }
                            catch (InterruptedException ex)
                            {
                                // Nothing to do here
                            }

                            lnProgress+=m_nTick;
                            if (m_nTimeout > 0 && lnProgress >= m_nTimeout && m_lRunning)
                            {
                                m_lRunning = false;
                                timerEvent(toActivity);
                            }
                        }
                    }
                    finally
                    {
                        m_lRunning = false;
                    }
                }
            });
            m_oTimer.start();
        }
    }
}