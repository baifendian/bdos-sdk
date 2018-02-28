package com.bfd.bdos;

import java.util.UUID;

import org.junit.Test;

import com.bfd.bdos.datafactory.ide.IDE.EchoType;


public class BDOSTest {

	
	@Test
	public void ideRun() throws Exception {
		BDOS bdos = new BDOS("6f97780a42394c49fd512f72a37a3ea2", "ce2df611a0607fae806df4b11c3de1fe");
		String uuid = UUID.randomUUID().toString();
		bdos.getIde().execute(uuid, "show tables;");
		
	
		for (int i = 0; i < 5; i++) {
			CommonResponse  res = bdos.getIde().executeLog(uuid, EchoType.PROCESS);
			Thread.sleep(1000L);
		}
		CommonResponse  res = bdos.getIde().executeLog(uuid, EchoType.RESULT);
		System.out.println(res.getData());
	}
	
}
