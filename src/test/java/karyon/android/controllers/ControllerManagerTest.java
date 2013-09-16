package karyon.android.controllers;

import android.support.v4.app.FragmentActivity;
import karyon.IRunnable;
import karyon.android.CustomRoboTestRunner;
import karyon.android.activities.SplashActivity;
import karyon.exceptions.NullParameterException;
import karyon.testing.KaryonTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: kmchugh
 * Date: 16/9/13
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(CustomRoboTestRunner.class)
public class ControllerManagerTest
    extends KaryonTest
{
    @Test
    public void testGetInstance() throws Exception
    {
        startMarker();

        ControllerManager loManager = ControllerManager.getInstance();

        assertSame(loManager, ControllerManager.getInstance());
    }

    @Test
    public void testRegisterBehaviour() throws Exception
    {
        /*
        startMarker();

        final ControllerBehaviour loBehavour = new ControllerBehaviour(){};

        assertWillThrow(NullParameterException.class, new IRunnable()
        {
            @Override
            public void run() throws Throwable
            {
                ControllerManager.getInstance().addBehaviour(null, null);
            }
        });

        assertWillThrow(NullParameterException.class, new IRunnable()
        {
            @Override
            public void run() throws Throwable
            {
                ControllerManager.getInstance().addBehaviour(null, loBehavour);
            }
        });

        assertWillThrow(NullParameterException.class, new IRunnable()
        {
            @Override
            public void run() throws Throwable
            {
                ControllerManager.getInstance().addBehaviour(SplashActivity.class, null);
            }
        });

        assertTrue(ControllerManager.getInstance().addBehaviour(SplashActivity.class, loBehavour));
        assertFalse(ControllerManager.getInstance().addBehaviour(SplashActivity.class, loBehavour));
        */
    }

    @Test
    public void testGetBehaviour() throws Exception
    {
        /*
        startMarker();

        ControllerBehaviour loTrueBehaviour = new ControllerBehaviour(){};
        ControllerBehaviour loFalseBehaviour = new ControllerBehaviour()
        {
            @Override
            public boolean isValid(IController toController)
            {
                return false;
            }
        };
        SplashActivity loController = new SplashActivity();


        ControllerManager.getInstance().clearBehaviours();

        assertNull(ControllerManager.getInstance().getBehaviour(loController));

        ControllerManager.getInstance().addBehaviour(loController.getClass(), loFalseBehaviour);

        assertNull(ControllerManager.getInstance().getBehaviour(loController));

        ControllerManager.getInstance().addBehaviour(loController.getClass(), loTrueBehaviour);

        assertSame(loTrueBehaviour, ControllerManager.getInstance().getBehaviour(loController));
        */
    }

    @Test
    public void testClearBehaviours() throws Exception
    {
        /*
        startMarker();

        ControllerBehaviour loTrueBehaviour = new ControllerBehaviour(){};
        ControllerBehaviour loFalseBehaviour = new ControllerBehaviour()
        {
            @Override
            public boolean isValid(IController toController)
            {
                return false;
            }
        };
        SplashActivity loController = new SplashActivity();


        ControllerManager.getInstance().clearBehaviours();

        assertNull(ControllerManager.getInstance().getBehaviour(loController));

        ControllerManager.getInstance().addBehaviour(loController.getClass(), loFalseBehaviour);

        assertNull(ControllerManager.getInstance().getBehaviour(loController));

        ControllerManager.getInstance().addBehaviour(loController.getClass(), loTrueBehaviour);

        assertSame(loTrueBehaviour, ControllerManager.getInstance().getBehaviour(loController));
        
        ControllerManager.getInstance().clearBehaviours();

        assertNull(ControllerManager.getInstance().getBehaviour(loController));
        */
    }

    @Test
    public void testRemoveBehaviour() throws Exception
    {
        /*
        startMarker();

        ControllerBehaviour loTrueBehaviour = new ControllerBehaviour(){};
        ControllerBehaviour loFalseBehaviour = new ControllerBehaviour()
        {
            @Override
            public boolean isValid(IController toController)
            {
                return false;
            }
        };
        SplashActivity loController = new SplashActivity();


        ControllerManager.getInstance().clearBehaviours();

        assertNull(ControllerManager.getInstance().getBehaviour(loController));

        ControllerManager.getInstance().addBehaviour(loController.getClass(), loFalseBehaviour);

        assertNull(ControllerManager.getInstance().getBehaviour(loController));

        ControllerManager.getInstance().addBehaviour(loController.getClass(), loTrueBehaviour);

        assertSame(loTrueBehaviour, ControllerManager.getInstance().getBehaviour(loController));

        ControllerManager.getInstance().removeBehaviour(loController.getClass(), loTrueBehaviour);

        assertNull(ControllerManager.getInstance().getBehaviour(loController));
        */
    }


}
