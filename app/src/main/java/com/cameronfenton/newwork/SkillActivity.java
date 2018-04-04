package com.cameronfenton.newwork;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

/**
 * A register screen that offers login via email/password.
 */
public class SkillActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        MenuItem itemID = item;

        String itemName = String.valueOf(itemID);
        Log.d("ActionBar","Item ID: " + itemID);

        switch (itemName){

            case "Profile":
                Intent intent = new Intent(SkillActivity.this, SkillActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_skill);

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

