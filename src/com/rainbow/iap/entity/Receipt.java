package com.rainbow.iap.entity;

import java.sql.Timestamp;

public class Receipt
{
	private long id;
	private String cpId;
	private String appId;
	private String productId;
	private String orderId;
	private String customData;
	private Timestamp receiptTime;
	private double price;
	private int hasNotify;
	
	public Receipt()
	{
		hasNotify = 0;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getCpId()
	{
		return cpId;
	}

	public void setCpId(String cpId)
	{
		this.cpId = cpId;
	}

	public String getAppId()
	{
		return appId;
	}

	public void setAppId(String appId)
	{
		this.appId = appId;
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}
	
	public String getOrderId()
	{
		return orderId;
	}

	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

	public String getCustomData()
	{
		return customData;
	}

	public void setCustomData(String customData)
	{
		this.customData = customData;
	}

	public Timestamp getReceiptTime()
	{
		return receiptTime;
	}

	public void setReceiptTime(Timestamp receiptTime)
	{
		this.receiptTime = receiptTime;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public int getHasNotify()
	{
		return hasNotify;
	}

	public void setHasNotify(int hasNotify)
	{
		this.hasNotify = hasNotify;
	}
}
