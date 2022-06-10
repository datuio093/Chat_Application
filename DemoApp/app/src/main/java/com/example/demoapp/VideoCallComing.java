package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.demoapp.databinding.ActivityVideoCallComingBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.URL;
import java.util.HashMap;


public class VideoCallComing extends AppCompatActivity {

    ActivityVideoCallComingBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        binding = ActivityVideoCallComingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        final String senderId = auth.getUid();

        String recieveId = getIntent().getStringExtra("userID");
        String userName = getIntent().getStringExtra("userName");
        String profilePic = getIntent().getStringExtra("profilePic");

        final String senderRoom = senderId + recieveId;
        final String receiverRoom = recieveId + senderId;

// outcomming : check có đồng ý ??
// incomming : check có cuộc gọi hay không ??

        binding.btnAppect.setOnClickListener(new View.OnClickListener() {  /// đồng ý trả lời
            @Override
            public void onClick(View view) {
                String recieveId = getIntent().getStringExtra("userID");
                final String senderId = auth.getUid();
                database = FirebaseDatabase.getInstance();

                HashMap<String, Object> obj = new HashMap<>();
                obj.put(senderId , "true");
                database.getReference().child("checkCall").child("outComeRoom").child(recieveId)
                        .updateChildren(obj);
                try {
                        JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                     .setServerURL(new URL("https://meet.jit.si"))
                     .setRoom("test123")
                     .setWelcomePageEnabled(false)
                     .build();
                     JitsiMeetActivity.launch(VideoCallComing.this, options);
                     finish();
                }catch (Exception e){}
            }
        });



    }
}