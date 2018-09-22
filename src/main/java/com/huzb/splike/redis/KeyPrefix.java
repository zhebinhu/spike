package com.huzb.splike.redis;

/**
 * @author huzb
 * @version v1.0.0
 * @date 2018/9/22
 */
public interface KeyPrefix {
    public int expireSeconds();
    public String getPrefix();
}
