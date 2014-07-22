package com.rainbow.iap.dao;

import com.rainbow.iap.entity.Order;

public interface OrderDAO
{
	public void save(Order entity);
	public Order getByOrderId(String orderId);
	public void remove(long id);
}
