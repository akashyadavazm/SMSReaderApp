package com.smsreader;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class SMSReaderModule extends ReactContextBaseJavaModule {
    private static ReactApplicationContext reactContext;

    public SMSReaderModule(ReactApplicationContext context) {
        super(context);
        reactContext = context;
    }

    @Override
    public String getName() {
        return "SMSReader";
    }

    @ReactMethod
    public void getSMS(Callback callback) {
        Context context = getReactApplicationContext();
        Uri inboxURI = Telephony.Sms.Inbox.CONTENT_URI;
        String[] reqCols = new String[] { Telephony.Sms.Inbox.ADDRESS, Telephony.Sms.Inbox.BODY };

        Cursor cursor = context.getContentResolver().query(inboxURI, reqCols, null, null, null);
        StringBuilder smsBuilder = new StringBuilder();

        if (cursor.moveToFirst()) {
            do {
                String address = cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.ADDRESS));
                String body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.BODY));
                smsBuilder.append("From: ").append(address).append(", Message: ").append(body).append("\n");
            } while (cursor.moveToNext());
        }

        cursor.close();
        callback.invoke(smsBuilder.toString());
    }
}
