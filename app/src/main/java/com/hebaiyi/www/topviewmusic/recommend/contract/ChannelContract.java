package com.hebaiyi.www.topviewmusic.recommend.contract;

import com.hebaiyi.www.topviewmusic.bean.Channel;

import java.util.List;

public class ChannelContract {

    public interface ChannelView {
         void showChannel(List<Channel>channels);
    }

    public interface ChannelPresenter {
         void obtainChannel(int pageNo,int pageSize);
    }

}
