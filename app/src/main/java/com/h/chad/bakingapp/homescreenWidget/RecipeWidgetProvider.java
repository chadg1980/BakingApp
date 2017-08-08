package com.h.chad.bakingapp.homescreenWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.h.chad.bakingapp.R;
import com.h.chad.bakingapp.userinterface.recipe.RecipeActivity;

/**
 * Implementation of App Widget functionality.
 * Started with the dev guide: https://developer.android.com/guide/topics/appwidgets/index.html
 * with referece to code:
 * https://github.com/vogellacompany/codeexamples-android/tree/master/com.example.android.stackwidget
 */


public class RecipeWidgetProvider extends AppWidgetProvider implements
        SharedPreferences.OnSharedPreferenceChangeListener{

    public static final String TAG = RecipeWidgetProvider.class.getName();
    public static final String TOAST_ACTION = "com.example.android.stackwidget.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.h.chad.bakingapp.homescreenWidge.EXTRA_ITEM";
    public static final String SELECT_RECIPE_TITLE = "SELECT_RECIPE_TITLE";
    public static final String SELECT_RECIPE_ID = "SELECT_RECIPE_ID";
    public static final String RECIPE_PREF = "RECIPE_PREF";
    private Context mContext;
    public String mRecipeTitle;
    public int mRecipeId;

    // Called when the BroadcastReceiver receives an Intent broadcast.
// Checks to see whether the intent's action is TOAST_ACTION. If it is, the app widget
// displays a Toast message for the current item.
    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        if(intent.getAction().equals(TOAST_ACTION)){
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
            Intent startRecipeIntent = new Intent(context.getApplicationContext(), RecipeActivity.class);
            context.getApplicationContext().startActivity(startRecipeIntent);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
                         final int appWidgetId) {


        //Sets up the intent that points to the StackViewService
        // provide the views for this collection
        Intent intent = new Intent(context, ListWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        SharedPreferences pref = context.getSharedPreferences(RecipeWidgetProvider.RECIPE_PREF, 0);
        mRecipeTitle = pref.getString(RecipeWidgetProvider.SELECT_RECIPE_TITLE, mContext.getString(R.string.nutella_pie));

        //When intents are compared, the extras are ignored, so we need to embed the extra
        //into the data so that the extras will not be ignored
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        RemoteViews rv =
                new RemoteViews(context.getPackageName(), R.layout.widget_layout_recipe);
        rv.setRemoteAdapter(R.id.appwidget_list, intent);

        //the empty view is displayed
        rv.setEmptyView(R.id.appwidget_list, R.id.textViewWidgetClickMe);
        rv.setTextViewText(R.id.widget_recipe_title, mRecipeTitle);



        // This section makes it possible for items to have individualized behavior.
        // It does this by setting up a pending intent template. Individuals items of a collection
        // cannot set up their own pending intents. Instead, the collection as a whole sets
        // up a pending intent template, and the individual items set a fillInIntent
        // to create unique behavior on an item-by-item basis.
        Intent toastIntent = new Intent(context, RecipeWidgetProvider.class);
        // Set the action for the intent.
        // When the user touches a particular view, it will have the effect of
        // broadcasting TOAST_ACTION.
        toastIntent.setAction(RecipeWidgetProvider.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setPendingIntentTemplate(R.id.appwidget_list, toastPendingIntent);

        //Widgets allow click handlers to only launch pending intent
        rv.setPendingIntentTemplate(R.id.appwidget_list, toastPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    //Called when the widget is created
    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        PreferenceManager.getDefaultSharedPreferences(mContext).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        mRecipeTitle = sharedPreferences.getString(key, mContext.getString(R.string.nutella_pie));
        mRecipeId = sharedPreferences.getInt(key, 0);



    }
}

