package com.rainbow.iap.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionFactory
{
	private static ConnectionFactory _instance;

	private DataSource _dataSource;

	static
	{
		_instance = new ConnectionFactory();
	}

	public static ConnectionFactory getInstance()
	{
		return _instance;
	}

	private ConnectionFactory()
	{
		try
		{
			InitialContext ctx = new InitialContext();
			_dataSource = (DataSource) ctx
					.lookup("java:/comp/env/jdbc/rainbow_iap");
		} catch (NamingException e)
		{
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException
	{
		return _dataSource.getConnection();
	}
}
