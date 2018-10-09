package com.hebaiyi.www.topviewmusic.util;

import com.hebaiyi.www.topviewmusic.bean.Lyrics;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class LyricsDecoder {

    private List<Lyrics> mLyricses;
    private long time;

    public LyricsDecoder() {
        mLyricses = new ArrayList<>();
    }

    public List<Lyrics> decodeLyrics(String lrcStr) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(lrcStr.getBytes
                        (Charset.forName("utf8"))), Charset.forName("utf8")));
        String line;
        while ((line = br.readLine()) != null) {
            decodeLine(line);
        }
        return mLyricses;
    }

    private void decodeLine(String str) {
        Lyrics lyrics = new Lyrics();
        if (str.startsWith("[ti:")) {
            lyrics.setContent(str.substring(4, str.lastIndexOf("]")));
            lyrics.setTime(time++);
        } else if (str.startsWith("[ar:")) {
            lyrics.setContent(str.substring(4, str.lastIndexOf("]")));
            lyrics.setTime(time++);
        } else if (str.startsWith("[by:")) {
            lyrics.setContent(str.substring(4, str.lastIndexOf("]")));
            lyrics.setTime(time++);
        } else if (str.startsWith("[al:")) {
            lyrics.setContent(str.substring(4, str.lastIndexOf("]")));
            lyrics.setTime(time++);
        } else if (str.startsWith("[offset:")) {
            lyrics.setContent(str.substring(4, str.lastIndexOf("]")));
            lyrics.setTime(time++);
        } else if (str.startsWith("[key:")) {
            lyrics.setContent(str.substring(4, str.lastIndexOf("]")));
            lyrics.setTime(time++);
        } else {
            int startIndex = -1;
            while ((startIndex = str.indexOf("[", startIndex + 1)) != -1) {
                int endIndex = str.indexOf("]", startIndex + 1);
                Long time = analyzeTime(str.substring(startIndex + 1, endIndex));
                lyrics.setContent(str.substring(str.lastIndexOf("]") + 1, str.length()));
                lyrics.setStrTime(timeMode(time) + "");
                lyrics.setTime(time);
            }
            mLyricses.add(lyrics);
        }
    }

    public Long analyzeTime(String str) {
        int m = Integer.parseInt(str.substring(0, str.indexOf(":")));
        int s, ms = 0;
        if (str.contains(".")) {
            s = Integer.parseInt(str.substring(str.indexOf(":") + 1, str.indexOf(".")));
            ms = Integer.parseInt(str.substring(str.indexOf(".") + 1, str.length()));
        } else {
            s = Integer.parseInt(str.substring(str.indexOf(":") + 1, str.length()));
        }
        return (long) (m * 60000 + s * 1000 + ms * 10);
    }

    private String timeMode(long time) {
        StringBuilder builder = new StringBuilder();
        long tmp = (time / 1000) % 60;
        if (time / 60000 < 10) {
            builder.append("0").append(time / 60000).append(":");
        } else {
            builder.append(time / 60000).append(":");
        }
        if (tmp < 10) {
            builder.append("0").append(tmp);
        } else {
            builder.append(tmp);
        }
        return builder.toString();
    }

}