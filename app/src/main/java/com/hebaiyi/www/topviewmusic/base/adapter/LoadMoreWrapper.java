package com.hebaiyi.www.topviewmusic.base.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.util.NetUtil;

import java.util.List;

public class LoadMoreWrapper extends RecyclerView.Adapter {

    private static final int TYPE_BOTTOM = 0X1589;
    private static final int TYPE_NORMAL = 0X9412;

    private boolean isLoading = false;
    private boolean haveBottom = false;
    private BaseAdapter mInnerAdapter;
    private View mBottomView;
    private LoadMoreScrollListener mListener;
    private Context context;
    private Handler mHandler;

    public LoadMoreWrapper(BaseAdapter adapter, Context context) {
        mInnerAdapter = adapter;
        this.context = context;
        mHandler = new Handler();
    }

    /**
     * 添加底部加载监听
     *
     * @param listener 监听回调
     */
    public void addOnLoadMoreListener(LoadMoreScrollListener listener) {
        mListener = listener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            return mInnerAdapter.onCreateViewHolder(parent, viewType);
        }
        if (viewType == TYPE_BOTTOM) {
            if (mBottomView == null) {
                mBottomView = LayoutInflater.from(context)
                        .inflate(R.layout.bottom_layout, parent, false);
            }
            return new CommonViewHolder(mBottomView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            mInnerAdapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mInnerAdapter.getItemCount() + (haveBottom ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == this.getItemCount() - 1 && haveBottom) {
            return TYPE_BOTTOM;
        } else {
            return TYPE_NORMAL;
        }
    }

    /**
     * 设置数据源变化
     *
     * @param data 变化数据
     * @param <T>  数据类型
     * @return 设置是否成功
     */
    public <T> boolean setDataChange(List<T> data) {
        if (mBottomView != null) {
            final TextView tv = mBottomView.findViewById(R.id.tv_loading);
            ProgressBar pb = mBottomView.findViewById(R.id.pb_loading);
            if (!NetUtil.isNetWorkConnection(context)) {
                tv.setText("电波无法传达...");
                pb.setVisibility(View.GONE);
            } else if (data == null || data.size() == 0) {
                tv.setText("没有更多了...");
                pb.setVisibility(View.GONE);
            } else {
                tv.setText("正在加载...");
                pb.setVisibility(View.VISIBLE);
                tv.setVisibility(View.VISIBLE);
            }
        }
        boolean isSuccess = false;
        if (data != null) {
            isSuccess = mInnerAdapter.getData().addAll(data);
        }
        isLoading = false;
        haveBottom = false;
        notifyDataSetChanged();
        return isSuccess;
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 获取关键参数
                int totalItemCount = recyclerView.getAdapter().getItemCount();
                int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                int visibleItemCount = recyclerView.getChildCount();
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition == totalItemCount - 1
                        && visibleItemCount > 0
                        && !isLoading) {
                    // 设置标记
                    isLoading = true;
                    haveBottom = true;
                    // 刷新列表
                    notifyDataSetChanged();
                    // 加载更多
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onLoadMore();
                        }
                    }, 500);
                }
            }
        });

    }

    public interface LoadMoreScrollListener {
        void onLoadMore();
    }

}
