package com.rainbow.iap.dao;

import com.rainbow.iap.entity.Receipt;

public interface ReceiptDAO
{
	public void save(Receipt receipt);
	public void notify(Receipt receipt);
}
