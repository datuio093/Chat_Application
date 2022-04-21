package com.example.demoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.navigation.ui.AppBarConfiguration;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoapp.Adapter.FragmentsAdapter;
import com.example.demoapp.Models.Users;
import com.example.demoapp.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener  {


    ActivityMainBinding binding;
    FirebaseAuth mAuth;
    Dialog dialog;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        database = FirebaseDatabase.getInstance();
//        binding.lnTablayout.setVisibility(View.GONE);
//        binding.showTablayout1.setVisibility(View.GONE);

        setContentView(binding.getRoot());
        dialog = new Dialog(this);
        mAuth = FirebaseAuth.getInstance();



        HashMap<String, Object> obj = new HashMap<>();

        obj.put("statusof", "Online");
        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .updateChildren(obj);
//        database.getReference().child("Contacts").child(FirebaseAuth.getInstance().getUid())
//                .updateChildren(obj);


//        database = FirebaseDatabase.getInstance();
//
//        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        Users users = snapshot.getValue(Users.class);
//                        Picasso.get()
//                                .load(users.getProfilePic())
//                                .placeholder(R.drawable.avatar)
//                                .into(imageView);
//
//                        name.setText(users.getUserName());
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });




//        getActionBar().hide();

        binding.viewPager.setAdapter(new FragmentsAdapter(getSupportFragmentManager()));

        binding.tabLayout.setupWithViewPager(binding.viewPager);

//        binding.showTablayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                binding.lnTablayout.setVisibility(View.VISIBLE);
//                binding.showTablayout.setVisibility(View.GONE);
//                binding.showTablayout1.setVisibility(View.VISIBLE);
//            }
//        });
//        binding.showTablayout1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.lnTablayout.setVisibility(View.GONE);
//                binding.showTablayout.setVisibility(View.VISIBLE);
//                binding.showTablayout1.setVisibility(View.GONE);
//            }
//        });

//        DrawerLayout drawerLayout;
//        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar_top);
//        drawerLayout =(DrawerLayout) findViewById(R.id.draw_bar);
//        getSupportActionBar().setTitle("mAIN");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.hello_blank_fragment,R.string.hello_blank_fragment);
//        actionBarDrawerToggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_layout);
        navigationView.setNavigationItemSelectedListener(this);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId())
//        {
//            case R.id.settings:
//                Intent intent2 = new Intent(MainActivity.this,SettingsActivity.class);
//                startActivity(intent2);
//                break;
//
//            case R.id.logout:
//                mAuth.signOut();
//                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
//                startActivity(intent);
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.settings:
                Intent intent2 = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent2);
                break;

            case R.id.findFriend:
                Intent intent4 = new Intent(MainActivity.this,FindFriendsActivity.class);
                startActivity(intent4);
                break;
            case R.id.logout:
                HashMap<String, Object> obj = new HashMap<>();

                obj.put("statusof", "Offline");
                database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                        .updateChildren(obj);
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.aboutus:

                dialog.setContentView(R.layout.aboutus);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                ImageView nav = (ImageView) dialog.findViewById(R.id.btn_nav1);
                nav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse("https://www.youtube.com/watch?v=TU-yDCgpbZw");
                        startActivity(new Intent(Intent.ACTION_VIEW,uri));
                    }
                });


        }

        return super.onOptionsItemSelected(item);
    }





}