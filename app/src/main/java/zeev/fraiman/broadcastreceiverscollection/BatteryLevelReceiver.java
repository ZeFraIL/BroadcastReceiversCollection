package zeev.fraiman.broadcastreceiverscollection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

public class BatteryLevelReceiver extends BroadcastReceiver {

    TextView tv;
    BatteryLevelReceiver(TextView tv) {
        this.tv=tv;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int percentOfLevel=intent.getIntExtra("level",0);
        if (percentOfLevel>20)  {
            tv.setText(percentOfLevel+"%");
        }
    }

}