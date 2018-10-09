// IMusicManager.aidl
package com.hebaiyi.www.topviewmusic.music;

interface IMusicManager {

    void setSong(String songUrl);
    void start();
    void pause();
    void stop();
    void setCurrTime(int currTime);
    int getCurrentPosition();

}
