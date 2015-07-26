package com.smithmicro.vvm.omtp.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.smithmicro.vvm.omtp.R;
import com.smithmicro.vvm.omtp.logging.Logger;
import com.smithmicro.vvm.omtp.service.OmtpSmsReceiverHandler;

import java.io.UnsupportedEncodingException;

public class DemoActivity extends Activity {

    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        tv = (TextView) findViewById(R.id.tt);
        IntentFilter ifil = new IntentFilter("android.intent.action.DATA_SMS_RECEIVED");
        ifil.addDataScheme("sms");
        registerReceiver(new SmsReceiver(), ifil);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class SmsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("DemoActivity", "SMS received");
            Object[] omtpSmsPdus = (Object[]) intent.getExtras().get("pdus");
            String smsOriginatorNumber = null;
            StringBuilder userData = new StringBuilder();
            StringBuilder messageBody = new StringBuilder();
            for (int i = 0; i < omtpSmsPdus.length; i++) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) omtpSmsPdus[i]);
                messageBody.append(sms.getMessageBody());
                String newUserData = null;
                try {
                    newUserData = new String(sms.getUserData(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                userData.append(newUserData);
                smsOriginatorNumber = sms.getOriginatingAddress();
            }
            Log.d("DemoActivity", userData.toString());
            Log.d("DemoActivity", messageBody.toString());
            tv.setText(userData.toString());
        }
    }
}
