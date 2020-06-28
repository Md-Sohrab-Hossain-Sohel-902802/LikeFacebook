package com.example.likefacebook.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.likefacebook.Adapter.MyPostAdapter;
import com.example.likefacebook.DataModuler.MyPostDataModuler;
import com.example.likefacebook.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    private RecyclerView recyclerView;
    private List<MyPostDataModuler > dataList=new ArrayList<>();

    MyPostAdapter adapter;



    private DatabaseReference databaseReference;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_home, container, false);

     databaseReference= FirebaseDatabase.getInstance().getReference().child("AllPost");

    recyclerView=root.findViewById(R.id.home_RecyclerViewid);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    adapter=new MyPostAdapter(getContext(),dataList);
    recyclerView.setAdapter(adapter);



        return root;
    }


    @Override
    public void onStart() {
        super.onStart();

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    dataList.clear();

                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        MyPostDataModuler upload=dataSnapshot1.getValue(MyPostDataModuler.class);
                        dataList.add(upload);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });





    }
}