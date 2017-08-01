package com.h.chad.bakingapp.userinterface.ingredients;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.h.chad.bakingapp.R;
import com.h.chad.bakingapp.model.Ingredients;
import com.h.chad.bakingapp.userinterface.steps.StepDetailFragment;

import junit.framework.Assert;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chad on 7/11/2017.
 */

public class IngredientsFragment extends Fragment {
    private final static String TAG = IngredientsFragment.class.getName();
    /* String constanct to receive data from Intent and parcelable*/
    public final static String GET_INGREDIENTS_ARRAYLIST = "GET_INGREDIENTS_ARRAYLIST";

    private IngredientsAdapter mIngredientsAdapter;
    @BindView(R.id.rv_ingredient_recyclerview) RecyclerView mRecyclerView;
    @BindView(R.id.tv_ingredients_error) TextView mErrorTextView;
    private ArrayList<Ingredients> mIngredients;
    private RecyclerView.LayoutManager mLayoutManager;
    private String mCurrentStep;
    Context mContext;
    public View mRootView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = inflater.inflate(R.layout.activity_ingredient, container, false);

        ButterKnife.bind(this, mRootView);
        mContext = getActivity();
        Bundle args = getArguments();
        mIngredients = args.getParcelableArrayList(GET_INGREDIENTS_ARRAYLIST);
        //Assert.assertNotNull(mIngredients);
        mCurrentStep = args.getString(StepDetailFragment.GET_STEP_ID);

        loadIngredients();
        return mRootView;
    }

    private void showErrorMessage(String error){
        mRecyclerView.setVisibility(View.GONE);
        mErrorTextView.setVisibility(View.VISIBLE);
        mErrorTextView.setText(error);
    }

    private void loadIngredients() {
        mIngredientsAdapter = new IngredientsAdapter(mContext, mIngredients);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mIngredientsAdapter);
    }
}
