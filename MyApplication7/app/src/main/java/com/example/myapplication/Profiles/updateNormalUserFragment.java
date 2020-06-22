package com.example.myapplication.Profiles;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
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

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_IS_USER_DONOR;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_ADRESSES;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_AGE;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_COMMUNE;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_EMAIL;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_IMAGE;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_NAME;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_TYPE;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_WILAYA;
import static com.example.myapplication.utilities.PreferenceUtilities.PREFERENCE_NAME;
import static com.example.myapplication.utilities.PreferenceUtilities.saveViewPagerInscriptionPosition;
import static com.example.myapplication.utilities.tools.getCommuns;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link updateNormalUserFragment#} factory method to
 * create an instance of this fragment.
 */
public class updateNormalUserFragment extends Fragment {



    private ImageView userImage;

    private ImageButton b1,b2;
    private EditText ageEdite;
    private Integer age = 20;
    private Button next;

    private CheckBox checkBox;

    private Spinner spinnerWilaya, spinnerCommuns;
    private ArrayAdapter<CharSequence> commmunsCodeAdapter;

    private String[] mAdress={"Médéa","Médéa","Ain Bensultan"};

    private int[] mType ={0};

    private boolean isDonor=false;

    private EditText adreassEdit, nameEdit;

    private Uri mImageUri;
    public static int PICK_IMAGE = 1;


    private ProgressBar mProgressBar;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    private String mEmail;
    private String mPhone;


    public updateNormalUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_normal_user, container, false);

        mStorageRef = FirebaseStorage.getInstance().getReference("User");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");

        userImage = (ImageView) view.findViewById(R.id.add_normal_user_image);
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClicks();
            }
        });


        ageEdite = (EditText) view.findViewById(R.id.add_age);

        b1 = (ImageButton) view.findViewById(R.id.minis_age);
        b2 = (ImageButton) view.findViewById(R.id.plus_age);

        adreassEdit = (EditText) view.findViewById(R.id.add_location);
        nameEdit = (EditText) view.findViewById(R.id.add_nom);

        spinnerWilaya = view.findViewById(R.id.add_spinner_wilaya);
        spinnerCommuns = view.findViewById(R.id.add_spinner_communs);


        checkBox = (CheckBox) view.findViewById(R.id.add_donnateur);

        next = (Button) view.findViewById(R.id.add_next);
        mProgressBar = view.findViewById(R.id.progress_b);

        initialiseView();

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isDonor = isChecked;
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                age = age.intValue() - 1;
                ageEdite.setText(age.toString());
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                age = age.intValue() + 1;
                ageEdite.setText(age.toString());
            }
        });

        spinnerWilaya.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerCommuns.setVisibility(View.VISIBLE);
                mAdress[2] = String.valueOf(spinnerWilaya.getSelectedItem());
                commmunsCodeAdapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_item, getCommuns(position));
                commmunsCodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCommuns.setAdapter(commmunsCodeAdapter);
                //


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfoInFireBsae();
            }
        });


        return view;
    }

    private void initialiseView() {
        SharedPreferences Pref = getContext().getSharedPreferences(PREFERENCE_NAME, getContext().MODE_PRIVATE);
        ageEdite.setText(Pref.getString(KEY_USER_AGE, "20"));
        nameEdit.setText(Pref.getString(KEY_USER_NAME, "Anonyme"));
        adreassEdit.setText(Pref.getString(KEY_USER_ADRESSES, "Ain Bensultan, Médéa, Médéa"));
        mImageUri = Uri.parse(Pref.getString(KEY_USER_IMAGE, "R.drawable.profile"));
        Glide.with(getContext()).load(mImageUri).into(userImage);
        isDonor = Pref.getBoolean(KEY_IS_USER_DONOR,false);
        checkBox.setChecked(isDonor);
        mEmail = Pref.getString(KEY_USER_EMAIL, "ak.bensalem@esi-sba.dz");
        mPhone = Pref.getString(KEY_USER_EMAIL, "0772375348");
        int wilaya = Integer.parseInt(Pref.getString(KEY_USER_WILAYA, "0"));
        spinnerWilaya.setSelection(wilaya);
        int commune = Integer.parseInt(Pref.getString(KEY_USER_COMMUNE, "0"));
        spinnerCommuns.setSelection(commune);
        mType[0] = Integer.parseInt(Pref.getString(KEY_USER_TYPE, "0"));
    }

    private void saveUserInfoInFireBsae() {

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
                                    String url =uri.toString();
                                    updateFireBase(url);

                                }
                            });
                        }
                    })
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
    private void updateFireBase(String  image_url) {

         String name = nameEdit.getText().toString().trim() ;
         String email = mEmail;
         String adress = adreassEdit.getText().toString() +", "+String.valueOf(spinnerCommuns.getSelectedItem())+", "+String.valueOf(spinnerWilaya.getSelectedItem());
         String willaya = String.valueOf(spinnerWilaya.getSelectedItemPosition());
         String commune = String.valueOf(spinnerCommuns.getSelectedItemPosition());

         final String type = String.valueOf(mType[0]);
         String phone = mPhone;
         String lage = String.valueOf(age);

         FirebaseUser user = FirebaseAuth.getInstance().getInstance().getCurrentUser();


/*
        Log.d("AKRATMTESTTEST","name is "+name);
        Log.d("AKRATMTESTTEST","adress is "+adress);
        Log.d("AKRATMTESTTEST","willaya is "+willaya);
        Log.d("AKRATMTESTTEST","commune is "+commune);
        Log.d("AKRATMTESTTEST","phone is "+phone);
        Log.d("AKRATMTESTTEST","type is "+type);
        Log.d("AKRATMTESTTEST","age is "+lage);
        Log.d("AKRATMTESTTEST","user is "+user.getUid());


 */

        Map<String, Object> UserData = new HashMap<String, Object>();

        UserData.put("UserName", name);
        UserData.put("UserEmail", email);
        UserData.put("UserType", type);
        UserData.put("_ID_Firebase", user.getUid());
        UserData.put("UserAge", lage);
        UserData.put("UserPhone", phone);
        UserData.put("UserAdress", adress);
        UserData.put("UserWillaya", willaya);
        UserData.put("UserCommune", commune);
        UserData.put("UserDonnation", isDonor);
        UserData.put("UserImageUrl", image_url);
        UserData.put("UserExist", true);


        mDatabaseRef.child(user.getUid()).setValue(UserData).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(),"vos information ont été mise à jour", Toast.LENGTH_LONG).show();
                } else {
                    String error;
                    error = task.getException().getMessage();
                    Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public void onClicks() {
        Intent gallery = new Intent();
        gallery.setType("image/*");
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
                userImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getFileExtension(Uri uri) {
        AppCompatActivity test=new AppCompatActivity();
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
