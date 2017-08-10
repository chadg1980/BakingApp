package com.h.chad.bakingapp.userinterface.steps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.h.chad.bakingapp.R;
import com.h.chad.bakingapp.model.Ingredients;
import com.h.chad.bakingapp.model.Steps;
import com.h.chad.bakingapp.userinterface.ingredients.IngredientsFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.h.chad.bakingapp.R.id.mp_step_detail_container;

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

        //Get the data passed from the recipe
        mCurrentRecipe = this.getIntent().getStringExtra(RECIPE_NAME);
        mIngredients = this.getIntent().getExtras().getParcelableArrayList(INGREDIENT_DATA);
        mSteps = this.getIntent().getExtras().getParcelableArrayList(STEP_DATA);

        //set the title as the current recipe name
        getSupportActionBar().setTitle(mCurrentRecipe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mContext = this;
        if(findViewById(mp_step_detail_container) != null){
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.step_list);
        assert recyclerView != null;
        setUpRecyclerView((RecyclerView) recyclerView);

        mIngredientLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                if (mTwoPane) {
                    args.putParcelableArrayList(IngredientsFragment.GET_INGREDIENTS_ARRAYLIST, mIngredients);
                    IngredientsFragment fragment = new IngredientsFragment();
                    fragment.setArguments(args);
                    getSupportFragmentManager().beginTransaction()
                            .replace(mp_step_detail_container, fragment)
                            .commit();

                } else {
                    Intent ingredientIntent = new Intent(mContext, StepDetailActivity.class);
                    args.putParcelableArrayList(IngredientsFragment.GET_INGREDIENTS_ARRAYLIST, mIngredients);
                    args.putString(RECIPE_NAME, mCurrentRecipe);
                    ingredientIntent.putExtra(StepDetailActivity.IS_STEP, false);
                    ingredientIntent.putExtras(args);
                    mContext.startActivity(ingredientIntent);
                }
            }
        });
    }

    private void setUpRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        StepAdapter stepAdapter = new StepAdapter(mContext, mIngredients, mSteps, mTwoPane, mCurrentRecipe);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(stepAdapter);
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
