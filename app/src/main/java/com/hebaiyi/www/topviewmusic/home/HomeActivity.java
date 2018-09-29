package com.hebaiyi.www.topviewmusic.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.base.activity.BaseActivity;
import com.hebaiyi.www.topviewmusic.base.activity.BottomActivity;
import com.hebaiyi.www.topviewmusic.base.adapter.FragmentAdapter;
import com.hebaiyi.www.topviewmusic.bean.BottomMusic;
import com.hebaiyi.www.topviewmusic.local.view.LocalFragment;
import com.hebaiyi.www.topviewmusic.music.view.BottomFragment;
import com.hebaiyi.www.topviewmusic.recommend.view.SelectionFragment;
import com.hebaiyi.www.topviewmusic.search.view.SearchActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BottomActivity implements View.OnClickListener {

    private Toolbar mTbTitle;
    private ImageButton mIbSearch;
    private TabLayout mTabLytSelection;
    private ViewPager mVpContent;
    private BottomFragment mBottomFragment;
    private DrawerLayout mDrawerLytSlidingMenu;
    private LinearLayout mLlytMsg;
    private LinearLayout mLlytVip;
    private LinearLayout mLlytShop;
    private LinearLayout mLlytGame;
    private LinearLayout mLlytFree;
    private LinearLayout mLlytLocal;
    private LinearLayout mLlytKnow;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        // 初始化BottomFragment
        mBottomFragment = (BottomFragment)
                getSupportFragmentManager().findFragmentById(R.id.home_fragment_bottom);
        // 初始化toolbar
        mTbTitle = findViewById(R.id.home_tb_title);
        initToolbar(mTbTitle, R.drawable.titlebar_menu);
        // 初始化TabLayout
        mTabLytSelection = findViewById(R.id.home_tab_layout_selection);
        mVpContent = findViewById(R.id.home_vp_content);
        initViewPagerAndTabLayout();
        // 初始化其他控件
        mIbSearch = findViewById(R.id.home_ib_search);
        mDrawerLytSlidingMenu = findViewById(R.id.home_drawer_layout_skid_menu);
        mLlytMsg = findViewById(R.id.home_llyt_msg);
        mLlytVip = findViewById(R.id.home_llyt_vip);
        mLlytShop = findViewById(R.id.home_llyt_shop);
        mLlytGame = findViewById(R.id.home_llyt_game);
        mLlytFree = findViewById(R.id.home_llyt_free);
        mLlytLocal = findViewById(R.id.home_llyt_local);
        mLlytKnow = findViewById(R.id.home_llyt_know);

        mLlytMsg.setOnClickListener(this);
        mLlytVip.setOnClickListener(this);
        mLlytShop.setOnClickListener(this);
        mLlytGame.setOnClickListener(this);
        mLlytFree.setOnClickListener(this);
        mLlytLocal.setOnClickListener(this);
        mLlytKnow.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initEvents() {
        // 查询按钮
        mIbSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 查询操作
                SearchActivity
                        .actionStart(HomeActivity.this);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // 开启滑动菜单
                mDrawerLytSlidingMenu.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    /**
     * 初始化TabLayout和ViewPager
     */
    private void initViewPagerAndTabLayout() {
        List<Fragment> fragments = new ArrayList<>();
        LocalFragment lf = new LocalFragment();
        SelectionFragment sf = new SelectionFragment();
        fragments.add(lf);
        fragments.add(sf);
        FragmentAdapter adapter = new FragmentAdapter(fragments, getSupportFragmentManager());
        mVpContent.setAdapter(adapter);
        // 关联TabLayout和ViewPager
        mTabLytSelection.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(mVpContent));
        mVpContent.setOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(mTabLytSelection));
        // 设置viewPager滑动监听
        mVpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position,
                                       float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mTabLytSelection.getTabAt(0).setIcon(R.drawable.titlebar_music_white);
                        mTabLytSelection.getTabAt(1).setIcon(R.drawable.titlebar_discover_grey);
                        mTabLytSelection.getTabAt(2).setIcon(R.drawable.titlebar_read_grey);
                        break;
                    case 1:
                        mTabLytSelection.getTabAt(0).setIcon(R.drawable.titlebar_music_grey);
                        mTabLytSelection.getTabAt(1).setIcon(R.drawable.titlebar_discover_white);
                        mTabLytSelection.getTabAt(2).setIcon(R.drawable.titlebar_read_grey);
                        break;
                    case 2:
                        mTabLytSelection.getTabAt(0).setIcon(R.drawable.titlebar_music_grey);
                        mTabLytSelection.getTabAt(1).setIcon(R.drawable.titlebar_discover_grey);
                        mTabLytSelection.getTabAt(2).setIcon(R.drawable.titlebar_read_white);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_llyt_msg:
                break;
            case R.id.home_llyt_vip:
                break;
            case R.id.home_llyt_shop:
                break;
            case R.id.home_llyt_game:
                break;
            case R.id.home_llyt_free:
                break;
            case R.id.home_llyt_local:
                break;
            case R.id.home_llyt_know:
                break;
            default:
                break;
        }
    }
}
