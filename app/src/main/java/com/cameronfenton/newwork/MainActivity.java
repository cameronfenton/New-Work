package com.cameronfenton.newwork;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.mindrot.jbcrypt.BCrypt;

/**
 * A login screen that offers login via email/password.
 */
public class MainActivity extends AppCompatActivity {
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
                Intent intent = new Intent(MainActivity.this, SkillActivity.class);
                intent.putExtra("SESSION_USER_ID", userID);
                intent.putExtra("SESSION_EMAIL", email);
                startActivity(intent);
                finish();
                return true;
            case "Home":
                Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
                intent1.putExtra("SESSION_USER_ID", userID);
                intent1.putExtra("SESSION_EMAIL", email);
                startActivity(intent1);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private Button btnProfile, btnListings, btnLogout;
    private TextView txtWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        sessionVariables = getIntent().getExtras();
        final String userID = sessionVariables.getString("SESSION_USER_ID");
        final String email = sessionVariables.getString("SESSION_EMAIL");

        btnProfile = (Button) findViewById(R.id.btnProfile);
        btnListings = (Button) findViewById(R.id.btnListings);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        txtWelcome = (TextView) findViewById(R.id.txtWelcome);

        txtWelcome.setText("Welcome to NewWork\nYou are logged in as: " + email);

        btnListings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListingsActivity.class);
                intent.putExtra("SESSION_USER_ID", userID);
                intent.putExtra("SESSION_EMAIL", email);
                startActivity(intent);
                finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("User ID: " + sessionVariables.getString("SESSION_USER_ID"));
            }
        });

    }

    public Connection getPostgreSQLConnection() {
        Connection conn = null;

        try {
            Class.forName("org.postgresql.Driver");

            String mysqlConnUrl = "jdbc:postgresql://99.238.34.94:5432/newwork_production";

            String mysqlUserName = "newwork";

            String mysqlPassword = "password";

            conn = DriverManager.getConnection(mysqlConnUrl, mysqlUserName , mysqlPassword);

        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            return conn;
        }
    }
}
