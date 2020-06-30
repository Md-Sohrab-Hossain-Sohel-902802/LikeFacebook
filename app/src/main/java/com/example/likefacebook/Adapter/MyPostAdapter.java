package com.example.likefacebook.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.likefacebook.Activitys.MyPostActivity;
import com.example.likefacebook.DataModuler.MyPostDataModuler;
import com.example.likefacebook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public   class  MyPostAdapter extends  RecyclerView.Adapter<MyPostAdapter.MYViewHOlder> {


    private OnItemClickListner listner;
    private List<MyPostDataModuler> dataList=new ArrayList<>();
    Context context;





    public MyPostAdapter(Context context,List<MyPostDataModuler> dataList) {
        this.context=context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MYViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.mypost_item_layoute,parent,false);

        return  new MYViewHOlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MYViewHOlder holder, int position) {
        final MyPostDataModuler data=dataList.get(position);

        holder.nameTextview.setText(data.getName());
        holder.textView.setText(data.getText());


            lastUpdate(holder.profileimageView,data.getUserid());


        cheking(data.getPostid(),holder.likeButton);



        String image=data.getImage();
        if(image.equals("null")){
            holder.postImageview.setVisibility(View.GONE);
        }


        holder.likeCount.setText(data.getLike());

        holder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(final LikeButton likeButton) {
                FirebaseAuth mAuth;
                mAuth=FirebaseAuth.getInstance();
                String currentUser=mAuth.getCurrentUser().getUid();

                HashMap<String,String> data2=new HashMap<>();
                data2.put("like","yes");
                data2.put("postid",data.getPostid());




                final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("ilike");
                databaseReference.child(currentUser).child(data.getPostid()).setValue(data2).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                    final int likecount=(Integer.parseInt(data.getLike()))+1;

                                    DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference().child(data.getUserid());
                                    databaseReference2.child("userpost").child(data.getPostid()).child("like").setValue(""+likecount).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("AllPost");
                                                databaseReference1.child(data.getPostid()).child("like").setValue(""+likecount).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            FirebaseAuth mAuth=FirebaseAuth.getInstance();
                                                            String currentuser2=mAuth.getCurrentUser().getUid();

                                                            HashMap<String,Object> notificaitonmap=new HashMap<>();
                                                            notificaitonmap.put("userid",currentuser2);
                                                            notificaitonmap.put("postid",data.getPostid());
                                                            notificaitonmap.put("value","like");


                                                            DatabaseReference databaseReference3=FirebaseDatabase.getInstance().getReference().child("Notification").child(data.getUserid());
                                                            databaseReference3.child(databaseReference.push().getKey()).updateChildren(notificaitonmap)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if(task.isSuccessful()){
                                                                                holder.likeButton.setLiked(true);
                                                                            }
                                                                        }
                                                                    });


                                                        }
                                                    }
                                                });


                                         }
                                        }
                                    });

                                }
                            }
                        });
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                FirebaseAuth mAuth;
                mAuth=FirebaseAuth.getInstance();
                final String currentUser=mAuth.getCurrentUser().getUid();

                HashMap<String,String> data2=new HashMap<>();
                data2.put("like","no");
                data2.put("postid",data.getPostid());




                final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("ilike");
                databaseReference.child(currentUser).child(data.getPostid()).setValue(data2).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            final int likecount=(Integer.parseInt(data.getLike()))-1;

                            DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference().child(data.getUserid());
                            databaseReference2.child("userpost").child(data.getPostid()).child("like").setValue(""+likecount).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("AllPost");
                                        databaseReference1.child(data.getPostid()).child("like").setValue(""+likecount).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){

                                                    FirebaseAuth mAuth=FirebaseAuth.getInstance();
                                                    String currentuser2=mAuth.getCurrentUser().getUid();

                                                    HashMap<String,Object> notificaitonmap=new HashMap<>();
                                                    notificaitonmap.put("userid",currentuser2);
                                                    notificaitonmap.put("postid",data.getPostid());
                                                    notificaitonmap.put("value","unlike");


                                                    DatabaseReference databaseReference3=FirebaseDatabase.getInstance().getReference().child("Notification").child(data.getUserid());
                                                    databaseReference3.child(databaseReference.push().getKey()).updateChildren(notificaitonmap)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                        if(task.isSuccessful()){
                                                                            holder.likeButton.setLiked(false);
                                                                        }
                                                                }
                                                            });


                                                }
                                            }
                                        });
                                    }
                                }
                            });

                        }
                    }
                });
            }
        });




    }

    public void lastUpdate(final CircleImageView profileimageView,String  postedUserid) {


        FirebaseAuth mAuth;
        DatabaseReference databaseReference;


        mAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("users");
        String currentUser=mAuth.getCurrentUser().getUid();


        databaseReference.child(postedUserid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    if(snapshot.hasChild("image")){
                        String image=snapshot.child("image").getValue().toString();
                        Picasso.with(context).load(image).placeholder(R.drawable.profile_image).into(profileimageView);

                    }else{
                        Picasso.with(context).load(R.drawable.profile_image).into(profileimageView);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });









    }

    private void cheking(final String postid, final LikeButton likeButton) {

        FirebaseAuth mAuth;
        mAuth=FirebaseAuth.getInstance();
        String currentUser=mAuth.getCurrentUser().getUid();

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("ilike");
        databaseReference.child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(postid)){
                        String value=snapshot.child(postid).child("like").getValue().toString();
                        if(value.equals("yes")){
                              likeButton.setLiked(true);
                        }else{
                            likeButton.setLiked(false);
                        }
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MYViewHOlder  extends RecyclerView.ViewHolder  implements View.OnClickListener {

        TextView nameTextview,textView,likeCount;
        ImageView postImageview;
        LikeButton likeButton;
        CircleImageView profileimageView;

        public MYViewHOlder(@NonNull View itemView) {
            super(itemView);


            profileimageView=itemView.findViewById(R.id.postItem_UserProfileImageid);
            nameTextview=itemView.findViewById(R.id.postItem_NameTextviewid);
            textView=itemView.findViewById(R.id.postItem_Textviewid);
            postImageview=itemView.findViewById(R.id.postItem_PostImageViewid);
            likeButton=itemView.findViewById(R.id.postItem_hearButtonid);
            likeCount=itemView.findViewById(R.id.postItem_LikeCount);


            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if(listner!=null){
                int position=getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION){
                    listner.OnItemClick(position);
                }
            }
        }

    }

    public interface  OnItemClickListner{
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListner listener){
        this.listner=listener;

    }
    }
