// IMusicManager.aidl
package com.hebaiyi.www.topviewmusic.music;

// Declare any non-default types here with import statements

interface IMusicManager {

    void setSong(String songUrl);
    void start();
    void pause();
    void stop();
    void setCurrTime(int currTime);

}
