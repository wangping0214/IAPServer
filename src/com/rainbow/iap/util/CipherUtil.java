package com.rainbow.iap.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

public class CipherUtil
{
	/**
	 * 
	 * @param plainStr
	 * @param keyStr
	 * @return
	 */
	public static String desEncrypt(String plainStr, String keyStr)
	{
		return desEncrypt(plainStr.getBytes(), keyStr);
	}
	
	/**
	 * 
	 * @param bytes
	 * @param keyStr
	 * @return the base64 encoded cipher bytes
	 */
	public static String desEncrypt(byte[] bytes, String keyStr)
	{
		byte[] cipherBytes = des(Cipher.ENCRYPT_MODE, bytes, keyStr);
		if (cipherBytes != null)
		{
			return Base64.encodeBase64String(cipherBytes);
		}
		return null;
	}
	
	/**
	 * 
	 * @param base64Str
	 * @param keyStr
	 * @return the plain string
	 */
	public static String desDecrypt(String base64Str, String keyStr)
	{
		byte[] cipherBytes = Base64.decodeBase64(base64Str);
		return desDecrypt(cipherBytes, keyStr);
	}
	
	/**
	 * 
	 * @param cipherBytes
	 * @param keyStr
	 * @return the plain string
	 */
	public static String desDecrypt(byte[] cipherBytes, String keyStr)
	{
		byte[] plainBytes = des(Cipher.DECRYPT_MODE, cipherBytes, keyStr);
		return new String(plainBytes);
	}
	
	private static byte[] des(int opmode, byte[] bytes, String keyStr)
	{
		try
		{
			KeyGenerator keyGen = KeyGenerator.getInstance("DES");
			keyGen.init(new SecureRandom(keyStr.getBytes()));
			Key key = keyGen.generateKey();
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(opmode, key);
			return cipher.doFinal(bytes);
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		} catch (NoSuchPaddingException e)
		{
			e.printStackTrace();
		} catch (InvalidKeyException e)
		{
			e.printStackTrace();
		} catch (IllegalBlockSizeException e)
		{
			e.printStackTrace();
		} catch (BadPaddingException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
