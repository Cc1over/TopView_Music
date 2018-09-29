package com.hebaiyi.www.topviewmusic.recommend.contract;

import com.hebaiyi.www.topviewmusic.bean.Billboard;

import java.util.List;

public class BillboardContract {

    public interface BillboardView {
        void showBillboard(List<Billboard> billboards);
    }

    public interface BillboardPresenter {
        void obtainBillboard();
    }


}
