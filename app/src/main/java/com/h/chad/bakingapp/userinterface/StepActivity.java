package com.h.chad.bakingapp.userinterface;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.h.chad.bakingapp.R;
import com.h.chad.bakingapp.model.Ingredients;
import com.h.chad.bakingapp.model.Recipe;
import com.h.chad.bakingapp.model.Steps;

import java.util.ArrayList;
import java.util.List;

import static android.provider.Telephony.Mms.Part._DATA;

public class StepActivity extends AppCompatActivity {

    public final static String TAG = StepActivity.class.getName();

    public final static String INGREDIENT_DATA = "INGREDIENT_DATA";
    public final static String STEP_DATA = "STEP_DATA";

    private RecyclerView mRecyclerView;
    private StepAdapter mStepAdapter;
    private TextView mErrorMessage;
    private ArrayList<Steps> mSteps;
    private ArrayList<Ingredients> mIngredients;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        mErrorMessage  = (TextView)findViewById(R.id.tv_step_error_message);
        mIngredients = this.getIntent().getExtras().getParcelableArrayList(INGREDIENT_DATA);
        mSteps = this.getIntent().getExtras().getParcelableArrayList(STEP_DATA);

        loadStepsIntoView();

    }

    private void loadStepsIntoView() {
        mStepAdapter = new StepAdapter(this, mIngredients, mSteps);
        mRecyclerView =  (RecyclerView) findViewById(R.id.rv_step);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mStepAdapter);
    }

    private void showError(String errorMessage) {
        mRecyclerView.setVisibility(View.GONE);
        mErrorMessage.setText(errorMessage);
        mErrorMessage.setVisibility(View.VISIBLE);

    }
}
