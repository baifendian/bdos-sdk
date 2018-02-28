package com.bfd.bdos;

import java.net.HttpURLConnection;
import java.util.UUID;

import org.apache.http.entity.StringEntity;

import com.bfd.bdos.auth.BdosAccount;
import com.bfd.bdos.auth.BdosCredentials;
import com.bfd.bdos.datafactory.ide.IDE;
import com.bfd.bdos.http.HttpRequest;
import com.bfd.bdos.http.HttpResponse;
import com.bfd.bdos.http.MethodType;
import com.bfd.org.json.JSONObject;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() throws Exception {
		HttpRequest req = new HttpRequest("http://www.baidu.com");
		req.setMethod(MethodType.GET);
		HttpResponse response = HttpResponse.getResponse(req);
		String strResult = new String(response.getHttpContent(), response.getEncoding());
		System.out.println(strResult);

	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp2() throws Exception {

		BdosCredentials account = new BdosAccount("actID", "appsecrect");
		CommonRequest request = new CommonRequest("aa", "a2");
		request.setMethod(MethodType.GET);
		DefaultBdosClient client = new DefaultBdosClient("aa", "ss");
		CommonResponse response = client.getAcsResponse(request);
		// String strResult = new String(response.getData(),
		// response.getEncoding());
		System.out.println(response.getData());

	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp3() throws Exception {

		BdosCredentials account = new BdosAccount("actID", "appsecrect");
		CommonRequest request = new CommonRequest("aa");
		request.setMethod(MethodType.GET);
		request.setActionName("a2");
		DefaultBdosClient client = new DefaultBdosClient("aa", "ss");
		CommonResponse response = client.getAcsResponse(request);
		// String strResult = new String(response.getData(),
		// response.getEncoding());
		System.out.println(response.getData());

		request.setActionName("token");
		response = client.getAcsResponse(request);
		System.out.println(response.getData());

	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp4() throws Exception {

		BdosCredentials account = new BdosAccount("32b5e8b9fe5c4942abba1c272a5e97b3",
				"3d95d146b18a40f9a58df076b562b388");
		CommonRequest request = new CommonRequest("aa");
		request.setMethod(MethodType.POST);
		request.setActionName("token");
		request.putQueryParameter("grant_type", "client_credentials");
		request.putQueryParameter("client_id", account.getAccessKeyId());
		request.putQueryParameter("client_secret", account.getAccessKeySecret());
		DefaultBdosClient client = new DefaultBdosClient("aa", "ss");

		CommonResponse response = client.getAcsResponse(request);
		System.out.println(response.getData());

	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp5() throws Exception {

		BdosCredentials account = new BdosAccount("32b5e8b9fe5c4942abba1c272a5e97b3",
				"3d95d146b18a40f9a58df076b562b388");
		CommonRequest request = new CommonRequest("aa");
		request.setMethod(MethodType.POST);
		request.setActionName("token");
		request.putQueryParameter("grant_type", "refresh_token");
		request.putQueryParameter("client_id", account.getAccessKeyId());
		request.putQueryParameter("client_secret", account.getAccessKeySecret());
		// {"expires_in":3600,"refresh_token":"09e2eb1dd8a17cdcf57918469cdc8e4a","access_token":"5fd08a0b7ea477e399e3701a54c8d01b"}
		request.putQueryParameter("refresh_token", "dbb325ae4da09ed2e8232708e4df20d8");
		DefaultBdosClient client = new DefaultBdosClient("aa", "ss");

		CommonResponse response = client.getAcsResponse(request);

		System.out.println(response.getData());

	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp6() throws Exception {

		DefaultBdosClient client = new DefaultBdosClient("32b5e8b9fe5c4942abba1c272a5e97b3",
				"3d95d146b18a40f9a58df076b562b388");
		CommonRequest request = new CommonRequest("aa");
		request.setActionName("/api/info/p_info3005");
		request.setMethod(MethodType.POST);
		request.putQueryParameter("@limit", 100);
		CommonResponse response = client.getAcsResponse(request);
		System.out.println(response.getData());

	}
	
	public void testApp7() throws Exception {

		DefaultBdosClient client = new DefaultBdosClient("6f97780a42394c49fd512f72a37a3ea2",
				"ce2df611a0607fae806df4b11c3de1fe");
		CommonRequest request = new CommonRequest("bdos");
		request.setActionName("/bdos-base/hdfs/browser");
		request.setMethod(MethodType.POST);
		request.putQueryParameter("pageSize", 10);
		request.putQueryParameter("currentPage", 1);//pageSize=10&currentPage=1
		CommonResponse response = client.getAcsResponse(request);
		System.out.println(response.getData());
		
	}
	
	public void testApp8() throws Exception {

		DefaultBdosClient client = new DefaultBdosClient("6f97780a42394c49fd512f72a37a3ea2",
				"ce2df611a0607fae806df4b11c3de1fe");
		CommonRequest request = new CommonRequest("bdos");
		request.setActionName("/bdos-base/hdfs/browser");
		request.setMethod(MethodType.POST);
		request.putQueryParameter("pageSize", 10);
		request.putQueryParameter("currentPage", 1);//pageSize=10&currentPage=1
		CommonResponse response = client.getAcsResponse(request);
		System.out.println(response.getData());
		
	}	
	
	
	
	public void testMkdirToHdfs() throws Exception {

		DefaultBdosClient client = new DefaultBdosClient("6f97780a42394c49fd512f72a37a3ea2",
				"ce2df611a0607fae806df4b11c3de1fe");
		CommonRequest request = new CommonRequest("bdos");
		request.setActionName("/bdos-base/hdfs/mkdirToHdfs");
		request.setMethod(MethodType.POST);
		request.putQueryParameter("path", "/bd-os/jupiter/home");
		request.putQueryParameter("newPath", "api2");//pageSize=10&currentPage=1
		CommonResponse response = client.getAcsResponse(request);
		System.out.println(response.getData());
		
	}
	
	public void testUploadToHdfs() throws Exception {

//		DefaultBdosClient client = new DefaultBdosClient("6f97780a42394c49fd512f72a37a3ea2",
//				"ce2df611a0607fae806df4b11c3de1fe");
//		CommonRpcRequest request = new CommonRpcRequest("bdos");
//		request.setActionName("/bdos-base/hdfs/uploadToHdfs");
//		request.setMethod(MethodType.POST);
//		
//		MultipartEntity mutiEntity = newMultipartEntity();		
//		
//		
//		request.putQueryParameter("path", "/bd-os/jupiter/home");
//		request.putQueryParameter("newPath", "api2");//pageSize=10&currentPage=1
//		CommonResponse response = client.getAcsResponse(request);
//		System.out.println(response.getData());
		
		
		
		
	}
	
	
	public void testJson() throws Exception {
		JSONObject jsonParam = new JSONObject();  
		jsonParam.put("name", "admin");
		jsonParam.put("pass", "123456");
		System.out.println(jsonParam.toString());
		StringEntity entity = new StringEntity(jsonParam.toString(),"utf-8");
		System.out.println(entity.toString());
	}
	

//	public void testRunTask() throws Exception {
//		DefaultBdosClient client = new DefaultBdosClient("6f97780a42394c49fd512f72a37a3ea2",
//				"ce2df611a0607fae806df4b11c3de1fe");
//		Task task = new Task(client);
//		String uuid = UUID.randomUUID().toString();
//		CommonResponse response =   task.execute(uuid);
//		System.out.println("--------------" + uuid);
//		System.out.println(response.getData());
//		for (int i = 0; i < 5; i++) {
//			response = task.executeLog(uuid, "PROCESS");
//			System.out.println(response.getData());
//			Thread.sleep(1000);
//		}
//		response = task.executeLog(uuid, "RESULT");
//		System.out.println(response.getData());
//	}
	
	
	public void testRunTaskBean() throws Exception {
		DefaultBdosClient client = new DefaultBdosClient("6f97780a42394c49fd512f72a37a3ea2",
				"ce2df611a0607fae806df4b11c3de1fe");
		IDE task = new IDE(client);
		String uuid = UUID.randomUUID().toString();
		CommonResponse response =   task.execute("dfsdsf", uuid, "show tables;");
		System.out.println("--------------" + uuid);
		System.out.println(response.getData());
		for (int i = 0; i < 5; i++) {
			response = task.executeLog(uuid, "PROCESS");
			System.out.println(response.getData());
			Thread.sleep(1000);
		}
		response = task.executeLog(uuid, "RESULT");
		System.out.println(response.getData());
	}
	
	public void testTaskSave() throws Exception {
		DefaultBdosClient client = new DefaultBdosClient("6f97780a42394c49fd512f72a37a3ea2",
				"ce2df611a0607fae806df4b11c3de1fe");
		IDE task = new IDE(client);
		CommonResponse response =   task.save("show1", "show tables;");
		System.out.println(response.getData());
	}	
	

}
