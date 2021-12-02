package com.example.duan1.Fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.duan1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Fragment extends Fragment {
    TextInputLayout email, password;
    TextView forgotPassword, jumpToSignup;
    Button btnLogin;
    ImageView facebook, google;

    FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();

        //hooks
        hooks(view);

        //login event
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(view);
            }
        });

        //jump to signup
        jumpToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToSignup(view);
            }
        });

        //forgot password
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword(view);
            }
        });
    }

    //login method
    public void login(View view) {
        String aEmail = email.getEditText().getText().toString();
        String aPassword = password.getEditText().getText().toString();

        if (TextUtils.isEmpty(aEmail)) {
            Toast.makeText(getContext(), "Enter email address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(aPassword)) {
            Toast.makeText(getContext(), "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(aEmail, aPassword)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Successfully", Toast.LENGTH_SHORT).show();
                            replaceFragment(new Home_Fragment());
                        } else {
                            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //jump to signup method
    public void jumpToSignup(View view) {
        replaceFragment(new Signup_Fragment());
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
                        Toast.makeText(getContext(), "Reset link sent to your email", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        passwordReset.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    }

    //replace fragment method
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_authentication, fragment).addToBackStack(null).commit();
    }

    //hooks
    public void hooks(View view) {
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        forgotPassword = view.findViewById(R.id.forgotPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        jumpToSignup = view.findViewById(R.id.jumpToSignup);
    }
}
