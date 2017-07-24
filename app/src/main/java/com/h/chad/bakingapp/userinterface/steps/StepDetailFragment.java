package com.h.chad.bakingapp.userinterface.steps;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
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

/**
 * Created by chad on 7/10/2017.
 */

public class StepDetailFragment extends Fragment {

    public final static String TAG = StepDetailFragment.class.getName();

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void setArguments(ArrayList<Parcelable> parcelableArrayListExtra) {
        Log.e(TAG, "FRAGMENT STARTED *****");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.step_detail, container, false);
        ((TextView)rootView.findViewById(R.id.step_detail)).setText("HELLO WORLD!");

        //exoplayer
        //mShouldAutoPlay = true;
        //mBandwidthMeter = new DefaultBandwidthMeter();
        //mMediaDataSourceFactory = new DefaultDataSourceFactory(mContext, Util.getUserAgent(
        //        mContext, "Baking App"), (TransferListener<? super DataSource>) mBandwidthMeter);


        return rootView;
    }

    //For the nex previous button, make sure there is a next step or previous step
    public void checkStep() {

        if (currentStepID <= 0) {
            mPreviousStep.setClickable(false);
            mPreviousStep.setBackgroundColor(ContextCompat.getColor(mContext, R.color.app_black));
        } else {
            mPreviousStep.setClickable(true);
            mPreviousStep.setBackgroundColor(ContextCompat.getColor(mContext, R.color.backgroundGray));
        }

        if (currentStepID >= mLastStep) {
            mNextStep.setClickable(false);
            mNextStep.setBackgroundColor(ContextCompat.getColor(mContext, R.color.app_black));
        } else {
            mNextStep.setClickable(true);
            mNextStep.setBackgroundColor(ContextCompat.getColor(mContext, R.color.backgroundGray));
        }

    }

    //Setting up the Exoplayer.
    /*
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
    public void onStart() {
        super.onStart();
        if(Util.SDK_INT > 23){
            setupVideoPlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if((Util.SDK_INT <=23 || mPlayer == null)){
            setupVideoPlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(Util.SDK_INT <= 23){
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(Util.SDK_INT > 23){
            releasePlayer();
        }
    }
    */
}
