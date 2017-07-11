package com.h.chad.bakingapp.userinterface;

import android.content.Context;
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

import java.util.ArrayList;

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
    public void onBindViewHolder(StepAdapterViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    class StepAdapterViewHolder extends RecyclerView.ViewHolder{
        private TextView stepNumber;
        private TextView shortDescription;

        public StepAdapterViewHolder(View itemView) {
            super(itemView);
            stepNumber = (TextView) itemView.findViewById(R.id.tv_step_number);
            shortDescription = (TextView) itemView.findViewById(R.id.tv_step_short_description);
        }

        public void bind(int position) {
            stepNumber.setText(Integer.toString(position + 1));
            String shortDescriptionString = mSteps.get(position).getShortDescription();
            if(!TextUtils.isEmpty(shortDescriptionString))
                shortDescription.setText(shortDescriptionString);
            else
                shortDescription.setText("Error Maing this call");
        }
    }
}
