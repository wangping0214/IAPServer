package com.rainbow.iap.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

import com.rainbow.iap.dao.UniqueIdDAO;

public class UniqueIdDAOImpl implements UniqueIdDAO
{
	private static UniqueIdDAO _instance = new UniqueIdDAOImpl();
	
	public static UniqueIdDAO getInstance()
	{
		return _instance;
	}
	
	private Set<String>				_newUniqueIdSet;
	private Map<String, AtomicLong>	_uniqueIdMap;
	private ReentrantLock			_lock;
	
	private UniqueIdDAOImpl()
	{
		_newUniqueIdSet = new HashSet<String>();
		_uniqueIdMap = new HashMap<String, AtomicLong>();
		_lock = new ReentrantLock();
	}
	
	@Override
	public void initialize()
	{
		try
		(
			Connection conn = ConnectionFactory.getInstance().getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select unique_id_name, unique_id_value from unique_id");
		)
		{
			while (rs.next())
			{
				String uniqueIdName = rs.getString("unique_id_name");
				long uniqueIdValue = rs.getLong("unique_id_value");
				_uniqueIdMap.put(uniqueIdName, new AtomicLong(uniqueIdValue));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public long getNextOrderSeq(String cpId, String appId)
	{
		String uniqueIdName = cpId + appId;
		AtomicLong uniqueIdValue = null;
		try
		{
			_lock.lock();
			
			uniqueIdValue = _uniqueIdMap.get(uniqueIdName);
			if (uniqueIdValue == null)
			{
				uniqueIdValue = new AtomicLong(0);
				_newUniqueIdSet.add(uniqueIdName);
				_uniqueIdMap.put(uniqueIdName, uniqueIdValue);
			}
		}
		finally
		{
			_lock.unlock();
		}
		return uniqueIdValue.incrementAndGet();
	}

	@Override
	public void destroy()
	{
		try
		(
			Connection conn = ConnectionFactory.getInstance().getConnection();
			PreparedStatement pstmt = conn.prepareStatement("insert into unique_id(unique_id_name, unique_id_value) values(?, ?)");
			PreparedStatement updatePstmt = conn.prepareStatement("update unique_id set unique_id_value=? where unique_id_name=?");
		)
		{
			//insert
			for (String newUniqueIdName : _newUniqueIdSet)
			{
				AtomicLong uniqueIdValue = _uniqueIdMap.remove(newUniqueIdName);
				pstmt.setString(1, newUniqueIdName);
				pstmt.setLong(2, uniqueIdValue.longValue());
				pstmt.executeUpdate();
			}
			//update
			for (Map.Entry<String, AtomicLong> entry : _uniqueIdMap.entrySet())
			{
				updatePstmt.setLong(1, entry.getValue().longValue());
				updatePstmt.setString(2, entry.getKey());
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
}
