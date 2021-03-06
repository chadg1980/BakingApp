package com.h.chad.bakingapp.userinterface.steps;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.h.chad.bakingapp.R;
import com.h.chad.bakingapp.model.Ingredients;
import com.h.chad.bakingapp.model.Steps;
import com.h.chad.bakingapp.userinterface.ingredients.IngredientsFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;

import static com.h.chad.bakingapp.R.id.mp_step_detail_container;

/**
 * Created by chad on 7/18/2017.
 */

public class StepDetailActivity extends AppCompatActivity {

    public final static String TAG = StepDetailActivity.class.getName();

    /* Constant for getting the step details*/
    public final static String GET_STEP_ARRAYLIST = "GET_STEP_ARRAYLIST";
    public final static String GET_STEP_ID = "GET_STEP_ID";
    public final static String IS_STEP = "IS_STEP";
    boolean mIsStep = true;

    private int currentStepID;
    public Context mContext;
    public String mCurrentRecipe;
    //For Steps
    private ArrayList<Steps> mSteps;

    //data for ingredient
    private ArrayList<Ingredients> mIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        mIsStep = this.getIntent().getBooleanExtra(IS_STEP, mIsStep);

        currentStepID = this.getIntent().getIntExtra(GET_STEP_ID, -1);
        mCurrentRecipe = this.getIntent().getStringExtra(StepListActivity.RECIPE_NAME);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mCurrentRecipe);

        if (mIsStep) {
            mSteps = this.getIntent().getParcelableArrayListExtra(GET_STEP_ARRAYLIST);
        } else {
            mIngredients = this.getIntent().getParcelableArrayListExtra(IngredientsFragment.GET_INGREDIENTS_ARRAYLIST);
        }

        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            //args.putParcelableArrayList(StepListActivity.INGREDIENT_DATA, ingredients);
            if (mIsStep) {
                args.putParcelableArrayList(StepListActivity.STEP_DATA, mSteps);
                args.putInt(StepDetailFragment.GET_STEP_ID, currentStepID);
                StepDetailFragment fragment = new StepDetailFragment();
                fragment.setArguments(args);
                getSupportFragmentManager().beginTransaction()
                        .add(mp_step_detail_container, fragment)
                        .commit();
            } else {
                args.putParcelableArrayList(IngredientsFragment.GET_INGREDIENTS_ARRAYLIST, mIngredients);
                args.putInt(StepDetailFragment.GET_STEP_ID, currentStepID);
                IngredientsFragment fragment = new IngredientsFragment();
                fragment.setArguments(args);
                getSupportFragmentManager().beginTransaction()
                        .add(mp_step_detail_container, fragment)
                        .commit();
            }
        }
        ButterKnife.bind(this);
        mContext = this;

        if (currentStepID < 0) {
            Log.e(TAG, "Step ID did not come through ID:" + currentStepID);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return true;
    }
}

