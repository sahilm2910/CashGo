package com.learnacad.cashgo.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.learnacad.cashgo.R;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    TextInputEditText emailEditText;
    TextInputEditText passwordEditText;
    Button signupButton;
    Button toLogin;
    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        pDialog = new ProgressDialog(this);
        this.setTitle("Sign Up");
        mAuth = FirebaseAuth.getInstance();
        this.getSupportActionBar().hide();


        emailEditText = (TextInputEditText) findViewById(R.id.loginEmailEditText);
        passwordEditText = (TextInputEditText) findViewById(R.id.loginPasswordEditText);
        signupButton = (Button) findViewById(R.id.loginButton);
        toLogin = (Button) findViewById(R.id.loginSingupButton);

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }
        });




        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = emailEditText.getText().toString().trim();
                final String password = passwordEditText.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                emailEditText.setText("");
                passwordEditText.setText("");
                passwordEditText.clearFocus();

                pDialog.setMessage("Loading...");
                showDialog();

                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                hideDialog();

                                if(task.isSuccessful()){

                                    Toast.makeText(SignUpActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(SignUpActivity.this,UserDataEntryActivity.class);
                                    intent.putExtra("email",email);
                                    intent.putExtra("pass",password);
                                    startActivity(intent);

                                }else{

                                    task.addOnFailureListener(SignUpActivity.this, new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SignUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            }
                        });

                }
            });



    }

    public void showDialog(){

        if(!pDialog.isShowing()){

            pDialog.show();
        }
    }

    public void hideDialog(){

        if(pDialog.isShowing()){

            pDialog.hide();
        }
    }



}
