package com.example.myapplication.toolsbar.don_de_sang;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.ui.login.Signin;
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
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_IMAGE;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_NAME;
import static com.example.myapplication.utilities.PreferenceUtilities.PREFERENCE_NAME;
import static com.example.myapplication.utilities.tools.getCommuns;


public class Blooddonor extends Fragment {
    private ImageView ProfileImage;
    private Button b3;
    private ImageButton b1,b2;
    private Uri mImageUri;
    public static int PICK_IMAGE = 1;
    private EditText first_name;
    private EditText second_name;
    private EditText adress;
    private EditText phone;
    private TextView ageq;
    private Integer age;
    private ProgressBar mProgressBar;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    private RadioGroup mRadioGroup_1;
    private RadioGroup mRadioGroup_2;
    private String[] mBlood={"O","+"};
    private String[] mAdress={"Médéa","Médéa","Ain Bensultan"};

    private Spinner spinnerWilaya, spinnerCommuns;
    private ArrayAdapter<CharSequence>  commmunsCodeAdapter;




    public Blooddonor(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(layout.fragment_blooddonor, container, false);

            ageq = (TextView) view.findViewById(id.age);

            b1 = (ImageButton) view.findViewById(id.minis);
            b2 = (ImageButton) view.findViewById(id.plus);


            b3 = (Button) view.findViewById(id.adddonateur);

            ProfileImage = (ImageView) view.findViewById(id.donateur_image);
            ProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClicks();
                }
            });

            spinnerWilaya = view.findViewById(R.id.spinner_wilaya);
            spinnerCommuns = view.findViewById(R.id.spinner_communs);


            mProgressBar = view.findViewById(id.progress_bardo);

            first_name = (EditText) view.findViewById(id.fullname);
            second_name = (EditText) view.findViewById(id.username_d);

            adress = (EditText) view.findViewById(id.location_d);
            phone = (EditText) view.findViewById(id.phone_d);


            mRadioGroup_1 = (RadioGroup) view.findViewById(id.blooddonor_fragment_radiobottongroup);
            mRadioGroup_2 = (RadioGroup) view.findViewById(id.blooddonor_fragment_radiobottongroup_second);



        mRadioGroup_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.blooddonor_o) {
                        mBlood[0] = "O";
                    } else if (checkedId == R.id.blooddonor_a) {
                        mBlood[0] = "A";
                    } else if (checkedId == R.id.blooddonor_b) {
                        mBlood[0] = "B";
                    } else if (checkedId == R.id.blooddonor_ab) {
                        mBlood[0] = "AB";
                    }
                }
            });
            mRadioGroup_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == id.blooddonor_plus) {
                        mBlood[1] = "+";
                    } else if (checkedId == id.blooddonor_minus) {
                        mBlood[1] = "-";
                    }
                }
            });

            mStorageRef = FirebaseStorage.getInstance().getReference("Donateur");
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("Donateur");
            age = 20;
            ageq.setText(age.toString());


            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    age = age.intValue() - 1;
                    ageq.setText(age.toString());
                }
            });
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    age = age.intValue() + 1;
                    ageq.setText(age.toString());
                }
            });
            b3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adddonations();
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
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            //declare
            SharedPreferences Pref = getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);


            String[] All_Name = Pref.getString(KEY_USER_NAME, "").split(" ");
            String Image = Pref.getString(KEY_USER_IMAGE, "");
            String place = Pref.getString("city", "");
            Glide.with(getContext()).load(Image).into(ProfileImage);
            try {
                first_name.setText(All_Name[0]);
                second_name.setText(All_Name[1]);
            }catch (Exception e){
                e.printStackTrace();
            }

            adress.setText(place);



        }else {
            new AlertDialog.Builder(getContext())
                    .setTitle("Service non disponible")
                    .setMessage("Vous devez vous connecter d'abord!")
                    .setPositiveButton("Se connecter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getContext(), Signin.class));
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                             // something
                        }
                    })
                    .setIcon(R.drawable.ic_do_not_disturb_alt_black_24dp)
                    .show();
        }


        return view;
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
                ProfileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    public void adddonations() {
        if (this.age-1 > 18 && this.age +1 <60){


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
                                    String donateurID = FirebaseAuth.getInstance().getInstance().getCurrentUser().getUid();
                                    String name=first_name.getText().toString().trim()+" "+second_name.getText().toString().trim();
                                    don_de_song donateur=new don_de_song (donateurID,name,adress.getText().toString().trim()+", "+spinnerCommuns.getSelectedItem()+", "+spinnerWilaya.getSelectedItem(),String.valueOf(spinnerWilaya.getSelectedItemPosition()),String.valueOf(spinnerCommuns.getSelectedItemPosition()),phone.getText().toString().trim(),ageq.getText().toString().trim(),mBlood[0]+mBlood[1],uri.toString());
                                    mDatabaseRef.child(donateurID).setValue(donateur);
                                    mStorageRef = FirebaseStorage.getInstance().getReference("Users");
                                    final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                                            + "." + getFileExtension(mImageUri));
                                    mUploadTask = fileReference.putFile(mImageUri);

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
        }else Toast.makeText(getContext(),"Votre age ne vous permer pas donner votre sang",Toast.LENGTH_LONG).show();
    }

    //jest for getting the extention of the image ex .png
    private String getFileExtension(Uri uri) {
        AppCompatActivity test=new AppCompatActivity();
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }




}
