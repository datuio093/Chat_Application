package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoapp.databinding.ActivityVideoCallOutGoingBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.URL;

public class VideoCallOutGoing extends AppCompatActivity {
    ImageView imageView;
    TextView tvname, tvprof;
    FloatingActionButton declinebtn;
    String receiver_url, receiver_prof, receiver_name, receiver_token;
    ActivityVideoCallOutGoingBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        binding = ActivityVideoCallOutGoingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        final String senderId = auth.getUid();

        String recieveId = getIntent().getStringExtra("userID");
        String userName = getIntent().getStringExtra("userName");
        String profilePic = getIntent().getStringExtra("profilePic");

        final String senderRoom = senderId + recieveId;
        final String receiverRoom = recieveId + senderId;

        binding.callusername.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.avatar).into(binding.profileimagecall);

            try {
                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                        .setServerURL(new URL("https://meet.jit.si"))
                        .setRoom("test123")
                        .setWelcomePageEnabled(false)
                        .build();
                JitsiMeetActivity.launch(VideoCallOutGoing.this, options);
                finish();
            }
            catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            JitsiMeetConferenceOptions options
                    = new JitsiMeetConferenceOptions.Builder()
                    .setRoom("test123")
                    .build();
            JitsiMeetActivity.launch(this,options);
    }
}