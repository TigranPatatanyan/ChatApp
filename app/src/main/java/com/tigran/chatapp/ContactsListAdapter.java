package com.tigran.chatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ViewHolder> {

    Context context;
    View.OnClickListener listener;
    List<User> users;
//    List<String> users;
    LayoutInflater inflater;

    public ContactsListAdapter(Context context, View.OnClickListener listener, List<User> users) {
        this.context = context;
        this.listener = listener;
        this.users = users;
        this.inflater = LayoutInflater.from(context);
    }

    public void update(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Glide.with(context).load(users.get(position).getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(holder.itemImage);
        holder.userName.setText(users.get(position).getName());
        holder.itemView.setTag(users.get(position));
        holder.itemView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemImage;
        View itemView;
        TextView userName;

        ViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.iv_chat_image);
            userName = itemView.findViewById(R.id.tv_chat_name);
            this.itemView = itemView;
        }
    }
}

