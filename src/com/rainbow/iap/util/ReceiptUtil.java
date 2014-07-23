package com.rainbow.iap.util;

import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rainbow.iap.dao.impl.OrderDAOImpl;
import com.rainbow.iap.dao.impl.ReceiptDAOImpl;
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
		}
		else
		{
			logger.error("Invalid orderId: " + orderId);
		}
	}
	
	public static void notify(Receipt receipt)
	{
		_notifyMap.put(receipt.getOrderId(), receipt);
		
	}
}
