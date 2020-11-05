package com.example.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText etEmail, etPassword;
    TextView tvRegister;
    Button btnLogin;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tv_register);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if( mFirebaseUser != null ){
                    Toast.makeText(Login.this,"You are logged in",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login.this, Tampilan.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(Login.this,"Please Login",Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String pw = etPassword.getText().toString();
                if (email.isEmpty()) {
                    etEmail.setError("Please enter email");
                    etEmail.requestFocus();
                }
                else if(pw.isEmpty()) {
                    etPassword.setError("Please enter password");
                    etPassword.requestFocus();
                }
                else if (email.isEmpty() && pw.isEmpty()) {
                    Toast.makeText(Login.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && pw.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(email,pw).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(Login.this, "Login Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent intToHome = new Intent(Login.this,Tampilan.class);
                                startActivity(intToHome);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(Login.this, "Error Ocured!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intRegister = new Intent(Login.this, MainActivity.class);
                startActivity(intRegister);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}