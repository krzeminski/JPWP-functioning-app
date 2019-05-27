package com.example.pleasework;

import android.app.NotificationManager;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RemoteReceiver extends AppCompatActivity {
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_receiver);

        textView = findViewById(R.id.txt_display);

        Bundle remoteReplay = RemoteInput.getResultsFromIntent(getIntent());

        if (remoteReplay != null) {

            String message = remoteReplay.getCharSequence(NotificationsActivity.TXT_REPLAY).toString();
            textView.setText(message);

        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(NotificationsActivity.NOTIFICATION_ID_REPLAY);

    }
}
