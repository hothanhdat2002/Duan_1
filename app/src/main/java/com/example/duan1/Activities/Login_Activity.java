package com.example.duan1.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.Fragment.Home_Fragment;
import com.example.duan1.Navigation_Drawer;
import com.example.duan1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Activity extends AppCompatActivity {
    TextInputLayout email, password;
    TextView forgotPassword, jumpToSignup;
    Button btnLogin;
    ImageView facebook, google;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);


        auth = FirebaseAuth.getInstance();

        //hooks
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        forgotPassword = findViewById(R.id.forgotPassword);
        btnLogin = findViewById(R.id.btnLogin);
        jumpToSignup = findViewById(R.id.jumpToSignup);

        //login event
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });

        //jump to signup
        jumpToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToSignup(v);
            }
        });

        //forgot password
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword(v);
            }
        });
    }
    public void login(View view) {
        String aEmail = email.getEditText().getText().toString();
        String aPassword = password.getEditText().getText().toString();

        if (TextUtils.isEmpty(aEmail)) {
            Toast.makeText(Login_Activity.this, "Enter email address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(aPassword)) {
            Toast.makeText(Login_Activity.this, "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(aEmail, aPassword)
                .addOnCompleteListener((Activity) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login_Activity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login_Activity.this, Navigation_Drawer.class));
                        } else {
                            Toast.makeText(Login_Activity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //jump to signup method
    public void jumpToSignup(View view) {
        startActivity(new Intent(Login_Activity.this, SignUp_Activity.class));
    }

    //forgot password method
    public void forgotPassword(View view) {
        final EditText resetEmail = new EditText(view.getContext());
        final AlertDialog.Builder passwordReset = new AlertDialog.Builder(view.getContext());
        passwordReset.setTitle("Are you forgot password?");
        passwordReset.setMessage("Enter your email to received reset link.");
        passwordReset.setView(resetEmail);

        passwordReset.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String mail = resetEmail.getText().toString();
                auth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Login_Activity.this, "Reset link sent to your email", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        passwordReset.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        passwordReset.show();
    }


}