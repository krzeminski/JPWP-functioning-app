package com.example.pleasework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DisplaySettings extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_settings);
    }
    public void displayNotificationSettings(View view){
        Intent intent = new Intent(this, DisplayNotificationSettings.class);
        startActivity(intent);
    }
    public void displayNotificationsActivity(View view){
        Intent intent = new Intent(this, NotificationsActivity.class);
        startActivity(intent);
    }

    public void displayLanguageActivity(View view){
        Intent intent = new Intent(this, LanguageActivity.class);
        startActivity(intent);
    }

    public void displaySoundActivity(View view){
        Intent intent = new Intent(this, SoundActivity.class);
        startActivity(intent);
    }

    public void displayDistractionActivity(View view){
        Intent intent = new Intent(this, DistractionActivity.class);
        startActivity(intent);
    }



}
