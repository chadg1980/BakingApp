package com.h.chad.bakingapp.userinterface.recipe;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

/**
 * RecipeActivity is the MainActivity of the Baking App.
 * ReipeActivity received data via service callback (see loadRecipe())
 * If there is network activity to receive the data
 *      RecipeActivty sends the data to RecipeAdapter in RecyclerView
 * */
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
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        //Check for internet
        checkConnection();
    }

    //checks if there is an internet connection
    //Returns a boolean if there is
    private void checkConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        //If we have a connection, then we load the recipes
        if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            hideError();
            mService = ApiUtils.getSOService();
            mRecipe = new ArrayList<>();
            mIsTablet = getResources().getBoolean(R.bool.is_tablet);

            //Loads the recipes using Retrofit2
            loadRecipe();
        }
        //If we do not have a connection, show an error
        else{
            String networkError = getString(R.string.network_error);
            showError(networkError);
            //Adding a retry button to try to load the recipes
            // once the user gains network connection
            mErrorMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkConnection();
                }
            });

        }
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
        //mRecyclerView.setVisibility(View.GONE);
        mErrorMessage.setText(errorMessage);
        mErrorMessage.setVisibility(View.VISIBLE);
    }
    private void hideError() {
        mErrorMessage.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_title);
        SpannableString s = new SpannableString(menuItem.getTitle());
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), 0);
        s.setSpan(new BackgroundColorSpan(Color.BLACK), 0, s.length(), 0);
        menuItem.setTitle(s);
        return true;
    }

    //Which recipe to put on our homescreen widget
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle item seltect
        int recipeListForWidget = 0;
        switch (item.getItemId()){
            case R.id.menu_nutella_pie:
                recipeListForWidget = 0;
                break;
            case R.id.menu_brownies:
                recipeListForWidget = 1;
                break;
            case R.id.menu_yellow_cake:
                recipeListForWidget = 2;
                break;
            case R.id.menu_cheesecake:
                recipeListForWidget = 3;
                break;
            default:
                recipeListForWidget = 0;
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
