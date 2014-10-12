package karyon.android.controls;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import karyon.applications.Application;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A Dropdown is a Spinner that allows
 * for a non selected prompt
 */
public class Dropdown
    extends Spinner
{
    private class SpinnerAdapterProxy
        implements InvocationHandler
    {
        protected SpinnerAdapter m_oWrappedAdapter;
        protected Method m_oGetView;

        private SpinnerAdapterProxy(SpinnerAdapter toWrappedAdapter)
        {
            m_oWrappedAdapter = toWrappedAdapter;
            try
            {
                m_oGetView = SpinnerAdapter.class.getMethod("getView", int.class, View.class, ViewGroup.class);
            }
            catch (NoSuchMethodException ex)
            {
                Application.log(ex);
            }
        }

        @Override
        public Object invoke(Object toProxy, Method toMethod, Object[] taArgs) throws Throwable
        {
            try
            {
                return toMethod.equals(m_oGetView) && (Integer)(taArgs[0]) <0 ?
                        getView((Integer) taArgs[0], (View) taArgs[1], (ViewGroup) taArgs[2]) :
                        toMethod.invoke(m_oWrappedAdapter, taArgs);
            }
            catch (InvocationTargetException ex)
            {
                Application.log(ex);
                return toMethod.invoke(m_oWrappedAdapter, taArgs);
            }
        }

        private View getView(int tnPosition, View toView, ViewGroup toParent)
        {
            if (tnPosition < 0)
            {
                android.widget.TextView loView = (android.widget.TextView)((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
                        R.layout.simple_spinner_item, toParent, false);
                loView.setText(getPrompt());
                return loView;
            }
            return m_oWrappedAdapter.getView(tnPosition, toView, toParent);
        }
    }

    public Dropdown(Context toContext)
    {
        super(toContext);
    }

    public Dropdown(Context toContext, int tnMode)
    {
        super(toContext, tnMode);
    }

    public Dropdown(Context toContext, AttributeSet toAttributes)
    {
        super(toContext, toAttributes);
    }

    public Dropdown(Context toContext, AttributeSet toAttributes, int tnStyle)
    {
        super(toContext, toAttributes, tnStyle);
    }

    public Dropdown(Context toContext, AttributeSet toAttributes, int tnStyle, int tnMode)
    {
        super(toContext, toAttributes, tnStyle, tnMode);
    }

    @Override
    public void setSelection(int tnPosition, boolean tlAnimate)
    {
        if (tnPosition < 0)
        {
            forceClear();
        }
        super.setSelection(tnPosition, tlAnimate);
    }

    @Override
    public void setSelection(int tnPosition)
    {
        if (tnPosition < 0)
        {
            forceClear();
        }
        super.setSelection(tnPosition);
    }

    private void forceClear()
    {
        SpinnerAdapter loAdaptor = getAdapter();
        if (loAdaptor != null)
        {
            try
            {
                Method loMethod = AdapterView.class.getDeclaredMethod("setNextSelectedPositionInt",int.class);
                loMethod.setAccessible(true);
                loMethod.invoke(this,-1);

                loMethod = AdapterView.class.getDeclaredMethod("setSelectedPositionInt",int.class);
                loMethod.setAccessible(true);
                loMethod.invoke(this,-1);
            }
            catch (NoSuchMethodException ex)
            {
                Application.log(ex);
            }
            catch (IllegalAccessException ex)
            {
                Application.log(ex);
            }
            catch (InvocationTargetException ex)
            {
                Application.log(ex);
            }
        }
    }

    @Override
    public final void setAdapter(SpinnerAdapter toAdapter)
    {
        SpinnerAdapter loProxy = createProxyAdapter(toAdapter);

        super.setAdapter(loProxy);

        forceClear();
    }

    private SpinnerAdapter createProxyAdapter(SpinnerAdapter toAdapter)
    {
        return (SpinnerAdapter) java.lang.reflect.Proxy.newProxyInstance(toAdapter.getClass().getClassLoader(),
                                                                         new Class[]{SpinnerAdapter.class},
                                                                         new SpinnerAdapterProxy(toAdapter));
    }
}
