package com.example.demoapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.demoapp.ChatDetailActivity;
import com.example.demoapp.Models.MessageModel;
import com.example.demoapp.R;
import com.example.demoapp.VideoCallComing;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<MessageModel> messageModels;
    Context context;
    String recId;
    FirebaseDatabase database;




    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context)
    {
        this.messageModels = messageModels;
        this.context = context;
    }

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context, String recId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = recId;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SENDER_VIEW_TYPE) {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHolder(view);
        }
        else
        {

            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciever,parent,false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER_VIEW_TYPE;
        }
        else
            return RECEIVER_VIEW_TYPE;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageModel messageModel = messageModels.get(position);

        Intent intent = new Intent();
        String recieveId = intent.getStringExtra("userID");

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure want to delete this message?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                String senderRoom = recId +FirebaseAuth.getInstance().getUid() ;
                                String receiverRoom = FirebaseAuth.getInstance().getUid() + recId  ;
                                database.getReference().child("chats").child(senderRoom)
                                        .child(messageModel.getMessageId())
                                        .setValue(null);

                                database.getReference().child("chats").child(receiverRoom)
                                        .child(messageModel.getMessageId())
                                        .setValue(null);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                }).show();
                return false;
            }
        });


                String senderRoom = recId +FirebaseAuth.getInstance().getUid() ;
                String receiverRoom = FirebaseAuth.getInstance().getUid() + recId  ;
                FirebaseDatabase database = FirebaseDatabase.getInstance();


        HashMap<String, Object> obj = new HashMap<>();
        obj.put("isSeen", "true");
        database.getReference().child("Checkseen").child(receiverRoom)
                .updateChildren(obj);


                FirebaseDatabase.getInstance().getReference().child("Checkseen")
                        .child(senderRoom)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Date date = new Date(messageModel.getTimestamp());
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
                                String strDate = simpleDateFormat.format(date);
                                //        ((SenderViewHolder)holder).senderTime.setText(strDate.toString());
                                try {
                                    if (snapshot.child("isSeen").getValue().toString().equals("true") && position == messageModels.size() - 1 ) {
                                        ((SenderViewHolder) holder).checkSeen.setText("Seen at " + strDate.toString());
                                        ((SenderViewHolder) holder).checkSeen.setVisibility(View.VISIBLE);
                                    } else
                                        ((SenderViewHolder) holder).checkSeen.setVisibility(View.GONE);
                                }catch(Exception e){ }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });





        if(holder.getClass() == SenderViewHolder.class)
        {
//
//            ((SenderViewHolder) holder).imageView.setOnClickListener(new() {
//                @Override
//                public boolean onLongClick(View view) {
//
//                    return false;
//                }
//            });

//            ((SenderViewHolder) holder).play.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    MediaPlayer mediaPlayer = new MediaPlayer();
//                    try{
//                        mediaPlayer.setDataSource(messageModel.getAudio());
//                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                            @Override
//                            public void onPrepared(MediaPlayer mediaPlayer) {
//                                mediaPlayer.start();
//                            }
//                        });
//                        mediaPlayer.prepare();
//                    } catch (Exception e){}
//                }
//            });

          //  Toast.makeText(context, messageModel.getAudio(), Toast.LENGTH_SHORT).show();

            ((SenderViewHolder) holder).senderMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        Uri uri = Uri.parse(((SenderViewHolder) holder).senderMsg.getText().toString());
                        context.startActivity(new Intent(Intent.ACTION_VIEW,uri));
//                        database.getReference().child("chats").child(senderRoom)
//                                .child(  messageModel.getMessageId()    ).child("message")
//                                .setValue("tao la thang gui");
                    }
                    catch (Exception e){

                    };


                }
            });

        //    Toast.makeText(context, messageModel.getImageMess() , Toast.LENGTH_LONG).show();
            if(!messageModel.getImageMess().isEmpty()  ){
                ((SenderViewHolder) holder).senderMsg.setVisibility(View.GONE);
                ((SenderViewHolder) holder).senderTime.setVisibility(View.GONE);
                ((SenderViewHolder) holder).imageView.setVisibility(View.VISIBLE);
                Picasso.get().load(messageModel.getImageMess() ).into(((SenderViewHolder) holder).imageView);

            }
            else {

                ((SenderViewHolder)holder).senderMsg.setText(messageModel.getAudio());
                ((SenderViewHolder) holder).imageView.setVisibility(View.GONE);

            }



            Date date = new Date(messageModel.getTimestamp());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
            String strDate = simpleDateFormat.format(date);
            ((SenderViewHolder)holder).senderTime.setText(strDate.toString());

           // ((SenderViewHolder)holder).checkSeenS.setText("Đã Xem");
        }
        else
        {

            ((ReceiverViewHolder) holder).receiverMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        Uri uri = Uri.parse(((ReceiverViewHolder) holder).receiverMsg.getText().toString());
                        context.startActivity(new Intent(Intent.ACTION_VIEW,uri));
                    }
                    catch (Exception e){

                    };


                }
            });
     //       Picasso.get().load(messageModel.getImageMess()).placeholder(R.drawable.avatar).into(((ReceiverViewHolder) holder).imageView);
            ((ReceiverViewHolder)holder).receiverMsg.setText(messageModel.getMessage());
            if(!messageModel.getImageMess().isEmpty() ){
                ((ReceiverViewHolder) holder).receiverMsg.setVisibility(View.GONE);
                ((ReceiverViewHolder) holder).receiverTime.setVisibility(View.GONE);
                Picasso.get().load( messageModel.getImageMess() ).placeholder(R.drawable.avatar).into(((ReceiverViewHolder) holder).imageView);

            }
            else
                ((ReceiverViewHolder) holder).imageView.setVisibility(View.GONE);


            Date date = new Date(messageModel.getTimestamp());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
            String strDate = simpleDateFormat.format(date);
            ((ReceiverViewHolder)holder).receiverTime.setText(strDate.toString());
            String strDate1 = simpleDateFormat.format(date);


        }
    }


    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView receiverMsg, receiverTime,checkSeenR;
        ImageView imageView,haha,like,said;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverMsg = itemView.findViewById(R.id.receiverText);
            receiverTime = itemView.findViewById(R.id.receiverTime);
        //   checkSeenR = itemView.findViewById(R.id.name_nav);
            imageView = itemView.findViewById(R.id.image_sent);

//            haha = itemView.findViewById(R.id.btn_like);
//            like = itemView.findViewById(R.id.likee);
//            said = itemView.findViewById(R.id.saidd);

        }

    }

    public class SenderViewHolder extends RecyclerView.ViewHolder{
        TextView senderMsg, senderTime, checkSeen;
        ImageView imageView;
        Button play;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            play = itemView.findViewById(R.id.btn_audio_play);
            senderMsg = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.senderTime);
            imageView = itemView.findViewById(R.id.image_sent);
            checkSeen = itemView.findViewById(R.id.check_seen_sender);
        }
    }
}
