package karyon.android.activities;

import android.view.View;
import android.widget.TextView;
import karyon.android.R;

/**
 *
 * @author admin
 */
public class ErrorActivity
        extends BaseActivity<ErrorActivity>
{

    @Override
    public int getPortraitViewResourceID()
    {
        return R.layout.error;
    }

    @Override
    public void onContentReady()
    {
        super.onContentReady();

        String lcMessage = (String) getIntent().getExtras().getSerializable("error.message");
        String lcTitle = (String) getIntent().getExtras().getSerializable("error.title");
        if (lcTitle == null)
        {
            lcTitle = getResources().getString(R.string.error);
        }

        TextView loMessage = (TextView)findViewById(R.id.error_message);
        TextView loTitle = (TextView)findViewById(R.id.error_title);

        if (loMessage != null)
        {
            loMessage.setText(lcMessage);
        }
        if (loTitle != null)
        {
            loTitle.setText(lcTitle);
            loTitle.setTag(lcTitle);
        }
    }

    public void onOkayClicked(View toView)
    {
        finish();
    }
    
}
