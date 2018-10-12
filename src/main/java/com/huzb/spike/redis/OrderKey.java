package com.huzb.spike.redis;

public class OrderKey extends BasePrefix {

	public OrderKey(String prefix) {
		super(prefix);
	}
	public static OrderKey getSpikeOrderByUidGid = new OrderKey("soug");
}
