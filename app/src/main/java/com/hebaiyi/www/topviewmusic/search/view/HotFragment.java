package com.hebaiyi.www.topviewmusic.search.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.bean.HotWord;
import com.hebaiyi.www.topviewmusic.widget.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class HotFragment extends Fragment {

    private FlowLayout mFlytHotWords;
    private SearchActivity mParentActivity;
    private View mView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParentActivity = (SearchActivity) getActivity();
    }

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragement_hot, container, false);
        mFlytHotWords = mView.findViewById(R.id.hot_flow_lyt_hotWords);
        List<HotWord> hotWords = getArguments().getParcelableArrayList("hot_words");
        initRecyclerView(hotWords);
        return mView;
    }

    private void initRecyclerView( List<HotWord> hotWords){
        List<String> tags = new ArrayList<>();
        for (int i = 0; i < hotWords.size(); i++) {
            tags.add(hotWords.get(i).getWord());
        }
        mFlytHotWords.setFlowLayout(tags, new FlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(String content) {
                mParentActivity.search(content);
            }
        });
    }


}
