package com.example.duan1.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.duan1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup_Fragment extends Fragment {
    TextInputLayout phoneNumber, email, password, rePassword;
    Button btnSignup;
    TextView jumpToLogin;

    FirebaseAuth auth;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();

        //hooks
        hooks(view);

        //signup event
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(view);
            }
        });

        //jump to login event
        jumpToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToLogin(view);
            }
        });
    }


    //sign up method
    public void signUp(View view){
        String aPhoneNumber = phoneNumber.getEditText().getText().toString();
        String aEmail = email.getEditText().getText().toString();
        String aPassword = password.getEditText().getText().toString();
        String aRePassword = rePassword.getEditText().getText().toString();

        if(TextUtils.isEmpty(aPhoneNumber)){
            Toast.makeText(getContext(), "Enter phone number", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(aEmail)){
            Toast.makeText(getContext(), "Enter email address", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(aPassword)){
            Toast.makeText(getContext(), "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(aPassword.length() < 6){
            Toast.makeText(getContext(), "Password must have minimum 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(aRePassword)){
            Toast.makeText(getContext(), "Enter confirm password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!aPassword.equals(aRePassword)){
            Toast.makeText(getContext(), "Confirm password must be the same password", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(aEmail, aPassword)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getContext(), "Successfully", Toast.LENGTH_SHORT).show();
                            replaceFragment(new Login_Fragment());
                        }
                        else{
                            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //jump to login method
    public void jumpToLogin(View view){
        replaceFragment(new Login_Fragment());
    }

    //replace fragment method
    public void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_authentication,fragment).addToBackStack(null).commit();
    }

    public void hooks(View view){
        phoneNumber = view.findViewById(R.id.phoneNumber);
        email =  view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        rePassword =  view.findViewById(R.id.rePassword);
        btnSignup = view.findViewById(R.id.btnSignup);
        jumpToLogin = view.findViewById(R.id.jumpToLogin);
    }
}
