package com.h.chad.bakingapp.userinterface.recipe;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.h.chad.bakingapp.R;
import com.h.chad.bakingapp.data.ApiUtils;
import com.h.chad.bakingapp.data.SOService;
import com.h.chad.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeActivity extends AppCompatActivity {

    private final static String TAG = RecipeActivity.class.getName();

    private RecipeAdapter mRecipeAdapter;
    @BindView(R.id.rv_recipes) RecyclerView mRecyclerView;
    @BindView(R.id.tv_error_message) TextView mErrorMessage;
    private SOService mService;
    private ArrayList<Recipe> mRecipe;
    private boolean mIsTablet;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);
        mService = ApiUtils.getSOService();
        mRecipe = new ArrayList<>();
        mIsTablet = getResources().getBoolean(R.bool.is_tablet);

        //Loads the recipes using Retrofit2
        loadRecipe();
    }

    /**
     * calls enque to the API https://d17h27t6h515a5.cloudfront.net
     * Puts the return values in an ArrayList of Recipes
     * calls setUpRecyclerView in the callback
     * (because Recyclerview should't be called without data)
     */
    public void loadRecipe() {
        mService.getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                if(response.isSuccessful()){
                    if (response.body() == null) {
                        showError(getString(R.string.response_error));
                    } else {
                        for (Recipe recipe : response.body()) {
                            mRecipe.add(recipe);
                        }
                        setUpRecyclerView(mRecipe);
                    }
                }else {
                    showError(getString(R.string.response_error));
                    Log.e(TAG, "response not successfull, response " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e(TAG, "failure making request call: " + call);
                t.printStackTrace();
            }
        });
    }

    /**
     * Displays error message
     * Takes string input of what error to display to user
     */
    private void showError(String errorMessage) {
        mRecyclerView.setVisibility(View.GONE);
        mErrorMessage.setText(errorMessage);
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    /**
     * setUpRecyclerView takes the ArrayList<Recipe>
     * If there is data, starts the Recycycler View for the Recipe Adpater Class
     */
    private void setUpRecyclerView(ArrayList<Recipe> recipeArrayList){
        if(recipeArrayList.size() > 0) {
            mRecyclerView.setHasFixedSize(true);
            mRecipeAdapter = new RecipeAdapter(this, recipeArrayList);
            if(!mIsTablet) {
                mLayoutManager = new LinearLayoutManager(this);
            }else {
                mLayoutManager = new GridLayoutManager(this, 3);
            }

            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mRecipeAdapter);
        }
    }
}
