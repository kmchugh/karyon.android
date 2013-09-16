package karyon.android.controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import karyon.android.activities.IActivity;

/**
 * A Controller is intended to control a
 * single view in the system.  A view is based around
 * a fragment which means that views can contain other views.
 */
public abstract class Controller<T extends Controller<T>>
    extends Fragment
    implements IActivity<T>
{
    /**
     * Helper method for creating a controller
     * @param toClass the class to load
     * @param <K> the return type of the controller
     * @return the Controller that was created
     */
    public static <K extends Controller<K>> K instantiate(Class<K> toClass)
            throws java.lang.InstantiationException, IllegalAccessException
    {
        return Controller.instantiate(toClass, null);
    }

    /**
     * Helper method for creating a controller
     * @param toClass the class to load
     * @param toArgs the bundle arguments to pass to the controller
     * @param <K> the return type of the controller
     * @return the Controller that was created
     */
    public static <K extends Controller<K>> K instantiate(Class<K> toClass, Bundle toArgs)
        throws java.lang.InstantiationException, IllegalAccessException
    {
        K loController = toClass.newInstance();
        loController.setArguments(toArgs);
        return loController;
    }

    /**
     * Initialises the activity when it is being created, if this is the first initialisation, toSavedInstanceState will be
     * null.  If this is being recreated then toSavedInstanceState will contain the most recent copy of the savedInstanceState
     * @param toInflater The LayoutInflater object that can be used to inflate any views in the controller
     * @param toContainer If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param toSavedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return the view for the fragments UI, or null if there is no view
     */
    @Override
    public final View onCreateView(LayoutInflater toInflater, ViewGroup toContainer, Bundle toSavedInstanceState)
    {
        return toInflater.inflate(getContentViewID(), toContainer, false);
    }

    /**
     * Hook to allow interaction with the view creation cycle.
     * Called after onCreate but before onActivityCreated.  At this stage
     * the view hierarchy is created but the view is not yet attached to it's parent
     * @param toView the view that was inflated for this controller
     * @param toBundle the bundle of arguments
     * @return the view to be used by this controller
     */
    @Override
    public void onViewCreated(View toView, Bundle toBundle)
    {
    }
}
