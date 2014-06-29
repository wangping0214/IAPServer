package com.rainbow.iap.entity.json;

import org.json.JSONException;
import org.json.JSONObject;

public interface JSONSerializable
{
	public JSONObject marshal(JSONObject jsonObj) throws JSONException;
	public JSONObject unmarshal(JSONObject jsonObj) throws JSONException;
}
