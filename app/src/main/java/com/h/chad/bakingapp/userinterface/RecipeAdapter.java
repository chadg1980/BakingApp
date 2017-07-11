package com.h.chad.bakingapp.userinterface;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.h.chad.bakingapp.R;
import com.h.chad.bakingapp.model.Ingredients;
import com.h.chad.bakingapp.model.Recipe;
import com.h.chad.bakingapp.model.Steps;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.R.string.no;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by chad on 7/8/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder>{

    public static final String TAG = RecipeAdapter.class.getName();
    public Context mContext;
    public ArrayList<Recipe> mRecipes;


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
                Intent stepIntent = new Intent(mContext, StepActivity.class);
                Bundle args = new Bundle();
                args.putParcelableArrayList(StepActivity.INGREDIENT_DATA, ingredients);
                args.putParcelableArrayList(StepActivity.STEP_DATA, steps);
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
        private TextView recipeName;
        private ImageView recipeImage;

        private RecipeAdapterViewHolder(View itemView) {
            super(itemView);
            recipeName = (TextView) itemView.findViewById(R.id.tv_recipe_name);
            recipeImage = (ImageView) itemView.findViewById(R.id.iv_recipe_image);
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
