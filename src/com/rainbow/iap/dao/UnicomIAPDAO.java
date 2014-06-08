package com.rainbow.iap.dao;

import com.rainbow.iap.entity.UnicomIAPInfo;

public interface UnicomIAPDAO
{
	public void save(UnicomIAPInfo unicomIAPInfo);
	public void update(UnicomIAPInfo unicomIAPInfo);
	public UnicomIAPInfo getByOrderId(String orderId);
}
