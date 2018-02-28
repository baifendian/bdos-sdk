package com.bfd.bdos.filebrowser;

import org.junit.Before;
import org.junit.Test;

import com.bfd.bdos.CommonResponse;
import com.bfd.bdos.DefaultBdosClient;
import com.bfd.bdos.exceptions.ClientException;
import com.bfd.bdos.exceptions.ServerException;

public class FileBrowserTest {
	
	FileBrowser fileBrowser = null;
	
	@Before
	public void init() {
		fileBrowser = new FileBrowser(new DefaultBdosClient("6f97780a42394c49fd512f72a37a3ea2", "ce2df611a0607fae806df4b11c3de1fe"));
	}

	@Test
	public void listFile() throws ClientException, ServerException {
		CommonResponse rep = fileBrowser.listFile("/bd-os/jupiter/home/");
		System.out.println(rep.getData());
	}
	
	
	@Test
	public void mkDir() throws ClientException, ServerException {
		CommonResponse rep = fileBrowser.mkDir("/bd-os/jupiter/home/", "demo_api1");
		System.out.println(rep.getData());
	}
	
	@Test
	public void uploadFile() throws ClientException, ServerException {
		CommonResponse rep = fileBrowser.uploadFile("/Users/yunchat/tmp/aa.java", "/bd-os/jupiter/home/demo_api1");
		System.out.println(rep.getData());
	}
	
	@Test
	public void delFile() throws ClientException, ServerException {
		CommonResponse rep = fileBrowser.delFile("/bd-os/jupiter/home/demo_api1/aa.java");
		System.out.println(rep.getData());
	}
	
}
