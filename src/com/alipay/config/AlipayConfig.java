package com.alipay.config;

/* *
 *������AlipayConfig
 *���ܣ�����������
 *��ϸ�������ʻ��й���Ϣ������·��
 *�汾��3.3
 *���ڣ�2012-08-10
 *˵����
 *���´���ֻ��Ϊ�˷����̻����Զ��ṩ���������룬�̻����Ը����Լ���վ����Ҫ�����ռ����ĵ���д,����һ��Ҫʹ�øô��롣
 *�ô������ѧϰ���о�֧�����ӿ�ʹ�ã�ֻ���ṩһ���ο���
	
 *��ʾ����λ�ȡ��ȫУ����ͺ��������ID
 *1.������ǩԼ֧�����˺ŵ�¼֧������վ(www.alipay.com)
 *2.������̼ҷ���(https://b.alipay.com/order/myOrder.htm)
 *3.�������ѯ���������(PID)��������ѯ��ȫУ����(Key)��

 *��ȫУ����鿴ʱ������֧�������ҳ��ʻ�ɫ��������ô�죿
 *���������
 *1�������������ã������������������������
 *2���������������ԣ����µ�¼��ѯ��
 */

public class AlipayConfig {
	
	//�����������������������������������Ļ�����Ϣ������������������������������
	// ���������ID����2088��ͷ��16λ��������ɵ��ַ���
	public static String partner = "2088411193613191";
	// �̻���˽Կ
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
	
	// ֧�����Ĺ�Կ�������޸ĸ�ֵ
	public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	//�����������������������������������Ļ�����Ϣ������������������������������
	

	// �����ã�����TXT��־�ļ���·��
	public static String log_path = "D:\\";

	// �ַ������ʽ Ŀǰ֧�� gbk �� utf-8
	public static String input_charset = "utf-8";
	
	// ǩ����ʽ �����޸�
	public static String sign_type = "RSA";

}
