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

public class MainActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    TextView tvLogin;
    Button btnRegister;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.btn_register);
        tvLogin = (TextView) findViewById(R.id.tv_login);
        btnRegister.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(MainActivity.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && pw.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pw).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Register Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                startActivity(new Intent(MainActivity.this, Tampilan.class));
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(MainActivity.this, "Error Ocured!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }

        });

    }
}