package com.example.buyerapp489.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buyerapp489.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //Initialize Variables
    private EditText etEmail, etPassword;
    private ProgressBar progressBar;
    private TextView txtNoAccount, txtForgetPassword;
    private Button btLogin;
    private CheckBox showPassword;

    //Initialize Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initialize Variables
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        progressBar = findViewById(R.id.progressbar);
        txtNoAccount = findViewById(R.id.txtNoAccount);
        txtForgetPassword = findViewById(R.id.txtForgetPassword);
        btLogin = findViewById(R.id.btLogin);
        showPassword = findViewById(R.id.showPassword);

        //Visible Invisible Password
        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showPassword.isChecked()) {
                    etPassword.setInputType(0x00000091);
                }else {
                    etPassword.setInputType(0x00000081);
                }
            }
        });

        //Initialize Firebase
        mAuth = FirebaseAuth.getInstance();

        //For going register activity
        txtNoAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        //Login button
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();

                //Not fill all editText
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
                    Toast.makeText(LoginActivity.this, "Input Email Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Progressbar
                    progressBar.setVisibility(View.VISIBLE);
                    btLogin.setVisibility(View.GONE);

                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Login Successful
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                finish();
                                Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                btLogin.setVisibility(View.VISIBLE);
                            }
                            else{
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                btLogin.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                }
            }
        });

    }
}