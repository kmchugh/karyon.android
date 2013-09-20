package karyon.android.gestures;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import karyon.applications.Application;

/**
 * A basic swipe gesture listener
 */
public abstract class SimpleSwipeListener
    extends karyon.Object
    implements View.OnTouchListener
{
    private class GestureListener
            extends GestureDetector.SimpleOnGestureListener
    {
        private int m_nSwipeThreshold;
        private int m_nVelocityThreshold;

        private GestureListener()
        {
            m_nSwipeThreshold = 100;
            m_nVelocityThreshold = 100;
        }

        @Override
        public boolean onDown(MotionEvent toEvent)
        {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent toEvent1, MotionEvent toEvent2, float tnVelocityX, float tnVelocityY)
        {
            try
            {
                float lnDiffY = toEvent2.getY() - toEvent1.getY();
                float lnDiffX = toEvent2.getX() - toEvent1.getX();
                if (Math.abs(lnDiffX) > Math.abs(lnDiffY))
                {
                    if (Math.abs(lnDiffX) > m_nSwipeThreshold && Math.abs(tnVelocityX) > m_nVelocityThreshold)
                    {
                        if (lnDiffX > 0)
                        {
                            return onSwipeRight();
                        }
                        else
                        {
                            return onSwipeLeft();
                        }
                    }
                }
                else
                {
                    if (Math.abs(lnDiffY) > m_nSwipeThreshold && Math.abs(tnVelocityY) > m_nVelocityThreshold)
                    {
                        if (lnDiffY > 0)
                        {
                            return onSwipeDown();
                        }
                        else
                        {
                            return onSwipeUp();
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                Application.log(ex);
            }

            return false;
        }
    }

    public boolean onSwipeRight()
    {
        return false;
    }

    public boolean onSwipeLeft()
    {
        return false;
    }

    public boolean onSwipeDown()
    {
        return false;
    }

    public boolean onSwipeUp()
    {
        return false;
    }


    private GestureDetector m_oDetector;

    /**
     * Creates a new instance of the simple swipe listener
     */
    protected SimpleSwipeListener(Context toContext)
    {
        m_oDetector = new GestureDetector(toContext, new GestureListener());
    }

    @Override
    public final boolean onTouch(View toView, MotionEvent toMotionEvent)
    {
        return m_oDetector.onTouchEvent(toMotionEvent);
    }
}
