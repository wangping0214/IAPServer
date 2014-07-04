package com.rainbow.iap.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.rainbow.iap.dao.ProductDAO;
import com.rainbow.iap.entity.Product;
import com.rainbow.iap.entity.json.PurchaseMethodType;

public class ProductDAOImpl implements ProductDAO
{
	private static final ProductDAO _instance;

	static
	{
		_instance = new ProductDAOImpl();
	}
	
	public static ProductDAO getInstance()
	{
		return _instance;
	}
	
	private ProductDAOImpl()
	{
	}
	
	@Override
	public Product getProduct(String productId,
			PurchaseMethodType purchaseMethod)
	{
		switch (purchaseMethod)
		{
		case PURCHASE_METHOD_TYPE_CHINA_UNICOM:
		{
			try
			(
				Connection conn = ConnectionFactory.getInstance().getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select * from messagePayment where product_id=\'" + productId + "\'");
			)
			{
				if (rs.next())
				{
					Product product = new Product();
					product.setId(rs.getLong("id"));
					product.setCpId(rs.getString("cp_id"));
					product.setAppId(rs.getString("app_id"));
					product.setProductId(rs.getString("product_id"));
					product.setName(rs.getString("name"));
					product.setDescription(rs.getString("description"));
					product.setUnicomProductId(rs.getString("china_unicom_product_id"));
					product.setUnicomConsumeCode(rs.getString("china_unicom_consume_code"));
					return product;
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
			break;
		case PURCHASE_METHOD_TYPE_ALIPAY:
		case PURCHASE_METHOD_TYPE_UNION_PAY:
		{
			try
			(
				Connection conn = ConnectionFactory.getInstance().getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select * from otherPayment where product_id=\'" + productId + "\'");
			)
			{
				if (rs.next())
				{
					Product product = new Product();
					product.setId(rs.getLong("id"));
					product.setCpId(rs.getString("cp_id"));
					product.setAppId(rs.getString("app_id"));
					product.setProductId(rs.getString("product_id"));
					product.setName(rs.getString("name"));
					product.setDescription(rs.getString("description"));
					product.setPrice(rs.getDouble("price"));
					return product;
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
			break;
		}
		return null;
	}

}
