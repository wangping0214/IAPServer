package com.rainbow.iap;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.wink.client.ClientConfig;
import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.json.JSONException;
import org.json.JSONObject;

public class ChinaUnicomIAPClient
{
private static Logger log = Logger.getLogger(RestClient.class);
    
    public final static String DEFAUT_CONTENTTYPE = "application/json";
    private static int corePoolSize = 50;
    private static int maximumPoolSize = 500;
    private static int keepAliveTime = 30;
    private static int restReadTimeout = 20000;
    private static ClientConfig config = new ClientConfig();
    
    static {
        config.connectTimeout(restReadTimeout);
        config.readTimeout(restReadTimeout);
    }

    public static ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, 
    	TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.DiscardOldestPolicy());

    public static JSONObject resourceGet(String url){
    	
        log.debug("rest url is..." + url);
        
        JSONObject obj = null;
        ClientResponse response = null ;
        org.apache.wink.client.RestClient restclient = null;
        
        try{
            restclient = new org.apache.wink.client.RestClient(config);
            Resource resource = restclient.resource(url);
            
            response = resource.contentType(DEFAUT_CONTENTTYPE).get();
            obj = response.getEntity(JSONObject.class);
        }catch (Exception ex){
            log.error(url + ex.getMessage());
        }finally{
            response = null ;
            restclient = null;
        }
        
        return obj;
    }
    
    public static JSONObject resourcePost(String url, JSONObject json){
    	
        log.debug("rest url is..." + url + json.toString());
        
        JSONObject obj = null;
        org.apache.wink.client.RestClient restclient = null;
        ClientResponse response = null;
        
        try{
            restclient = new org.apache.wink.client.RestClient(config);
            Resource resource = restclient.resource(url);
            response = resource.contentType(DEFAUT_CONTENTTYPE).accept(DEFAUT_CONTENTTYPE).post(json);
            obj = response.getEntity(JSONObject.class);
        }catch (Exception ex){
            log.error(url + ex.getMessage());
        }finally{
            response = null;
            restclient = null;
        }
        
        return obj;
    }
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		testValidateOrderId();
		testNotifyResult();
	}

	private static void testValidateOrderId()
	{
		JSONObject requestJSON = new JSONObject();
        JSONObject responseJSON = new JSONObject();
        
        try
        {
        	// 请求地址   域名 + rest接口访问路径
        	String requestUrl = "http://182.92.65.140/IAPServer/" + "rest/ChinaUnicomIAPService/request/post/validateorderid";
        	System.out.println(requestUrl);
        	// 请求参数  json body, 以key-value形式装入参数
            requestJSON.put("orderid", "rainbow_order_id");
            requestJSON.put("signMsg", "rainbow_sign_msg");
            // 通过RestClient发post的rest接口请求, 返回json body
            responseJSON = resourcePost(requestUrl, requestJSON);
            
            System.out.println(responseJSON);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
	}
	
	public static void testNotifyResult()
	{
		JSONObject requestJSON = new JSONObject();
        JSONObject responseJSON = new JSONObject();
        
        try
        {
        	// 请求地址   域名 + rest接口访问路径
        	String requestUrl = "http://182.92.65.140/IAPServer/" + "rest/ChinaUnicomIAPService/request/post/notifyresult";
        	System.out.println(requestUrl);
        	// 请求参数  json body, 以key-value形式装入参数
            requestJSON.put("orderid", "rainbow_order_id");
            requestJSON.put("ordertime", "20140609234200");
            requestJSON.put("cpid", "rainbow_cp_id");
            requestJSON.put("consumeCode", "rainbow_consume_code");
            requestJSON.put("hRet", 0);
            requestJSON.put("status", 0);
            requestJSON.put("signMsg", "rainbow_sign_msg");
            // 通过RestClient发post的rest接口请求, 返回json body
            responseJSON = resourcePost(requestUrl, requestJSON);
            
            System.out.println(responseJSON);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
	}
}
