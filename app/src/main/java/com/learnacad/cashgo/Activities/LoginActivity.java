package com.learnacad.cashgo.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.learnacad.cashgo.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    ProgressDialog pDialog;
    TextInputEditText emailEditText;
    TextInputEditText passwordEditText;
    Button loginButton,facebookLoginButton;
    Button toSignUp;
    private CallbackManager callbackManager;
    public static final int REQUEST_CODE_GOOGLE = 7;
    public GoogleApiClient googleApiClient;
    SignInButton signInButton;
    String gname,gGender,gBday;



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);

        if(requestCode == REQUEST_CODE_GOOGLE){

            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if(googleSignInResult.isSuccess()){

                GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();

                FirebaseUserAuth(googleSignInAccount);

            }

        }
    }

    private void FirebaseUserAuth(GoogleSignInAccount googleSignInAccount) {

        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

        Toast.makeText(LoginActivity.this,""+ authCredential.getProvider(),Toast.LENGTH_LONG).show();

        mAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                gname = user.getDisplayName();

                                String gmail = user.getEmail();
                                String number = user.getPhoneNumber();

                                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                intent.putExtra("googleLogin",true);
                                intent.putExtra("gname",gname);
                                intent.putExtra("gmail",gmail);
                                intent.putExtra("gnumber",number);
                                Toast.makeText(LoginActivity.this, gname, Toast.LENGTH_SHORT).show();
                                startActivity(intent);

                            }
                        }else{

                            Toast.makeText(LoginActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getSupportActionBar().hide();
        pDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        facebookLoginButton = findViewById(R.id.facebook_loginButton);
        callbackManager = CallbackManager.Factory.create();
        signInButton = findViewById(R.id.signInButton);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(LoginActivity.this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .addApi(Plus.API)
                .build();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent authIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(authIntent,REQUEST_CODE_GOOGLE);
            }
        });


        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                AccessToken accessToken = loginResult.getAccessToken();

               GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                   @Override
                   public void onCompleted(JSONObject object, GraphResponse response) {
                       Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        String name = "o",email = "0",gender = "0",birthday = "0";
                       try {
                           name = object.getString("name");
                           email = object.getString("email");
                           gender = object.getString("gender");
                           birthday = object.getString("birthday");
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                       intent.putExtra("facebookLogin",true);
                       intent.putExtra("facebookName",name);
                       intent.putExtra("facebookEmail",email);
                       intent.putExtra("facebookGender",gender);
                       intent.putExtra("facebookBday",birthday);
                       Toast.makeText(LoginActivity.this, name, Toast.LENGTH_SHORT).show();
                       startActivity(intent);

                   }
               });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();



            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });




        if(mAuth.getCurrentUser() != null || (AccessToken.getCurrentAccessToken() != null)){

            Log.d("minorCheck", String.valueOf(mAuth));

            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
        }

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
