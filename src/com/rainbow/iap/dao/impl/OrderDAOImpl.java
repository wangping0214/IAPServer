package com.rainbow.iap.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.rainbow.iap.dao.OrderDAO;
import com.rainbow.iap.entity.Order;

public class OrderDAOImpl implements OrderDAO
{
	private static final OrderDAO _instance;
	
	static
	{
		_instance = new OrderDAOImpl();
	}
	
	public static OrderDAO getInstance()
	{
		return _instance;
	}
	
	private OrderDAOImpl()
	{
	}

	@Override
	public void save(Order entity)
	{
		try
		(
			Connection conn = ConnectionFactory.getInstance().getConnection();
			PreparedStatement pstmt = conn.prepareStatement("insert into order_cache(order_id, product_id, custom_data, price, order_time) values(?, ?, ?, ?, ?)");
		)
		{
			pstmt.setString(1, entity.getOrderId());
			pstmt.setString(2, entity.getProductId());
			pstmt.setString(3, entity.getCustomData());
			pstmt.setDouble(4, entity.getPrice());
			pstmt.setTimestamp(5, entity.getOrderTime());
			pstmt.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public Order getByOrderId(String orderId)
	{
		try 
		(
			Connection conn = ConnectionFactory.getInstance().getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from order_cache where order_id=\'" + orderId + "\'");
		)
		{
			if (rs.next())
			{
				Order entity = new Order();
				entity.setId(rs.getLong("id"));
				entity.setOrderId(rs.getString("order_id"));
				entity.setProductId(rs.getString("product_id"));
				entity.setCustomData(rs.getString("custom_data"));
				entity.setOrderTime(rs.getTimestamp("order_time"));
				entity.setPrice(rs.getDouble("price"));
				return entity;
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void remove(long id)
	{
		try
		(
			Connection conn = ConnectionFactory.getInstance().getConnection();
			PreparedStatement pstmt = conn.prepareStatement("delete from order_cache where id=?");
		)
		{
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
