package com.example.likefacebook.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.likefacebook.DataModuler.UserData;
import com.example.likefacebook.MainActivity;
import com.example.likefacebook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    //<--------------------------Layout Variables-------------------------------------->


        private EditText emailEdittext;
        private  EditText passwordEdittext;
        private Button createAccountButton;
        private  Button  allreadyHaveAnAccountButton;



        private ProgressDialog progressDialog;


        //<------------------------Firebase Variables---------------------------->




        private FirebaseAuth mAuth;
        private DatabaseReference databaseReference;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        mAuth=FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("users");


                emailEdittext=findViewById(R.id.loginEmailEdittextid);
                passwordEdittext=findViewById(R.id.loginPasswordEdittextid);
                createAccountButton=findViewById(R.id.loginCreateAccountButtonid);
                allreadyHaveAnAccountButton=findViewById(R.id.alreadyhaveAnAccount);



                progressDialog=new ProgressDialog(this);
                progressDialog.setTitle("Please Wait");
                progressDialog.setMessage("We Are Checking Your Account");





                allreadyHaveAnAccountButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(LoginActivity.this,RegistrationActivity.class);
                        startActivity(intent);
                    }
                });

                createAccountButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                              String email=emailEdittext.getText().toString();
                              String password=passwordEdittext.getText().toString();



                              if(email.isEmpty()){
                                  Toast.makeText(LoginActivity.this, "Please Enter Your  Email here", Toast.LENGTH_SHORT).show();
                              }else if(password.isEmpty()){
                                  Toast.makeText(LoginActivity.this, "Please Enter Your  Password here", Toast.LENGTH_SHORT).show();

                              }else {
                                    progressDialog.show();
                                  checkUser(email,password);
                              }
                    }
                });
















    }

    private void checkUser(final String email, final String password) {

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        startActivity(intent);
                        finish();

                    }
                }
            });
    }
}