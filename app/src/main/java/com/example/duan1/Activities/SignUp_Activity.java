package com.example.duan1.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp_Activity extends AppCompatActivity {
    TextInputLayout phoneNumber, email, password, rePassword;
    Button btnSignup;
    TextView jumpToLogin;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signup);
        auth = FirebaseAuth.getInstance();
        phoneNumber = findViewById(R.id.phoneNumber);
        email =  findViewById(R.id.email);
        password =findViewById(R.id.password);
        rePassword = findViewById(R.id.rePassword);
        btnSignup = findViewById(R.id.btnSignup);
        jumpToLogin = findViewById(R.id.jumpToLogin);

        //signup event
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(v);
            }
        });

        //jump to login event
        jumpToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToLogin(v);
            }
        });
    }
    public void signUp(View view){
        String aPhoneNumber = phoneNumber.getEditText().getText().toString();
        String aEmail = email.getEditText().getText().toString();
        String aPassword = password.getEditText().getText().toString();
        String aRePassword = rePassword.getEditText().getText().toString();

        if(TextUtils.isEmpty(aPhoneNumber)){
            Toast.makeText(SignUp_Activity.this, "Enter phone number", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(aEmail)){
            Toast.makeText(SignUp_Activity.this, "Enter email address", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(aPassword)){
            Toast.makeText(SignUp_Activity.this, "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(aPassword.length() < 6){
            Toast.makeText(SignUp_Activity.this, "Password must have minimum 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(aRePassword)){
            Toast.makeText(SignUp_Activity.this, "Enter confirm password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!aPassword.equals(aRePassword)){
            Toast.makeText(SignUp_Activity.this, "Confirm password must be the same password", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(aEmail, aPassword)
                .addOnCompleteListener((Activity) SignUp_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(SignUp_Activity.this, "Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp_Activity.this, Login_Activity.class));
                        }
                        else{
                            Toast.makeText(SignUp_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //jump to login method
    public void jumpToLogin(View view){
        startActivity(new Intent(SignUp_Activity.this, Login_Activity.class));
    }



}