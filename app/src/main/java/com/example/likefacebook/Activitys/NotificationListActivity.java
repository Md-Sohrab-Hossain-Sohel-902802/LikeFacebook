package com.example.likefacebook.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.renderscript.Sampler;

import com.example.likefacebook.Adapter.NotificationAdapter;
import com.example.likefacebook.DataModuler.MyPostDataModuler;
import com.example.likefacebook.DataModuler.NotificationDataModuler;
import com.example.likefacebook.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationListActivity extends AppCompatActivity {


    private RecyclerView recyclerView;


    private NotificationAdapter adapter;
    private List<NotificationDataModuler> dataModulerList = new ArrayList<>();



    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private  String currentUser;
    private  int[] data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);


        databaseReference= FirebaseDatabase.getInstance().getReference("Notification");
        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser().getUid();



        recyclerView=findViewById(R.id.notificaiton_RecyclerViewid);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new NotificationAdapter(this,dataModulerList);

        recyclerView.setAdapter(adapter);












    }


    @Override
    protected void onStart() {
        super.onStart();



        databaseReference.child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataModulerList.clear();


                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){




                    NotificationDataModuler upload=dataSnapshot1.getValue(NotificationDataModuler.class);
                    dataModulerList.add(upload);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });










    }
}