package com.jackhack2002.mychat;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public  class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyviewHolder>{

        private MainActivity context;
        private List<UserModel> userModelList;

    public UserAdapter(MainActivity context) {
        this.context = context;
        userModelList = new ArrayList<>();
    }
    public void add(UserModel userModel){
        userModelList.add(userModel);
        notifyDataSetChanged();
    }
    public void clear(){
        userModelList.clear();
        notifyDataSetChanged();
    }

    @NonNull
        @Override
        public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row,parent,false);
            return new MyviewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        UserModel userModel =userModelList.get(position);
        holder.name.setText(userModel.getUserName());
        holder.email.setText(userModel.getUserEmail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ChatActivity.class);
                intent.putExtra("id",userModel.getUserId());
                context.startActivity(intent);
            }
        });
        }

        @Override
        public int getItemCount() {
            return userModelList.size();
        }

        public class MyviewHolder extends RecyclerView.ViewHolder{
            private TextView name,email;
            public MyviewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.userName);
                email = itemView.findViewById(R.id.userEmail);
            }
        }
    }
