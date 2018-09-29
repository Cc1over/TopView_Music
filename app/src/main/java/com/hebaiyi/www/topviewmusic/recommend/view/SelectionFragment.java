package com.hebaiyi.www.topviewmusic.recommend.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.base.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class SelectionFragment extends Fragment {

    private View mView;
    private ViewPager mVpPlace;
    private TabLayout mTablytSelected;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       mView = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_selection,container,false);
        mVpPlace = mView.findViewById(R.id.selection_vp_fragment_place);
        mTablytSelected = mView.findViewById(R.id.selection_tabLyt_selected);
        initViewPagerAndTabLayout();
        return mView;
    }

    private void initViewPagerAndTabLayout(){
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new RecommendFragment());
        fragments.add(new ChannelFragment());
        fragments.add(new BillboardFragment());
        mVpPlace.setOffscreenPageLimit(2);
        FragmentAdapter adapter = new FragmentAdapter(fragments,
                getActivity().getSupportFragmentManager());
        mVpPlace.setAdapter(adapter);
        // 关联TabLayout和ViewPager
        mTablytSelected.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(mVpPlace));
        mVpPlace.setOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(mTablytSelected));
    }

}
