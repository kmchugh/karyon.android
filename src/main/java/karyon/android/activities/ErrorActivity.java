package karyon.android.activities;

import android.view.View;
import android.widget.TextView;
import karyon.android.R;

/**
 *
 * @author admin
 */
public class ErrorActivity extends Controller
{

    @Override
    public int getPortraitViewResourceID()
    {
        return R.layout.error;
    }

    //@Override
    public void onUpdateUI()
    {
        //super.onUpdateUI();
        String lcMessage = (String) getIntent().getExtras().getSerializable("com.youcommentate.message");
        String lcTitle = (String) getIntent().getExtras().getSerializable("com.youcommentate.title");
        if (lcTitle == null)
        {
            lcTitle = getResources().getString(R.string.error);
        }
        
        TextView loMessage = (TextView)findViewById(R.id.error_message);
        TextView loTitle = (TextView)findViewById(R.id.error_title);
        loMessage.setText(lcMessage);
        loTitle.setTag(lcTitle);
    }
    
    public void onOkayClicked(View toView) 
    {
        finish();
    }
    
}
