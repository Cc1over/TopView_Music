package com.hebaiyi.www.topviewmusic.util;

import java.util.Random;

public class MatheUtil {

    public static int createRandomNumber(int min,int max){
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }

}
