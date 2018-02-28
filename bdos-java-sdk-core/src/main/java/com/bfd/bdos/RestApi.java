package com.bfd.bdos;

import com.bfd.bdos.http.MethodType;

public class RestApi {

	private DefaultBdosClient client;
	
	public RestApi(DefaultBdosClient client) {
		this.client = client;
	}
	
	public String commonApi1() throws Exception {
		CommonRequest request = new CommonRequest("bdos");
		request.setActionName("/bdos-base/project/list");
		request.setMethod(MethodType.POST);
		request.putQueryParameter("limit", 10);
		request.putQueryParameter("pageNum", 1);
		CommonResponse response = client.getAcsResponse(request);
		System.out.println(response.getData());
		return response.getData();
	}
	
	public String commonApi() throws Exception {
		CommonRequest request = new CommonRequest("bdos");
		request.setActionName("/aegis/oauth/token/getUser");
		request.setMethod(MethodType.POST);
		CommonResponse response = client.getAcsResponse(request);
		System.out.println(response.getData());
		return response.getData();		
	}

}
