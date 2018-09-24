package com.huzb.splike.validator;

import java.util.UUID;

/**
 * @author huzb
 * @version v1.0.0
 * @date 2018/9/24
 */
public class UUIDUtil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
