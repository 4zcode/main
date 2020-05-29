package com.example.myapplication.addProfile;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.pharma.DBManagerPharmacy;
import com.example.myapplication.R;
import com.example.myapplication.pharma.pharmacy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddPharmacyProfil extends AppCompatActivity  {
        private ImageView ProfileImage;
        private String openclose;
        private Uri mImageUri;
        public static int PICK_IMAGE = 1;
        private EditText first_name;
        private EditText adress;
        private EditText phone;
        private String tim;
        private EditText description;
        private CheckBox checkBox1;
        private CheckBox checkBox2;
        public String mSpinnerLabel;
        private Spinner spiner1;
        private Spinner spiner2;
        private ProgressBar mProgressBar;
        private StorageReference mStorageRef;
        private DatabaseReference mDatabaseRef;
        private StorageTask mUploadTask;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_pharmacy);
            ProfileImage = (ImageView) findViewById(R.id.Profile_Imageph);
            mProgressBar = findViewById(R.id.progress_barph);
            first_name=(EditText) findViewById(R.id.pfname);
            adress=(EditText) findViewById(R.id.phadress);
            phone=(EditText) findViewById(R.id.phnumber);
            description=(EditText) findViewById(R.id.phdescription);
            mStorageRef = FirebaseStorage.getInstance().getReference("pharmacies");
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("pharmacies");
        }
        public void onClick(View v) {
            Intent gallery = new Intent();
            gallery.setType("image/*");
            gallery.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
        }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
                mImageUri = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mImageUri);
                    ProfileImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void create_profile(View view) {
           final String time1=tim;
           Log.d("profiletest","time:"+time1);
            if (mImageUri != null) {
                final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                        + "." + getFileExtension(mImageUri));
                mUploadTask = fileReference.putFile(mImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mProgressBar.setProgress(0);
                                    }
                                }, 500);
                                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String pharmacyID = mDatabaseRef.push().getKey();
                                        Log.d("profiletest",first_name.getText().toString().trim()+ adress.getText().toString().trim()+ phone.getText().toString()+time1.trim()+  uri.toString()+description.getText().toString().trim());
                                        pharmacy pharmacy = new pharmacy(pharmacyID, first_name.getText().toString().trim(), adress.getText().toString().trim(), phone.getText().toString(),time1.trim(),  uri.toString(),description.getText().toString().trim());
                                        mDatabaseRef.child(pharmacyID).setValue(pharmacy);
                                        mStorageRef = FirebaseStorage.getInstance().getReference("Users");
                                        final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                                                + "." + getFileExtension(mImageUri));
                                        mUploadTask = fileReference.putFile(mImageUri);
                                        FirebaseUser user = FirebaseAuth.getInstance().getInstance().getCurrentUser();
                                        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");
                                        Map<String, Object> UserData = new HashMap<String, Object>();
                                        UserData.put("UserName", first_name.getText().toString().trim());
                                        UserData.put("UserType", "Pharmacie");
                                        UserData.put("User_ID_Firebase", user.getUid());
                                        UserData.put("UserImageUrl", uri.toString());
                                        mDatabaseRef.child(user.getUid()).setValue(UserData).addOnCompleteListener(new OnCompleteListener() {
                                            @Override
                                            public void onComplete(@NonNull Task task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "your User information is update", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(AddPharmacyProfil.this, MainActivity.class);
                                                    startActivity(intent);
                                                } else {
                                                    String error;
                                                    error = task.getException().getMessage();
                                                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();

                                                }
                                            }
                                        });

                                    }
                                });
                            }})
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddPharmacyProfil.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                mProgressBar.setProgress((int) progress);
                            }
                        });
            } else {
                Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            }

        }

        //jest for getting the extention of the image ex .png
        private String getFileExtension(Uri uri) {
            ContentResolver cR = getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            return mime.getExtensionFromMimeType(cR.getType(uri));
        }
        public String timing() {
            String time = "";
            Spinner spin1, spin2;
            CheckBox checkBox3 = (CheckBox) findViewById(R.id.checkbox_24_dimanche);
            checkBox1 = (CheckBox) findViewById(R.id.checkbox_24);
            if (checkBox1.isChecked()) {
                time = "it's open 24h/24h and 7days/7days@123brtest";
                Log.d("profiletest",time);
            } else {
                for (int i = 0; i <= 6; i++) {
                    switch (i) {
                        case 0:
                            checkBox3 = (CheckBox) findViewById(R.id.checkbox_24_dimanche);
                            checkBox2 = (CheckBox) findViewById(R.id.notify_me_checkbox_dimanche);
                            break;
                        case 1:
                            checkBox3 = (CheckBox) findViewById(R.id.checkbox_24_lundi);
                            checkBox2 = (CheckBox) findViewById(R.id.notify_me_checkbox_lundi);
                            break;
                        case 2:
                            checkBox3 = (CheckBox) findViewById(R.id.checkbox_24_mardi);
                            checkBox2 = (CheckBox) findViewById(R.id.notify_me_checkbox_mardi);
                            break;
                        case 3:
                            checkBox3 = (CheckBox) findViewById(R.id.checkbox_24_mercredi);
                            checkBox2 = (CheckBox) findViewById(R.id.notify_me_checkbox_mercredi);
                            break;
                        case 4:
                            checkBox3 = (CheckBox) findViewById(R.id.checkbox_24_jeudi);
                            checkBox2 = (CheckBox) findViewById(R.id.notify_me_checkbox_jeudi);
                            break;
                        case 5:
                            checkBox3 = (CheckBox) findViewById(R.id.checkbox_24_vendredi);
                            checkBox2 = (CheckBox) findViewById(R.id.notify_me_checkbox_vendredi);
                            break;
                        case 6:
                            checkBox3 = (CheckBox) findViewById(R.id.checkbox_24_samedi);
                            checkBox2 = (CheckBox) findViewById(R.id.notify_me_checkbox_samedi);
                            break;
                    }
                    if (checkBox2.isChecked()) {
                        spin1 = (Spinner) findViewById(R.id.dimanche_temp1);
                        spin2 = (Spinner) findViewById(R.id.dimanche_temp2);
                        switch (i) {
                            case 0:
                                spin1 = (Spinner) findViewById(R.id.dimanche_temp1);
                                spin2 = (Spinner) findViewById(R.id.dimanche_temp2);
                                time = time+"dimanche<br>";
                                break;
                            case 1:
                                spin1 = (Spinner) findViewById(R.id.lundi_temp1);
                                spin2 = (Spinner) findViewById(R.id.lundi_temp2);
                                time = time+"lundi<br>";
                                break;
                            case 2:
                                spin1 = (Spinner) findViewById(R.id.mardi_temp1);
                                spin2 = (Spinner) findViewById(R.id.mardi_temp2);
                                time = time+"mardi<br>";
                                break;
                            case 3:
                                spin1 = (Spinner) findViewById(R.id.mercredi_temp1);
                                spin2 = (Spinner) findViewById(R.id.mercredi_temp2);
                                time = time+"mercredi<br>";
                                break;
                            case 4:
                                spin1 = (Spinner) findViewById(R.id.jeudi_temp1);
                                spin2 = (Spinner) findViewById(R.id.jeudi_temp2);
                                time = time+"jeudi<br>";
                                break;
                            case 5:
                                spin1 = (Spinner) findViewById(R.id.vendredi_temp1);
                                spin2 = (Spinner) findViewById(R.id.vendredi_temp2);
                                time = time+"vendredi<br>";
                                break;
                            case 6:
                                spin1 = (Spinner) findViewById(R.id.samedi_temp1);
                                spin2 = (Spinner) findViewById(R.id.samedi_temp2);
                                time = time+"samedi<br>";
                                break;
                        }
                        spin1.setVisibility(View.VISIBLE);
                        spin2.setVisibility(View.VISIBLE);
                        checkBox3.setVisibility(View.VISIBLE);
                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                                R.array.horraire, android.R.layout.simple_spinner_item);

                        adapter.setDropDownViewResource
                                (android.R.layout.simple_spinner_dropdown_item);
                        if (spin1 != null) {
                            spin1.setAdapter(adapter);
                        }
                        if (spin2 != null) {
                            spin2.setAdapter(adapter);
                        }
                        if(checkBox3.isChecked()){
                            time = time +"24h/24h@123brtest";

                        }else{
                            spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    openclose=parent.getItemAtPosition(position).toString();
                                    Log.d("profiletest","spin1"+openclose);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                            time=time+openclose+"<br>";
                            Log.d("profiletest",time);
                            spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    openclose=parent.getItemAtPosition(position).toString();
                                    Log.d("profiletest","spin2"+openclose);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                            time=time+openclose+"@123brtest";
                            Log.d("profiletest",time);

                        }


                    }
                    Log.d("profiletest",time);
                }

            }
            return time;
        }


    public void check_h(View view) {
          tim=timing();
    }
}
