package com.hebaiyi.www.topviewmusic.local.contract;

import com.hebaiyi.www.topviewmusic.bean.HotGeDan;

import java.util.List;

public class LocalContract {

    public interface LocalView {

        void showHotGeDan(List<HotGeDan> hotGeDans);
    }

    public interface LocalPresenter {

        void obtainHotGeDan(int num);

    }

}
