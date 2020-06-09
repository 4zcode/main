package com.example.myapplication.don_sang;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.addProfile.addDonation;
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

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.R.*;


public class Blooddonor extends Fragment {
    private ImageView ProfileImage;
    private Button b1,b2,b3;
    private Uri mImageUri;
    public static int PICK_IMAGE = 1;
    private EditText first_name;
    private EditText second_name;
    private EditText adress;
    private EditText phone;
    private TextView ageq;
    private Integer age;
    private CheckBox ap;
    private CheckBox an;
    private CheckBox bp;
    private CheckBox bn;
    private CheckBox op;
    private CheckBox on;
    private CheckBox abp;
    private CheckBox abn;
    private ProgressBar mProgressBar;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    public Blooddonor(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("donateur","we are in 4.0");
        View view = inflater.inflate(R.layout.fragment_blooddonor, container, false);
        Log.d("donateur","we are in 4");
        ageq=(TextView) view.findViewById(R.id.age);
        Log.d("donateur","we are in 5");
        b1=(Button) view.findViewById(id.minis);
        b2=(Button) view.findViewById(id.plus);
        b3=(Button) view.findViewById(id.adddonateur);
        ProfileImage = (ImageView) view.findViewById(R.id.donateur_image);
        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClicks();
            }
        });
        Log.d("donateur","we are in 6");
        mProgressBar = view.findViewById(R.id.progress_bardo);
        Log.d("donateur","we are in 7");
        first_name=(EditText) view.findViewById(R.id.fullname);
        Log.d("donateur","we are in 8");
        second_name=(EditText) view.findViewById(R.id.username_d);
        Log.d("donateur","we are in 9");
        adress=(EditText) view.findViewById(R.id.location_d);
        Log.d("donateur","we are in 10");
        phone=(EditText) view.findViewById(R.id.phone_d);
        Log.d("donateur","we are in 11");
        ap=(CheckBox) view.findViewById(R.id.ap);
        Log.d("donateur","we are in 12");
        an=(CheckBox) view.findViewById(R.id.an);
        Log.d("donateur","we are in 13");
        bp=(CheckBox) view.findViewById(R.id.bp);
        Log.d("donateur","we are in 14");
        bn=(CheckBox) view.findViewById(R.id.bn);
        Log.d("donateur","we are in 15");
        op=(CheckBox) view.findViewById(R.id.op);
        Log.d("donateur","we are in 16");
        on=(CheckBox) view.findViewById(R.id.on);
        Log.d("donateur","we are in 17");
        abp=(CheckBox) view.findViewById(R.id.abp);
        Log.d("donateur","we are in 18");
        abn=(CheckBox) view.findViewById(R.id.abn);
        Log.d("donateur","we are in 19");
        mStorageRef = FirebaseStorage.getInstance().getReference("Donateur");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Donateur");
        age=new Integer(20);
        ageq.setText(age.toString());
        Log.d("donateur","we are in 20");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minis();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plus();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adddonations();
            }
        });
        return view;
    }

    public void onClicks() {
        Log.d("image","1");
        Intent gallery = new Intent();
        Log.d("image","2");
        gallery.setType("image/*");
        Log.d("image","3");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            mImageUri = data.getData();

            try {
                AppCompatActivity test=new AppCompatActivity();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap( getActivity().getContentResolver(), mImageUri);
                ProfileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void adddonations() {

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
                                    String gr=grsanguin();
                                    Log.d("donateur","we still alive 1.0");
                                    String name=first_name.getText().toString().trim()+" "+second_name.getText().toString().trim();
                                    Log.d("donateur","we still alive 1");
                                    don_de_song donateur=new don_de_song (donateurID,name,adress.getText().toString().trim(),phone.getText().toString().trim(),ageq.getText().toString().trim(),gr,uri.toString());
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
                                                Toast.makeText(getContext(), "your User information is update", Toast.LENGTH_LONG).show();
                                                Log.d("donateur","we still alive 15");
                                                Intent intent = new Intent(getContext(), MainActivity.class);
                                                Log.d("donateur","we still alive 17");
                                                startActivity(intent);
                                            } else {
                                                String error;
                                                error = task.getException().getMessage();
                                                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
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
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), "No file selected", Toast.LENGTH_SHORT).show();
        }

    }

    //jest for getting the extention of the image ex .png
    private String getFileExtension(Uri uri) {
        AppCompatActivity test=new AppCompatActivity();
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private String grsanguin(){
        String gr="null";
        if(ap.isChecked()){gr="A+";}else {if(an.isChecked()){gr="A-";}else{if(bp.isChecked()){gr="B+";}else{if(bn.isChecked()){gr="B-";}else{if(op.isChecked()){gr="O+";}else{if(on.isChecked()){gr="O-";}else{if(abp.isChecked()){gr="AB+";}else{if(abn.isChecked()){gr="AB-";}}}}}}}}
        return gr;
    }
    public void minis()
    {
        this.age=age.intValue()-1;
        ageq.setText(age.toString());
    }
    public void plus()
    {
        this.age=age.intValue()+1;
        ageq.setText(age.toString());
    }
}
