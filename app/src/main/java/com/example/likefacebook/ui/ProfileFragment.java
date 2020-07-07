package com.example.likefacebook.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.renderscript.Sampler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.likefacebook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {


    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;


    private CircleImageView profileImage;
    private TextView nameTextView;
    private EditText postEdittext;
    private Button postButton;
    String name;
    private  Button saveImageButton;


    private Uri imageUri;


    private ImageView imageView;
    private  TextView chooseImage;






    private StorageReference storageReference;
    private DatabaseReference rootRef;



    public ProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);






        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Loading.......");

        progressDialog.show();


        storageReference= FirebaseStorage.getInstance().getReference();

        mAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("users");

        profileImage=root.findViewById(R.id.profile_ProfileImageViewid);
        nameTextView=root.findViewById(R.id.profile_NameTextviewid);
        postEdittext=root.findViewById(R.id.profile_postEdittextid);
        postButton=root.findViewById(R.id.profile_postButtonid);
        saveImageButton=root.findViewById(R.id.profile_ImageUploadButtonid);

        chooseImage=root.findViewById(R.id.profile_ChooseImageTextviewid);
        imageView=root.findViewById(R.id.profiel_postImageViewid);


        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                // intent.setType("application/pdf");
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),2);

            }
        });



        saveImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    saveImage();
            }
        });


        lastUpdate();

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUri==null){
                    postData();
                }else{
                    saveImage2();
                }

            }
        });



        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                // intent.setType("application/pdf");
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),1);

            }
        });
















        return root;
    }

    private void postData() {



        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading.......");
        progressDialog.show();


        String currentUser=mAuth.getCurrentUser().getUid();


            String post=postEdittext.getText().toString();
            if(TextUtils.isEmpty(post)){
                Toast.makeText(getContext(), "Write Something The Click Post Button", Toast.LENGTH_SHORT).show();
            }else{
                DatabaseReference postRefrence=FirebaseDatabase.getInstance().getReference();

                final String key=postRefrence.push().getKey();

                final HashMap<String,Object> postMap=new HashMap<>();
                postMap.put("text",post);
                postMap.put("image","null");
                postMap.put("like","0");
                postMap.put("comment","0");
                postMap.put("postid",key);
                postMap.put("userid",currentUser);
                postMap.put("name",name);


                postRefrence.child(currentUser).child("userpost").child(key).setValue(postMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("AllPost");
                                databaseReference.child(key).setValue(postMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                                            postEdittext.setText("");
                                            progressDialog.dismiss();
                                        }
                                    }
                                });

                        }
                    }
                });




            }


    }

    private void lastUpdate() {





        String currentUser=mAuth.getCurrentUser().getUid();


        databaseReference.child(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    if(!snapshot.hasChild("image")){
                        Toast.makeText(getContext(), "Profile Image Not found", Toast.LENGTH_SHORT).show();
                    }else{
                        Picasso.with(getContext()).load(snapshot.child("image").getValue().toString()).into(profileImage);

                    }

                     name=snapshot.child("name").getValue().toString();

                    nameTextView.setText(name);
                    postEdittext.setHint(name+" , Whats on your mind?");
                    progressDialog.dismiss();



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });









    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data.getData()!=null && data !=null){

            saveImageButton.setVisibility(View.VISIBLE);
                imageUri=data.getData();
            Picasso.with(getContext()).load(imageUri).into(profileImage);

        }    else    if(requestCode==2 && resultCode==RESULT_OK && data.getData()!=null && data !=null){

           imageUri=data.getData();
           imageView.setVisibility(View.VISIBLE);
            postEdittext.setWidth(30);
            Picasso.with(getContext()).load(imageUri).into(imageView);


        }









    }




    private void saveImage2() {



        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading.......");
        progressDialog.show();


        String currentUser=mAuth.getCurrentUser().getUid();




        final long time=System.currentTimeMillis();
        StorageReference reference=storageReference.child("Post/"+currentUser+"/"+time+".jpg");
        reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                while (!uri.isSuccessful());
                Uri url=uri.getResult();

                String currentUser=mAuth.getCurrentUser().getUid();


                String post=postEdittext.getText().toString();
                if(TextUtils.isEmpty(post)){
                    Toast.makeText(getContext(), "Write Something The Click Post Button", Toast.LENGTH_SHORT).show();
                }else{
                    DatabaseReference postRefrence=FirebaseDatabase.getInstance().getReference();

                    final String key=postRefrence.push().getKey();

                    final HashMap<String,Object> postMap=new HashMap<>();
                    postMap.put("text",post);
                    postMap.put("image",url.toString());
                    postMap.put("like","0");
                    postMap.put("comment","0");
                    postMap.put("postid",key);
                    postMap.put("userid",currentUser);
                    postMap.put("name",name);


                    postRefrence.child(currentUser).child("userpost").child(key).setValue(postMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("AllPost");
                                databaseReference.child(key).setValue(postMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            progressDialog.dismiss();
                                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                                            postEdittext.setText("");
                                        }
                                    }
                                });

                            }
                        }
                    });




                }





            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }







    private void saveImage() {



        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading.......");
        progressDialog.show();
        final long time=System.currentTimeMillis();
        StorageReference reference=storageReference.child("profileImage/"+time+".jpg");
        reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                while (!uri.isSuccessful());
                Uri url=uri.getResult();

                HashMap<String ,Object> profiledata=new HashMap<>();

                profiledata.put("image",url.toString());

                String user=mAuth.getCurrentUser().getUid();



                databaseReference.child(user).updateChildren(profiledata).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Upload Successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }











}
