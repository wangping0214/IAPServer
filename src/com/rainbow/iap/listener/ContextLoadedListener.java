package com.rainbow.iap.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.rainbow.iap.dao.impl.UniqueIdDAOImpl;

/**
 * Application Lifecycle Listener implementation class ContextLoadedListener
 *
 */
@WebListener
public class ContextLoadedListener implements ServletContextListener
{

	/**
	 * Default constructor.
	 */
	public ContextLoadedListener()
	{
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0)
	{
		UniqueIdDAOImpl.getInstance().initialize();
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0)
	{
		UniqueIdDAOImpl.getInstance().destroy();
	}

}
