package com.h.chad.bakingapp.userinterface;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.h.chad.bakingapp.R;
import com.h.chad.bakingapp.model.Ingredients;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.x;

/**
 * Created by chad on 7/11/2017.
 */

public class IngredientsActivity extends AppCompatActivity{
    private final static String TAG = IngredientsActivity.class.getName();
    /* String constanct to receive data from Intent and parcelable*/
    public final static String GET_INGREDIENTS_ARRAYLIST = "GET_INGREDIENTS_ARRAYLIST";

    private IngredientsAdapter mIngredientsAdapter;
    @BindView(R.id.rv_ingredient_recyclerview) RecyclerView mRecyclerView;
    @BindView(R.id.tv_ingredients_error) TextView mErrorTextView;
    private ArrayList<Ingredients> mIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        ButterKnife.bind(this);
        mIngredients = this.getIntent().getParcelableArrayListExtra(GET_INGREDIENTS_ARRAYLIST);
        loadIngredients();
    }

    private void showErrorMessage(String error){
        mRecyclerView.setVisibility(View.GONE);
        mErrorTextView.setVisibility(View.VISIBLE);
        mErrorTextView.setText(error);
    }

    private void loadIngredients() {

        mIngredientsAdapter = new IngredientsAdapter(this, mIngredients);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mIngredientsAdapter);
    }
}
