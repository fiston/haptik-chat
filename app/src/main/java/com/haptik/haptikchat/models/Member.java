package com.haptik.haptikchat.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jyotman on 24/4/16.
 */
public class Member implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Member> CREATOR = new Parcelable.Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel in) {
            return new Member(in);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };
    String username, name, imageUrl;
    int messageCount;

    public Member(String username, String name, String imageUrl, int messages) {
        this.username = username;
        this.name = name;
        this.imageUrl = imageUrl;
        this.messageCount = messages;
    }

    public Member(String username, String name, String imageUrl) {
        this.username = username;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    protected Member(Parcel in) {
        username = in.readString();
        name = in.readString();
        imageUrl = in.readString();
        messageCount = in.readInt();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeInt(messageCount);
    }
}
