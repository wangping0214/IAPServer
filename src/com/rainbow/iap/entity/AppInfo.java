package com.rainbow.iap.entity;

public class AppInfo
{
	private long id;
	private String notifyUrl;
	
	public AppInfo()
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
	public String getNotifyUrl()
	{
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl)
	{
		this.notifyUrl = notifyUrl;
	}
}
