package com.h.chad.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chad on 7/6/2017.
 */

public class Steps implements Parcelable{

    private static final String TAG = Steps.class.getName();

    private int mRecipeID;
    private int mStepID;
    private String mShortDescription;
    private String mDescription;
    private String mVideoURL;
    private String mThumbNailURL;

    public Steps(){
        super();
    }
    public Steps(Parcel parcel) {
        this.mRecipeID = parcel.readInt();
        this.mStepID = parcel.readInt();
        this.mShortDescription = parcel.readString();
        this.mDescription = parcel.readString();
        this.mVideoURL = parcel.readString();
        this.mThumbNailURL = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mRecipeID);
        dest.writeInt(this.mStepID);
        dest.writeString(this.mShortDescription);
        dest.writeString(this.mDescription);
        dest.writeString(this.mVideoURL);
        dest.writeString(this.mThumbNailURL);

    }

    public final static Creator<Steps> CREATOR = new Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel source) {
            return new Steps(source);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };

    public int getRecipeID() {
        return mRecipeID;
    }

    public int getStepID() {
        return mStepID;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getVideoURL() {
        return mVideoURL;
    }

    public String getThumbNailUrl() {
        return mThumbNailURL;
    }
}
