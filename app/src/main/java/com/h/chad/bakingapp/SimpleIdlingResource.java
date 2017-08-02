package com.h.chad.bakingapp;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import com.h.chad.bakingapp.userinterface.recipe.RecipeActivity;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by chad on 8/2/2017.
 */

public class SimpleIdlingResource implements IdlingResource {

    @Nullable
    private volatile ResourceCallback mCallback;
    private RecipeActivity mActivity;

    //Idleness is controlled with this boolean.
    private AtomicBoolean mIsIdleNow = new AtomicBoolean(true);

    public SimpleIdlingResource(RecipeActivity recipeActivity){
        this.mActivity = recipeActivity;
    }


    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIsIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.mCallback = callback;
    }

    public void setIdleState(boolean isIdleNow) {
        mIsIdleNow.set(isIdleNow);
        if (isIdleNow && mCallback != null) {
            mCallback.onTransitionToIdle();
        }
    }
}

