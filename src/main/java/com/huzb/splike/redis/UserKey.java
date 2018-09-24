package com.huzb.splike.redis;

/**
 * @author huzb
 * @version v1.0.0
 * @date 2018/9/22
 */
public class UserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    private UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static UserKey token = new UserKey(TOKEN_EXPIRE, "tk");
}
