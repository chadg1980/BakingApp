package com.h.chad.bakingapp.userinterface.steps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.h.chad.bakingapp.R;
import com.h.chad.bakingapp.model.Ingredients;
import com.h.chad.bakingapp.model.Steps;
import com.h.chad.bakingapp.userinterface.ingredients.IngredientsFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.h.chad.bakingapp.userinterface.ingredients.IngredientsFragment.GET_INGREDIENTS_ARRAYLIST;
import static com.h.chad.bakingapp.userinterface.steps.StepListActivity.RECIPE_NAME;

/**
 * Created by chad on 7/10/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterViewHolder>{
    private final static String TAG = StepAdapter.class.getName();
    private Context mContext;
    private ArrayList<Ingredients> mIngredients;
    private ArrayList<Steps> mSteps;
    private boolean mTwoPane;
    private String mCurrentRecipe;

    public StepAdapter(Context context, ArrayList<Ingredients> ingredients, ArrayList<Steps> steps
            , boolean twoPane, String currentRecipe){
        this.mContext = context;
        this.mIngredients = ingredients;
        this.mSteps = steps;
        this.mTwoPane = twoPane;
        this.mCurrentRecipe = currentRecipe;
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
        //When a step gets clicked on, it opens up StepDetailActivity.class
        
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPane) {

                    Log.e(TAG, "Two Pane!");

                } else {
                    Context context = v.getContext();
                    int stepID = mSteps.get(position).getId();
                    Intent stepDetailIntent = new Intent(context, StepDetailActivity.class);
                    Bundle args = new Bundle();
                    args.putParcelableArrayList(StepDetailFragment.GET_STEP_ARRAYLIST, mSteps);
                    args.putParcelableArrayList(IngredientsFragment.GET_INGREDIENTS_ARRAYLIST, mIngredients);
                    args.putString(StepListActivity.RECIPE_NAME, mCurrentRecipe);
                    stepDetailIntent.putExtras(args);
                    stepDetailIntent.putExtra(StepDetailFragment.GET_STEP_ID, stepID);
                    stepDetailIntent.putExtra(StepDetailActivity.IS_STEP, true);
                    context.startActivity(stepDetailIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public class StepAdapterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_step_number) TextView stepNumber;
        @BindView(R.id.tv_step_short_description) TextView shortDescription;
        public final View mView;

        public Steps mItem;

        public StepAdapterViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            ButterKnife.bind(this, itemView);
        }

        public void bind(int position) {
            int stepint = mSteps.get(position).getId() + 1;
            stepNumber.setText(Integer.toString(stepint) +mContext.getString(R.string.step_paren));
            String shortDescriptionString = mSteps.get(position).getShortDescription();
            if (!TextUtils.isEmpty(shortDescriptionString))
                shortDescription.setText(shortDescriptionString);
            else
                shortDescription.setText(mContext.getString(R.string.no_description));
        }
    }
}
