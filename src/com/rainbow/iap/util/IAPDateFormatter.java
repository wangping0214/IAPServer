package com.rainbow.iap.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IAPDateFormatter
{
	public static Timestamp strToTime(String timeStr, String format)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try
		{
			Date date = dateFormat.parse(timeStr);
			return new Timestamp(date.getTime());
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return new Timestamp(System.currentTimeMillis());
	}
}
