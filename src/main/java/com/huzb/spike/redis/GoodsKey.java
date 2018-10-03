package com.huzb.spike.redis;

/**
 * @author huzb
 * @version v1.0.0
 * @date 2018/9/22
 */
public class GoodsKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 60;

    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static GoodsKey goodsList = new GoodsKey(TOKEN_EXPIRE, "gl");
    public static GoodsKey goodsDetail = new GoodsKey(TOKEN_EXPIRE, "gd");
}
