package com.cameronfenton.newwork;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
public class LoginActivity extends AppCompatActivity {
    private Button btnLogin, btnRegister;
    private EditText inputUsername, inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        inputUsername = (EditText) findViewById(R.id.txtEmail);
        inputPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String username = inputUsername.getText().toString();
                String enteredPassword = inputPassword.getText().toString();
                String password = "";
                String userID = "-1";
                String email = "";
                boolean passwordResult = false;

                try {

                    Connection conn = getPostgreSQLConnection();

                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM users WHERE email='"+username+"'");
                    while (rs.next()) {
                        System.out.print("Column 1 returned ");
                        System.out.println("Password" + rs.getString("password_digest"));
                        password = rs.getString("password_digest");
                        userID = String.valueOf(rs.getInt("user_id"));
                        email = String.valueOf(rs.getString("email"));
                    }

                    passwordResult = BCrypt.checkpw(enteredPassword, password);

                    System.out.println("Password result " + passwordResult);
                    System.out.println("User ID: " + userID);
                    System.out.println("Username: " + email);
                    rs.close();
                    st.close();
                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if (passwordResult) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("SESSION_USER_ID", userID);
                    intent.putExtra("SESSION_EMAIL", email);
                    startActivity(intent);
                    finish();

                    System.out.println("Login successful");
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "Invalid Login!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
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
