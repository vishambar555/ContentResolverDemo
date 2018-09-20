package com.example.vishambar.contentresolverdemo2018.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MyModel implements Parcelable {
    private String name;
    private String  status;
    public MyModel(String name, String  status){
        this.name=name;
        this.status=status;
    }

    protected MyModel(Parcel in) {
        name = in.readString();
        status = in.readString();
    }

    public static final Creator<MyModel> CREATOR = new Creator<MyModel>() {
        @Override
        public MyModel createFromParcel(Parcel in) {
            return new MyModel(in);
        }

        @Override
        public MyModel[] newArray(int size) {
            return new MyModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(status);
    }
}
