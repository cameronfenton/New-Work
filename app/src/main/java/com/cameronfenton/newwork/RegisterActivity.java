package com.cameronfenton.newwork;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

/**
 * A register screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity {
    private Button btnRegister;
    private EditText inputUsername, inputPassword, inputPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputUsername = (EditText) findViewById(R.id.txtEmail);
        inputPassword = (EditText) findViewById(R.id.txtPassword);
        inputPassword2 = (EditText) findViewById(R.id.txtPassword2);


    }
}

