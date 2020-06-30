package com.example.likefacebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.likefacebook.Activitys.LoginActivity;
import com.example.likefacebook.Activitys.MyPostActivity;
import com.example.likefacebook.Activitys.NotificationListActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        mAuth=FirebaseAuth.getInstance();





        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_chats, R.id.navigation_Friends,R.id.navigation_Profile,R.id.navigation_Group)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }


    @Override
    public void onStart() {
        super.onStart();
      FirebaseUser currentUser = mAuth.getCurrentUser();


      if(currentUser==null){
          Intent intent=new Intent(MainActivity.this, LoginActivity.class);
          startActivity(intent);
      }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);




        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.logoutMenuButtonid){
            mAuth.signOut();
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);

        }    if(item.getItemId()==R.id.mypostMenuButtonid){
            Intent intent=new Intent(MainActivity.this, MyPostActivity.class);
            startActivity(intent);

        }  if(item.getItemId()==R.id.notificationMenuButtonid){
            Intent intent=new Intent(MainActivity.this, NotificationListActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
}