package com.bfd.bdos.http;

import java.io.File;

import com.bfd.bdos.CommonResponse;
import com.bfd.bdos.DefaultBdosClient;
import com.bfd.bdos.FileHttpRequest;

import junit.framework.TestCase;

public class HttpFilePostTest extends TestCase {


	public void testDoAction()  {
		try {
			DefaultBdosClient client = new DefaultBdosClient("6f97780a42394c49fd512f72a37a3ea2",
					"ce2df611a0607fae806df4b11c3de1fe");
			HttpFilePost fileClient = new HttpFilePost(client);
			FileHttpRequest req = new FileHttpRequest("bdos");
			req.setActionName("/bdos-base/hdfs/uploadToHdfs");
			File filAbs = new File("/Users/yunchat/tmp/aa.java");
			req.putFile("file", filAbs);
			req.putBodyParameter("path", "/bd-os/jupiter/home/abc");
			CommonResponse rep =  fileClient.doAction(req);
			System.out.println(rep.getData());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
