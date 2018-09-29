package com.hebaiyi.www.topviewmusic.search.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.hebaiyi.www.topviewmusic.base.presenter.BasePresenter;
import com.hebaiyi.www.topviewmusic.bean.HotWord;
import com.hebaiyi.www.topviewmusic.bean.SearchMerge;
import com.hebaiyi.www.topviewmusic.search.contract.SearchContract;
import com.hebaiyi.www.topviewmusic.search.model.SearchModel;

import java.lang.ref.WeakReference;
import java.util.List;

public class SearchPresenterImp
        extends BasePresenter<SearchContract.SearchView>
        implements SearchContract.SearchPresenter {

    private static final int OBTAIN_HOTWORD_SUCCESS = 0X5656;
    private static final int OBTAIN_HOTWORD_FAIL = 0X7487;

    private static final int OBTAIN_SEARCHMERGE_SUCCESS = 0XCCCC;
    private static final int OBTAIN_SEARCHMERGE_FAIL = 0XDDDD;

    private SearchContract.SearchView mView;
    private SearchModel mModel;
    private SearchHandler mHandler;

    public SearchPresenterImp(SearchContract.SearchView view){
        mView = view;
        mModel = new SearchModel();
        mHandler = new SearchHandler(this);
    }

    @Override
    public void obtainHotWords() {
        mModel.loadHotWord(new SearchModel.HotWordCallback() {
            @Override
            public void onSuccess(List<HotWord> hotWords) {
                Message msg = Message.obtain();
                msg.what = OBTAIN_HOTWORD_SUCCESS;
                msg.obj = hotWords;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFail(Exception e) {
                Message msg = Message.obtain();
                msg.what = OBTAIN_HOTWORD_FAIL;
                mHandler.sendMessage(msg);
            }
        });
    }

    @Override
    public void obtainSearchMerge(String query, int pageNo, int pageSize) {
        mModel.loadSearchMerge(query, pageNo, pageSize, new SearchModel.SearchMergeCallback() {
            @Override
            public void onSuccess(List<SearchMerge.AlbumInfo> ais,
                                  List<SearchMerge.ArtistInfo> ars,
                                  List<SearchMerge.SongInfo> sis) {
                Message msg = Message.obtain();
                msg.what = OBTAIN_SEARCHMERGE_SUCCESS;
                msg.obj = new SearchContract.MergeSet(ais,ars,sis);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFail(Exception e) {
                e.printStackTrace();
                Message msg = Message.obtain();
                msg.what = OBTAIN_SEARCHMERGE_FAIL;
                mHandler.sendMessage(msg);
            }
        });
    }


    private static class SearchHandler extends Handler {

        private WeakReference<SearchPresenterImp> spRef;

        private SearchHandler(SearchPresenterImp imp) {
            spRef = new WeakReference<>(imp);
        }

        @Override
        public void handleMessage(Message msg) {
            SearchPresenterImp sp = spRef.get();
            if(sp==null){
                return;
            }
            SearchContract.SearchView sv = sp.mView;
            switch (msg.what) {
                case OBTAIN_HOTWORD_SUCCESS:
                    sv.showHotWords((List<HotWord>) msg.obj);
                    break;
                case OBTAIN_HOTWORD_FAIL:
                    sv.showHotWords(null);
                    break;

                case OBTAIN_SEARCHMERGE_SUCCESS:
                    sv.showSearchMerge((SearchContract.MergeSet) msg.obj);
                    break;
                case OBTAIN_SEARCHMERGE_FAIL:
                    sv.showSearchMerge(null);
                    break;
            }
        }
    }


}
