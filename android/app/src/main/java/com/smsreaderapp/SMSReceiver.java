package com.smsreaderapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {
    private static final String TAG = "SMSReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                String msgFrom = msgs[i].getOriginatingAddress();
                String msgBody = msgs[i].getMessageBody();
                Log.d(TAG, "Received SMS: " + msgFrom + ": " + msgBody);

                // Forward the SMS content to a web server or Firebase
                Intent serviceIntent = new Intent(context, SMSForwardService.class);
                serviceIntent.putExtra("msgFrom", msgFrom);
                serviceIntent.putExtra("msgBody", msgBody);
                context.startService(serviceIntent);
            }
        }
    }
}
