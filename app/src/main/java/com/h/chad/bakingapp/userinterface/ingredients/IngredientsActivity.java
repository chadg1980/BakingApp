package com.h.chad.bakingapp.userinterface.ingredients;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.h.chad.bakingapp.R;
import com.h.chad.bakingapp.model.Ingredients;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private boolean mIsTablet;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        ButterKnife.bind(this);
        mIngredients = this.getIntent().getParcelableArrayListExtra(GET_INGREDIENTS_ARRAYLIST);
        mIsTablet = getResources().getBoolean(R.bool.is_tablet);
        loadIngredients();

    }

    private void showErrorMessage(String error){
        mRecyclerView.setVisibility(View.GONE);
        mErrorTextView.setVisibility(View.VISIBLE);
        mErrorTextView.setText(error);
    }

    private void loadIngredients() {

        mIngredientsAdapter = new IngredientsAdapter(this, mIngredients);
        if(!mIsTablet) {
            mLayoutManager = new LinearLayoutManager(this);
        }else
            mLayoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mIngredientsAdapter);
    }
}
