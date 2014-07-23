package com.rainbow.iap.entity.json;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

public class PurchaseOrderRequest implements JSONSerializable
{
	private static final Log logger = LogFactory.getLog(PurchaseOrderRequest.class);
	
	private PurchaseMethodType	_iapMethodType;
	private String			_productId;
	private String 			_customData;

	public PurchaseOrderRequest()
	{
	}
	
	public PurchaseOrderRequest(PurchaseMethodType iapMethodType, String productId, String customData)
	{
		_iapMethodType = iapMethodType;
		_productId = productId;
		_customData = customData;
	}
	
	public PurchaseMethodType getIAPMethodType()
	{
		return _iapMethodType;
	}
	
	public String getProductId()
	{
		return _productId;
	}
	
	public String getCustomData()
	{
		return _customData;
	}
	
	@Override
	public JSONObject marshal(JSONObject jsonObj) throws JSONException
	{
		jsonObj.put("iapMethodType", _iapMethodType.getValue());
		jsonObj.put("productId", _productId);
		jsonObj.put("customData", _customData);
		return jsonObj;
	}

	@Override
	public JSONObject unmarshal(JSONObject jsonObj) throws JSONException
	{
		_iapMethodType = PurchaseMethodType.getByValue(jsonObj.getInt("iapMethodType"));
		_productId = jsonObj.getString("productId");
		if (jsonObj.has("customData"))
		{
			_customData = jsonObj.getString("customData");
		}
		else
		{
			logger.error("customData not found!");
		}
		return jsonObj;
	}

}
