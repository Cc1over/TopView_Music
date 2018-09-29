package com.hebaiyi.www.topviewmusic.search.view;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.base.adapter.FragmentAdapter;
import com.hebaiyi.www.topviewmusic.bean.SearchMerge;
import com.hebaiyi.www.topviewmusic.search.contract.SearchContract;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class ResultFragment extends Fragment {

    private View mView;
    private TabLayout mTbSelected;
    private ViewPager mVpContent;
    private AlbumInfoFragment mAlbumInfoFragment;
    private SongInfoFragment mSongInfoFragment;
    private ArtistInfoFragment mArtistInfoFragment;
    private SearchActivity mParentActivity;
    private List<SearchMerge.SongInfo> mSongInfos;
    private List<SearchMerge.AlbumInfo> mAlbumInfos;
    private List<SearchMerge.ArtistInfo> mArtistInfos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlbumInfoFragment = new AlbumInfoFragment();
        mSongInfoFragment = new SongInfoFragment();
        mArtistInfoFragment = new ArtistInfoFragment();
        mParentActivity = (SearchActivity) getActivity();
        // 添加事务
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.result_vp_content, mSongInfoFragment);
        transaction.add(R.id.result_vp_content, mAlbumInfoFragment);
        transaction.add(R.id.result_vp_content, mArtistInfoFragment);
        transaction.commit();
        // 获取数据
        Bundle data = getArguments();
        SearchContract.MergeSet ms = (SearchContract.MergeSet) data.get("MergeSet");
        mAlbumInfos = ms.getAlbumInfos();
        mArtistInfos = ms.getArtistInfos();
        mSongInfos = ms.getSongInfos();
        // 注册EventBus
        EventBus.getDefault().register(mSongInfoFragment);
        EventBus.getDefault().register(mAlbumInfoFragment);
        EventBus.getDefault().register(mArtistInfoFragment);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(mSongInfoFragment);
        EventBus.getDefault().unregister(mAlbumInfoFragment);
        EventBus.getDefault().unregister(mArtistInfoFragment);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_result, container, false);
        mTbSelected = mView.findViewById(R.id.result_tab_layout_selection);
        mVpContent = mView.findViewById(R.id.result_vp_content);
        initViewPageAndTabLayout();
        return mView;
    }

    @Subscribe()
    public void onEvent(Bundle data) {
        SearchContract.MergeSet ms = (SearchContract.MergeSet) data.get("MergeSet");
        EventBus.getDefault().post(ms);
    }

    public void loadMore(){
        mParentActivity.loadMore();
    }


    private void initViewPageAndTabLayout() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(mSongInfoFragment);
        fragments.add(mAlbumInfoFragment);
        fragments.add(mArtistInfoFragment);
        FragmentAdapter adapter = new FragmentAdapter(fragments, getFragmentManager());
        mVpContent.setOffscreenPageLimit(2);
        mVpContent.setAdapter(adapter);
        // 关联TabLayout和ViewPager
        mTbSelected.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(mVpContent));
        mVpContent.setOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(mTbSelected));
    }

    public List<SearchMerge.SongInfo> obtainSongInfos() {
        return mSongInfos;
    }

    public List<SearchMerge.AlbumInfo> obtainAlbumInfos() {
        return mAlbumInfos;
    }

    public List<SearchMerge.ArtistInfo> obtainArtistInfos() {
        return mArtistInfos;
    }

    public int obtainCurrState(){
        return mParentActivity.obtainCurrState();
    }

}
