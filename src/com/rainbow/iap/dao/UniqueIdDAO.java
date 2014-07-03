package com.rainbow.iap.dao;

public interface UniqueIdDAO
{
	public void initialize();
	public long getNextOrderSeq(String cpId, String appId);
	public void destroy();
}
