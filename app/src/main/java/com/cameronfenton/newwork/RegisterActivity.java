package com.cameronfenton.newwork;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
public class RegisterActivity extends AppCompatActivity {
    private Button btnRegister;
    private EditText inputUsername, inputFirstName, inputLastName, inputPassword, inputPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputUsername = (EditText) findViewById(R.id.txtEmail);
        inputFirstName = (EditText) findViewById(R.id.txtFirstName);
        inputLastName = (EditText) findViewById(R.id.txtLastName);
        inputPassword = (EditText) findViewById(R.id.txtPassword);
        inputPassword2 = (EditText) findViewById(R.id.txtPassword1);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password;
                String fname = inputFirstName.getText().toString();
                String lname = inputLastName.getText().toString();
                String email = inputUsername.getText().toString();
                String password1 = inputPassword.getText().toString();
                String password2 = inputPassword.getText().toString();
                Date date = new java.util.Date();
                Timestamp currDateTime = new Timestamp(date.getTime());

                System.out.println("Passwords: " + password1 +" " + password2);

                if (password1.equals(password2) && fname.length() > 1 && lname.length() > 1 && email.length() > 1) {

                    password = BCrypt.hashpw(password1, BCrypt.gensalt());

                    try {
                        System.out.println("passwords are equal attempting to insert");
                        Connection conn = getPostgreSQLConnection();

                        Statement st = conn.createStatement();
                        ResultSet rs = st.executeQuery("select count(*) from users");
                        rs.next();
                        int tblI = rs.getInt(1)+1;

                        String query = "INSERT INTO users VALUES ('" + tblI + "', '" + fname + "','" + lname + "', '" + password + "', '" + email + "', '" + currDateTime +"', '" + currDateTime +"')";
                        System.out.println("Query: " + query + "");

                        int count = st.executeUpdate(query);

                        st.close();

                        if (count > 0) {
                            Context context = getApplicationContext();
                            CharSequence text = "Account Created!";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            System.out.println("Successful");
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            System.out.println("Not Successful");
                            Context context = getApplicationContext();
                            CharSequence text = "Register Not Successful!";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "Passwords must match and fields must be not be empty!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
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

