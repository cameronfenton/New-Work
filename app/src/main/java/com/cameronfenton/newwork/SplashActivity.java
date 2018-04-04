package com.cameronfenton.newwork;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class SplashActivity extends AppCompatActivity {
    Bundle sessionVariables;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        MenuItem itemID = item;
        String userID = sessionVariables.getString("SESSION_USER_ID");
        String email = sessionVariables.getString("SESSION_EMAIL");
        String itemName = String.valueOf(itemID);
        Log.d("ActionBar","Item ID: " + itemID);

        switch (itemName){

            case "Profile":
                Intent intent = new Intent(SplashActivity.this, SkillActivity.class);
                intent.putExtra("SESSION_USER_ID", userID);
                intent.putExtra("SESSION_EMAIL", email);
                startActivity(intent);
                finish();
                return true;
            case "Home":
                Intent intent1 = new Intent(SplashActivity.this, MainActivity.class);
                intent1.putExtra("SESSION_USER_ID", userID);
                intent1.putExtra("SESSION_EMAIL", email);
                startActivity(intent1);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                //nothing
            }

            public void onFinish() {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }.start();
    }


}
