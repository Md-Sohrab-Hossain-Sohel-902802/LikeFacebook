package com.example.likefacebook.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MuViewHolder> {

    private Context context;
    private List<NotificationDataModuler> dataModulerList = new ArrayList<>();
    int counter=0;

    public NotificationAdapter(Context context, List<NotificationDataModuler> dataModulerList) {
        this.context = context;
        this.dataModulerList = dataModulerList;
    }

    @NonNull
    @Override
    public MuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.notification_item_layout,parent,false);
        MuViewHolder holder=new MuViewHolder(view);





        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MuViewHolder holder, int position) {
        NotificationDataModuler data=dataModulerList.get(position);

           seartForName(data.getUserid(),holder.textView,data.getValue(),counter) ;




    }

    private void seartForName(String userid, final TextView textView, final String value, final int counter) {



        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("users");
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        String currentUser=mAuth.getCurrentUser().getUid();

        databaseReference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name=snapshot.child("name").getValue().toString();

                    String sourceString ="<i><u>"+name+"</u></i>"+"<b>" +"  "+ value + "</b> " +"  Your Post";
                    textView.setText(Html.fromHtml(sourceString));





                   // textView.setText(counter+". "+name+"  "+value+"  Your Post");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });









    }

    @Override
    public int getItemCount() {
        return dataModulerList.size();
    }

    public class MuViewHolder  extends RecyclerView.ViewHolder {

        TextView textView;

        public MuViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.notification_textViewid);


        }
    }
}
