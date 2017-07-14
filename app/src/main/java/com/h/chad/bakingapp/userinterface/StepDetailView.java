package com.h.chad.bakingapp.userinterface;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
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
import java.util.logging.Handler;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chad on 7/10/2017.
 */

public class StepDetailView extends AppCompatActivity {

    public final static String TAG = StepDetailView.class.getName();

    /* Constant for getting the step details*/
    public final static String GET_STEP_ARRAYLIST = "GET_STEP_ARRAYLIST";
    public final static String GET_STEP_ID = "GET_STEP_ID";

    @BindView(R.id.tv_step_detail_instructions) TextView mInstructions;
    @BindView(R.id.tv_previous_step) TextView mPreviousStep;
    @BindView(R.id.tv_next_step) TextView mNextStep;
    @BindView(R.id.mp_step_detail) SimpleExoPlayerView mVideoPlayerView;
    @BindView(R.id.layout_no_media)
    RelativeLayout mNoVideo;
    ArrayList<Steps> mSteps;
    int currentStepID;
    public Context mContext;

    private DataSource.Factory mMediaDataSourceFactory;
    private SimpleExoPlayer mPlayer;
    private DefaultTrackSelector mTrackSelector;
    private boolean mShouldAutoPlay;
    private BandwidthMeter mBandwidthMeter;
    private int mLastStep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_detail);
        ButterKnife.bind(this);
        mContext = this;
        mShouldAutoPlay = true;
        mBandwidthMeter = new DefaultBandwidthMeter();
        mMediaDataSourceFactory = new DefaultDataSourceFactory(mContext, Util.getUserAgent(
                mContext, "Project Sample"),(TransferListener<? super DataSource>) mBandwidthMeter );


        mSteps = this.getIntent().getParcelableArrayListExtra(GET_STEP_ARRAYLIST);
        currentStepID = this.getIntent().getIntExtra(GET_STEP_ID, -1);
        if(currentStepID < 0 ){
            Log.e(TAG, "Step ID did not come through ID:" + currentStepID);
        }
        mInstructions.setText(mSteps.get(currentStepID).getDescription());

        mLastStep = mSteps.size() -1;
        if (currentStepID >= mLastStep) {
            mNextStep.setClickable(false);
            mNextStep.setBackgroundColor(ContextCompat.getColor(this, R.color.app_black));
        } else {
            mNextStep.setClickable(true);
            mNextStep.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundGray));
        }
        //Only click the next step if it is clickable
        if (mNextStep.isClickable()) {
            mNextStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nextStep = new Intent(StepDetailView.this, StepDetailView.class);
                    Bundle args = new Bundle();
                    args.putParcelableArrayList(StepDetailView.GET_STEP_ARRAYLIST, mSteps);
                    nextStep.putExtras(args);
                    nextStep.putExtra(StepDetailView.GET_STEP_ID, currentStepID + 1);
                    mContext.startActivity(nextStep);
                }
            });
        }
        if (currentStepID <= 0) {
            mPreviousStep.setClickable(false);
            mPreviousStep.setBackgroundColor(ContextCompat.getColor(this, R.color.app_black));
        } else {
            mPreviousStep.setClickable(true);
            mPreviousStep.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundGray));
        }
        //Only click the next step if it is clickable
        if (mPreviousStep.isClickable()) {
            mPreviousStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nextStep = new Intent(StepDetailView.this, StepDetailView.class);
                    Bundle args = new Bundle();
                    args.putParcelableArrayList(StepDetailView.GET_STEP_ARRAYLIST, mSteps);
                    nextStep.putExtras(args);
                    nextStep.putExtra(StepDetailView.GET_STEP_ID, currentStepID - 1);
                    mContext.startActivity(nextStep);
                }
            });
        }


    }
    private void setupVideoPlayer(){
        mVideoPlayerView.requestFocus();

        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(mBandwidthMeter);
        mTrackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        mPlayer = ExoPlayerFactory.newSimpleInstance(mContext, mTrackSelector);
        mVideoPlayerView.setPlayer(mPlayer);
        mPlayer.setPlayWhenReady(mShouldAutoPlay);

        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        String mediaUrl = new String();
        mediaUrl = mSteps.get(currentStepID).getVideoURL();

        Log.e(TAG, mediaUrl);
        if(!TextUtils.isEmpty(mediaUrl)) {
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(mediaUrl),
                    mMediaDataSourceFactory, extractorsFactory, null, null);
            mPlayer.prepare(mediaSource);
        }
        else {
            mVideoPlayerView.setVisibility(View.GONE);
            mNoVideo.setVisibility(View.VISIBLE);

            releasePlayer();
        }

    }
    private void releasePlayer(){
        if(mPlayer != null){
            mShouldAutoPlay = mPlayer.getPlayWhenReady();
            mPlayer.release();
            mPlayer = null;
            mTrackSelector = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Util.SDK_INT > 23){
            setupVideoPlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if((Util.SDK_INT <=23 || mPlayer == null)){
            setupVideoPlayer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(Util.SDK_INT <= 23){
            releasePlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(Util.SDK_INT > 23){
            releasePlayer();
        }
    }
}
