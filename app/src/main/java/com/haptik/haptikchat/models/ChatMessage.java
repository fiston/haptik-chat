package com.haptik.haptikchat.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jyotman on 22/4/16.
 */

public class ChatMessage implements Parcelable {
    private String body;
    private long messageTime;
    private Member member;
    private int color;

    public ChatMessage(String body, long messageTime, Member member, int color) {
        this.body = body;
        this.messageTime = messageTime;
        this.member = member;
        this.color = color;
    }

    protected ChatMessage(Parcel in) {
        body = in.readString();
        messageTime = in.readLong();
        member = (Member) in.readValue(Member.class.getClassLoader());
        color = in.readInt();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(body);
        dest.writeLong(messageTime);
        dest.writeValue(member);
        dest.writeInt(color);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ChatMessage> CREATOR = new Parcelable.Creator<ChatMessage>() {
        @Override
        public ChatMessage createFromParcel(Parcel in) {
            return new ChatMessage(in);
        }

        @Override
        public ChatMessage[] newArray(int size) {
            return new ChatMessage[size];
        }
    };
}