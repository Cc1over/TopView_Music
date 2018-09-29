package com.hebaiyi.www.topviewmusic.util;

import java.util.Collection;
import java.util.List;

public class CollectionUtil {

    public static boolean isEmpty(Collection c) {
        if (c == null) {
            return true;
        }
        return c.size() == 0;
    }


}
