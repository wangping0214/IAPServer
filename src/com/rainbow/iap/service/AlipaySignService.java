package com.rainbow.iap.service;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.config.AlipayConfig;
import com.alipay.sign.RSA;

/**
 * Servlet implementation class AlipaySignService
 */
@WebServlet("/AlipaySignService")
public class AlipaySignService extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AlipaySignService()
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
		process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		process(request, response);
	}

	private void process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		StringBuilder content = new StringBuilder();
		String line = null;
		BufferedReader br = request.getReader();
		while ((line = br.readLine()) != null)
		{
			content.append(line);
		}
		String signStr = RSA.sign(content.toString(), AlipayConfig.private_key, AlipayConfig.input_charset);
		response.getWriter().println(signStr);
	}
}
