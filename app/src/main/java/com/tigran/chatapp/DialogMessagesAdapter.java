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

public class DialogMessagesAdapter extends RecyclerView.Adapter<DialogMessagesAdapter.ViewHolder> {
    private List<Message> messageList;
    String user;
    private Context context;
    private LayoutInflater mInflater;

    public DialogMessagesAdapter(Context context, String user, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
        this.mInflater = LayoutInflater.from(context);
        this.user = user;
    }

    public void update(List<Message> messageList) {
        this.messageList = messageList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DialogMessagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            view = mInflater.inflate(R.layout.item_message_sent, parent, false);
        } else {
            view = mInflater.inflate(R.layout.item_message_recived, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogMessagesAdapter.ViewHolder holder, int position) {
        Message message = messageList.get(position);
        if (messageList.get(position).isSenderI(user) != true) {
//            Glide.with(context).load(message.getAvatarUrl()).apply(RequestOptions.circleCropTransform()).into(holder.itemImage);
        }
        holder.itemMessage.setText(message.getTextMassage());
        holder.itemTime.setText(String.valueOf(message.getSendTime()));
        holder.itemView.setTag(message);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).isSenderI(user) == true) {
            return 1;
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemImage;
        View itemView;
        TextView itemMessage;
        TextView itemTime;

        ViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.iv_avatar);
            itemMessage = itemView.findViewById(R.id.tv_message);
            itemTime = itemView.findViewById(R.id.tv_time);
            this.itemView = itemView;
        }
    }
}
