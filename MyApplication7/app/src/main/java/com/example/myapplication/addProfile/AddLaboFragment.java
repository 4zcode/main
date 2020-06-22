package com.example.myapplication.addProfile;

        import android.content.ContentResolver;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.os.Bundle;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.Fragment;

        import android.os.Handler;
        import android.provider.MediaStore;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.webkit.MimeTypeMap;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.ProgressBar;
        import android.widget.RadioGroup;
        import android.widget.Spinner;
        import android.widget.Toast;

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
        import static com.example.myapplication.utilities.tools.getCommuns;


public class AddLaboFragment extends Fragment {
    private ImageView userImage;

    private Button next;
    private RadioGroup mRadioGroup;
    private int[] mType ={0};
    private Spinner spinnerWilaya, spinnerCommuns;
    private ArrayAdapter<CharSequence> commmunsCodeAdapter;

    private String[] mAdress={"Médéa","Médéa","Ain Bensultan"};

    private EditText nomEdit, ordreEdit, descriptionEdit, serviceEdit, timeEdit, adrressEdit;

    private Uri mImageUri;
    public static int PICK_IMAGE = 1;


    private ProgressBar mProgressBar;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    public AddLaboFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_hospital, container, false);
        mStorageRef = FirebaseStorage.getInstance().getReference("laboratoir");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("laboratoir");

        userImage = (ImageView) view.findViewById(R.id.add_etablissement_image);
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClicks();
            }
        });


        nomEdit = (EditText) view.findViewById(R.id.etablissement_nom);
        ordreEdit =(EditText) view.findViewById(R.id.etablissement_dorde);
        descriptionEdit =(EditText) view.findViewById(R.id.etablissement_description);
        serviceEdit =(EditText) view.findViewById(R.id.etablissement_services);
        timeEdit = (EditText) view.findViewById(R.id.etablissement_ouverture);
        adrressEdit = (EditText) view.findViewById(R.id.etablissement_location);

        spinnerWilaya = view.findViewById(R.id.etablissement_spinner_wilaya);
        spinnerCommuns = view.findViewById(R.id.etablissement_spinner_communs);

        next = (Button) view.findViewById(R.id.add_next_etablissement);
        mProgressBar = view.findViewById(R.id.etablissement_progressbar);

        mRadioGroup = (RadioGroup) view.findViewById(R.id.etablisement_fragment_radiobottongroup);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.etablissement_type_publique) {
                    mType[0] = 0;
                } else if (checkedId == R.id.etablissement_type_prive) {
                    mType[0] = 1;
                }
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

        String Name = nomEdit.getText().toString();
        String adress = adrressEdit.getText().toString() +", "+String.valueOf(spinnerCommuns.getSelectedItem())+", "+String.valueOf(spinnerWilaya.getSelectedItem());
        String willaya = String.valueOf(spinnerWilaya.getSelectedItemPosition());
        String commune = String.valueOf(spinnerCommuns.getSelectedItemPosition());
        String ouverture = timeEdit.getText().toString();
        String phone = "0799434259";
        String type = String.valueOf(mType[0]);
        String description = descriptionEdit.getText().toString();
        String services = serviceEdit.getText().toString();
        String numOrdre = ordreEdit.getText().toString();

        FirebaseUser user = FirebaseAuth.getInstance().getInstance().getCurrentUser();
        Map<String, Object> DoctorUserData = new HashMap<String, Object>();

        DoctorUserData.put("Name", Name);
        DoctorUserData.put("Place", adress);
        DoctorUserData.put("Wilaya", willaya);
        DoctorUserData.put("Commune", commune);
        DoctorUserData.put("Phone", phone);
        DoctorUserData.put("Description", description);
        DoctorUserData.put("Service", services);
        DoctorUserData.put("numOrdre", numOrdre);
        DoctorUserData.put("Type", type);
        DoctorUserData.put("Time", ouverture);
        DoctorUserData.put("ImageUrl", image_url);
        DoctorUserData.put("_ID_Firebase", user.getUid());
        DoctorUserData.put("EtablissmentExist", true);


        mDatabaseRef.child(user.getUid()).setValue(DoctorUserData).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    AlertDialog message = new AlertDialog.Builder(getContext())
                            .setTitle("Inscription Terminé")
                            .setMessage(" Merci d'avoir complété votre profil \n Nous vous contacterons bientôt \n Pour confirmer ces informations")
                            .setPositiveButton("Terminer", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setIcon(android.R.drawable.ic_media_rew)
                            .show();
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
