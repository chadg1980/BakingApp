package com.h.chad.bakingapp.userinterface;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.h.chad.bakingapp.R;
import com.h.chad.bakingapp.model.Ingredients;
import com.h.chad.bakingapp.model.Recipe;
import com.h.chad.bakingapp.model.Steps;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.Telephony.Mms.Part._DATA;

public class StepActivity extends AppCompatActivity {

    public final static String TAG = StepActivity.class.getName();
    /* Constants for passing data through intents and parcelable */
    public final static String INGREDIENT_DATA = "INGREDIENT_DATA";
    public final static String STEP_DATA = "STEP_DATA";

    @BindView(R.id.tv_step_error_message) TextView mErrorMessage;
    @BindView(R.id.rv_step) RecyclerView mRecyclerView;
    @BindView(R.id.tv_ingredients) TextView mIngredientTextView;
    private ArrayList<Steps> mSteps;
    private ArrayList<Ingredients> mIngredients;
    private Context mContext;
    private StepAdapter mStepAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        mContext = this;
        ButterKnife.bind(this);

        mIngredients = this.getIntent().getExtras().getParcelableArrayList(INGREDIENT_DATA);
        mSteps = this.getIntent().getExtras().getParcelableArrayList(STEP_DATA);

        loadStepsIntoView();
        scrollToTop();

        mIngredientTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ingredientIntent = new Intent(mContext, IngredientsActivity.class);
                Bundle args = new Bundle();
                args.putParcelableArrayList(IngredientsActivity.GET_INGREDIENTS_ARRAYLIST, mIngredients);
                ingredientIntent.putExtras(args);
                mContext.startActivity(ingredientIntent);
            }
        });
    }

    /**
     * Added the scrollToTop so the Ingredients are shown when the
     * layout first appears instead of needing to scroll up.
     */
    private void scrollToTop() {

        final ScrollView stepScroll = (ScrollView) findViewById(R.id.sv_step_ingredient);
        stepScroll.post(new Runnable() {
            @Override
            public void run() {
                stepScroll.scrollTo(0, stepScroll.getTop());
            }
        });
    }

    private void loadStepsIntoView() {
        mIngredientTextView.setText(R.string.ingredient_string);
        mStepAdapter = new StepAdapter(this, mIngredients, mSteps);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mStepAdapter);
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
