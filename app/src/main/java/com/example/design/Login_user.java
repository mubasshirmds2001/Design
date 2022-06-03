package com.example.design;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_user extends AppCompatActivity implements View.OnClickListener{
    private TextView txtReg;
    private EditText logemail,logpassword;
    private Button login;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        txtReg=(TextView) findViewById(R.id.txtReg);
        txtReg.setOnClickListener(this);

        login=(Button) findViewById(R.id.btnLogin);
        login.setOnClickListener(this);

        logemail=(EditText) findViewById(R.id.edlogemail);
        logpassword=(EditText) findViewById(R.id.edlogpassword);

        progressBar=(ProgressBar) findViewById(R.id.progressBar2);

        mAuth=FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtReg:
                startActivity(new Intent(this,Register_page.class));
                break;

            case R.id.btnLogin:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String Logemail=logemail.getText().toString().trim();
        String Logpassword=logpassword.getText().toString().trim();

        if(Logemail.isEmpty()){
            logemail.setError("Username is required!");
            logemail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(Logemail).matches()){
            logemail.setError("Please provide valid email..");
            logemail.requestFocus();
            return;
        }

        if(Logpassword.isEmpty()){
            logpassword.setError("Password is required!");
            logpassword.requestFocus();
            return;
        }


        if(Logpassword.length()<6){
            logpassword.setError("Min password length should be 6 characters");
            logpassword.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(Logemail,Logpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()){
                        startActivity(new Intent(Login_user.this,Profile.class));
                    }else {
                        user.sendEmailVerification();
                        Toast.makeText(Login_user.this, "Check your email to verify your account", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }

                }else{
                    Toast.makeText(Login_user.this, "Failed to login! please check your credentials..", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }

            }


        });

    }
}