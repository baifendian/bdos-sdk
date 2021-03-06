package com.bfd.bdos.endpoint;

public class ApiEndPoint {


	public ApiEndPoint() {
		// TODO Auto-generated constructor stub
	}
	
	public static String getEndPoint(String product, String action) {
		if ("a1".equalsIgnoreCase(action)) {
			return "www.baidu.com";
		} else if ("a2".equalsIgnoreCase(action)) {
			return "webapi.cninfo.com.cn/api/public/p_public0006?access_token=d84990daf737ad0c14917b00dbdff949";
		} else if ("token".equalsIgnoreCase(action)) {
			return "webapi.cninfo.com.cn/api-cloud-platform/oauth2/token";
		} else if ("access_token".equalsIgnoreCase(action)) {
			return "newbdos.com/aegis/oauth/access_token";
		} else if ("refresh_token".equalsIgnoreCase(action)) {
			return "newbdos.com/aegis/oauth/refresh_token";
		} else if ("bdos".equalsIgnoreCase(product)) {
			return "newbdos.com" + action;
//		} else if ("fileBrowse".equalsIgnoreCase(product)) {
//			return "bdosnew.combdos-base" + action;			
		} else {
			return "webapi.cninfo.com.cn" + action;
		}
	}
	
	public static String getEndPoint() {
		return "www.baidu.com";
	}

}
