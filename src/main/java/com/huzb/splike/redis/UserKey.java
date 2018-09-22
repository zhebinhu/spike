package com.huzb.splike.redis;

/**
 * @author huzb
 * @version v1.0.0
 * @date 2018/9/22
 */
public class UserKey extends BasePrefix{

    private UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
}
