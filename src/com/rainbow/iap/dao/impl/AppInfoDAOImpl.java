package com.rainbow.iap.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.rainbow.iap.dao.AppInfoDAO;
import com.rainbow.iap.entity.AppInfo;

public class AppInfoDAOImpl implements AppInfoDAO
{
	private static final AppInfoDAO _instance;
	
	static 
	{
		_instance = new AppInfoDAOImpl();
	}
	
	public static AppInfoDAO getInstance()
	{
		return _instance;
	}
	
	private AppInfoDAOImpl()
	{
	}
	
	@Override
	public AppInfo getByCpIdAndAppId(String cpId, String appId)
	{
		try 
		(
			Connection conn = ConnectionFactory.getInstance().getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from appinfo where cp_id=\'" + cpId + "\' " +
					"app_id=\'" + appId + "\'");
		)
		{
			if (rs.next())
			{
				AppInfo entity = new AppInfo();
				entity.setId(rs.getLong("id"));
				entity.setNotifyUrl(rs.getString("notify_url"));
				return entity;
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
