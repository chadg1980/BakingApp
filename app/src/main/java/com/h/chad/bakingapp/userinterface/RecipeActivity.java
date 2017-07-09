package com.h.chad.bakingapp.userinterface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.h.chad.bakingapp.R;
import com.h.chad.bakingapp.data.ApiUtils;
import com.h.chad.bakingapp.data.SOService;
import com.h.chad.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeActivity extends AppCompatActivity {

    private final static String TAG = RecipeActivity.class.getName();

    private RecyclerView mRecyclerView;
    private RecipeAdapter mRecipeAdapter;
    private SOService mService;

    private ArrayList<Recipe> mRecipe;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mService = ApiUtils.getSOService();
        mRecipe = new ArrayList<>();
       loadRecipe();

    }

    public void loadRecipe() {
        mService.getRecipes().enqueue(new Callback<List<Recipe>>() {

            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if(response.isSuccessful()){
                    for( Recipe recipe : response.body()){
                        mRecipe.add(recipe);

                    }
                    setUpRecyclerView(mRecipe);

                }else {
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
    private void setUpRecyclerView(ArrayList<Recipe> recipeArrayList){
        Log.e(TAG, "Array size " + recipeArrayList.size());
        if(recipeArrayList.size() > 0) {
            mRecyclerView = (RecyclerView) findViewById(R.id.rv_recipes);
            mRecyclerView.setHasFixedSize(true);
            mRecipeAdapter = new RecipeAdapter(this, recipeArrayList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);

            mRecyclerView.setAdapter(mRecipeAdapter);
            RecyclerView.ItemDecoration itemDecoration =
                    new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            mRecyclerView.addItemDecoration(itemDecoration);

        }


    }
}
