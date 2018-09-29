package com.hebaiyi.www.topviewmusic.local.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.base.activity.PermissionActivity;
import com.hebaiyi.www.topviewmusic.base.activity.PresenterActivity;
import com.hebaiyi.www.topviewmusic.bean.BottomMusic;
import com.hebaiyi.www.topviewmusic.bean.LocalMusic;
import com.hebaiyi.www.topviewmusic.local.adapter.LocalMusicListAdapter;
import com.hebaiyi.www.topviewmusic.local.contract.LocalMusicListContract;
import com.hebaiyi.www.topviewmusic.local.presenter.LocalMusicListPresenterImp;
import com.hebaiyi.www.topviewmusic.music.MusicManager;
import com.hebaiyi.www.topviewmusic.music.view.BottomFragment;
import com.hebaiyi.www.topviewmusic.widget.SidebarView;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
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
    private List<LocalMusic> mMusic;
    private int currPosition;
    private MusicManager mManager = MusicManager.getInstance();

    public static void actionStart(Context context) {
        Intent i = new Intent(context, LocalMusicListActivity.class);
        context.startActivity(i);
    }

    @Override
    protected LocalMusicListPresenterImp createPresenter() {
        return new LocalMusicListPresenterImp(this);
    }

    @Override
    protected void getBottomState(BottomMusic music) {
        mBottomFragment.setBottomSong(music);
    }

    @Override
    protected BottomMusic setBottomState() {
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
                char ch = musics.get(i).getTitle().charAt(0);
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
        mMusic = musics;
        final LocalMusicListAdapter adapter = new LocalMusicListAdapter(musics);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        mRcvContent.setLayoutManager(manager);
        mRcvContent.setAdapter(adapter);
        obtainFirstWord(musics);
        adapter.setLocalMusicListListener(new LocalMusicListAdapter.LocalMusicListListener() {
            @Override
            public void onClick(int position) {
                currPosition = position;
                mManager.setSong(setBottomSong(position).getPlayUrl());
                mManager.start();
            }
        });
        mManager.setOnMusicCompleteListener(new MusicManager.OnMusicCompleteListener() {
            @Override
            public void onComplete() {
                if (currPosition + 1 == mMusic.size()) {
                    currPosition = 0;
                }
                mManager.setSong(setBottomSong(currPosition + 1).getPlayUrl());
                mManager.start();
            }
        });
    }

    private BottomMusic setBottomSong(int position) {
        LocalMusic lm = mMusic.get(position);
        BottomMusic bm = new BottomMusic(lm.getAlbumPic(), lm.getTitle(),
                lm.getArtist(), true, lm.getUrl());
        mBottomFragment.setBottomSong(bm);
        return bm;
    }

    @Override
    protected void initVariables() {

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
    }


}