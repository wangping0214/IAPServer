package com.rainbow.iap.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.rainbow.iap.dao.UnicomIAPDAO;
import com.rainbow.iap.entity.UnicomIAPInfo;

public class UnicomIAPDAOImpl implements UnicomIAPDAO
{
	private static final UnicomIAPDAO _instance;
	
	static
	{
		_instance = new UnicomIAPDAOImpl();
	}
	
	public static UnicomIAPDAO getInstance()
	{
		return _instance;
	}
	
	private UnicomIAPDAOImpl()
	{
	}
	
	@Override
	public void save(UnicomIAPInfo unicomIAPInfo)
	{
		try
		(
			Connection conn = ConnectionFactory.getInstance().getConnection();
			PreparedStatement pstmt = conn.prepareStatement("insert into unicom_iap(order_id, order_time, cp_id, consume_code, order_result, order_status) values(?, ?, ?, ?, ?, ?)");
		)
		{
			pstmt.setString(1, unicomIAPInfo.getOrderId());
			pstmt.setTimestamp(2, unicomIAPInfo.getOrderTime());
			pstmt.setString(3, unicomIAPInfo.getCpId());
			pstmt.setString(4, unicomIAPInfo.getConsumeCode());
			pstmt.setInt(5, unicomIAPInfo.getOrderResult());
			pstmt.setInt(6, unicomIAPInfo.getOrderStatus());
			pstmt.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void update(UnicomIAPInfo unicomIAPInfo)
	{
		try
		(
			Connection conn = ConnectionFactory.getInstance().getConnection();
			PreparedStatement pstmt = conn.prepareStatement("update unicom_iap set order_id=?, order_time=?, cp_id=?, consume_code=?, order_result=?, order_status=? where id=?");
		)
		{
			pstmt.setString(1, unicomIAPInfo.getOrderId());
			pstmt.setTimestamp(2, unicomIAPInfo.getOrderTime());
			pstmt.setString(3, unicomIAPInfo.getCpId());
			pstmt.setString(4, unicomIAPInfo.getConsumeCode());
			pstmt.setInt(5, unicomIAPInfo.getOrderResult());
			pstmt.setInt(6, unicomIAPInfo.getOrderStatus());
			pstmt.setLong(7, unicomIAPInfo.getId());
			pstmt.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public UnicomIAPInfo getByOrderId(String orderId)
	{
		try 
		(
			Connection conn = ConnectionFactory.getInstance().getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select id, order_id, order_time, cp_id, consume_code, order_result, order_status from unicom_iap where order_id=\'" + orderId + "\'");
		)
		{
			if (rs.next())
			{
				UnicomIAPInfo iapInfo = new UnicomIAPInfo();
				iapInfo.setId(rs.getLong("id"));
				iapInfo.setOrderId(rs.getString("order_id"));
				iapInfo.setOrderTime(rs.getTimestamp("order_time"));
				iapInfo.setCpId(rs.getString("cp_id"));
				iapInfo.setConsumeCode(rs.getString("consume_code"));
				iapInfo.setOrderResult(rs.getInt("order_result"));
				iapInfo.setOrderStatus(rs.getInt("order_status"));
				return iapInfo;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
