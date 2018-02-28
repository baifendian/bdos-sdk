package com.bfd.bdos.http;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.bfd.bdos.BaseError;
import com.bfd.bdos.BaseHttpRequest;
import com.bfd.bdos.BaseHttpResponse;
import com.bfd.bdos.DefaultBdosClient;
import com.bfd.bdos.FileHttpRequest;
import com.bfd.bdos.UnmarshallerContext;
import com.bfd.bdos.auth.BdosCredentials;
import com.bfd.bdos.auth.BdosSessionCredentials;
import com.bfd.bdos.auth.ICredentialProvider;
import com.bfd.bdos.exceptions.ClientException;
import com.bfd.bdos.exceptions.ServerException;

public class HttpFilePost {

	private DefaultBdosClient client;
	
	private CloseableHttpClient httpclient;
	

	public HttpFilePost (DefaultBdosClient client) {
		this.client = client;
		this.httpclient = HttpClients.createDefault();
	}

    public <T extends BaseHttpResponse>T doAction(BaseHttpRequest<T> request)
            throws ClientException, ServerException {
    	
    		ICredentialProvider provider = client.getCredentialProvider();
    		BdosCredentials credential= provider.getCredentials();
    		
    		if (credential instanceof BdosSessionCredentials) {
    			request.putQueryParameter("access_token", ((BdosSessionCredentials)credential).getSessionToken());
    		}
    		return this.post(request);
    }
    
    

	public <T extends BaseHttpResponse>T post(BaseHttpRequest<T> request) throws ClientException {
		HttpPost post = new HttpPost(request.getEndPoint());  
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		
		if (!(request instanceof FileHttpRequest)) {
			throw new ClientException("SDK.request instance", "Request shoud be instance of FileHttpRequest.");
		}
		FileHttpRequest req = (FileHttpRequest)request;
		Map<String, File> map = req.getFileMap();
		
		
		for (String key : map.keySet()) {
			Object obj = map.get(key);
			builder.addBinaryBody(key, (File)obj);		
		}
		
		Map<String, String> queryParams = request.getQueryParameters();
		for (String key : queryParams.keySet()) {
			String obj = queryParams.get(key);
			builder.addTextBody(key, obj);		
		}
		
		Map<String, String> formParams = request.getBodyParameters();
		for (String key : formParams.keySet()) {
			String obj = formParams.get(key);
			builder.addTextBody(key, obj);		
		}
		
		HttpEntity entity = builder.build();
		post.setEntity(entity);
        try {  
        	CloseableHttpResponse response = httpclient.execute(post);
        	HttpEntity httpEntity = response.getEntity();
        	int status = response.getStatusLine().getStatusCode();
        	if (this.isSuccess(status)) {
        		return readResponse(request.getResponseClass(), response, httpEntity);
        	} else {
	            BaseError error = apiError(httpEntity, status);
	            if (500 <= status) {
	                throw new ServerException(error.getErrorCode(), error.getErrorMessage(), error.getRequestId());
	            } else {
	                throw new ClientException(error.getErrorCode(), error.getErrorMessage(), error.getRequestId());
	            }
        	}
        	
//          System.out.println(EntityUtils.toString(response.getEntity())+"");
//        	HttpEntity httpEntity = response.getEntity();
//        	
//        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
//          
//                System.out.println(EntityUtils.toString(response.getEntity())+"");  
//        } else {
//        	System.out.println(EntityUtils.toString(response.getEntity())+""); 
//        }
//        } catch (InvalidKeyException exp) {
//            throw new ClientException("SDK.InvalidAccessSecret", "Speicified access secret is not valid.");
        } catch (SocketTimeoutException exp) {
            throw new ClientException("SDK.ServerUnreachable",
                "SocketTimeoutException has occurred on a socket read or accept.");
        } catch (IOException exp) {
            throw new ClientException("SDK.ServerUnreachable", "Server unreachable: " + exp.toString());
        }
//        } catch (NoSuchAlgorithmException exp) {
//            throw new ClientException("SDK.InvalidMD5Algorithm", "MD5 hash is not supported by client side.");
//        }
	
		
	}
	
