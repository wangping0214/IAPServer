package com.rainbow.iap.entity;

import java.sql.Timestamp;

public class UnionPayIAPInfo
{
	private long		id;
	private String 		tradeType;
	private String 		tradeId;
	private String 		tradeAmount;
	private String 		tradeCurrency;
	private Timestamp 	tradeTime;
	private String		tradeNote;
	private int			tradeStatus;
	
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public String getTradeType()
	{
		return tradeType;
	}
	public void setTradeType(String tradeType)
	{
		this.tradeType = tradeType;
	}
	public String getTradeId()
	{
		return tradeId;
	}
	public void setTradeId(String tradeId)
	{
		this.tradeId = tradeId;
	}
	public String getTradeAmount()
	{
		return tradeAmount;
	}
	public void setTradeAmount(String tradeAmount)
	{
		this.tradeAmount = tradeAmount;
	}
	public String getTradeCurrency()
	{
		return tradeCurrency;
	}
	public void setTradeCurrency(String tradeCurrency)
	{
		this.tradeCurrency = tradeCurrency;
	}
	public Timestamp getTradeTime()
	{
		return tradeTime;
	}
	public void setTradeTime(Timestamp tradeTime)
	{
		this.tradeTime = tradeTime;
	}
	public String getTradeNote()
	{
		return tradeNote;
	}
	public void setTradeNote(String tradeNote)
	{
		this.tradeNote = tradeNote;
	}
	public int getTradeStatus()
	{
		return tradeStatus;
	}
	public void setTradeStatus(int tradeStatus)
	{
		this.tradeStatus = tradeStatus;
	}
}
