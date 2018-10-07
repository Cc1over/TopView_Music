package com.hebaiyi.www.topviewmusic.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;

public class LrcUtil {

    public static HashMap<String, String> decodeLyrics(String lrcStr) throws IOException {
        HashMap<String, String> lrcMap = new HashMap<>();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(lrcStr.getBytes
                        (Charset.forName("utf8"))), Charset.forName("utf8")));
        String line;
        while ((line = br.readLine()) != null) {
            decodeLine(line,lrcMap);
        }
        return lrcMap;
    }

    private static void decodeLine(String str, HashMap<String, String> lrcMap) {
        if (str.startsWith("[ti:")) {
            lrcMap.put("ti", str.substring(4, str.lastIndexOf("]")));
        } else if (str.startsWith("[ar:")) {
            lrcMap.put("ar", str.substring(4, str.lastIndexOf("]")));
        } else if (str.startsWith("[by:")) {
            lrcMap.put("by", str.substring(4, str.lastIndexOf("]")));
        } else if (str.startsWith("[la:")) {
            lrcMap.put("la", str.substring(4, str.lastIndexOf("]")));
        } else {
            int startIndex = -1;
            while ((startIndex = str.indexOf("[", startIndex + 1)) != -1) {
                int endIndex = str.indexOf("]", startIndex + 1);
                lrcMap.put(strToLongToTime(str.substring(startIndex + 1,
                        endIndex)) + "", str.substring(
                        str.lastIndexOf("]") + 1, str.length()));
            }
        }
    }

    private static String strToLongToTime(String str) {
        int m = Integer.parseInt(str.substring(0, str.indexOf(":")));
        int s, ms = 0;
        if (str.contains(".")) {
            s = Integer.parseInt(str.substring(str.indexOf(":")) + 1, str.indexOf("."));
            ms = Integer.parseInt(str.substring(str.indexOf(".")) + 1, str.length());
        } else {
            s = Integer.parseInt(str.substring(str.indexOf(":")) + 1, str.length());
        }
        return timeMode(m * 60000 + s * 1000 + ms * 10);
    }

    private static String timeMode(int time) {
        StringBuilder builder = new StringBuilder();
        int tmp = (time / 1000) % 60;
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