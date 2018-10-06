package com.hebaiyi.www.topviewmusic.music.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hebaiyi.www.topviewmusic.R;

public class LyricsFragment extends Fragment {

    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = LayoutInflater.from(container.getContext())
                    .inflate(R.layout.fragment_lyrics, container, false);
        }
        return mView;
    }
}
