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

public class RegistrationActivity extends AppCompatActivity {




    //<--------------------------Layout Variables-------------------------------------->


    private EditText emailEdittext;
    private  EditText passwordEdittext;
    private  EditText nameEdittext;
    private Button createAccountButton;
    private  Button  allreadyHaveAnAccountButton;
    private EditText phoneEdittext;



    private ProgressDialog progressDialog;


    //<------------------------Firebase Variables---------------------------->




    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);







        mAuth= FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("users");


        emailEdittext=findViewById(R.id.registrationEmailEdittextid);
        passwordEdittext=findViewById(R.id.registrationpasswordEdittextid);
        createAccountButton=findViewById(R.id.registrationCreateAccountButtonid);
        allreadyHaveAnAccountButton=findViewById(R.id.registrationalreadyhaveAnAccount);
        phoneEdittext=findViewById(R.id.phoneNumberEdittextid);
        nameEdittext=findViewById(R.id.registrationNameEdittext);


        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("We Are Creating Your Account");





        allreadyHaveAnAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailEdittext.getText().toString();
                String password=passwordEdittext.getText().toString();
                String phonenumber=phoneEdittext.getText().toString();
                String name=nameEdittext.getText().toString();

                if(email.isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "Please Enter Your  Email ", Toast.LENGTH_SHORT).show();
                }else if(password.isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "Please Enter Your  Password ", Toast.LENGTH_SHORT).show();

                }else if(phonenumber.isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "Please Enter Your  Phone Number ", Toast.LENGTH_SHORT).show();

                }else if(name.isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "Please Enter Your  Name", Toast.LENGTH_SHORT).show();

                }else {
                    progressDialog.show();
                    createUser(email,password,phonenumber,name);
                }
            }
        });



















    }





    private void createUser(final String email, final String password,final  String phoneNumber,final String name) {

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){


                    String  currentUser= mAuth.getCurrentUser().getUid();
                    UserData userData=new UserData(email,password,currentUser,phoneNumber,name);
                    databaseReference.child(currentUser).setValue( userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                Toast.makeText(RegistrationActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                progressDialog.dismiss();
                                finish();
                            }

                        }});
                }
            }
        });
    }
}