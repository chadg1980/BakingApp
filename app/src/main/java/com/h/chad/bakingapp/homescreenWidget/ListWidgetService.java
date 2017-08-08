package com.h.chad.bakingapp.homescreenWidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.h.chad.bakingapp.R;
import com.h.chad.bakingapp.data.ApiUtils;
import com.h.chad.bakingapp.data.SOService;
import com.h.chad.bakingapp.model.Recipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by chad on 8/3/2017.
 */

public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeWidgetRemoteViewFactory(this.getApplicationContext(), intent);
    }
}

class RecipeWidgetRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = RecipeWidgetRemoteViewFactory.class.getName();

    private List<Recipe> mRecipe;
    private Context mContext;
    private int mAppWidgetID;
    private SOService mService;
    private int mRecipeId;
    public String RECIPE_WIDGET_DATA = "recipe_widget_data";

    public RecipeWidgetRemoteViewFactory(Context context, Intent intent) {
        this.mContext = context;
        mAppWidgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    //initialize the data set
    @Override
    public void onCreate() {
        // In onCreate() you set up any connections / cursors to your data source. Heavy lifting,
        // for example downloading or creating content etc, should be deferred to onDataSetChanged()
        // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.
        SharedPreferences pref = mContext.getSharedPreferences(RecipeWidgetProvider.RECIPE_PREF, 0);
        mRecipeId = pref.getInt(RecipeWidgetProvider.SELECT_RECIPE_ID, 0);
        SharedPreferences.OnSharedPreferenceChangeListener changeListener = new
                SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                        mRecipeId = sharedPreferences.getInt(key, 0);
                        onDataSetChanged();
                    }
                };
    }

    @Override
    public void onDataSetChanged() {
        //Set JSON this method

        SOService service = ApiUtils.getSOService();
        mRecipe = new ArrayList<Recipe>();
        try {
            Response<List<Recipe>> result;
            result = service.getRecipes().execute();
            if (result.isSuccessful()) {
                for (Recipe r : result.body()) {
                    mRecipe.add(r);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            }
    }

    @Override
    public void onDestroy() {
        mRecipe.clear();
    }

    @Override
    public int getCount() {
        return mRecipe.get(mRecipeId).getIngredients().size();

    }

    // Given the position (index) of a WidgetItem in the array, use the item's text value in
    // combination with the app widget item XML file to construct a RemoteViews object.
    @Override
    public RemoteViews getViewAt(int position) {
        //postion will always range from 0 to getcount() - 1

        //Construct a remoteViews based on the app widget item XML file,
        // and set the text based on the position
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        String quantity = mRecipe.get(mRecipeId).getIngredients().get(position).getQuantity().toString();
        String measure = mRecipe.get(mRecipeId).getIngredients().get(position).getMeasure();
        String item = mRecipe.get(mRecipeId).getIngredients().get(position).getIngredient();

        rv.setTextViewText(R.id.widget_quantity, quantity);
        rv.setTextViewText(R.id.widget_measure, measure);
        rv.setTextViewText(R.id.widget_ingredient_item, item);

        //Set a fill-intent, which will be used to fill the pending intent template
        // that is set on the collection view in RecipeWidgetProvider
        Bundle args = new Bundle();
        args.putInt(RecipeWidgetProvider.EXTRA_ITEM, mRecipeId);
        Intent fillInIntent = new Intent();

        fillInIntent.putExtra(RECIPE_WIDGET_DATA, quantity);
        fillInIntent.putExtras(args);
        //Make it possible to distinguishe the individual on-click
        rv.setOnClickFillInIntent(R.id.item_layout, fillInIntent);

        //return the remoteview object
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


}
