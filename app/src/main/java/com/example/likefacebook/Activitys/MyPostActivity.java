package com.example.likefacebook.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.likefacebook.Adapter.MyPostAdapter;
import com.example.likefacebook.DataModuler.MyPostDataModuler;
import com.example.likefacebook.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;

import java.util.ArrayList;
import java.util.List;

public class MyPostActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private String currentUser;



    private RecyclerView recyclerView;


    private List<MyPostDataModuler> postData=new ArrayList<>();



    private MyPostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);




        FirebaseUser current_user=FirebaseAuth.getInstance().getCurrentUser();
        if(current_user!=null) {
            currentUser = current_user.getUid();
        } databaseReference=FirebaseDatabase.getInstance().getReference();

        recyclerView=findViewById(R.id.mypost_RecyclerViewid);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MyPostAdapter(MyPostActivity.this,postData);
        recyclerView.setAdapter(adapter);














    }


    @Override
    protected void onStart() {
        super.onStart();


       databaseReference.child(currentUser).child("userpost").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                postData.clear();

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    MyPostDataModuler upload=dataSnapshot1.getValue(MyPostDataModuler.class);
                      postData.add(upload);
                      adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });











    }
}