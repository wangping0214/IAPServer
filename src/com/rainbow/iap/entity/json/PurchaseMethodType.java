package com.rainbow.iap.entity.json;

public enum PurchaseMethodType
{
	PURCHASE_METHOD_TYPE_CHINA_UNICOM(0),
	PURCHASE_METHOD_TYPE_ALIPAY(1),
	PURCHASE_METHOD_TYPE_UNION_PAY(2),
	;
	
	private int _value;
	
	private PurchaseMethodType(int value)
	{
		_value = value;
	}
	
	public int getValue()
	{
		return _value;
	}
	
	public static PurchaseMethodType getByValue(int value)
	{
		for (PurchaseMethodType type : PurchaseMethodType.values())
		{
			if (type.getValue() == value)
			{
				return type;
			}
		}
		return null;
	}
}
