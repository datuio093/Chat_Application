package com.example.demoapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.ChatDetailActivity;
import com.example.demoapp.Models.Users;
import com.example.demoapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {
    ArrayList<Users> list;
    Context context;

    public StoryAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_show_user_story,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryAdapter.ViewHolder holder, int position) {
        Users users = list.get(position);
        Picasso.get().load(users.getProfilePic()).placeholder(R.drawable.avatar3).into(holder.image);


        Picasso.get().load(users.getProfilePic()).into(holder.image2);
        holder.userName.setText(users.getUserName());


         //check status
        holder.check.setVisibility(View.GONE);

        if(users.getStatusof().equals("Online") ) {
            holder.check.setVisibility(View.VISIBLE);
        }
        else
            holder.check.setVisibility(View.GONE);


        FirebaseDatabase.getInstance().getReference().child("Story")
                .child(users.getUserId() )

                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                try {
                                   holder.like.setText(snapshot.child("like").getValue().toString());
                                    holder.textStory.setText(snapshot.child("text").getValue().toString());
                                  Picasso.get().load((snapshot.child("profilePic")).getValue().toString()).into(holder.image2);
                                } catch (Exception e )  {      holder.textStory.setText(" "); }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        holder.btlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btlike.setVisibility(View.GONE);
            }
        });


        holder.btchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatDetailActivity.class);
                Intent intent2 = new Intent(context, ChatAdapter.class);
                intent2.putExtra("userID",users.getUserId());


                intent.putExtra("userID",users.getUserId());
                intent.putExtra("profilePic",users.getProfilePic());
                intent.putExtra("userName",users.getUserName());
                context.startActivity(intent);
            }
        });
  }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image,image2;
        TextView userName, textStory, like;
        ImageButton check, btchat, btlike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            like = itemView.findViewById(R.id.txt_show_like);
            image = itemView.findViewById(R.id.profile_image);
            userName = itemView.findViewById(R.id.userNameList);
            textStory = itemView.findViewById(R.id.text_story);
            check = itemView.findViewById(R.id.online_offline);
            image2 =  itemView.findViewById(R.id.image_story);
            btchat = itemView.findViewById(R.id.btn_chat);
            btlike =  itemView.findViewById(R.id.btn_like);

        }
    }
}
