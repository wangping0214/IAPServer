package com.rainbow.iap.service.notify;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.rainbow.iap.entity.UnionPayIAPInfo;
import com.rainbow.iap.util.CipherUtil;
import com.rainbow.iap.util.IAPDateFormatter;

/**
 * Servlet implementation class UnionPayIAPService
 */
@WebServlet("/UnionPayIAPService")
public class UnionPayIAPService extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String UNION_PAY_KEY = "wcsk_unionpay_md5_key_xx";
	private static final Log logger = LogFactory.getLog(UnionPayIAPService.class);
	
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UnionPayIAPService()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		validate(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		validate(request, response);
	}
	
	private void validate(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(request.getInputStream());
			Element chinaBankElem = doc.getDocumentElement();
			Element cipherDataElem = null;
			String requestData = null;
			String signStr = null;
			NodeList childNodes = chinaBankElem.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); ++ i)
			{
				Node childNode = childNodes.item(i);
				if (childNode instanceof Element)
				{
					Element childElem = (Element) childNode;
					if (childElem.getTagName().equals("VERSION"))
					{
						logger.info("VERSION: " + childElem.getTextContent());
					}
					else if (childElem.getTagName().equals("MERCHANT"))
					{
						logger.info("MERCHANT: " + childElem.getTextContent());
					}
					else if (childElem.getTagName().equals("TERMINAL"))
					{
						logger.info("TERMINAL: " + childElem.getTextContent());
					}
					else if (childElem.getTagName().equals("DATA"))
					{
						logger.info("DATA: " + childElem.getTextContent());
						cipherDataElem = childElem;
						requestData = childElem.getTextContent();
					}
					else if (childElem.getTagName().equals("SIGN"))
					{
						logger.info("SIGN: " + childElem.getTextContent());
						signStr = childElem.getTextContent();
					}
				}
			}
			
			boolean validateSignResult = validateSign(requestData, signStr);
			if (requestData != null && signStr != null)
			{
				String plainRequestData = CipherUtil.desDecrypt(requestData, UNION_PAY_KEY);
				if (plainRequestData != null)
				{
					Document dataDoc = docBuilder.parse(new InputSource(new StringReader(plainRequestData)));
					Element dataElem = dataDoc.getDocumentElement();
					Element tradeElem = null;
					Element cardElem = null;
					NodeList nodeList = dataElem.getElementsByTagName("TRADE");
					if (nodeList.getLength() >= 1)
					{
						tradeElem = (Element) nodeList.item(0);
					}
					nodeList = dataElem.getElementsByTagName("CARD");
					if (nodeList.getLength() >= 1)
					{
						cardElem = (Element) nodeList.item(0);
					}
					if (tradeElem != null && cardElem != null)
					{
						processTrade(tradeElem);
						processCard(cardElem);
						Element returnElem = genReturn(dataDoc, validateSignResult);
						dataElem.replaceChild(returnElem, cardElem);
						String returnStr = transform(dataDoc);
						String cipherReturnStr = CipherUtil.desEncrypt(returnStr, UNION_PAY_KEY);
						cipherDataElem.setTextContent(cipherReturnStr);
					}
				}
			}
			
			response.setContentType("application/xml");
			response.getWriter().println(transform(doc));
		} catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		} catch (SAXException e)
		{
			e.printStackTrace();
		}
	}
	
	private boolean validateSign(String dataStr, String signStr)
	{
		return true;
	}
	
	private void processTrade(Element tradeElem)
	{
		UnionPayIAPInfo iapInfo = new UnionPayIAPInfo();
		String tradeTimeStr = "";
		NodeList childNodes = tradeElem.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); ++ i)
		{
			Element childElem = (Element) childNodes.item(i);
			if (childElem.getTagName().equals("TYPE"))
			{
				iapInfo.setTradeType(childElem.getTextContent());
			}
			else if (childElem.getTagName().equals("ID"))
			{
				iapInfo.setTradeId(childElem.getTextContent());
			}
			else if (childElem.getTagName().equals("AMOUNT"))
			{
				iapInfo.setTradeAmount(childElem.getTextContent());
			}
			else if (childElem.getTagName().equals("CURRENCY"))
			{
				iapInfo.setTradeCurrency(childElem.getTextContent());
			}
			else if (childElem.getTagName().equals("DATE"))
			{
				tradeTimeStr = childElem.getTextContent() + tradeTimeStr;
			}
			else if (childElem.getTagName().equals("TIME"))
			{
				tradeTimeStr += childElem.getTextContent();
			}
			else if (childElem.getTagName().equals("NOTE"))
			{
				iapInfo.setTradeNote(childElem.getTextContent());
			}
			else if (childElem.getTagName().equals("STATUS"))
			{
				iapInfo.setTradeStatus(Integer.parseInt(childElem.getTextContent()));
			}
		}
		if (!tradeTimeStr.isEmpty())
		{
			iapInfo.setTradeTime(IAPDateFormatter.strToTime(tradeTimeStr, "yyyyMMddHHmmss"));
		}
	}
	
	private void processCard(Element cardElem)
	{
	}
	
	private Element genReturn(Document dataDoc, boolean validateSignResult)
	{
		Element returnElem = dataDoc.createElement("RETURN");
		Element codeElem = dataDoc.createElement("CODE");
		Element descElem = dataDoc.createElement("DESC");
		codeElem.setTextContent("0000000000");
		descElem.setTextContent("³É¹¦");
		returnElem.appendChild(codeElem);
		returnElem.appendChild(descElem);
		return returnElem;
	}
	
	private String transform(Document doc)
	{
		StringWriter strWriter = new StringWriter();
		Transformer transformer;
		try
		{
			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty("indent", "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(new DOMSource(doc), new StreamResult(strWriter));
			return strWriter.toString();
		} catch (TransformerConfigurationException e)
		{
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e)
		{
			e.printStackTrace();
		} catch (TransformerException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
