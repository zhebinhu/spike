package com.huzb.spike.redis;

/**
 * @author huzb
 * @version v1.0.0
 * @date 2018/9/22
 */
public class SpikeKey extends BasePrefix {

    private SpikeKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static SpikeKey isGoodsOver = new SpikeKey(0, "go");
    public static SpikeKey getSpikePath = new SpikeKey(60, "mp");
    public static SpikeKey getSpikeVerifyCode = new SpikeKey(300, "vc");
}
