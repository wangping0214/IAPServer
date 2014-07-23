package com.rainbow.iap.util;

import java.security.MessageDigest;

/**
 * MD5ͨ����
 * @since 2013.01.15
 * @version 1.0.0_1
 * 
 */
public class MD5 {
    /**
     * MD5����
     * 
     * @param text ����
     * @param key ��Կ
     * @return ����
     * @throws Exception
     */
	public static String md5(String text, String key) throws Exception {
		byte[] bytes = (text + key).getBytes();
		
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(bytes);
		bytes = messageDigest.digest();
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < bytes.length; i ++)
		{
			if((bytes[i] & 0xff) < 0x10)
			{
				sb.append("0");
			}

			sb.append(Long.toString(bytes[i] & 0xff, 16));
		}
		
		return sb.toString().toLowerCase();
	}
	
	/**
	 * MD5��֤����
	 * 
	 * @param text ����
	 * @param key ��Կ
	 * @param md5 ����
	 * @return true/false
	 * @throws Exception
	 */
	public static boolean verify(String text, String key, String md5) throws Exception {
		String md5Text = md5(text, key);
		if(md5Text.equalsIgnoreCase(md5))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static void main(String[] args) {
		try {
			System.out.println(MD5.md5("", ""));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}