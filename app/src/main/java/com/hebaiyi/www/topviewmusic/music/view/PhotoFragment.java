package com.hebaiyi.www.topviewmusic.music.view;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hebaiyi.www.topviewmusic.R;

public class PhotoFragment extends Fragment implements View.OnClickListener {

    private static final int STATE_PLAYING = 0XDD44;
    private static final int STATE_PAUSE = 0XCC11;
    private static final int STATE_STOP = 0XEEEE;
    private int currState = STATE_STOP;
    private View mView;
    private ImageView mIvPhoto;
    private TextView mTvStandard, mTvMv, mTvEffect;
    private ObjectAnimator mRotationAnim;
    private MusicActivity mParentActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParentActivity = (MusicActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = LayoutInflater.from(container.getContext())
                    .inflate(R.layout.fragment_photo, container, false);
            mIvPhoto = mView.findViewById(R.id.photo_iv_photo);
            mTvEffect = mView.findViewById(R.id.photo_tv_effect);
            mTvMv = mView.findViewById(R.id.photo_tv_mv);
            mTvStandard = mView.findViewById(R.id.photo_tv_standard);
            mTvStandard.setOnClickListener(this);
            mTvMv.setOnClickListener(this);
            mTvEffect.setOnClickListener(this);
            if (getArguments() != null) {
                String url = getArguments().getString("url");
                setPhoto(url);
            }
            if (mRotationAnim == null) {
                initAnimation();
            }
        }
        setRotate(mParentActivity.isPlaying());
        return mView;
    }

    public void setPhoto(String url) {
        if (url == null || "".equals(url)) {
            return;
        }
        Glide.with(getContext()).load(url).into(mIvPhoto);
    }

    public void setRotate(boolean isRotate) {
        if (isRotate) {
            if (currState == STATE_STOP) {
                mRotationAnim.start();
                currState = STATE_PLAYING;
            }
            if (currState == STATE_PAUSE) {
                mRotationAnim.resume();
                currState = STATE_PLAYING;
            }
        } else {
            if (currState == STATE_PLAYING) {
                mRotationAnim.pause();
                currState = STATE_PAUSE;
            }
        }
    }

    private void initAnimation() {
        mRotationAnim = ObjectAnimator.ofFloat(mIvPhoto, "rotation", 0f, 360f);
        mRotationAnim.setDuration(20000);
        mRotationAnim.setInterpolator(new LinearInterpolator());
        mRotationAnim.setRepeatCount(ObjectAnimator.INFINITE);
        mRotationAnim.setRepeatMode(ObjectAnimator.RESTART);
    }

    @Override
    public void onDestroyView() {
        mRotationAnim.pause();
        currState = STATE_PAUSE;
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mRotationAnim.end();
        currState = STATE_STOP;
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_tv_standard:
                break;
            case R.id.photo_tv_mv:
                break;
            case R.id.photo_tv_effect:
                break;
        }
    }


}
