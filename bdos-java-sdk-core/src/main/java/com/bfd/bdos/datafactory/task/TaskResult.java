package com.bfd.bdos.datafactory.task;

import java.util.List;


public class TaskResult {

	private String scriptType;//脚本类型
	//现在只针对hive有用，查询显示时的表头集合，即列名，在文本第一行。
	private Object result;
	
	private List<String> pl_output;//plsql output
	/*
	 * result：
	 * 对于hive类型脚本，就除去表头后的数据信息，就是从第二行开始之后的信息。
	 * 对于其他脚本类型是全部输出信息。
	 */
	//private String result;
	private int lineNumber;//文件结束的行号
	private int pos;//偏移量
	private String echoType;//要读取的回显信息类型，process、result
	private boolean complete=false;
	private String queryName;
	private int status;
	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	public String getScriptType() {
		return scriptType;
	}
	public void setScriptType(String scriptType) {
		this.scriptType = scriptType;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getEchoType() {
		return echoType;
	}
	public void setEchoType(String echoType) {
		this.echoType = echoType;
	}
	public String getQueryName() {
		return queryName;
	}
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public List<String> getPl_output() {
		return pl_output;
	}
	public void setPl_output(List<String> pl_output) {
		this.pl_output = pl_output;
	}

}
