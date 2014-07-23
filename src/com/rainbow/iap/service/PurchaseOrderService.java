package com.rainbow.iap.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;

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

import com.rainbow.iap.dao.impl.OrderDAOImpl;
import com.rainbow.iap.dao.impl.ProductDAOImpl;
import com.rainbow.iap.dao.impl.UniqueIdDAOImpl;
import com.rainbow.iap.entity.Order;
import com.rainbow.iap.entity.Product;
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
			Product product = ProductDAOImpl.getInstance().getProduct(purchaseOrderRequest.getProductId(), purchaseOrderRequest.getIAPMethodType());
			PurchaseOrder purchaseOrder = new PurchaseOrder();
			purchaseOrder.setResponseCode(PurchaseOrder.RESPONSE_FAILED);
			if (product != null)
			{
				purchaseOrder.setResponseCode(PurchaseOrder.RESPONSE_SUCCEED);
				switch (purchaseOrderRequest.getIAPMethodType())
				{
				case PURCHASE_METHOD_TYPE_CHINA_UNICOM:
				{
					purchaseOrder.setName(product.getName());
					purchaseOrder.setChinaUnicomProductId(product.getUnicomProductId());
					purchaseOrder.setChinaUnicomConsumeCode(product.getUnicomConsumeCode());
					purchaseOrder.setDescription(product.getDescription());
					long orderSeq = UniqueIdDAOImpl.getInstance().getNextOrderSeq(product.getCpId(), product.getAppId());
					purchaseOrder.setOrderId(product.getCpId() + product.getAppId() + "00" + String.format("%010d", orderSeq));
				}
					break;
				case PURCHASE_METHOD_TYPE_ALIPAY:
				{
					purchaseOrder.setName(product.getName());
					purchaseOrder.setPrice(product.getPrice());
					purchaseOrder.setDescription(product.getDescription());
					long orderSeq = UniqueIdDAOImpl.getInstance().getNextOrderSeq(product.getCpId(), product.getAppId());
					purchaseOrder.setOrderId(product.getCpId() + product.getAppId() + "01" + String.format("%050d", orderSeq));
				}
					break;
				case PURCHASE_METHOD_TYPE_UNION_PAY:
				{
					purchaseOrder.setName(product.getName());
					purchaseOrder.setPrice(product.getPrice());
					purchaseOrder.setDescription(product.getDescription());
					long orderSeq = UniqueIdDAOImpl.getInstance().getNextOrderSeq(product.getCpId(), product.getAppId());
					purchaseOrder.setOrderId(product.getCpId() + product.getAppId() + "02" + String.format("%010d", orderSeq));
				}
					break;
				}
			}
			
			if (purchaseOrder != null)
			{
				purchaseOrder.setProductId(purchaseOrderRequest.getProductId());
				purchaseOrder.setPurchaseTime(System.currentTimeMillis());
				
				Order order = new Order();
				order.setOrderId(purchaseOrder.getOrderId());
				order.setProductId(purchaseOrder.getProductId());
				order.setOrderTime(new Timestamp(purchaseOrder.getPurchaseTime()));
				order.setCustomData(purchaseOrderRequest.getCustomData());
				OrderDAOImpl.getInstance().save(order);
				
				JSONObject resultJsonObj = new JSONObject();
				purchaseOrder.marshal(resultJsonObj);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().println(resultJsonObj.toString());
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}
}
