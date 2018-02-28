package com.bfd.bdos.datafactory.ide;

import com.bfd.bdos.CommonRequest;
import com.bfd.bdos.CommonResponse;
import com.bfd.bdos.DefaultBdosClient;
import com.bfd.bdos.datafactory.task.TExecuteReq;
import com.bfd.bdos.exceptions.ClientException;
import com.bfd.bdos.exceptions.ServerException;
import com.bfd.bdos.http.MethodType;

public class IDE {
	
	private String BDOS_MODULE_PRODUCT = "bdos";
	
	private String BDOS_MODULE_PRODUCT_ACTION_EXECUTE = "/bdos-base/ide/taskExecute";
	
	private String BDOS_MODULE_PRODUCT_ACTION_EXECUTE_LOG = "/bdos-base/ide/executeLog";
	
	private String BDOS_MODULE_PRODUCT_ACTION_TASK_SAVE = "/bdos-base/ide/task/save";
	
	
	private DefaultBdosClient client = null;
	
	public IDE(DefaultBdosClient client) {
		if (client == null) {
			throw new IllegalArgumentException("You should set the Account (DefaultBdosClient)");
		}
		this.client = client;
	}
	
	//projectCode=dfsdsf&execType=ide&execName=9CBFF2EC-10FC-472B-B2D9-ABE2C074D2B8&taskCode=&command=show+tables%3B&typeCode=3&isMr=0&queue=hadoop01&queuePriority=1
//	public CommonResponse execute(String execName) throws ClientException, ServerException {
//		CommonRequest request = new CommonRequest(BDOS_MODULE_PRODUCT);
//		request.setActionName(BDOS_MODULE_PRODUCT_ACTION_EXECUTE);
//		request.setMethod(MethodType.POST);
//		request.putBodyParameter("projectCode", "dfsdsf");
//		request.putBodyParameter("execType", "ide");
//		request.putBodyParameter("execName", execName);
//		request.putBodyParameter("taskCode", "");
//		request.putBodyParameter("command", "show tables;");	
//		request.putBodyParameter("typeCode", "3");			
//		request.putBodyParameter("isMr", "0");
//		request.putBodyParameter("queue", "hadoop01");	
//		request.putBodyParameter("queuePriority", "1");
//		return client.getAcsResponse(request);
//	}
	
	
	public CommonResponse execute(String execName, String command, int commandType) throws ClientException, ServerException {
		TExecuteReq task = new TExecuteReq();
		task.setProjectCode(client.getDefaultProject());
		task.setExecType("ide");
		task.setExecName(execName);
		task.setTaskCode("");
		task.setCommand(command);
		task.setTypeCode(commandType);
		task.setIsMr(0);
		task.setQueue("hadoop01");
		task.setQueuePriority(1);
		
		CommonRequest request = new CommonRequest(BDOS_MODULE_PRODUCT);
		request.setActionName(BDOS_MODULE_PRODUCT_ACTION_EXECUTE);
		request.setMethod(MethodType.POST);
		request.setFormBean(task);
//		request.putBodyParameter("projectCode", "dfsdsf");
//		request.putBodyParameter("execType", "ide");
//		request.putBodyParameter("execName", execName);
//		request.putBodyParameter("taskCode", "");
//		request.putBodyParameter("command", "show tables;");	
//		request.putBodyParameter("typeCode", "3");			
//		request.putBodyParameter("isMr", "0");
//		request.putBodyParameter("queue", "hadoop01");	
//		request.putBodyParameter("queuePriority", "1");
		return client.getAcsResponse(request);
	}
	
	
	
	//	execName:452F120B-35B1-41F4-B333-007D93D8A930
	//	lineFrom:0
	//	lineTo:1000
	//	echoType:PROCESS
	public CommonResponse executeLog(String execName, EchoType echoType) throws ClientException, ServerException {
		CommonRequest request = new CommonRequest(BDOS_MODULE_PRODUCT);
		request.setActionName(BDOS_MODULE_PRODUCT_ACTION_EXECUTE_LOG);
		request.setMethod(MethodType.POST);
		request.putBodyParameter("projectCode", client.getDefaultProject());
		request.putBodyParameter("execName", execName);
		request.putBodyParameter("lineFrom", "0");
		request.putBodyParameter("lineTo", "1000");	
		request.putBodyParameter("echoType", echoType);			
		return client.getAcsResponse(request);
	}
	
	public CommonResponse save(String name, String command, String remark) throws ClientException, ServerException {
		CommonRequest request = new CommonRequest(BDOS_MODULE_PRODUCT);
		request.setActionName(BDOS_MODULE_PRODUCT_ACTION_TASK_SAVE);
		request.setMethod(MethodType.POST);	
//		request.putBodyParameter("code", "dfsdsf");
		request.putBodyParameter("projectCode", client.getDefaultProject());
		request.putBodyParameter("typeCode", "3");
		request.putBodyParameter("name", name + ".hql");
		request.putBodyParameter("mr", "0");		
		request.putBodyParameter("remark", remark);		
		request.putBodyParameter("pid", "");
		request.putBodyParameter("command", command);
		request.putBodyParameter("queue", "hadoop01");		
		request.putBodyParameter("priority", 1);			
		return client.getAcsResponse(request);
	}

	public enum EchoType {
	    // 客户端异常
	    PROCESS,

	    // 服务端异常
	    RESULT,
	}

	
}
