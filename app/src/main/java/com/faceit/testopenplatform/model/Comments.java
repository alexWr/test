package com.faceit.testopenplatform.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comments implements Parcelable {

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("postId")
    private int postId;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("body")
    private String body;

    @Expose
    @SerializedName("email")
    private String email;

    public int getPostId() {
        return postId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.postId);
        dest.writeString(this.name);
        dest.writeString(this.body);
        dest.writeString(this.email);
    }

    protected Comments(Parcel in) {
        this.id = in.readInt();
        this.postId = in.readInt();
        this.name = in.readString();
        this.body = in.readString();
        this.email = in.readString();
    }

    public static final Creator<Comments> CREATOR = new Creator<Comments>() {
        @Override
        public Comments createFromParcel(Parcel source) {
            return new Comments(source);
        }

        @Override
        public Comments[] newArray(int size) {
            return new Comments[size];
        }
    };
}
