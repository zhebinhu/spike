package com.huzb.spike.redis;

/**
 * @author huzb
 * @version v1.0.0
 * @date 2018/9/22
 */
public class SpikeKey extends BasePrefix {
    private SpikeKey(String prefix) {
        super(prefix);
    }

    public static SpikeKey isGoodsOver = new SpikeKey("go");
}
