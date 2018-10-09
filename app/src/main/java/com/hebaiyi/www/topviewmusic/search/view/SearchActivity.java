package com.hebaiyi.www.topviewmusic.search.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.base.activity.PresenterActivity;
import com.hebaiyi.www.topviewmusic.bean.Music;
import com.hebaiyi.www.topviewmusic.bean.HotWord;
import com.hebaiyi.www.topviewmusic.bean.NetMusic;
import com.hebaiyi.www.topviewmusic.bean.SearchMerge;
import com.hebaiyi.www.topviewmusic.music.service.MusicManager;
import com.hebaiyi.www.topviewmusic.music.view.BottomFragment;
import com.hebaiyi.www.topviewmusic.search.contract.SearchContract;
import com.hebaiyi.www.topviewmusic.search.presenter.SearchPresenterImp;
import com.hebaiyi.www.topviewmusic.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class SearchActivity
        extends PresenterActivity<SearchContract.SearchView,
        SearchPresenterImp> implements SearchContract.SearchView {

    public static final int PAGE_SIZE = 10;

    public static final int SEARCH_RESET = 0X98CC;
    public static final int SEARCH_READD = 0XDDDD;

    private static final int FRAGMENT_HOT = 0;
    private static final int FRAGMENT_RESULT = 1;

    private boolean haveSoftInput = true;
    private int currFragment;
    private int currPager = 1;
    private int currState;
    private ImageButton mIbBack;
    private SearchView mSvSearch;
    private HotFragment mHotFragment;
    private ResultFragment mResultFragment;
    private String currQuery;
    private BottomFragment mBottomFragment;
    private MusicManager mManager;

    public static void actionStart(Context context) {
        Intent i = new Intent(context, SearchActivity.class);
        context.startActivity(i);
    }

    @Override
    protected SearchPresenterImp createPresenter() {
        return new SearchPresenterImp(this);
    }

    @Override
    protected void getBottomState(Music music) {
        mBottomFragment.setBottomSong(music);
    }

    @Override
    protected Music setBottomState() {
        return mBottomFragment.getBottomMusic();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        mBottomFragment = (BottomFragment) getSupportFragmentManager()
                .findFragmentById(R.id.search_fragment_bottom);
        mIbBack = findViewById(R.id.search_ib_back);
        mSvSearch = findViewById(R.id.search_sv_search);
    }

    @Override
    protected void initVariables() {
        mHotFragment = new HotFragment();
        mResultFragment = new ResultFragment();
        mManager = MusicManager.getInstance();
        EventBus.getDefault().register(mResultFragment);
    }

    public void loadMore() {
        currState = SEARCH_READD;
        obtainPresenter().obtainSearchMerge(currQuery, PAGE_SIZE, currPager++);
    }

    @Subscribe
    public void onEvent(SearchMerge.SongInfo info) {
        if (info == null) {
            return;
        }
        obtainPresenter().obtainNetMusic(info.getSongId());
    }

    @Override
    public void showNetMusic(NetMusic nm) {
        if (nm == null) {
            ToastUtil.showToast("无网络，暂时无法播放在线歌曲", Toast.LENGTH_SHORT);
            return;
        }
        Music music = nm.createMusic(true);
        mBottomFragment.setBottomSong(music);
        mManager.setSong(music.getUrls().get(0));
    }

    public void search(String query) {
        mSvSearch.setQuery(query, true);
    }

    @Override
    protected void loadData() {
        obtainPresenter().obtainHotWords();
    }

    @Override
    protected void initEvents() {
        mIbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSvSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                currQuery = query;
                currState = SEARCH_RESET;
                currPager = 1;
                obtainPresenter().obtainSearchMerge(query, currPager++, PAGE_SIZE);
                hideSoftInput();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mSvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSoftInput();
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (haveSoftInput) {
                hideSoftInput();
            } else {
                finish();
            }
        }
        return true;
    }


    @Override
    public void showHotWords(List<HotWord> hotWords) {
        Bundle data = new Bundle();
        data.putParcelableArrayList("hot_words", (ArrayList<? extends Parcelable>) hotWords);
        replaceFragment(mHotFragment, data);
    }

    @Override
    public void showSearchMerge(SearchContract.MergeSet ms) {
        Bundle data = new Bundle();
        data.putParcelable("MergeSet", ms);
        if (currFragment == FRAGMENT_HOT) {
            replaceFragment(mResultFragment, data);
            return;
        }
        if (currFragment == FRAGMENT_RESULT && currState == SEARCH_RESET) {
            if (data != null) {
                EventBus.getDefault().post(data);
            }
        }
        if (currFragment == FRAGMENT_RESULT && currState == SEARCH_READD) {
            if (data != null) {
                EventBus.getDefault().post(data);
            }
        }
    }

    public int obtainCurrState() {
        return currState;
    }

    private void replaceFragment(Fragment fragment, Bundle data) {
        if (fragment instanceof HotFragment) {
            currFragment = FRAGMENT_HOT;
        }
        if (fragment instanceof ResultFragment) {
            currFragment = FRAGMENT_RESULT;
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        fragment.setArguments(data);
        transaction.replace(R.id.search_flyt_fragment_place, fragment);
        transaction.commit();
    }

    private void showSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInputFromInputMethod(mSvSearch.getWindowToken(), 0);
        }
        haveSoftInput = true;
    }

    private void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(mSvSearch.getWindowToken(), 0);
        }
        haveSoftInput = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(mResultFragment);
    }
}
