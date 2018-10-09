package com.hebaiyi.www.topviewmusic.local.view;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.base.activity.PermissionActivity;
import com.hebaiyi.www.topviewmusic.base.activity.PresenterActivity;
import com.hebaiyi.www.topviewmusic.bean.LocalMusic;
import com.hebaiyi.www.topviewmusic.bean.Music;
import com.hebaiyi.www.topviewmusic.local.adapter.LocalMusicListAdapter;
import com.hebaiyi.www.topviewmusic.local.contract.LocalMusicListContract;
import com.hebaiyi.www.topviewmusic.local.presenter.LocalMusicListPresenterImp;
import com.hebaiyi.www.topviewmusic.music.service.MusicManager;
import com.hebaiyi.www.topviewmusic.music.view.BottomFragment;
import com.hebaiyi.www.topviewmusic.music.view.MusicActivity;
import com.hebaiyi.www.topviewmusic.util.MatheUtil;
import com.hebaiyi.www.topviewmusic.widget.SidebarView;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class LocalMusicListActivity
        extends PresenterActivity<LocalMusicListContract.
        LocalMusicListView, LocalMusicListPresenterImp>
        implements LocalMusicListContract.LocalMusicListView {

    private RecyclerView mRcvContent;
    private Toolbar mTbTitle;
    private SidebarView mSidebarView;
    private List<String> mFirstWords;
    private BottomFragment mBottomFragment;
    private List<LocalMusic> mMusics;
    private int currPosition;
    private MusicManager mManager = MusicManager.getInstance();
    private MusicReceiver mReceiver;
    private MusicManager.MusicObserver mObserver;

    public static void actionStart(Context context) {
        Intent i = new Intent(context, LocalMusicListActivity.class);
        context.startActivity(i);
    }

    @Override
    protected LocalMusicListPresenterImp createPresenter() {
        return new LocalMusicListPresenterImp(this);
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
        setContentView(R.layout.activity_local_music_list);
        mRcvContent = findViewById(R.id.local_music_list_rcv_content);
        mTbTitle = findViewById(R.id.local_music_list_tb_title);
        mSidebarView = findViewById(R.id.local_music_list_sidebar_view);
        initToolbar(mTbTitle, R.drawable.back_icon);
        mTbTitle.setTitle("本地音乐");
        mBottomFragment = (BottomFragment) getSupportFragmentManager()
                .findFragmentById(R.id.local_music_list_fragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void obtainFirstWord(List<LocalMusic> musics) {
        mFirstWords = new ArrayList<>(musics.size());
        //1.创建一个格式化输出对象
        HanyuPinyinOutputFormat outputF = new HanyuPinyinOutputFormat();
        //2.设置好格式
        outputF.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        outputF.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        try {
            for (int i = 0; i < musics.size(); i++) {
                char ch = musics.get(i).getName().charAt(0);
                String[] firstWord = PinyinHelper.toHanyuPinyinStringArray(ch, outputF);
                if (firstWord != null) {
                    mFirstWords.add(firstWord[0].charAt(0) + "");
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
            mFirstWords.add("#");
        }
    }

    @Override
    public void showLocalMusicList(List<LocalMusic> musics) {
        if (musics == null) {
            return;
        }
        mMusics = musics;
        final LocalMusicListAdapter adapter = new LocalMusicListAdapter(musics);
        final LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        mRcvContent.setLayoutManager(manager);
        mRcvContent.setAdapter(adapter);
        obtainFirstWord(musics);
        adapter.setLocalMusicListListener(new LocalMusicListAdapter.LocalMusicListListener() {
            @Override
            public void onClick(int position) {
                if (mMusics.size() == 0) {
                    return;
                }
                if (currPosition == position && currPosition != 0) {
                    MusicActivity.actionStart(
                            LocalMusicListActivity.this, setBottomSong(currPosition));
                } else {
                    currPosition = position;
                    mManager.setSong(setBottomSong(position).getUrl());
                }
            }
        });

    }

    private void listLoop() {
        if (currPosition + 1 == mMusics.size()) {
            currPosition = 0;
            postMusic(currPosition);
            return;
        }
        postMusic(++currPosition);
    }

    private void singleLoop() {
        postMusic(currPosition);
    }

    private void shufflePlayback() {
        currPosition = MatheUtil.createRandomNumber(0, mMusics.size() - 1);
        postMusic(currPosition);
    }

    private void postMusic(int position) {
        Music music = setBottomSong(position);
        mManager.setSong(music.getUrl());
        EventBus.getDefault().postSticky(music);
    }

    private Music setBottomSong(int position) {
        LocalMusic lm = mMusics.get(position);
        Music m = lm.createMusic(true);
        mBottomFragment.setBottomSong(m);
        return m;
    }

    private void registered() {
        mReceiver = new MusicReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MusicActivity.RECEIVER_ACTION);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void initVariables() {
        registered();
        mObserver = new MusicManager.MusicObserver() {
            @Override
            public void OnPrepare() {

            }

            @Override
            public void onComplete() {
                nextSong(false);
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        mManager.detach(mObserver);
    }

    @Override
    protected void loadData() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            PermissionActivity.actionStart(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
        } else {
            obtainPresenter().obtainLocalMusicList(getApplicationContext());
        }
    }

    private void nextSong(boolean manual) {
        switch (MusicActivity.currMode) {
            case MusicActivity.MODE_LIST_LOOP:
                listLoop();
                break;
            case MusicActivity.MODE_SINGER_LOOP:
                if (manual) {
                    listLoop();
                } else {
                    singleLoop();
                }
                break;
            case MusicActivity.MODE_SHUFFLE_PLAYBACK:
                shufflePlayback();
                break;
            default:
                listLoop();
                break;
        }
    }


    @Override
    protected void initEvents() {
        mSidebarView.setOnSidebarViewListener(new SidebarView.OnSidebarViewListener() {
            @Override
            public void onSelectItem(int index, String value) {
                for (int i = 0; i < mFirstWords.size(); i++) {
                    if (mFirstWords.get(i).equals(value)) {
                        mRcvContent.scrollToPosition(i);
                        break;
                    }
                }
            }
        });
        mManager.attach(mObserver);
    }

    private class MusicReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int operate = intent.getIntExtra("operate", -1);
            if (operate != -1) {
                if (operate == MusicActivity.NEXT_SONG) {
                    nextSong(true);
                }
                if (operate == MusicActivity.PAST_SONG) {
                    if (currPosition - 1 < 0) {
                        currPosition = mMusics.size() - 1;
                        postMusic(currPosition);
                        return;
                    }
                    postMusic(--currPosition);
                }
            }
        }
    }

}
