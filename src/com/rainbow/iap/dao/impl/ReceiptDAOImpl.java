package com.rainbow.iap.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.rainbow.iap.dao.ReceiptDAO;
import com.rainbow.iap.entity.Receipt;

public class ReceiptDAOImpl implements ReceiptDAO
{
	private static final ReceiptDAO _instance;
	
	static
	{
		_instance = new ReceiptDAOImpl();
	}
	
	public static ReceiptDAO getInstance()
	{
		return _instance;
	}
	
	@Override
	public void save(Receipt receipt)
	{
		try
		(
			Connection conn = ConnectionFactory.getInstance().getConnection();
			PreparedStatement pstmt = conn.prepareStatement("insert into receipt(cp_id, app_id, product_id, order_id, custom_data, receipt_time, price, has_notify) values(?, ?, ?, ?, ?, ?, ?, ?)");
		)
		{
			pstmt.setString(1, receipt.getCpId());
			pstmt.setString(2, receipt.getAppId());
			pstmt.setString(3, receipt.getProductId());
			pstmt.setString(4, receipt.getOrderId());
			pstmt.setString(5, receipt.getCustomData());
			pstmt.setTimestamp(6, receipt.getReceiptTime());
			pstmt.setDouble(7, receipt.getPrice());
			pstmt.setInt(8, receipt.getHasNotify());
			pstmt.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void notify(Receipt receipt)
	{
		try
		(
			Connection conn = ConnectionFactory.getInstance().getConnection();
			PreparedStatement pstmt = conn.prepareStatement("update receipt set has_notify=? where order_id=?");
		)
		{
			pstmt.setInt(1, receipt.getHasNotify());
			pstmt.setString(2, receipt.getOrderId());
			pstmt.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

}
