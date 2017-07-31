package com.h.chad.bakingapp.userinterface.steps;

import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import com.h.chad.bakingapp.R;
import com.h.chad.bakingapp.model.Steps;
import java.util.ArrayList;

import butterknife.ButterKnife;

import static com.h.chad.bakingapp.userinterface.steps.StepListActivity.RECIPE_NAME;

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

    ArrayList<Steps> mSteps;
    int currentStepID;
    public Context mContext;
    private String mCurrentRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        mIsStep = this.getIntent().getBooleanExtra(IS_STEP, mIsStep);

        if(mIsStep) {
            mSteps = this.getIntent().getParcelableArrayListExtra(GET_STEP_ARRAYLIST);
            currentStepID = this.getIntent().getIntExtra(GET_STEP_ID, -1);
            mCurrentRecipe = this.getIntent().getStringExtra(RECIPE_NAME);
        }
        else{
            Log.e(TAG, "We are doing the ingredients with the step onCreate");        }
        //getSupportActionBar().setTitle(mCurrentRecipe);



        if(savedInstanceState == null){
            Bundle args = new Bundle();
            //args.putParcelableArrayList(StepListActivity.INGREDIENT_DATA, ingredients);
            if(mIsStep) {
                args.putParcelableArrayList(StepListActivity.STEP_DATA, mSteps);
                args.putInt(StepDetailFragment.GET_STEP_ID, currentStepID);
                StepDetailFragment fragment = new StepDetailFragment();
                fragment.setArguments(args);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.mp_step_detail_container, fragment)
                        .commit();
            }else {
                Log.e(TAG, "We are doing the ingredients with the step Saved instance");

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
            //Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
