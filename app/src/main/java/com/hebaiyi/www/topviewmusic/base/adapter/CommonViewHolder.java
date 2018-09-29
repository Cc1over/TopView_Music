package com.hebaiyi.www.topviewmusic.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

public class CommonViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;

    public CommonViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    /**
     * @param id 缓存View的唯一标识
     * @return 对应的View
     */
    public <T extends View> T getView(int id) {
        View view = mViews.get(id);
        if(view==null){
            view = itemView.findViewById(id);
            mViews.put(id,view);
        }
        return (T) view;
    }

}
