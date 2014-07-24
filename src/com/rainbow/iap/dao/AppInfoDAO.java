package com.rainbow.iap.dao;

import com.rainbow.iap.entity.AppInfo;

public interface AppInfoDAO
{
	public AppInfo getByCpIdAndAppId(String cpId, String appId);
}
