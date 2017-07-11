package com.h.chad.bakingapp.userinterface;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.h.chad.bakingapp.R;
import com.h.chad.bakingapp.model.Ingredients;


import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chad on 7/11/2017.
 */

public class IngredientsAdapter extends RecyclerView.Adapter
        <IngredientsAdapter.IngredientsAdapterViewHolder>{

    public static final String TAG = IngredientsAdapter.class.getName();
    private Context mContext;
    private ArrayList<Ingredients> mIngredients;

    public IngredientsAdapter(Context context, ArrayList<Ingredients> ingredients){
        this.mContext = context;
        this.mIngredients = ingredients;

    }

    @Override
    public IngredientsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int layoutId = R.layout.ingredient_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean attachToParentImmediatly = false;
        View view = inflater.inflate(layoutId, parent, attachToParentImmediatly);
        return new IngredientsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsAdapterViewHolder holder, int position) {

        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }


    public class IngredientsAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_quantity) TextView quantityText;
        @BindView(R.id.tv_measure) TextView measureText;
        @BindView(R.id.tv_ingredient_item) TextView ingredientItemText;


        public IngredientsAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(int position) {
            Log.e(TAG, "float " + mIngredients.get(position).getQuantity());
            Float quantityFloat = mIngredients.get(position).getQuantity();
            String quantityString;
            if(quantityFloat != null){
                quantityString = Float.toString(quantityFloat);
                quantityText.setText(quantityString);
            }else {
                quantityText.setText("No measurement found");
            }

            measureText.setText(mIngredients.get(position).getMeasure());
            ingredientItemText.setText(mIngredients.get(position).getIngredient());
        }
    }
}
