package com.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088411193613191";
	// 商户的私钥
	public static String private_key = 
					"MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALtZaaMfEJY5EKqY" +
					"YaP2zSUOn5MKlFRxU7+8tZ6pyb1ywVr8K92DWeqy9PSpebTLk2LU1Esa0zRYZTrT" +
					"SaQE2nUtZ4szR9gYmBx8iIGDQga0QQfMyAKO16/n0LctL3JHB22VUfFS+VEBJ6M1" +
					"FMxtt8Q3DfoNYdl6mOD4hETmpoxJAgMBAAECgYBgauOyz4n5xeSN515Yw+tP5va4" +
					"9fjgfHJdewD9ZuQsW6Km8KCin7bm0rK+N3orUZnIgz++Z0/K4LM4UwTTJKxIQC0R" +
					"xDa+0rHgOvIcK7+teXEj58/GqHo/yYJfauQz83YsqCb3VfprlRCWbu/vvAMgwlcZ" +
					"x/5F/QgulSV4CmNAAQJBAOVjYtTMjoxW2XQXhp3EBK2CON+BSTb/E36t2bhht6CV" +
					"i6gsbVM3gT2DoM5JPJaf7M8Wq1OHsjEeUTUQB2KIa+kCQQDRFYJZEtLy3kOqECL9" +
					"FWBRUkJZsjY+lEw6+MdAzIYB73PpmOO3+LELE+RJmcfbFDkBC38RG6BHbZm99VrI" +
					"48FhAkAmcu847gSiv1f5novg299Q2fgAdqI4Bq9U130b670kvIxJJxE4FqCiF/MX" +
					"QK1YLfw6hfk3qhITK5q/Ay3JtUYpAkBk2WYdBrpfURv8HHpz7mqd7vp3/0Cw4KEA" +
					"VNzvAXel2VTkmM1GAJuMx1R2t8kxf8ibG2t32gZuTYw5lu3qNgkhAkBmZlJzrzOd" +
					"HR/l/wgRDVnZAvngT+54RwNcOWwSzcOEEmDyDLtcQJDIzkCwBLrEbPmxwfi6a/5t" +
					"xb/DLxPglEoV";;
	
	// 支付宝的公钥，无需修改该值
	public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "RSA";

}
