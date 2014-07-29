package com.rainbow.iap.entity;

public class AppInfo
{
	private long id;
	private String notifyUrl;
	private String md5Key;
	
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

	public String getMd5Key()
	{
		return md5Key;
	}

	public void setMd5Key(String md5Key)
	{
		this.md5Key = md5Key;
	}
}
