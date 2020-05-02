package com.example.myapplication.addProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.doctors.Doctors;
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

public class AddDoctorProfile extends AppCompatActivity {
    private ImageView ProfileImage;
    private Uri mImageUri;
    public static int PICK_IMAGE = 1;
    private EditText first_name;
    private EditText second_name;
    private EditText adress;
    private EditText speciality;
    private EditText phone;
    private RadioGroup sexe;
    private ProgressBar mProgressBar;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ProfileImage = (ImageView) findViewById(R.id.Profile_Image);
        mProgressBar = findViewById(R.id.progress_bar);
        first_name=(EditText) findViewById(R.id.fname);
        second_name=(EditText) findViewById(R.id.sname);
        adress=(EditText) findViewById(R.id.padress);
        sexe=(RadioGroup) findViewById(R.id.sex);
        phone=(EditText) findViewById(R.id.pnumber);
        speciality=(EditText) findViewById(R.id.doctor_speciality);
        mStorageRef = FirebaseStorage.getInstance().getReference("Doctor");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Doctor");
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
                            Toast.makeText(AddDoctorProfile.this, "you have creat a Doctor", Toast.LENGTH_LONG).show();
                            String DoctorID = mDatabaseRef.push().getKey();
                            sexe=(RadioGroup) findViewById(R.id.sex);
                            String sex;
                            int i = sexe.getCheckedRadioButtonId();
                            if (i == R.id.man) {
                                sex = "man";
                            } else {
                                sex = "woman";
                            }
                            Doctors doctors = new Doctors(DoctorID, first_name.getText().toString().trim() + " " + second_name.getText().toString().trim(), adress.getText().toString().trim(), phone.getText().toString().trim(), speciality.getText().toString().trim(), sex, taskSnapshot.getUploadSessionUri().toString());
                            mDatabaseRef.child(DoctorID).setValue(doctors);
                            Intent intent = new Intent(AddDoctorProfile.this, MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddDoctorProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
}
