package com.bfd.bdos.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.client.params.HttpClientParams;

import org.apache.http.util.EntityUtils;

public class HttpClient {

	private CloseableHttpClient httpclient;

	public HttpClient() {
		this.httpclient = HttpClients.createDefault();
	}

	/**
	 * 直接在http body 中传递字符串
	 * @param url
	 * @param header
	 * @param httpbody
	 * @return
	 * @throws Exception
	 */
	public String post(String url, Map<String, String> header, String httpbody)
			throws Exception {

		// 创建httppost
		HttpPost httpPost = new HttpPost(url);
		if (header != null) {
			for (Map.Entry<String, String> entry : header.entrySet()) {
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
		}
		httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
		StringEntity se = new StringEntity(httpbody,"utf-8");
		se.setContentType("text/json");
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
				"application/json"));
		httpPost.setEntity(se);

		CloseableHttpResponse response = httpclient.execute(httpPost);
		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// System.out.println(EntityUtils.toString(entity, "UTF-8"));
				return EntityUtils.toString(entity, "UTF-8");
			}
		} finally {
			response.close();
			// this.httpclient.close();
		}
		return null;

	}

	/**
	 * 在http body 中传递post的参数
	 * @param url
	 * @param header
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String post(String url, Map<String, String> header,
			Map<String, String> params) throws Exception {

		// 创建httppost
		HttpPost httpPost = new HttpPost(url);
		if (header != null) {
			for (Map.Entry<String, String> entry : header.entrySet()) {
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
		}

		if (params != null) {
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				formparams.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
			UrlEncodedFormEntity uefEntity;
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httpPost.setEntity(uefEntity);
		}

		CloseableHttpResponse response = httpclient.execute(httpPost);
		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// System.out.println(EntityUtils.toString(entity, "UTF-8"));
				return EntityUtils.toString(entity, "UTF-8");
			}
		} finally {
			response.close();
			// this.httpclient.close();
		}
		return null;

	}

	public String get(String url, Map<String, String> header,
			Map<String, String> params) {
		try {
			// 组装http get参数
			if (params != null) {
				StringBuilder sbParams = new StringBuilder();
				for (Map.Entry<String, String> entry : params.entrySet()) {
					sbParams.append(String.format("%s=%s&", entry.getKey(),
							entry.getValue()));
				}
				String params_tmp = sbParams.toString();
				url = url + "?"
						+ params_tmp.substring(0, params_tmp.length() - 1);
			}
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);

			if (header != null) {
				for (Map.Entry<String, String> entry : header.entrySet()) {
					httpget.setHeader(entry.getKey(), entry.getValue());
				}
			}

			// 执行get请求.
			CloseableHttpResponse response = this.httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				System.out.println("--------------------------------------");
				if (entity != null) {
					return EntityUtils.toString(entity, "UTF-8");
				}
				System.out.println("------------------------------------");
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			// try {
			// httpclient.close();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
		}
		return null;
	}

	public void close() throws IOException {
		this.httpclient.close();
	}

	/**
	 * http client 在http body中直接传递字符串
	 * @param httpclient
	 * @param url
	 * @param header
	 * @param httpbody
	 * @return
	 * @throws Exception
	 */
	public static String post(HttpClient httpclient, String url,
			Map<String, String> header, String httpbody) throws Exception {
		return httpclient.post(url, header, httpbody);
	}

	/**
	 * httpclient 传递post类型的参数
	 * @param httpclient
	 * @param url
	 * @param header
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String post(HttpClient httpclient, String url,
			Map<String, String> header, Map<String, String> params)
			throws Exception {
		return httpclient.post(url, header, params);
	}

	/**
	 * httpclient 传递get类型的参数
	 * @param httpclient
	 * @param url
	 * @param header
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String get(HttpClient httpclient, String url,
			Map<String, String> header, Map<String, String> params)
			throws Exception {
		return httpclient.get(url, header, params);
	}

	/**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
	 */
	private void post11() {
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost(
				"http://localhost:8080/myDemo/Ajax/serivceJ.action");
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("type", "house"));
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					System.out
							.println("--------------------------------------");
					System.out.println("Response content: "
							+ EntityUtils.toString(entity, "UTF-8"));
					System.out
							.println("--------------------------------------");
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 发送 get请求
	 */
	private void gettmp() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet("http://www.baidu.com/");
			System.out.println("executing request " + httpget.getURI());
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				System.out.println("--------------------------------------");
				// 打印响应状态
				System.out.println(response.getStatusLine());
				if (entity != null) {
					// 打印响应内容长度
					System.out.println("Response content length: "
							+ entity.getContentLength());
					// 打印响应内容
					System.out.println("Response content: "
							+ EntityUtils.toString(entity));
				}
				System.out.println("------------------------------------");
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 上传文件
	 */
	// public void upload() {
	// CloseableHttpClient httpclient = HttpClients.createDefault();
	// try {
	// HttpPost httppost = new
	// HttpPost("http://localhost:8080/myDemo/Ajax/serivceFile.action");
	//
	// FileBody bin = new FileBody(new File("F:\\image\\sendpix0.jpg"));
	// StringBody comment = new StringBody("A binary file of some kind",
	// ContentType.TEXT_PLAIN);
	//
	// HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("bin",
	// bin).addPart("comment", comment).build();
	//
	// httppost.setEntity(reqEntity);
	//
	// System.out.println("executing request " + httppost.getRequestLine());
	// CloseableHttpResponse response = httpclient.execute(httppost);
	// try {
	// System.out.println("----------------------------------------");
	// System.out.println(response.getStatusLine());
	// HttpEntity resEntity = response.getEntity();
	// if (resEntity != null) {
	// System.out.println("Response content length: " +
	// resEntity.getContentLength());
	// }
	// EntityUtils.consume(resEntity);
	// } finally {
	// response.close();
	// }
	// } catch (ClientProtocolException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// try {
	// httpclient.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }

}
