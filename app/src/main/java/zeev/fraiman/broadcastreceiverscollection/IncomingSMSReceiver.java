package zeev.fraiman.broadcastreceiverscollection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.Arrays;

class IncomingSMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        Toast.makeText(context, "Incoming SMS!", Toast.LENGTH_LONG).show();
        final String action = intent.getAction();
        final Bundle extras = intent.getExtras();
        if (action.equals("android.provider.Telephony.SMS_RECEIVED")
                && extras!=null)  {
            //Create SMSMessages from PDUs in the Bundle
            final Object[] pdus = (Object[])extras.get("pdus");
            String phoneNumber="";
            final SmsMessage[] messages = new SmsMessage[pdus.length];
            String msg="";
            for (int i = 0; i < pdus.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }

            for (int i = 0; i < messages.length; i++) {
                msg=messages[i].getMessageBody();
                phoneNumber=messages[i].getOriginatingAddress();
            }
            String[] msgBody=new String[4];
            for (int i = 0; i < msgBody.length; i++) {
                msgBody[i]="";
            }
            int c=0;
            int j=0;
            for (int i = 0; i < msg.length(); i++) {
                if (msg.charAt(i)=='\n')
                    j++;
                else
                    msgBody[j]+=msg.charAt(i);
            }
            Toast.makeText(context, ""+ Arrays.toString(msgBody), Toast.LENGTH_LONG).show();
/*
            HelperDB helperDB=new HelperDB(context);
            SQLiteDatabase db=helperDB.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put(helperDB.MYVISIT_CLIENT,msgBody[0]+"."+phoneNumber);
            contentValues.put(helperDB.MYVISIT_STARTTIME,msgBody[1]);
            contentValues.put(helperDB.MYVISIT_DATE,msgBody[2]);
            contentValues.put(helperDB.MYVISIT_TYPE,msgBody[3]);
            db.insert(helperDB.TABLE_MYVISITS, null, contentValues);
            db.close();
 */
        }

    }
}