package com.h.chad.bakingapp.userinterface;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.h.chad.bakingapp.R;
import com.h.chad.bakingapp.model.Ingredients;
import com.h.chad.bakingapp.model.Steps;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by chad on 7/10/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterViewHolder>{
    private final static String TAG = StepAdapter.class.getName();
    public Context mContext;
    public ArrayList<Ingredients> mIngredients;
    public ArrayList<Steps> mSteps;

    public StepAdapter(Context context, ArrayList<Ingredients> ingredients, ArrayList<Steps> steps){
        this.mContext = context;
        this.mIngredients = ingredients;
        this.mSteps = steps;
    }

    @Override
    public StepAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int layoutIdForStep = R.layout.step_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean attachToParentImmediatly = false;
        View view = inflater.inflate(layoutIdForStep, parent, attachToParentImmediatly);
        return new StepAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepAdapterViewHolder holder, final int position) {
        holder.bind(position);
        if (position == 0) {
            RelativeLayout.LayoutParams params =
                    (RelativeLayout.LayoutParams) holder.shortDescription.getLayoutParams();
            params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            holder.shortDescription.setLayoutParams(params);
            holder.shortDescription.setGravity(Gravity.CENTER);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ingredientIntent = new Intent(mContext, IngredientsActivity.class);
                    Bundle args = new Bundle();
                    args.putParcelableArrayList(IngredientsActivity.GET_INGREDIENTS_ARRAYLIST, mIngredients);
                    ingredientIntent.putExtras(args);
                    mContext.startActivity(ingredientIntent);
                    }
            });
        } else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int stepID = mSteps.get(position).getId();
                    Intent stepDetailIntent = new Intent(mContext, StepDetailView.class);
                    Bundle args = new Bundle();
                    args.putParcelableArrayList(StepDetailView.GET_STEP_ARRAYLIST, mSteps);
                    stepDetailIntent.putExtras(args);
                    stepDetailIntent.putExtra(StepDetailView.GET_STEP_ID, stepID);
                    mContext.startActivity(stepDetailIntent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    class StepAdapterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_step_number) TextView stepNumber;
        @BindView(R.id.tv_step_short_description) TextView shortDescription;

        public StepAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(int position) {
            if (position > 0) {
                int stepint = mSteps.get(position).getId();
                stepNumber.setText(Integer.toString(stepint) +
                        mContext.getString(R.string.step_paren));
                String shortDescriptionString = mSteps.get(position).getShortDescription();
                if (!TextUtils.isEmpty(shortDescriptionString))
                    shortDescription.setText(shortDescriptionString);
                else
                    shortDescription.setText(mContext.getString(R.string.no_description));
            } else {
                shortDescription.setText(mContext.getString(R.string.ingredient_string));


            }
        }
    }
}
