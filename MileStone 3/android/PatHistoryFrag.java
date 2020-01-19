package com.example.voiceprescription;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class PatHistoryFrag extends Fragment {
    EditText et_contact;
    Button btn_history;

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    RecyclerView rv;
    HistoryAdapter adp;
    ArrayList<DatabasePojo> list=null;

    String contact_string=null;

    PatHistoryFrag(){   }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pat_history_frag,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference("dr_mail");

        rv=(RecyclerView)view.findViewById(R.id.rv_phf);
        list=new ArrayList<>();
        adp=new HistoryAdapter(getActivity(),list);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adp);
        //adp.notifyDataSetChanged();

        et_contact=(EditText)view.findViewById(R.id.et_mobile_no_phf);
        btn_history=(Button)view.findViewById(R.id.btn_history_phf);

        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_contact.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(), "Please enter contact no.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    contact_string=et_contact.getText().toString();
                    mRef.child(contact_string).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            Map<String,String> map=(Map<String, String>)dataSnapshot.getValue();
                            list.add(new DatabasePojo((String)map.get("timeInMillis"),(String)map.get("date"),(String) map.get("url")));
                            adp.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getActivity(), "Error: "+databaseError.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
//                    mRef.child(contact_string).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if(dataSnapshot.getChildrenCount()==0)
//                            {
//                                Toast.makeText(getActivity(), "No History", Toast.LENGTH_SHORT).show();
//                            }
//                            else
//                            {
//                                for(DataSnapshot snapshot:dataSnapshot.getChildren())
//                                {
//                                    Map<String,String> map=(Map<String, String>) snapshot.getValue();
//                                    list.add(new DatabasePojo((String)map.get("timeInMillis"),(String)map.get("date"),(String) map.get("url")));
//                                    adp.notifyDataSetChanged();
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//                            Toast.makeText(getActivity(), "Error: "+databaseError.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
                }
            }
        });

    }
}
