package com.rainbow.iap.entity;

public class Product
{
	private long id;
	private String cpId;
	private String appId;
	private String productId;
	private String name;
	private String description;
	
	private double price;
	private String unicomProductId;
	private String unicomConsumeCode;
	
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
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public String getUnicomProductId()
	{
		return unicomProductId;
	}

	public void setUnicomProductId(String unicomProductId)
	{
		this.unicomProductId = unicomProductId;
	}

	public String getUnicomConsumeCode()
	{
		return unicomConsumeCode;
	}

	public void setUnicomConsumeCode(String unicomConsumeCode)
	{
		this.unicomConsumeCode = unicomConsumeCode;
	}
}
