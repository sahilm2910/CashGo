package com.learnacad.cashgo.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.learnacad.cashgo.R;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    ProgressDialog pDialog;
    TextInputEditText emailEditText;
    TextInputEditText passwordEditText;
    Button loginButton;
    Button toSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        emailEditText = (TextInputEditText) findViewById(R.id.loginEmailEditText);
        passwordEditText = (TextInputEditText) findViewById(R.id.loginPasswordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        toSignUp = (Button) findViewById(R.id.loginSingupButton);

        this.setTitle("Login");

        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pDialog.setMessage("Loading...");
                showDialog();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                emailEditText.setText("");
                passwordEditText.setText("");
                passwordEditText.clearFocus();

                mAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                hideDialog();

                                if(task.isSuccessful()){

                                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                                }else{

                                    task.addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(LoginActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
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
