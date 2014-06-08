package com.rainbow.iap.servlet;

import java.io.IOException;

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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.rainbow.iap.dao.impl.UnicomIAPDAOImpl;
import com.rainbow.iap.entity.UnicomIAPInfo;
import com.rainbow.iap.util.IAPDateFormatter;

/**
 * Servlet implementation class IAPServlet
 */
@WebServlet("/UnicomIAPServlet")
public class UnicomIAPServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	private static final String VALIDATE_ORDER_ID 	= "validateorderid";
	private static final String NOTIFY_RESULT		= "notifyresult";
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UnicomIAPServlet()
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
		intercept(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		intercept(request, response);
	}

	private void intercept(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String serviceId = request.getParameter("serviceid");
		if (VALIDATE_ORDER_ID.equals(serviceId))
		{
			validateOrderId(request, response);
		}
		else if (NOTIFY_RESULT.equals(serviceId))
		{
			notifyResult(request, response);
		}
	}
	
	private void validateOrderId(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(request.getInputStream());
			Element rootElem = doc.getDocumentElement();
			String orderId = null;
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
					else if (childElem.getTagName().equals("signMsg"))
					{
						signMsg = childElem.getTextContent();
					}
				}
			}
			
			response.setContentType("application/xml");
			Document resultDoc = docBuilder.newDocument();
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
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty("indent", "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(new DOMSource(doc), new StreamResult(response.getWriter()));
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
		}
	}
	
	private void notifyResult(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(request.getInputStream());
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
			
			response.setContentType("application/xml");
			Document resultDoc = docBuilder.newDocument();
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
					unicomIapInfo.setOrderTime(IAPDateFormatter.strToTime(orderTime, "yyyyMMddHHmmss"));
					unicomIapInfo.setCpId(cpId);
					unicomIapInfo.setConsumeCode(consumeCode);
					unicomIapInfo.setOrderResult(Integer.parseInt(hRet));
					unicomIapInfo.setOrderStatus(Integer.parseInt(status));
					UnicomIAPDAOImpl.getInstance().save(unicomIapInfo);
				}
			}
			resElem.setTextContent("0");
			resultDoc.appendChild(resElem);
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty("indent", "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(new DOMSource(doc), new StreamResult(response.getWriter()));
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
		}
	}
}
