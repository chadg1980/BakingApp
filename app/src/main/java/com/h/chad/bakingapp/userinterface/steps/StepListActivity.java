package com.h.chad.bakingapp.userinterface.steps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepListActivity extends AppCompatActivity {

    public final static String TAG = StepListActivity.class.getName();

    /* Constants for passing data through intents and parcelable */
    public final static String INGREDIENT_DATA = "INGREDIENT_DATA";
    public final static String STEP_DATA = "STEP_DATA";
    public final static String RECIPE_NAME = "RECIPE_NAME";

    @BindView(R.id.rl_ingredient_layout) RelativeLayout mIngredientLayout;
    private ArrayList<Steps> mSteps;
    private ArrayList<Ingredients> mIngredients;

    //Weather or not the activity is run on a tablet, this should be in two-pane mode
    private boolean mTwoPane;
    private Context mContext;
    private String mCurrentRecipe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);
        ButterKnife.bind(this);
        mCurrentRecipe = this.getIntent().getStringExtra(RECIPE_NAME);
        getSupportActionBar().setTitle(mCurrentRecipe);

        //Get the data passed from the recipe
        mIngredients = this.getIntent().getExtras().getParcelableArrayList(INGREDIENT_DATA);
        mSteps = this.getIntent().getExtras().getParcelableArrayList(STEP_DATA);
        mContext = this;
        if(findViewById(R.id.mp_step_detail_container) != null){
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.step_list);
        assert recyclerView != null;
        setUpRecyclerView((RecyclerView) recyclerView);

        mIngredientLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ingredientIntent = new Intent(mContext, IngredientsActivity.class);
                Bundle args = new Bundle();
                args.putParcelableArrayList(IngredientsActivity.GET_INGREDIENTS_ARRAYLIST, mIngredients);
                args.putString(RECIPE_NAME, mCurrentRecipe);
                ingredientIntent.putExtras(args);
                mContext.startActivity(ingredientIntent);
            }
        });
    }

    private void setUpRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        StepAdapter stepAdapter = new StepAdapter(mContext, mIngredients, mSteps, mTwoPane);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(stepAdapter);

    }






}
