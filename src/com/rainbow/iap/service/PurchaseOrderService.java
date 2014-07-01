package com.rainbow.iap.service;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.rainbow.iap.entity.json.PurchaseOrder;
import com.rainbow.iap.entity.json.PurchaseOrderRequest;

/**
 * Servlet implementation class IAPProductService
 */
@WebServlet("/PurchaseOrderService")
public class PurchaseOrderService extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(PurchaseOrderService.class);
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PurchaseOrderService()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		process(request, response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		JSONTokener tokener = new JSONTokener(new InputStreamReader(request.getInputStream()));
		try
		{
			JSONObject jsonObj = new JSONObject(tokener);
			PurchaseOrderRequest purchaseOrderRequest = new PurchaseOrderRequest();
			purchaseOrderRequest.unmarshal(jsonObj);
			logger.info("purchaseMethod:" + purchaseOrderRequest.getIAPMethodType() + ";productId:" + purchaseOrderRequest.getProductId());
			PurchaseOrder purchaseOrder = null;
			switch (purchaseOrderRequest.getIAPMethodType())
			{
			case PURCHASE_METHOD_TYPE_CHINA_UNICOM:
				purchaseOrder = new PurchaseOrder();
				purchaseOrder.setName("短代支付测试物品");
				purchaseOrder.setChinaUnicomProductId("0000000000001001");
				purchaseOrder.setChinaUnicomConsumeCode("000000010029");
				purchaseOrder.setDescription("短代支付测试物品详细信息");
				purchaseOrder.setOrderId("00000001" + "0001" + "00" + String.format("%010d", System.currentTimeMillis()/1000));
				break;
			case PURCHASE_METHOD_TYPE_ALIPAY:
				purchaseOrder = new PurchaseOrder();
				purchaseOrder.setName("支付宝支付测试物品");
				purchaseOrder.setPrice(0.01);
				purchaseOrder.setDescription("支付宝支付测试物品详细信息");
				purchaseOrder.setOrderId("00000001" + "0001" + "01" + String.format("%050d", System.currentTimeMillis()));
				break;
			case PURCHASE_METHOD_TYPE_UNION_PAY:
				purchaseOrder = new PurchaseOrder();
				purchaseOrder.setName("银联支付测试物品");
				purchaseOrder.setPrice(0.01);
				purchaseOrder.setDescription("银联支付测试物品详细信息");
				purchaseOrder.setOrderId("00000001" + "0001" + "02" + String.format("%010d", System.currentTimeMillis()/1000));
				break;
			}
			
			if (purchaseOrder != null)
			{
				purchaseOrder.setProductId(purchaseOrderRequest.getProductId());
				purchaseOrder.setPurchaseTime(System.currentTimeMillis());
				JSONObject resultJsonObj = new JSONObject();
				purchaseOrder.marshal(resultJsonObj);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().println(resultJsonObj.toString());
			}
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
	}
}
