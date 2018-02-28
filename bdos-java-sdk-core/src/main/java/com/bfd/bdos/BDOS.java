package com.bfd.bdos;

import com.bfd.bdos.datafactory.ide.IDE;
import com.bfd.bdos.model.Projects;

public class BDOS {

	private DefaultBdosClient client;

	private String defaultProject;

	private Projects projects;

	private String homePath = "/bd-os/jupiter/home";

	private RestApi restApi;

	private String userAgent;

	private String currentPath;

	private IDE ide;

	public String getHomePath() {
		return homePath;
	}

	public void setHomePath(String homePath) {
		this.homePath = homePath;
	}

	public String getCurrentPath() {
		if (currentPath == null) {
			return homePath;
		}
		return currentPath;
	}

	public void setCurrentPath(String currentPath) {
		this.currentPath = currentPath;
	}

	public DefaultBdosClient getClient() {
		return client;
	}

	public void setClient(DefaultBdosClient client) {
		this.client = client;
	}

	public Projects getProjects() {
		return projects;
	}

	/**
	 * 设置访问API时附加的User-Agent信息
	 *
	 * @param userAgent
	 *            User Agent信息
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * 获取访问API时附加的User-Agent信息
	 *
	 * @return User Agent信息
	 */
	public String getUserAgent() {
		return userAgent;
	}

	public RestApi getRestApi() {
		return restApi;
	}

	public void setRestApi(RestApi restApi) {
		this.restApi = restApi;
	}

	public BDOS(String accesskeyId, String secret) {
		client = new DefaultBdosClient(accesskeyId, secret);
		projects = new Projects(client);
		restApi = new RestApi(client);
		ide = new IDE(client);
	}

	/**
	 * 取得当前对象的默认{@link Project}名称
	 *
	 * @return 默认{@link Project}名称
	 */
	public String getDefaultProject() {
		return defaultProject;
	}

	/**
	 * 指定默认使用的{@link Project}名称
	 *
	 * @param defaultProject
	 *            默认{@link Project}名称
	 */
	public void setDefaultProject(String defaultProject) {
		this.defaultProject = defaultProject;
		client.setDefaultProject(defaultProject);
	}

	public IDE getIde() {
		return ide;
	}

	public void setIde(IDE ide) {
		this.ide = ide;
	}

	// @Override
	// public BDOS clone() {
	// return new BDOS(this);
	// }
	//
	// public Odps(Odps odps) {
	// this(odps.account);
	// setDefaultProject(odps.getDefaultProject());
	// setUserAgent(odps.getUserAgent());
	// setEndpoint(odps.getEndpoint());
	// setLogViewHost(odps.getLogViewHost());
	// client.setIgnoreCerts(odps.getRestClient().isIgnoreCerts());
	// instances.setDefaultRunningCluster(odps.instances.getDefaultRunningCluster());
	// }

}
