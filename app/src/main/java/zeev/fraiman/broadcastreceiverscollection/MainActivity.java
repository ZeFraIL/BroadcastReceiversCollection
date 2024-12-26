package zeev.fraiman.broadcastreceiverscollection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Context context;
    Activity activity;
    Switch swIncomingCall, swNetworkConnected, swBattery;
    IncomingCallReceiver callReceiver;
    IntentFilter filter;
    NetworkReceiver networkReceiver;
    TextView tvBattery;
    BatteryLevelReceiver batteryLevelReceiver;
    IntentFilter intentBatteryFilter;
    InternetConnectionReceiver internetConnectionReceiver;
    IntentFilter intentConnectionFilter;
    IncomingSMSReceiver incomingSMSReceiver;
    IntentFilter incomingSMSFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initElements();

        swIncomingCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (swIncomingCall.isChecked()) {
                    ActivityCompat.requestPermissions(activity, new String[]{
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.READ_PHONE_NUMBERS,
                            Manifest.permission.READ_CALL_LOG,
                            Manifest.permission.RECEIVE_SMS},1);

                    //filter=new IntentFilter("android.intent.action.READ_CALL_LOG");
                    filter=new IntentFilter("android.intent.action.PHONE_STATE");
                    registerReceiver(callReceiver, filter);
                }
                else {
                    unregisterReceiver(callReceiver);
                }
            }
        });

        swNetworkConnected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (swNetworkConnected.isChecked())  {
                    IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
                    registerReceiver(networkReceiver, filter);
                }
                else {
                    unregisterReceiver(networkReceiver);
                }
            }
        });

        swBattery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (swBattery.isChecked())  {
                    tvBattery.setVisibility(View.VISIBLE);
                    intentBatteryFilter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                    registerReceiver(batteryLevelReceiver,intentBatteryFilter);
                }
                else {
                    tvBattery.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void initElements() {
        activity=MainActivity.this;
        context=MainActivity.this;
        swIncomingCall=findViewById(R.id.swIncomingCall);
        swNetworkConnected=findViewById(R.id.swNetworkConnected);
        networkReceiver=new NetworkReceiver();
        AlertDialog.Builder adb =new AlertDialog.Builder(context);
        //callReceiver=new IncomingCallReceiver(adb,context);
        callReceiver=new IncomingCallReceiver();
        tvBattery=findViewById(R.id.tv);
        swBattery=findViewById(R.id.swBattery);
        batteryLevelReceiver=new BatteryLevelReceiver(tvBattery);
        internetConnectionReceiver=new InternetConnectionReceiver();
        intentConnectionFilter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        incomingSMSReceiver=new IncomingSMSReceiver();
        incomingSMSFilter=new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(internetConnectionReceiver, intentConnectionFilter);
        registerReceiver(incomingSMSReceiver, incomingSMSFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(internetConnectionReceiver);
        unregisterReceiver(incomingSMSReceiver);
    }

    
}