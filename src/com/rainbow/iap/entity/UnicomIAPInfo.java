package com.rainbow.iap.entity;

import java.sql.Timestamp;

public class UnicomIAPInfo
{
	private long		id;
	private String		orderId;
	private Timestamp	orderTime;
	private String		cpId;
	private String		consumeCode;
	private int			orderResult;
	private int			orderStatus;
	
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
	public Timestamp getOrderTime()
	{
		return orderTime;
	}
	public void setOrderTime(Timestamp orderTime)
	{
		this.orderTime = orderTime;
	}
	public String getCpId()
	{
		return cpId;
	}
	public void setCpId(String cpId)
	{
		this.cpId = cpId;
	}
	public String getConsumeCode()
	{
		return consumeCode;
	}
	public void setConsumeCode(String consumeCode)
	{
		this.consumeCode = consumeCode;
	}
	public int getOrderResult()
	{
		return orderResult;
	}
	public void setOrderResult(int orderResult)
	{
		this.orderResult = orderResult;
	}
	public int getOrderStatus()
	{
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus)
	{
		this.orderStatus = orderStatus;
	}
}
