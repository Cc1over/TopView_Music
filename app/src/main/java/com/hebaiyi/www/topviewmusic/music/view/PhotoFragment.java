package com.hebaiyi.www.topviewmusic.music.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hebaiyi.www.topviewmusic.R;

public class PhotoFragment extends Fragment {

    private View mView;
    private ImageView mIvPhoto;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = LayoutInflater.from(container.getContext())
                .inflate(R.layout.fragment_photo,container,false);
        mIvPhoto = mView.findViewById(R.id.photo_iv_photo);
        String url = getArguments().getString("url");
        setPhoto(url);
        return mView;
    }

    public void setPhoto(String url){
        if(url.isEmpty()){
            return;
        }
        Glide.with(getContext()).load(url).into(mIvPhoto);
    }


}