    private BaseError apiError(HttpEntity httpEntity, int status) throws IOException {
        BaseError error = new BaseError();
        String encoding =  httpEntity.getContentEncoding() == null ? null : httpEntity.getContentEncoding().getValue();
        String stringContent = EntityUtils.toString(httpEntity, encoding);
        error.setErrorMessage(stringContent);
        error.setStatusCode(status);
        error.setErrorCode("" + status);
        return error;
    }
	
    private boolean isSuccess(int code) {
        if (200 <= code && 300 > code) {
        	return true;
        	}
        return false;
    }	
	

    private <T extends BaseHttpResponse> T readResponse(Class<T> clasz, CloseableHttpResponse resResponse, HttpEntity httpEntity)
            throws ClientException {
            UnmarshallerContext context = new UnmarshallerContext();
            T response = null;
            String stringContent = null;
            try {
                String encoding =  httpEntity.getContentEncoding() == null ? null : httpEntity.getContentEncoding().getValue();
                stringContent = EntityUtils.toString(httpEntity, encoding);
                response = clasz.newInstance();
            } catch (Exception e) {
                throw new ClientException("SDK.InvalidResponseClass", "Unable to allocate " + clasz.getName() + " class");
            }
            context.setData(stringContent);
            context.setHttpStatus(resResponse.getStatusLine().getStatusCode());
//            context.setHttpResponse(httpResponse);
            response.getInstance(context);
            return response;
    }

//        private String getResponseContent(HttpResponse httpResponse) throws ClientException {
//            String stringContent = null;
//            try {
//                if (null == httpResponse.getEncoding()) {
//                    stringContent = new String(httpResponse.getHttpContent());
//                } else {
//                    stringContent = new String(httpResponse.getHttpContent(), httpResponse.getEncoding());
//                }
//            } catch (UnsupportedEncodingException exp) {
//                throw new ClientException("SDK.UnsupportedEncoding",
//                    "Can not parse response due to un supported encoding.");
//            }
//            return stringContent;
//        }
//        	

	private String getAccessToken() throws ClientException, ServerException {
		BdosCredentials account = client.getCredentialProvider().getCredentials();
        if(account instanceof BdosSessionCredentials) {
        	return ((BdosSessionCredentials) account).getSessionToken();
        }
        return "";
        
	}
	
	
//	public void testUpFile() {
//		CloseableHttpClient httpclient = HttpClients.createDefault();
//        HttpPost post = new HttpPost("http://bdosnew.com/bdos-base/hdfs/uploadToHdfs");  
//
//        File filAbs = new File("/Users/yunchat/tmp/aa.java");  
//         System.out.println("--" + filAbs.length());
//
//        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//        System.out.println(filAbs.getName());
//        builder.addBinaryBody("file", filAbs);
//        //builder.addBinaryBody("file", filAbs, ContentType.DEFAULT_BINARY, "a4.java");
//        builder.addTextBody("path", "/bd-os/jupiter/home/api2");
//        builder.addTextBody("access_token", "5b52e87e71b0b64af2d608df0e76a2fd");
//        HttpEntity entity = builder.build();
//        post.setEntity(entity);
////        post.setParams(HttpParam);
//        try {  
//        	CloseableHttpResponse response = client.execute(post);  
//          System.out.println("------------------");
////          System.out.println(EntityUtils.toString(response.getEntity())+""); 
//        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
//          
//                System.out.println(EntityUtils.toString(response.getEntity())+"");  
//        } else {
//        	System.out.println(EntityUtils.toString(response.getEntity())+""); 
//        }
//        } catch (ClientProtocolException e1) {  
//            e1.printStackTrace();  
//        } catch (IOException e1) {  
//            e1.printStackTrace();  
//        }  
//	}
//	
	
	
//
//	public HttpFilePost() {
//		// TODO Auto-generated constructor stub
//	}

}
