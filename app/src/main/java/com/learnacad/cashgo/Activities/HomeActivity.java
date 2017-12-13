package com.learnacad.cashgo.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.joaquimley.faboptions.FabOptions;
import com.learnacad.cashgo.Fragments.ContactUs;
import com.learnacad.cashgo.Fragments.HomeHoldingFragment;
import com.learnacad.cashgo.Fragments.PartnersFragment;
import com.learnacad.cashgo.Fragments.RatingsFragment;
import com.learnacad.cashgo.Fragments.TransactionsFragment;
import com.learnacad.cashgo.Models.SharedPrefManager;
import com.learnacad.cashgo.Models.User;
import com.learnacad.cashgo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static int SELECT_IMAGE = 1996;
    public static int CLICKED_IMAGE = 1990;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    Uri filePathUri;
    SharedPrefManager sharedPrefManager;
    ProgressDialog pd;
    FirebaseAuth auth;
    String fileName;

    // changes in this Home

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.libraryCourseContentToolbar);
        toolbar.setBackgroundColor(Color.parseColor("#0277BD"));
        setSupportActionBar(toolbar);
        this.setTitle("Home");


        pd = new ProgressDialog(this);
        sharedPrefManager = new SharedPrefManager(this);
        auth = FirebaseAuth.getInstance();
     //   TextView nameNav = findViewById(R.id.navHeadName);


        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        Intent intent = getIntent();
        String name = intent.getStringExtra("facebookName");

        Boolean isGoogleLogin = intent.getBooleanExtra("googleLogin",false);

        if(isGoogleLogin){

            String gname = intent.getStringExtra("gname");
            String email =  intent.getStringExtra("gmail" );
            String number = intent.getStringExtra("gnumber");
            String userId = databaseReference.push().getKey();
            sharedPrefManager.setUserId(userId);
            sharedPrefManager.setUserName(gname);
      //      nameNav.setText(sharedPrefManager.getUserName() + " ");
            User user = new User(userId,gname,number,email,null,null);
            databaseReference.child(userId).setValue(user);
        }

        if(!TextUtils.isEmpty(name)){

            String email = intent.getStringExtra("facebookEmail");
            String gender = intent.getStringExtra("facebookGender");
            String bday = intent.getStringExtra("facebookBday");
            String userId = databaseReference.push().getKey();
            sharedPrefManager.setUserId(userId);
            sharedPrefManager.setUserName(name);
       //     nameNav.setText(sharedPrefManager.getUserName() + " ");

            User user = new User(userId,name,"000",email,bday,gender);
            databaseReference.child(userId).setValue(user);
        }

        FabOptions fab = (FabOptions) findViewById(R.id.fab);
        fab.setBackgroundColor(R.color.red);
        fab.setFabColor(R.color.red);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()){

                    case R.id.fabmenu_camera:
                        onLaunchCamera();
                        break;

                    case R.id.fabmenu_images:{

                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,"Select Picture"),SELECT_IMAGE);
                        break;
                    }

                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame_homeActivity,new HomeHoldingFragment());
        fragmentTransaction.commit();

}

    private void onLaunchCamera() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){

            startActivityForResult(takePictureIntent,CLICKED_IMAGE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SELECT_IMAGE && resultCode == Activity.RESULT_OK){

            if(data != null){

                try {

                    filePathUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),data.getData());

                    uploadImageToFirebase();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        if(requestCode == CLICKED_IMAGE && resultCode == Activity.RESULT_OK){
            
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            Uri uri = getImageUri(bitmap);

            filePathUri = uri;
            uploadImageToFirebase();
        }
    }

    private void uploadImageToFirebase() {

        if(filePathUri != null){

            fileName = String.valueOf(System.currentTimeMillis());

            StorageReference storageReference1 = storageReference.child("users/"+ sharedPrefManager.getUserName() + "/" + fileName + "." + getFileExtension(filePathUri));

            pd.setMessage("Uploading");
            pd.show();
            pd.setCancelable(false);
            storageReference1.putFile(filePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(HomeActivity.this, "Image Upload Successfully", Toast.LENGTH_SHORT).show();

                           AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                            LayoutInflater inflater = HomeActivity.this.getLayoutInflater();
                            View dialogView = inflater.inflate(R.layout.alert_dialog_form,null);
                            builder.setView(dialogView);
                            final EditText billAmountEditText = dialogView.findViewById(R.id.alertDialogTotalAmtEditText);
                            final EditText orderIdEditText = dialogView.findViewById(R.id.alertDialogOrderIdEditText);
                            ImageView alertDialogImageView = dialogView.findViewById(R.id.alertDialogImageView);

                            alertDialogImageView.setImageURI(filePathUri);
                            final AlertDialog dialog = builder.create();



                            dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                        String billAmt = billAmountEditText.getText().toString().trim();
                                        String orderId = orderIdEditText.getText().toString().trim();

                                        if(TextUtils.isEmpty(billAmt)){

                                            Toast.makeText(HomeActivity.this, "Sorry Bill amount is necessary to get cashback", Toast.LENGTH_SHORT).show();
                                            return;
                                        }


                                        getApiCallResult(billAmt,orderId);
                                        dialog.cancel();
                                }
                            });
                            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    dialog.dismiss();
                                }
                            });
                            dialog.show();

                            pd.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(HomeActivity.this, "Couldn't uoload the image.", Toast.LENGTH_SHORT).show();

                            pd.dismiss();
                        }
                    });
        }
    }

    private void getApiCallResult(String billAmt, String orderId) {

        pd.setMessage("Verifying");
        pd.setCancelable(false);
        pd.show();
        String username = sharedPrefManager.getUserName();
        String url = "https://cashgo.herokuapp.com/cashgo/verifytrans/2.0/" + username + "/" + fileName + ".jpg/" + billAmt;
        if(!orderId.isEmpty()){

            StringBuilder sb = new StringBuilder();
            sb.append(url);
            sb.append("/").append(orderId);

            url = sb.toString();
        }

        Log.d("urlCheck",url);

        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String result = response.getString("result");

                            if(result.contentEquals("true")){

                                Toast.makeText(HomeActivity.this, "Results matched", Toast.LENGTH_LONG).show();


                            }else{


                                Toast.makeText(HomeActivity.this, "Not verified", Toast.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pd.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        pd.dismiss();

                        Toast.makeText(HomeActivity.this, "Not verified", Toast.LENGTH_SHORT).show();
                        pd.dismiss();

                        Log.d("errorincon",anError.getLocalizedMessage());
                        Log.d("errorincon", String.valueOf(anError.getErrorCode()));
                        Log.d("errorincon",anError.getErrorBody());
                        Log.d("errorincon",anError.getErrorDetail());

                    }
                });
    }

    public String getFileExtension(Uri uri){

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public Uri getImageUri(Bitmap bitmap){

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,"bill",null);
        return Uri.parse(path);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            Log.d("checkHome","inside home menu");
            fragmentTransaction.replace(R.id.content_frame_homeActivity,new HomeHoldingFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.trans) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame_homeActivity,new TransactionsFragment()).addToBackStack(null).commit();


        } else if (id == R.id.contact) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame_homeActivity,new ContactUs()).addToBackStack(null).commit();

        } else if (id == R.id.partners) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame_homeActivity,new PartnersFragment()).addToBackStack(null).commit();

        } else if (id == R.id.rate) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame_homeActivity,new RatingsFragment()).addToBackStack(null).commit();

        } else if (id == R.id.logout) {

            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();

            Log.d("minorCheck","signed out");
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
