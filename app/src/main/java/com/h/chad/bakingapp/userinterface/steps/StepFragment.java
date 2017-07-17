package com.h.chad.bakingapp.userinterface.steps;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
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
import com.h.chad.bakingapp.model.Steps;
import com.h.chad.bakingapp.userinterface.ingredients.IngredientsActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends Fragment {

    public final static String TAG = StepFragment.class.getName();
    /* Constants for passing data through intents and parcelable */
    public final static String INGREDIENT_DATA = "INGREDIENT_DATA";
    public final static String STEP_DATA = "STEP_DATA";

    @BindView(R.id.tv_step_error_message) TextView mErrorMessage;
    @BindView(R.id.rv_step) RecyclerView mRecyclerView;
    @BindView(R.id.rl_ingredient_layout) RelativeLayout mIngredientLayout;
    private ArrayList<Steps> mSteps;
    private ArrayList<Ingredients> mIngredients;
    private Context mContext;
    private StepAdapter mStepAdapter;
    //Weather or not the activity is run on a tablet, this should be in two-pane mode
    private boolean mTwoPane;

    @Override
    public View onCreateView(LayoutInflater infater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_step);
        View rootView = infater.inflate(R.layout.activity_step, container, false);

        return rootView;
    }


    /**
     * showError is a place that can put an error on the screen for the user
     * instead of crashing the app
     * */
    private void showError(String errorMessage) {
        mRecyclerView.setVisibility(View.GONE);
        mErrorMessage.setText(errorMessage);
        mErrorMessage.setVisibility(View.VISIBLE);

    }


}
