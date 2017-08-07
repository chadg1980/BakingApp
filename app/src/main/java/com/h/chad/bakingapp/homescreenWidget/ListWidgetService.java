package com.h.chad.bakingapp.homescreenWidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.h.chad.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

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
    private static final int mCount = 10;
    private List<String> mWidgetItem = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetID;

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
        for (int i = 0; i < mCount; i++) {
            mWidgetItem.add((i + "!"));
        }
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
        mWidgetItem.clear();

    }

    @Override
    public int getCount() {
        return mCount;
    }

    // Given the position (index) of a WidgetItem in the array, use the item's text value in
    // combination with the app widget item XML file to construct a RemoteViews object.
    @Override
    public RemoteViews getViewAt(int position) {

        //postion will always range from 0 to getcount() - 1


        //Construct a remoteViews based on the app widget item XML file,
        // and set the text based on the position
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        String data = mWidgetItem.get(position);
        rv.setTextViewText(R.id.widget_item_number, data);

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
