package com.example.pleasework;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);
        loadFragment(new ProfileFragment());
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()){
            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                break;

            case R.id.navigation_revision:
                fragment = new RevisionFragment();
                break;

            case R.id.navigation_lessons:
                fragment = new LessonsFragment();
                break;

        }
        return loadFragment(fragment);
    }

//    public void login(View view){
//        Intent intent = new Intent(this, DisplayMainViewActivity.class);
//        startActivity(intent);
//    }

    public void displaySettings(View view){
        Intent intent = new Intent(this, DisplaySettings.class);
        startActivity(intent);
    }
}
