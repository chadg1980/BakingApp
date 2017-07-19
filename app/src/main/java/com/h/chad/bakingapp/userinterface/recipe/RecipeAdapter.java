package com.h.chad.bakingapp.userinterface.recipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.h.chad.bakingapp.R;
import com.h.chad.bakingapp.model.Ingredients;
import com.h.chad.bakingapp.model.Recipe;
import com.h.chad.bakingapp.model.Steps;
import com.h.chad.bakingapp.userinterface.steps.StepListActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chad on 7/8/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder>{

    public static final String TAG = RecipeAdapter.class.getName();

    public Context mContext;
    public ArrayList<Recipe> mRecipes;
    public boolean mTwoPane;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes){
        this.mRecipes = recipes;
        this.mContext = context;
    }

    @Override
    public RecipeAdapter.RecipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int layoutIdForRecipeItem = R.layout.recipe_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean attachToParentImmediatly = false;
        View view = inflater.inflate(layoutIdForRecipeItem, parent, attachToParentImmediatly);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeAdapter.RecipeAdapterViewHolder holder, final int position) {
        holder.bind(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Ingredients> ingredients = mRecipes.get(position).getIngredients();
                ArrayList<Steps> steps = mRecipes.get(position).getSteps();
                Intent stepIntent = new Intent(mContext, StepListActivity.class);
                Bundle args = new Bundle();
                args.putParcelableArrayList(StepListActivity.INGREDIENT_DATA, ingredients);
                args.putParcelableArrayList(StepListActivity.STEP_DATA, steps);
                stepIntent.putExtra(StepListActivity.RECIPE_NAME, mRecipes.get(position).getName());
                stepIntent.putExtras(args);
                mContext.startActivity(stepIntent);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    class RecipeAdapterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_recipe_name)
        TextView recipeName;
        @BindView(R.id.iv_recipe_image)
        ImageView recipeImage;

        private RecipeAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        void bind(int listIndex){
            recipeName.setText(mRecipes.get(listIndex).getName());
            if(!TextUtils.isEmpty(mRecipes.get(listIndex).getImage())) {
                Picasso.with(mContext)
                        .load(mRecipes.get(listIndex).getImage())
                        .placeholder(R.drawable.cupcake)
                        .error(R.drawable.cupcake)
                        .into(recipeImage);
            }
            else{
                Picasso.with(mContext)
                        .load(R.drawable.cupcake)
                        .placeholder(R.drawable.cupcake)
                        .error(R.drawable.cupcake)
                        .into(recipeImage);
            }
        }
    }
}
