package com.example.demoapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.Models.Contacts;
import com.example.demoapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    ArrayList<Contacts> list;
    Context context;

    public ContactsAdapter(ArrayList<Contacts> list, Context context ) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_show_contact,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contacts contacts = list.get(position);
        Picasso.get().load(contacts.getProfilePic()).placeholder(R.drawable.avatar3).into(holder.image);
        holder.name.setText(contacts.getUserName());

//        FirebaseDatabase.getInstance().getReference().child("Groups")
//                .child(FirebaseAuth.getInstance().getUid() + users.getUserId())
//                .orderByChild("timestamp")
//                .limitToLast(1)
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if(snapshot.hasChildren()){
//                            for(DataSnapshot snapshot1:snapshot.getChildren())
//                            {
//                                holder.lastMessage.setText(snapshot1.child("message").getValue().toString());
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, ChatDetailActivity.class);
//                intent.putExtra("groupName", contacts.getGroupName());
//                intent.putExtra("groupPic", contacts.getGroupPic());
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.contactimage);
            name = itemView.findViewById(R.id.contactname);
        }
    }
}
