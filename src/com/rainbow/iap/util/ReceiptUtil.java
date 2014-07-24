package com.rainbow.iap.util;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import com.rainbow.iap.dao.impl.AppInfoDAOImpl;
import com.rainbow.iap.dao.impl.OrderDAOImpl;
import com.rainbow.iap.dao.impl.ReceiptDAOImpl;
import com.rainbow.iap.entity.AppInfo;
import com.rainbow.iap.entity.Order;
import com.rainbow.iap.entity.Receipt;

public class ReceiptUtil
{
	private static final Log logger = LogFactory.getLog(ReceiptUtil.class);
	private static final int MIN_ORDER_ID_LEN = 24;
	private static final int CP_ID_LEN = 8;
	private static final int APP_ID_LEN = 4;
	
	private static Map<String, Receipt> _notifyMap = new ConcurrentHashMap<String, Receipt>();
	
	public static void generateReceipt(String orderId)
	{
		if (orderId.length() >= MIN_ORDER_ID_LEN)
		{
			Order order = OrderDAOImpl.getInstance().getByOrderId(orderId);
			if (order == null)
			{
				logger.error("orderId: + " + orderId + " was not found!");
				return;
			}
			OrderDAOImpl.getInstance().remove(order.getId());
			Receipt receipt = new Receipt();
			String cpId = orderId.substring(0, CP_ID_LEN);
			String appId = orderId.substring(CP_ID_LEN, APP_ID_LEN + CP_ID_LEN);
			receipt.setCpId(cpId);
			receipt.setAppId(appId);
			receipt.setProductId(order.getProductId());
			receipt.setOrderId(orderId);
			receipt.setCustomData(order.getCustomData());
			receipt.setReceiptTime(new Timestamp(System.currentTimeMillis()));
			ReceiptDAOImpl.getInstance().save(receipt);
			notify(receipt);
		}
		else
		{
			logger.error("Invalid orderId: " + orderId);
		}
	}
	
	public static void notify(Receipt receipt)
	{
		//_notifyMap.put(receipt.getOrderId(), receipt);
		AppInfo appInfo = AppInfoDAOImpl.getInstance().getByCpIdAndAppId(receipt.getAppId(), receipt.getCpId());
		if (appInfo != null && appInfo.getNotifyUrl() != null)
		{
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("productId", receipt.getProductId());
			jsonObj.put("orderId", receipt.getOrderId());
			jsonObj.put("customData", receipt.getCustomData());
			
			HttpPost post = new HttpPost(appInfo.getNotifyUrl());
			StringEntity strEntity = new StringEntity(jsonObj.toString(),"UTF-8");
			post.setEntity(strEntity);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			CloseableHttpClient httpClient = HttpClients.createDefault();
			try
			{
				try
				{
					httpClient.execute(post, responseHandler);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			finally
			{
				try
				{
					httpClient.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
