package com.rainbow.iap.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import com.rainbow.iap.dao.impl.UnicomIAPDAOImpl;
import com.rainbow.iap.entity.UnicomIAPInfo;
import com.rainbow.iap.util.IAPDateFormatter;

@Path("/ChinaUnicomIAPService")
public class ChinaUnicomIAPService
{
	private static final String VALIDATE_ORDER_ID 	= "validateorderid";
	private static final String NOTIFY_RESULT		= "notifyresult";
	
	@POST
    @Path("/request/post/{serviceId}")
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	public JSONObject postValidateOrderId(@PathParam("serviceId") String serviceId, 
			JSONObject request)
	{
		if (VALIDATE_ORDER_ID.equals(serviceId))
		{
			try
			{
				return validateOrderId(request);
			} catch (JSONException e)
			{
				e.printStackTrace();
			}
		}
		else if (NOTIFY_RESULT.equals(serviceId))
		{
			try
			{
				return notifyResult(request);
			} catch (JSONException e)
			{
				e.printStackTrace();
			}
		}
		return new JSONObject();
	}
	
	private JSONObject validateOrderId(JSONObject request) throws JSONException
	{
		String orderId = null;
		String signMsg = null;
		if (request.has("orderid"))
		{
			orderId = request.getString("orderid");
		}
		if (request.has("signMsg"))
		{
			signMsg = request.getString("signMsg");
		}
		int result = 1;
		if (orderId != null && signMsg != null)
		{
			result = 0;
		}
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("checkOrderIdRsp", result);
		return jsonResult;
	}
	
	private JSONObject notifyResult(JSONObject request) throws JSONException
	{
		String orderId = null;
		String orderTime = null;
		String cpId = null;
		String consumeCode = null;
		int hRet = 1;
		int status = 0;
		String signMsg = null;
		if (request.has("orderid"))
		{
			orderId = request.getString("orderid");
		}
		if (request.has("ordertime"))
		{
			orderTime = request.getString("ordertime");
		}
		if (request.has("cpid"))
		{
			cpId = request.getString("cpid");
		}
		if (request.has("consumeCode"))
		{
			consumeCode = request.getString("consumeCode");
		}
		if (request.has("hRet"))
		{
			hRet = request.getInt("hRet");
		}
		if (request.has("status"))
		{
			status = request.getInt("status");
		}
		if (request.has("signMsg"))
		{
			signMsg = request.getString("signMsg");
		}
		if (orderId != null && signMsg != null)
		{
			//handle the result
			//persist them
			UnicomIAPInfo unicomIapInfo = UnicomIAPDAOImpl.getInstance().getByOrderId(orderId);
			if (unicomIapInfo == null)
			{
				unicomIapInfo = new UnicomIAPInfo();
				unicomIapInfo.setOrderId(orderId);
				unicomIapInfo.setOrderTime(IAPDateFormatter.strToTime(orderTime, "yyyyMMddHHmmss"));
				unicomIapInfo.setCpId(cpId);
				unicomIapInfo.setConsumeCode(consumeCode);
				unicomIapInfo.setOrderResult(hRet);
				unicomIapInfo.setOrderStatus(status);
				UnicomIAPDAOImpl.getInstance().save(unicomIapInfo);
			}
		}
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("callbackRsp", 0);
		return jsonResult;
	}
}
