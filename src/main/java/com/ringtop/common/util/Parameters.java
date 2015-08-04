package com.ringtop.common.util;

import java.util.Map;

/**
 * 公共参数配置类
 * @author Administrator
 *
 */
public class Parameters {
	
	public static final String webRoot				= "RTDiscoveryBackstage01";
	public static final String pathPrefix			= "/" + webRoot + "/";
	public static final String servletPathPrdfix	= "/";	
	public static final String primevalPW			= "666666";
	public static final String jndiName				= "rtdiscovery";
	//日志文件所在的上级目录
	public static final String logDirectory 		= "Log";
	
	//替代传输服务的占位符
	public static final String DiscoveryDossierCamionFlag =  "c5c96bc89d90440a9b18ce189207df2a";
	
	public static Map<String, String> map = null;
	
}
