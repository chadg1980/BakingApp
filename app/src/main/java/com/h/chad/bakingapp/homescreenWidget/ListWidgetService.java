package com.h.chad.bakingapp.homescreenWidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.h.chad.bakingapp.R;
import com.h.chad.bakingapp.data.ApiUtils;
import com.h.chad.bakingapp.data.SOService;
import com.h.chad.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.media.CamcorderProfile.get;
import static android.os.Build.VERSION_CODES.M;

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

    private List<String> mRecipe;
    private Context mContext;
    private int mAppWidgetID;
    private SOService mService;

    public RecipeWidgetRemoteViewFactory(Context context, Intent intent) {
        this.mContext = context;
        mAppWidgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    //initialize the data set
    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate()");
        // In onCreate() you set up any connections / cursors to your data source. Heavy lifting,
        // for example downloading or creating content etc, should be deferred to onDataSetChanged()
        // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.
         mRecipe = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            int x = i + 1;
            mRecipe.add("step " + x );
        }


    }

    @Override
    public void onDataSetChanged() {
        //Set JSON this method
        Log.e(TAG, "onDataSetChanged ");

        /**
        SOService service = ApiUtils.getSOService();
        mRecipe = new ArrayList<>();
        service.getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        Log.e(TAG, "response.body() is null");
                    } else {
                        for (Recipe recipe : response.body()) {
                            mRecipe.add(recipe);
                            Log.e(TAG, "mrecipe size " + mRecipe.size());
                        }
                    }
                } else {
                    Log.e(TAG, "response not successfull, response " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e(TAG, "failure making request call: " + call);
                t.printStackTrace();
            }
        });
        **/

    }

    @Override
    public void onDestroy() {
        mRecipe.clear();

    }

    @Override
    public int getCount() {
        Log.e(TAG, "mRecipe size getCount() " + mRecipe.size());
        return mRecipe.size();
    }

    // Given the position (index) of a WidgetItem in the array, use the item's text value in
    // combination with the app widget item XML file to construct a RemoteViews object.
    @Override
    public RemoteViews getViewAt(int position) {

        //postion will always range from 0 to getcount() - 1
        Log.e(TAG, "mrecipe size getViewAt() " + mRecipe.size() + " position " + position);


        //Construct a remoteViews based on the app widget item XML file,
        // and set the text based on the position
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        String data = mRecipe.get(position);
        Log.e(TAG, "DATA IS THE DATA IS THE DATA!!!!! " + data);
        String instructions = " Stir and mix";
        rv.setTextViewText(R.id.widget_step_number, data);
        rv.setTextViewText(R.id.tv_widget_short_description, instructions);

        //Set a fill-intent, which will be used to fill the pending intent template
        // that is set on the collection view in RecipeWidgetProvider
        Bundle args = new Bundle();
        args.putInt(RecipeWidgetProvider.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();

        fillInIntent.putExtra("recipe_widget_data", data);
        fillInIntent.putExtras(args);
        //Make it possible to distinguishe the individual on-click
        rv.setOnClickFillInIntent(R.id.item_layout, fillInIntent);
        Log.e(TAG, "Loading View " + position);

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
