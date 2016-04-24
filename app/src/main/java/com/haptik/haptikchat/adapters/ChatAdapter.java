package com.haptik.haptikchat.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haptik.haptikchat.R;
import com.haptik.haptikchat.models.ChatMessage;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jyotman on 22/4/16.
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_OTHER = 0;
    private final int TYPE_SELF = 1;

    private ArrayList<ChatMessage> messages;
    private int initialSize;
    private Context context;
    int[] colors;

    public ChatAdapter(ArrayList<ChatMessage> messages, Context context) {
        this.messages = messages;
        initialSize = messages.size();
        this.context = context;
        colors = context.getResources().getIntArray(R.array.UserNameColors);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_OTHER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_other, parent, false);
            return new OtherViewHolder(v);
        } else if (viewType == TYPE_SELF) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_self, parent, false);
            return new SelfViewHolder(v);
        } else
            throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OtherViewHolder) {
            ChatMessage chatMessage = messages.get(holder.getAdapterPosition());
            ((OtherViewHolder) holder).messageUserName.setText(chatMessage.getMember().getName());
            ((OtherViewHolder) holder).messageUserName.setTextColor(colors[chatMessage.getColor()]);
            ((OtherViewHolder) holder).message.setText(chatMessage.getBody());
            ((OtherViewHolder) holder).messageTime.setText(new Date(chatMessage.getMessageTime()).toString().substring(11, 16));
        } else if (holder instanceof SelfViewHolder) {
            ChatMessage chatMessage = messages.get(holder.getAdapterPosition());
            ((SelfViewHolder) holder).messageSelfName.setText(chatMessage.getMember().getName());
            ((SelfViewHolder) holder).messageSelf.setText(chatMessage.getBody());
            ((SelfViewHolder) holder).messageTimeSelf.setText(new Date(chatMessage.getMessageTime()).toString().substring(11, 16));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < initialSize)
            return 0;
        else return 1;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class OtherViewHolder extends RecyclerView.ViewHolder {
        TextView messageTime, messageUserName, message;

        public OtherViewHolder(View view) {
            super(view);
            messageTime = (TextView) view.findViewById(R.id.message_time);
            messageUserName = (TextView) view.findViewById(R.id.message_user_name);
            message = (TextView) view.findViewById(R.id.message);
        }
    }

    public static class SelfViewHolder extends RecyclerView.ViewHolder {
        TextView messageTimeSelf, messageSelfName, messageSelf;

        public SelfViewHolder(View view) {
            super(view);
            messageTimeSelf = (TextView) view.findViewById(R.id.message_time_self);
            messageSelfName = (TextView) view.findViewById(R.id.message_self_name);
            messageSelf = (TextView) view.findViewById(R.id.message_self);
        }
    }
}
