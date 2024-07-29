package com.smsreaderapp;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;

public class SMSForwardService extends IntentService {
    private static final String TAG = "SMSForwardService";
    private static final String SERVER_URL = "https://your-web-server.com/api/sms";

    public SMSForwardService() {
        super("SMSForwardService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String msgFrom = intent.getStringExtra("msgFrom");
            String msgBody = intent.getStringExtra("msgBody");

            // Forward to web server
            forwardToServer(msgFrom, msgBody);

            // Forward to Firebase
            forwardToFirebase(msgFrom, msgBody);
        }
    }

    private void forwardToServer(String msgFrom, String msgBody) {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String json = "{\"from\":\"" + msgFrom + "\",\"body\":\"" + msgBody + "\"}";
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(SERVER_URL)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            Log.d(TAG, "Server Response: " + response.body().string());
        } catch (IOException e) {
            Log.e(TAG, "Error forwarding to server", e);
        }
    }

    private void forwardToFirebase(String msgFrom, String msgBody) {
        FirebaseMessaging fm = FirebaseMessaging.getInstance();
        fm.send(new RemoteMessage.Builder("your-server-key@fcm.googleapis.com")
                .setMessageId(Integer.toString(msgBody.hashCode()))
                .addData("from", msgFrom)
                .addData("body", msgBody)
                .build());
    }
}
