package com.haptik.haptikchat.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.haptik.haptikchat.MySingleton;
import com.haptik.haptikchat.R;
import com.haptik.haptikchat.models.Member;

import java.util.ArrayList;

/**
 * Created by jyotman on 24/4/16.
 */
public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MemberViewHolder> {

    private ArrayList<Member> members;
    private Context context;

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_item, parent, false);
        return new MemberViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MemberViewHolder holder, int position) {
        Member member = members.get(holder.getAdapterPosition());
        holder.memberName.setText(member.getName());
        holder.memberMessagesCount.setText("Messages: " + String.valueOf(member.getMessageCount()));

        ImageLoader loader = MySingleton.getInstance(context).getImageLoader();
        holder.memberPic.setDefaultImageResId(R.drawable.default_pic);
        holder.memberPic.setImageUrl(member.getImageUrl(), loader);
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public MembersAdapter(ArrayList<Member> members, Context context){
        this.members = members;
        this.context = context;
    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder {
        TextView memberName, memberMessagesCount;
        NetworkImageView memberPic;

        public MemberViewHolder(View view) {
            super(view);
            memberName = (TextView) view.findViewById(R.id.member_name);
            memberMessagesCount = (TextView) view.findViewById(R.id.member_messages_count);
            memberPic = (NetworkImageView) view.findViewById(R.id.member_pic);
        }
    }
}
