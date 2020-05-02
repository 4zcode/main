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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Laboratoir.Labo;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
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

public class AddLaboProfile extends AppCompatActivity {
    private ImageView ProfileImage;
    private Uri mImageUri;
    public static int PICK_IMAGE = 1;
    private EditText first_name;
    private EditText adress;
    private EditText phone;
    private ProgressBar mProgressBar;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_labo_profile);
        ProfileImage = (ImageView) findViewById(R.id.Profile_Imagelabo);
        mProgressBar = findViewById(R.id.progress_barlabo);
        first_name=(EditText) findViewById(R.id.laboname);
        adress=(EditText) findViewById(R.id.laboadress);
        phone=(EditText) findViewById(R.id.labonumber);
        mStorageRef = FirebaseStorage.getInstance().getReference("laboratoir");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("laboratoir");
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

    public void create_labo(View view) {
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
                                    String laboID = mDatabaseRef.push().getKey();
                                    Labo labo=new Labo(laboID,first_name.getText().toString().trim(),adress.getText().toString().trim(),phone.getText().toString().trim(),uri.toString());
                                    mDatabaseRef.child(laboID).setValue(labo);
                                    mStorageRef = FirebaseStorage.getInstance().getReference("Users");
                                    final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                                            + "." + getFileExtension(mImageUri));
                                    mUploadTask = fileReference.putFile(mImageUri);
                                    FirebaseUser user = FirebaseAuth.getInstance().getInstance().getCurrentUser();
                                    mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");
                                    Map<String, Object> UserData = new HashMap<String, Object>();
                                    UserData.put("UserName", first_name.getText().toString().trim());
                                    UserData.put("UserType", "Laboratoir");
                                    UserData.put("User_ID_Firebase", user.getUid());
                                    UserData.put("UserImageUrl", uri.toString());
                                    mDatabaseRef.child(user.getUid()).setValue(UserData).addOnCompleteListener(new OnCompleteListener() {
                                        @Override
                                        public void onComplete(@NonNull Task task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "your User information is update", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(AddLaboProfile.this, MainActivity.class);
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
                            Toast.makeText(AddLaboProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
