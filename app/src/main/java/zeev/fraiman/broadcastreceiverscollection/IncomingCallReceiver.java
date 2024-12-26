package zeev.fraiman.broadcastreceiverscollection;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class IncomingCallReceiver extends BroadcastReceiver {

    private String phoneNumber;
    //AlertDialog.Builder adb_here;
    //Context context;
/*
    public IncomingCallReceiver(AlertDialog.Builder adb, Context context) {
        this.adb_here=adb;
        this.context=context;
    }
*/
    @Override
    public void onReceive(final Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String state = extras.getString(TelephonyManager.EXTRA_STATE);
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                //phoneNumber=intent.getStringExtra( "incoming_number");
                if (phoneNumber!=null) {
                    Toast.makeText(context, "PhoneNumber=" + phoneNumber, Toast.LENGTH_LONG).show();
                    //adb_here=new AlertDialog.Builder(context);
                    //adb_here.setTitle(phoneNumber);
                    //adb_here.create().show();
                }
                else {
                    Toast.makeText(context, "Number not defined", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else {
            Toast.makeText(context, "No information", Toast.LENGTH_SHORT).show();
        }
    }

}