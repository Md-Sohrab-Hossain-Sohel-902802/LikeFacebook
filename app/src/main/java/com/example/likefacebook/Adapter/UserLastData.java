package com.example.likefacebook.Adapter;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class UserLastData {




private Context context;



    String name1;
    String image1;

    public UserLastData(Context context) {
        this.context = context;
    }

    public void lastUpdate() {


         FirebaseAuth mAuth;
         DatabaseReference databaseReference;


        mAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("users");
        String currentUser=mAuth.getCurrentUser().getUid();


        databaseReference.child(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    if(snapshot.hasChild("image")){
                        String image=snapshot.child("image").getValue().toString();
                        setImage1(image);
                    }
                   String name=snapshot.child("name").getValue().toString();
                    setName1(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });









    }


    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }
}
