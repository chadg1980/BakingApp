package com.h.chad.bakingapp.userinterface.steps;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
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
 * Created by chad on 7/10/2017.
 */

public class StepDetailFragment extends Fragment {

    public final static String TAG = StepDetailFragment.class.getName();

    /* Constant for getting the step details*/
    public final static String GET_STEP_ARRAYLIST = "GET_STEP_ARRAYLIST";
    public final static String GET_STEP_ID = "GET_STEP_ID";

    @BindView(R.id.tv_step_detail_instructions) TextView mInstructions;
    @BindView(R.id.tv_previous_step) TextView mButtonPreviousStep;
    @BindView(R.id.tv_next_step) TextView mButtonNextStep;

    ArrayList<Steps> mSteps;
    public Context mContext;

    //Exoplayer
    @BindView(R.id.mp_step_detail) SimpleExoPlayerView mVideoPlayerView;
    @BindView(R.id.iv_no_media)
    ImageView mNoMedia;

    private DataSource.Factory mMediaDataSourceFactory;
    private SimpleExoPlayer mPlayer;
    private DefaultTrackSelector mTrackSelector;
    private boolean mShouldAutoPlay;
    private BandwidthMeter mBandwidthMeter;

    //If No Video for Exoplayer


    private int mCurentStep;
    private int mLastStep;
    public View mRootView;

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
        mRootView = inflater.inflate(R.layout.step_detail, container, false);
        ButterKnife.bind(this, mRootView);

        Bundle args = getArguments();
        mCurentStep = args.getInt(GET_STEP_ID);

        mSteps =  args.getParcelableArrayList(StepListActivity.STEP_DATA);
        assert mSteps != null;
        mLastStep = mSteps.size();
        String instructions = mSteps.get(mCurentStep).getDescription();
        if(!TextUtils.isEmpty(instructions)) {
            mInstructions.setText(instructions);
        }else{
            mInstructions.setText(getActivity().getString(R.string.no_description));
        }
        mContext = getActivity();

        //exoplayer
        mShouldAutoPlay = true;
        mBandwidthMeter = new DefaultBandwidthMeter();
        mMediaDataSourceFactory = new DefaultDataSourceFactory(mContext, Util.getUserAgent(
                mContext, "Baking App"), (TransferListener<? super DataSource>) mBandwidthMeter);
        Timeline.Window window = new Timeline.Window();

        checkNextStep(mCurentStep);

        return mRootView;
    }

    //Next/Previos button
    public void checkNextStep(final int currentStep) {

        //Previous Button, if current step is 0, we disable the button
        if (currentStep <= 0) {
            mButtonPreviousStep.setClickable(false);
            mButtonPreviousStep.setBackgroundColor(ContextCompat.getColor(mContext, R.color.app_black));
        } else {
            mButtonPreviousStep.setClickable(true);
            mButtonPreviousStep.setBackgroundColor(ContextCompat.getColor(mContext, R.color.backgroundGray));
            mButtonPreviousStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    //args.putParcelableArrayList(StepListActivity.INGREDIENT_DATA, ingredients);
                    args.putParcelableArrayList(StepListActivity.STEP_DATA, mSteps);
                    args.putInt(StepDetailFragment.GET_STEP_ID, currentStep-1);
                    StepDetailFragment fragment = new StepDetailFragment();
                    fragment.setArguments(args);
                    FragmentTransaction transaction =  getFragmentManager().beginTransaction();
                    transaction
                            .replace(R.id.mp_step_detail_container, fragment)
                            .commit();
                }
            });
        }

        //Next Button, if the next step +1 is greater than the arraylist size (mLastStep)
        // The button is disabled
        if (currentStep+1 >= mLastStep) {
            mButtonNextStep.setClickable(false);
            mButtonNextStep.setBackgroundColor(ContextCompat.getColor(mContext, R.color.app_black));
        } else {
            mButtonNextStep.setClickable(true);
            mButtonNextStep.setBackgroundColor(ContextCompat.getColor(mContext, R.color.backgroundGray));

            mButtonNextStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    //args.putParcelableArrayList(StepListActivity.INGREDIENT_DATA, ingredients);
                    args.putParcelableArrayList(StepListActivity.STEP_DATA, mSteps);
                    args.putInt(StepDetailFragment.GET_STEP_ID, currentStep+1);
                    StepDetailFragment fragment = new StepDetailFragment();
                    fragment.setArguments(args);
                    FragmentTransaction transaction =  getFragmentManager().beginTransaction();
                    transaction
                            .replace(R.id.mp_step_detail_container, fragment)
                            .commit();
                }
            });
        }
    }

    //Setting up the Exoplayer.

    private void setupVideoPlayer(){

        mVideoPlayerView.requestFocus();

       TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(mBandwidthMeter);
        mTrackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        mPlayer = ExoPlayerFactory.newSimpleInstance(mContext, mTrackSelector);
        mVideoPlayerView.setPlayer(mPlayer);
        mPlayer.setPlayWhenReady(mShouldAutoPlay);

        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        String stepVideoUrlString = new String();
        stepVideoUrlString = mSteps.get(mCurentStep).getVideoURL();

        if(!TextUtils.isEmpty(stepVideoUrlString)) {
            MediaSource mediaSource = new ExtractorMediaSource(
                    Uri.parse(stepVideoUrlString),
                    mMediaDataSourceFactory,
                    extractorsFactory,
                    null,
                    null);
            mPlayer.prepare(mediaSource);
            mPlayer.prepare(mediaSource, true, false);
            mVideoPlayerView.setVisibility(View.VISIBLE);
            mNoMedia.setVisibility(View.GONE);
        }
        else {
            mVideoPlayerView.setVisibility(View.GONE);
            mNoMedia.setVisibility(View.VISIBLE);
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
        //hideSystemUi();
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
        if(Util.SDK_INT >= 24){
            releasePlayer();
        }
    }

    private void hideSystemUi() {
        mVideoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }


}
