package com.h.chad.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chad on 7/6/2017.
 */

public class Ingredients implements Parcelable{
    private static final String TAG = Ingredients.class.getName();

    private int mRecipeID;
    private float mQuantity;
    private String mMeasure;
    private String mIngredient;

    public Ingredients(){
        super();
    }

    public Ingredients(Parcel parcel){
        this.mRecipeID = parcel.readInt();
        this.mQuantity = parcel.readFloat();
        this.mMeasure = parcel.readString();
        this.mIngredient = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mRecipeID);
        dest.writeFloat(mQuantity);
        dest.writeString(mMeasure);
        dest.writeString(mIngredient);

    }

    public final static Creator<Ingredients> CREATOR = new Creator<Ingredients>() {
        @Override
        public Ingredients createFromParcel(Parcel source) {
            return new Ingredients(source);
        }

        @Override
        public Ingredients[] newArray(int size) {
            return new Ingredients[size];
        }
    };

    public int getRecipeID(){
        return mRecipeID;
    }

    public float getQuantity() {
        return mQuantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public String getIngredient() {
        return mIngredient;
    }


}
