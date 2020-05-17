package com.example.myapplication.toolsbar.don_de_sang;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.myapplication.utilities.tools.isNetworkAvailable;

public class don_de_sangFragment extends Fragment {

    private ArrayList<don_de_song> mdonateurdata = new ArrayList<don_de_song>();
    private DBManagerDonateur dbManagerDonateur;
    private RecyclerView mRecyclerView;
    private DonateurAdapter mAdapter;
    private SearchView searchView;
    private final DatabaseReference PhREf = FirebaseDatabase.getInstance().getReference().child("Donateur");
    private ProgressDialog mProgressDialog;
    private interface FireBaseCallBack {
        void onCallBack(ArrayList<String> list);

    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_a_proos_de_nous, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerViewd);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchView = (SearchView) root.findViewById(R.id.search_donateur);

        dbManagerDonateur = new DBManagerDonateur(getActivity());
        dbManagerDonateur.open();
        if (isNetworkAvailable(getContext())) {
            Toast.makeText(getContext(), "there is connection", Toast.LENGTH_LONG).show();
            readData(new FireBaseCallBack() {
                @Override
                public void onCallBack(ArrayList<String> list) {

                }
            });
        } else {
            Toast.makeText(getContext(), "no connection", Toast.LENGTH_LONG).show();
        }

        mdonateurdata = dbManagerDonateur.listdonateur();

        mAdapter = new DonateurAdapter(getContext(), mdonateurdata);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.filter(newText);
                return true;
            }
        });
        return root;
    }

        public void readData(FireBaseCallBack fireBaseCallBack) {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setMessage("Loading");
                mProgressDialog.setIndeterminate(true);
            }
            mProgressDialog.show();

            PhREf.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(isNetworkAvailable(getContext())){dbManagerDonateur.deleteall();}
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        boolean Is_Exist =ds.child("_ID_firebase").exists() &&ds.child("imaged").exists() && ds.child("fullname").exists() && ds.child("adressd").exists() && ds.child("contact").exists()&& ds.child("grsanguin").exists() ;
                        if (Is_Exist) {
                            String id_firebase = ds.child("_ID_firebase").getValue(String.class);
                            String Name = ds.child("fullname").getValue(String.class);
                            String Place = ds.child("adressd").getValue(String.class);
                            String Grsanguin = ds.child("grsanguin").getValue(String.class);
                            String Phone = ds.child("contact").getValue(String.class);
                            String ImageUrl = ds.child("imaged").getValue(String.class);
                            if (dbManagerDonateur.CheckIsDataAlreadyInDBorNot(id_firebase)) {
                                dbManagerDonateur.update(id_firebase, Name, Place, Phone,Grsanguin,ImageUrl);
                            } else {
                                dbManagerDonateur.insert(id_firebase,Name,Place,Phone,Grsanguin,ImageUrl);

                            }
                        }
                    }
                    mdonateurdata= dbManagerDonateur.listdonateur();
                    mAdapter = new DonateurAdapter(getActivity(),mdonateurdata);
                    mRecyclerView.setAdapter(mAdapter);
                    mProgressDialog.dismiss();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }


    }
