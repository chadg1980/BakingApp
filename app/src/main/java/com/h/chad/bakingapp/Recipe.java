package com.h.chad.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

/**
 * Created by chad on 7/6/2017.
 */

public class Recipe implements Parcelable{
    private static final String TAG = Recipe.class.getName();

    private int mRecipeID;
    private String mRecipeName;
    private int mServings;
    private String mImageString;



    public Recipe(){
        super();
    }

    public Recipe(Parcel parcel){
        this.mRecipeID = parcel.readInt();
        this.mRecipeName = parcel.readString();
        this.mServings = parcel.readInt();
        this.mImageString = parcel.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mRecipeID);
        dest.writeString(this.mRecipeName);
        dest.writeInt(this.mServings);
        dest.writeString(this.mImageString);
    }

    public final static Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getRecipeID() {
        return mRecipeID;
    }

    public String getRecipeName() {
        return mRecipeName;
    }

    public int getServings() {
        return mServings;
    }

    public String gemImageString() {
        return mImageString;
    }
}
