package com.learnacad.cashgo.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.learnacad.cashgo.Models.User;
import com.learnacad.cashgo.R;

import java.util.ArrayList;

public class UserDataEntryActivity extends AppCompatActivity {

     TextInputEditText nameEditText;
     TextInputEditText emailEditText;
     TextInputEditText dobEditText;
     TextInputEditText mobileNumEditText;
     Spinner spinner;
    String gender;
    Button registerButton;
    private DatabaseReference mDatabase;
    String email,name,dob,mobile,password;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_entry);

        pDialog = new ProgressDialog(this);
        this.setTitle("Register");


        nameEditText = (TextInputEditText) findViewById(R.id.editTextNameRegisterActivity);
          emailEditText = (TextInputEditText) findViewById(R.id.editTextEmailRegisterActivity);
          dobEditText = (TextInputEditText) findViewById(R.id.editTextDOBNumRegisterActivity);
          mobileNumEditText = (TextInputEditText) findViewById(R.id.editTextmobileNumRegisterActivity);
          registerButton = (Button) findViewById(R.id.buttonRegisterRegisterActivity);

          mDatabase = FirebaseDatabase.getInstance().getReference("users");

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("pass");
        emailEditText.setText(email);
        emailEditText.setClickable(false);
        emailEditText.setEnabled(false);

        final ArrayList<String> genders = new ArrayList<>();
        genders.add("Male");
        genders.add("Female");
        genders.add("Others");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,genders);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = (Spinner) findViewById(R.id.spinnerRegisterActivity);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(0,true);
        View v = spinner.getSelectedView();
        ((TextView)v).setTextColor(Color.WHITE);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)view).setTextColor(Color.WHITE);
                gender =  adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = nameEditText.getText().toString().trim();
                mobile = mobileNumEditText.getText().toString().trim();
                dob = dobEditText.getText().toString().trim();


                if(name.isEmpty()){

                    nameEditText.setError("Enter your name.");
                    return;
                }

                if(mobile.isEmpty()){

                    mobileNumEditText.setError("Enter mobile number.");
                    return;
                }

                if(dob.isEmpty()){

                    dobEditText.setError("Enter your date of birth.");
                    return;
                }

                pDialog.setMessage("Loading...");
                showDialog();
                nameEditText.setText("");
                mobileNumEditText.setText("");
                dobEditText.setText("");
                spinner.setSelection(0,true);
                String userId = mDatabase.push().getKey();
                User user = new User(userId,name,mobile,email,dob,password,gender);
                mDatabase.child(userId).setValue(user);
                hideDialog();
                Log.d("pasword",password);
                startActivity(new Intent(UserDataEntryActivity.this,HomeActivity.class));

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
