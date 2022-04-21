package com.example.demoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoapp.Adapter.ChatAdapter;
import com.example.demoapp.Models.MessageModel;
import com.example.demoapp.Models.Users;
import com.example.demoapp.databinding.ActivityChatDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {

    ActivityChatDetailBinding binding;
    FirebaseDatabase database;
    FirebaseStorage storage;
    FirebaseAuth auth;
    Dialog dialog;

//    final String senderId = auth.getUid();
//
//    // lấy về uid của người dùng
//    String recieveId = getIntent().getStringExtra("userID");      // lấy dữ liệu userID truyền qua thông qua key = userID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dialog = new Dialog(this);
      binding.send.setVisibility(View.GONE);
        binding.show.setVisibility(View.GONE);
//        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        final String senderId = auth.getUid();

        // lấy về uid của người dùng
        String recieveId = getIntent().getStringExtra("userID");      // lấy dữ liệu userID truyền qua thông qua key = userID
        String userName = getIntent().getStringExtra("userName");     // lấy dữ liệu userID truyền qua thông qua key = userName
        String profilePic = getIntent().getStringExtra("profilePic");  // lấy dữ liệu userID truyền qua thông qua key = profilePic

        binding.userName.setText(userName);   // set userName = với thông tin người dùng
        Picasso.get().load(profilePic).placeholder(R.drawable.avatar).into(binding.profileImage);  // load image

//        binding.backArrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ChatDetailActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });   // nút back

        final ArrayList<MessageModel> messageModels = new ArrayList<>();  // tạo mảng MessageModel
        final ChatAdapter chatAdapter = new ChatAdapter(messageModels, this, recieveId); // biến chatadapter()

        binding.chatRecyclerView.setAdapter(chatAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(linearLayoutManager);

        final String senderRoom = senderId + recieveId;
        final String receiverRoom = recieveId + senderId;

        database.getReference().child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageModels.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            MessageModel model = snapshot1.getValue(MessageModel.class);
                            model.setMessageId(snapshot1.getKey());
                            messageModels.add(model);
                        }
                        chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.showInfor.setVisibility(View.VISIBLE);
                binding.show.setVisibility(View.GONE);
                binding.hide.setVisibility(View.VISIBLE);
            }
        });
        binding.hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.showInfor.setVisibility(View.GONE);
                binding.hide.setVisibility(View.GONE);
                binding.show.setVisibility(View.VISIBLE);
            }
        });
        binding.enterMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    binding.send.setVisibility(View.GONE);
                binding.send2.setVisibility(View.VISIBLE);

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.length() > 0) {
                    binding.send.setVisibility(View.VISIBLE);    binding.send2.setVisibility(View.GONE);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.enterMessage.getText().toString().isEmpty()) {
                    binding.sendItemShow.setVisibility(View.GONE);
                    String message = binding.enterMessage.getText().toString();

                    final MessageModel model = new MessageModel(senderId, message);
                    model.setTimestamp(new Date().getTime());
                    binding.enterMessage.setText("");
                    database.getReference().child("chats")
                            .child(senderRoom)
                            .push()
                            .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            database.getReference().child("chats")
                                    .child(receiverRoom)
                                    .push()
                                    .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            });
                        }
                    });
                }else {

                    Toast.makeText(ChatDetailActivity.this, "Nothing", Toast.LENGTH_SHORT).show();

                   }
            }
        });


//        binding.sendItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startActivityForResult(intent, 25);
//
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        final String senderId = auth.getUid();
//
//        // lấy về uid của người dùng
//        String recieveId = getIntent().getStringExtra("userID");      // lấy dữ liệu userID truyền qua thông qua key = userID
//        final String senderRoom = senderId + recieveId;
//        final String receiverRoom = recieveId + senderId;
//
//        if (resultCode == Activity.RESULT_OK) {
//            Uri sFile = data.getData();
//            binding.sendItemShow.setImageURI(sFile);
//            binding.sendItemShow.setVisibility(View.VISIBLE);
//
//
//        }
//    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        final String senderId = auth.getUid();
//
//        // lấy về uid của người dùng
//        String recieveId = getIntent().getStringExtra("userID");      // lấy dữ liệu userID truyền qua thông qua key = userID
//        final String senderRoom = senderId + recieveId;
//        final String receiverRoom = recieveId + senderId;
//
//        if(resultCode == Activity.RESULT_OK )
//        {
//            Uri sFile = data.getData();
//            binding.sendItemShow.setImageURI(sFile);
//
//
//
//            final StorageReference reference = storage.getReference().child("profilePic")
//                    .child(senderRoom);
//
//            reference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            database.getReference().child("chats").child(senderRoom)
//                                    .child("profilePic").setValue(uri.toString());
//                        }
//                    });
//                }
//            });
//        }
//        else
//        {
//            Intent intent = new Intent(ChatDetailActivity.this, ChatDetailActivity.class);
//            startActivity(intent);
//        }
//    }
    }
    public class SenderViewHolder extends RecyclerView.ViewHolder{
        TextView senderMsg, senderTime;
        ImageView imageView;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMsg = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.senderTime);
            imageView = itemView.findViewById(R.id.image_sent);
        }
    }
}