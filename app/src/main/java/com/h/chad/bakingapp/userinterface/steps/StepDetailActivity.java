package com.h.chad.bakingapp.userinterface.steps;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.h.chad.bakingapp.R;
import com.h.chad.bakingapp.model.Steps;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chad on 7/18/2017.
 */

public class StepDetailActivity extends AppCompatActivity {

    public final static String TAG = StepDetailActivity.class.getName();

    /* Constant for getting the step details*/
    public final static String GET_STEP_ARRAYLIST = "GET_STEP_ARRAYLIST";
    public final static String GET_STEP_ID = "GET_STEP_ID";

    ArrayList<Steps> mSteps;
    int currentStepID;
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        mSteps = this.getIntent().getParcelableArrayListExtra(GET_STEP_ARRAYLIST);
        currentStepID = this.getIntent().getIntExtra(GET_STEP_ID, -1);

        if(savedInstanceState == null){
            Bundle args = new Bundle();
            //args.putParcelableArrayList(StepListActivity.INGREDIENT_DATA, ingredients);
            args.putParcelableArrayList(StepListActivity.STEP_DATA, mSteps);
            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mp_step_detail_container, fragment)
                    .commit();
            //stepIntent.putExtra(StepListActivity.RECIPE_NAME, mRecipes.get(position).getName());
            //stepIntent.putExtras(args);
            //mContext.startActivity(stepIntent);
        }

        ButterKnife.bind(this);
        mContext = this;

        if (currentStepID < 0) {
            Log.e(TAG, "Step ID did not come through ID:" + currentStepID);
        }
 }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
