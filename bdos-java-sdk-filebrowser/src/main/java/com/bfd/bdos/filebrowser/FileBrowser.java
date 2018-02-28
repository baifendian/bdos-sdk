package com.bfd.bdos.filebrowser;

import java.io.File;

import com.bfd.bdos.BaseHttpRequest;
import com.bfd.bdos.CommonResponse;
import com.bfd.bdos.CommonRequest;
import com.bfd.bdos.DefaultBdosClient;
import com.bfd.bdos.FileHttpRequest;
import com.bfd.bdos.exceptions.ClientException;
import com.bfd.bdos.exceptions.ServerException;
import com.bfd.bdos.http.HttpFilePost;
import com.bfd.bdos.http.MethodType;

public class FileBrowser {

	private String BDOS_MODULE_PRODUCT = "bdos";

	private String BDOS_MODULE_PRODUCT_ACTION_UPLOAD_FILE = "/bdos-base/hdfs/uploadToHdfs";

	private String BDOS_MODULE_PRODUCT_ACTION_LIST_FILE = "/bdos-base/hdfs/browser";

	private String BDOS_MODULE_PRODUCT_ACTION_DEL_FILE = "/bdos-base/hdfs/deleteHdfs";

	private String BDOS_MODULE_PRODUCT_ACTION_MKDIR = "/bdos-base/hdfs/mkdirToHdfs";

	private DefaultBdosClient client = null;

	public FileBrowser(DefaultBdosClient client) {
		if (client == null) {
			throw new IllegalArgumentException("You should set the Account (DefaultBdosClient)");
		}
		this.client = client;
	}

	public CommonResponse listFile(String path) throws ClientException, ServerException {
		return listFile(path, 10, 1);
	}

	public CommonResponse listFile(String path, int pageSize, int currentPage) throws ClientException, ServerException {
		CommonRequest request = new CommonRequest(BDOS_MODULE_PRODUCT);
		request.setActionName(BDOS_MODULE_PRODUCT_ACTION_LIST_FILE);
		request.setMethod(MethodType.POST);
		request.putQueryParameter("path", path);
		request.putQueryParameter("pageSize", pageSize);
		request.putQueryParameter("currentPage", currentPage);
		return client.getAcsResponse(request);
	}

	public CommonResponse uploadFile(String absoluteFilePath, String targetPath)
			throws ClientException, ServerException {
		File filAbs = new File(absoluteFilePath);
		return uploadFile(filAbs, targetPath);
	}

	public CommonResponse uploadFile(File file, String targetPath) throws ClientException, ServerException {
		HttpFilePost fileClient = new HttpFilePost(client);
		FileHttpRequest req = new FileHttpRequest(BDOS_MODULE_PRODUCT);
		req.setActionName(BDOS_MODULE_PRODUCT_ACTION_UPLOAD_FILE);
		req.putFile("file", file);
		req.putBodyParameter("path", targetPath);
		CommonResponse rep = fileClient.doAction(req);
		return rep;
	}

	public CommonResponse delFile(String path) throws ClientException, ServerException {
		CommonRequest request = new CommonRequest(BDOS_MODULE_PRODUCT);
		request.setActionName(BDOS_MODULE_PRODUCT_ACTION_DEL_FILE);
		request.setMethod(MethodType.POST);
		request.putQueryParameter("path", path);
		return client.getAcsResponse(request);
	}

	public CommonResponse mkDir(String path, String name) throws ClientException, ServerException {
		CommonRequest request = new CommonRequest(BDOS_MODULE_PRODUCT);
		request.setActionName(BDOS_MODULE_PRODUCT_ACTION_MKDIR);
		request.setMethod(MethodType.POST);
		request.putQueryParameter("path", path);
		request.putQueryParameter("newPath", name);
		return client.getAcsResponse(request);
	}

}
