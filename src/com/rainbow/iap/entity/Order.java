package com.rainbow.iap.entity;

import java.sql.Timestamp;

public class Order
{
	private long id;
	private String orderId;
	private String productId;
	private String customData;
	private Timestamp orderTime;
	
	public Order()
	{
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getOrderId()
	{
		return orderId;
	}

	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}
	
	public String getCustomData()
	{
		return customData;
	}

	public void setCustomData(String customData)
	{
		this.customData = customData;
	}

	public Timestamp getOrderTime()
	{
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime)
	{
		this.orderTime = orderTime;
	}
}
