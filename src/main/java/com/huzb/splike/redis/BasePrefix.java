package com.huzb.splike.redis;

/**
 * @author huzb
 * @version v1.0.0
 * @date 2018/9/22
 */
public abstract class BasePrefix implements KeyPrefix {
    private int expireSeconds;
    private String prefix;

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    public BasePrefix(String prefix) {
        this(0, prefix);
    }


    @Override
    public int expireSeconds() {//默认0代表永不过期
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
