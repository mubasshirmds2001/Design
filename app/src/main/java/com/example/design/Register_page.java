package com.example.design;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register_page extends AppCompatActivity implements View.OnClickListener{

    private EditText username,email,password,cpassword;
    private ProgressBar progressBar;
    private Button createact;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        mAuth = FirebaseAuth.getInstance();

        username=(EditText) findViewById(R.id.edusername);
        email=(EditText) findViewById(R.id.edemail);
        password=(EditText) findViewById(R.id.edpassword);
        cpassword=(EditText) findViewById(R.id.edcpassword);
        createact =(Button) findViewById(R.id.btnCreateAcc);

        progressBar=(ProgressBar) findViewById(R.id.progressBar);

        createact.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCreateAcc:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String Username=username.getText().toString().trim();
        String Email=email.getText().toString().trim();
        String Password=password.getText().toString().trim();
        String CPassword=cpassword.getText().toString().trim();

        if(Username.isEmpty()){
            username.setError("Username is required!");
            username.requestFocus();
            return;
        }

        if(Email.isEmpty()){
            email.setError("E-mail is required!");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            email.setError("Please provide valid email..");
            email.requestFocus();
            return;
        }

        if(Password.isEmpty()){
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }

        if(CPassword.isEmpty()){
            cpassword.setError("Please confirm your password!");
            cpassword.requestFocus();
            return;
        }

        if(password.length()<6){
            password.setError("Min password length should be 6 characters");
            password.requestFocus();
            return;
        }



        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    User user = new User(Username,Email);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register_page.this, "User has been registered successfully", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    else{
                                        Toast.makeText(Register_page.this, "Failed to register! Try again..", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                }else {
                    Toast.makeText(Register_page.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });

    }

}