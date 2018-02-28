package com.bfd.bdos;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.bfd.bdos.BaseHttpRequest;
import com.bfd.bdos.CommonResponse;
import com.bfd.bdos.endpoint.ApiEndPoint;
import com.bfd.bdos.http.FormatType;

public class FileHttpRequest extends BaseHttpRequest<CommonResponse> {
	
	private Map<String, File> fileMap = new HashMap<String, File>();
	
    public Map<String, File> getFileMap() {
		return fileMap;
	}

	public FileHttpRequest(String product) {
        super(product);
        setAcceptFormat(FormatType.FORM_DATA);
    }
    
    public void putFile(String key, File file) {
    	fileMap.put(key, file);
    }
    
    public void clearFile() {
    	fileMap.clear();
    	fileMap = null;
    	fileMap = new HashMap<String, File>();
    }
    
	
    @Override
    public Class<CommonResponse> getResponseClass() {
        return CommonResponse.class;
    }
    
    
    @Override
    public String getEndPoint() {
    	System.out.println("enter into getEndPoint");
    	String endpoint = ApiEndPoint.getEndPoint(super.getProduct(), super.getActionName());
        StringBuilder urlBuilder = new StringBuilder("");
        urlBuilder.append(this.getProtocol().toString());
        urlBuilder.append("://").append(endpoint);
    	
        return urlBuilder.toString();
    }

}
