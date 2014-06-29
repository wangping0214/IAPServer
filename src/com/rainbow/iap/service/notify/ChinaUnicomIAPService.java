package com.rainbow.iap.service.notify;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
//import javax.ws.rs.QueryParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.rainbow.iap.dao.impl.UnicomIAPDAOImpl;
import com.rainbow.iap.entity.UnicomIAPInfo;
import com.rainbow.iap.util.IAPDateFormatter;

@Path("/ChinaUnicomIAPService")
public class ChinaUnicomIAPService
{
	private static final Log logger = LogFactory.getLog(ChinaUnicomIAPService.class);
	
	@POST
    @Path("/request/post")
    @Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public String postValidateOrderId(String xmlContent)
	{
		logger.info("recv: " + xmlContent);
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new InputSource(new StringReader(xmlContent)));
			Element rootElem = doc.getDocumentElement();
			
			String orderId = null;
			String orderTime = null;
			String cpId = null;
			String consumeCode = null;
			String hRet = null;
			String status = null;
			String signMsg = null;
			NodeList childNodes = rootElem.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); ++ i)
			{
				Node childNode = childNodes.item(i);
				if (childNode instanceof Element)
				{
					Element childElem = (Element) childNode;
					if (childElem.getTagName().equals("orderid"))
					{
						orderId = childElem.getTextContent();
					}
					else if (childElem.getTagName().equals("ordertime"))
					{
						orderTime = childElem.getTextContent();
					}
					else if (childElem.getTagName().equals("cpid"))
					{
						cpId = childElem.getTextContent();
					}
					else if (childElem.getTagName().equals("consumeCode"))
					{
						consumeCode = childElem.getTextContent();
					}
					else if (childElem.getTagName().equals("hRet"))
					{
						hRet = childElem.getTextContent();
					}
					else if (childElem.getTagName().equals("status"))
					{
						status = childElem.getTextContent();
					}
					else if (childElem.getTagName().equals("signMsg"))
					{
						signMsg = childElem.getTextContent();
					}
				}
			}
			
			Document resultDoc = docBuilder.newDocument();
			if (consumeCode == null)	//validate ordier id
			{
				Element resElem = resultDoc.createElement("checkOrderIdRsp");
				if (orderId != null && signMsg != null)
				{
					//validate orderId and signMsg
					resElem.setTextContent("0");
				}
				else
				{
					resElem.setTextContent("1");
				}
				resultDoc.appendChild(resElem);
			}
			else						//notify result
			{
				Element resElem = resultDoc.createElement("callbackRsp");
				if (orderId != null && signMsg != null)
				{
					//handle the result
					//persist them
					UnicomIAPInfo unicomIapInfo = UnicomIAPDAOImpl.getInstance().getByOrderId(orderId);
					if (unicomIapInfo == null)
					{
						unicomIapInfo = new UnicomIAPInfo();
						unicomIapInfo.setOrderId(orderId);
						if (orderTime != null)
						{
							unicomIapInfo.setOrderTime(IAPDateFormatter.strToTime(orderTime, "yyyyMMddHHmmss"));
						}
						unicomIapInfo.setCpId(cpId);
						unicomIapInfo.setConsumeCode(consumeCode);
						if (hRet != null)
						{
							unicomIapInfo.setOrderResult(Integer.parseInt(hRet));
						}
						if (status != null)
						{
							unicomIapInfo.setOrderStatus(Integer.parseInt(status));
						}
						UnicomIAPDAOImpl.getInstance().save(unicomIapInfo);
					}
				}
				resElem.setTextContent("0");
				resultDoc.appendChild(resElem);
				
			}
			StringWriter strWriter = new StringWriter();
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty("indent", "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(new DOMSource(resultDoc), new StreamResult(strWriter));
			logger.info("send: " + strWriter.toString());
			return strWriter.toString();
		} catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		} catch (SAXException e)
		{
			e.printStackTrace();
		} catch (TransformerConfigurationException e)
		{
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e)
		{
			e.printStackTrace();
		} catch (TransformerException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return "";
	}
}
