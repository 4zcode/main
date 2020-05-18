package com.example.myapplication.addProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.don_sang.don_de_song;
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
import java.util.HashMap;
import java.util.Map;

public class addDonation extends AppCompatActivity {

    private ImageView ProfileImage;
    private Uri mImageUri;
    public static int PICK_IMAGE = 1;
    private EditText first_name;
    private EditText adress;
    private EditText phone;
    private EditText groupsanguin;
    private ProgressBar mProgressBar;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donation);
        ProfileImage = (ImageView) findViewById(R.id.Profile_Imaged);
        mProgressBar = findViewById(R.id.progress_bard);
        first_name=(EditText) findViewById(R.id.dfname);
        adress=(EditText) findViewById(R.id.dadress);
        phone=(EditText) findViewById(R.id.dnumber);
        groupsanguin=(EditText) findViewById(R.id.groupsanguin);
        mStorageRef = FirebaseStorage.getInstance().getReference("Donateur");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Donateur");
    }

    public void onClicks(View v) {
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

    public void adddonations(View view) {

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
                                    String donateurID = mDatabaseRef.push().getKey();
                                    Log.d("donateur","we still alive 1");
                                    don_de_song donateur=new don_de_song (donateurID,first_name.getText().toString().trim(),adress.getText().toString().trim(),phone.getText().toString().trim(),groupsanguin.getText().toString().trim(),uri.toString());
                                    Log.d("donateur","we still alive 2");
                                    mDatabaseRef.child(donateurID).setValue(donateur);
                                    Log.d("donateur","we still alive 3");
                                    mStorageRef = FirebaseStorage.getInstance().getReference("Users");
                                    Log.d("donateur","we still alive 4");
                                    final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                                            + "." + getFileExtension(mImageUri));
                                    Log.d("donateur","we still alive 5");
                                    mUploadTask = fileReference.putFile(mImageUri);
                                    Log.d("donateur","we still alive 6");
                                    FirebaseUser user = FirebaseAuth.getInstance().getInstance().getCurrentUser();
                                    Log.d("donateur","we still alive 7");
                                    mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");
                                    Log.d("donateur","we still alive 8");
                                    Map<String, Object> UserData = new HashMap<String, Object>();
                                    Log.d("donateur","we still alive 9");
                                    UserData.put("UserName", first_name.getText().toString().trim());
                                    Log.d("donateur","we still alive 10");
                                    UserData.put("UserType", "Donateur");
                                    Log.d("donateur","we still alive 11");
                                    UserData.put("User_ID_Firebase", user.getUid());
                                    Log.d("donateur","we still alive 12");
                                    UserData.put("UserImageUrl", uri.toString());
                                    Log.d("donateur","we still alive 13");
                                    mDatabaseRef.child(user.getUid()).setValue(UserData).addOnCompleteListener(new OnCompleteListener() {
                                        @Override
                                        public void onComplete(@NonNull Task task) {
                                            if (task.isSuccessful()) {
                                                Log.d("donateur","we still alive 14");
                                                Toast.makeText(addDonation.this, "your User information is update", Toast.LENGTH_LONG).show();
                                                Log.d("donateur","we still alive 15");
                                                Intent intent = new Intent(addDonation.this, MainActivity.class);
                                                Log.d("donateur","we still alive 17");
                                                startActivity(intent);
                                            } else {
                                                String error;
                                                error = task.getException().getMessage();
                                                Toast.makeText(addDonation.this, error, Toast.LENGTH_LONG).show();
                                                Log.d("donateur","we still alive 18");

                                            }
                                        }
                                    });

                                }
                            });
                        }})
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(addDonation.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
