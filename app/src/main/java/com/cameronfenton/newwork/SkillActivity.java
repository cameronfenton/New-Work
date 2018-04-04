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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.mindrot.jbcrypt.BCrypt;
import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * A profile screen that offers login via email/password.
 */
public class SkillActivity extends AppCompatActivity {
    Bundle sessionVariables;
    EditText inputSkill;
    EditText txtSkills;

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
                Intent intent = new Intent(SkillActivity.this, SkillActivity.class);
                intent.putExtra("SESSION_USER_ID", userID);
                intent.putExtra("SESSION_EMAIL", email);
                startActivity(intent);
                finish();
                return true;
            case "Home":
                Intent intent1 = new Intent(SkillActivity.this, MainActivity.class);
                intent1.putExtra("SESSION_USER_ID", userID);
                intent1.putExtra("SESSION_EMAIL", email);
                startActivity(intent1);
                finish();
                return true;
            case "Logout":
                Intent intent2 = new Intent(SkillActivity.this, LoginActivity.class);
                startActivity(intent2);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_skill);

        sessionVariables = getIntent().getExtras();
        Spinner skillSpinner = (Spinner) findViewById(R.id.spinnerSkill);
        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        inputSkill = (EditText) findViewById(R.id.txtSkillName);
        txtSkills =  (EditText) findViewById(R.id.skillsList);

        try {

            Connection conn = getPostgreSQLConnection();

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT skill_level FROM skill_levels");
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {
                int skill = rs.getInt("skill_level");
                data.add(String.valueOf(skill));
            }
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,

                    android.R.layout.simple_list_item_1, data);

            skillSpinner.setAdapter(NoCoreAdapter);

            rs.close();
            st.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            String userID = sessionVariables.getString("SESSION_USER_ID");
            Connection conn = getPostgreSQLConnection();

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT skill_id FROM skillsets WHERE user_id='"+userID+"'");
            ArrayList<String> data = new ArrayList<String>();

            while(rs.next()) {
                System.out.println("Skill ID: " + rs.getString("id"));
            }
            //
            // array of skill ids
            //

            rs.close();
            st.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String skillName = inputSkill.getText().toString().toLowerCase();

                    Connection conn = getPostgreSQLConnection();

                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery("SELECT count(*) FROM skills WHERE skill_name='" + skillName + "'");
                    rs.next();

                    int count = rs.getInt(1);

                    System.out.println("Count of matching skills: " + count);

                    if (count == 1) {
                        Statement st1 = conn.createStatement();
                        ResultSet rs1 = st.executeQuery("SELECT id FROM skills WHERE skill_name='" + skillName + "'");
                        rs1.next();
                        int id = rs1.getInt("id");
                        System.out.println("ID of matching skill: " + id);

                    } else {
                        System.out.println("Creating skill");
                    }

                    rs.close();
                    st.close();
                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
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

