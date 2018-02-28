/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.bfd.bdos.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.bfd.bdos.CommonRequest;
import com.bfd.bdos.CommonResponse;
import com.bfd.bdos.DefaultBdosClient;
import com.bfd.bdos.exceptions.BdosException;
import com.bfd.bdos.exceptions.ClientException;
import com.bfd.bdos.exceptions.ServerException;
import com.bfd.bdos.utils.GsonUtil;

/**
 * ODPS项目空间
 */
public class Project extends LazyLoad {

  /**
   * 项目空间状态
   */
  public static enum Status {
    /**
     * 正常
     */
    AVAILABLE,
    /**
     * 只读
     */
    READONLY,
    /**
     * 删除
     */
    DELETING,
    /**
     * 冻结
     */
    FROZEN,
    /**
     * 未知
     */
    UNKOWN
  }

//  /**
//   * Project model
//   */
//  @XmlRootElement(name = "Project")
//  static class ProjectModel {
//	  
//    @XmlElement(name = "Code")
//    String code;
//
//    @XmlElement(name = "Name")
//    String name;
//
//    @XmlElement(name = "Comment")
//    String comment;
//
//    @XmlElement(name = "Owner")
//    String owner;
//
//    @XmlElement(name = "CreationTime")
//    Date creationTime;
//
//    @XmlElement(name = "LastModifiedTime")
//    Date lastModified;
//
//    @XmlElement(name = "ProjectGroupName")
//    String projectGroupName;
//
//    @XmlElement(name = "Properties")
//    @XmlJavaTypeAdapter(PropertyAdapter.class)
//    HashMap<String, String> properties;
//
//    @XmlElement(name = "ExtendedProperties")
//    @XmlJavaTypeAdapter(PropertyAdapter.class)
//    HashMap<String, String> extendedProperties;
//
//
//    @XmlElement(name = "State")
//    String state;
//
//
//  }
  /**
   * Created by jiangnan on 16/6/14.
   */
  static class ProjectInfoVo {

      private String code;

      private String enName;

      private String cnName;

      private String description;

      public String getCode() {
          return code;
      }

      public void setCode(String code) {
          this.code = code;
      }

      public String getEnName() {
          return enName;
      }

      public void setEnName(String enName) {
          this.enName = enName;
      }

      public String getCnName() {
          return cnName;
      }

      public void setCnName(String cnName) {
          this.cnName = cnName;
      }

      public String getDescription() {
          return description;
      }

      public void setDescription(String description) {
          this.description = description;
      }

      @Override
      public String toString() {
          return "ProjectInfoVo{" +
                  "code='" + code + '\'' +
                  ", enName='" + enName + '\'' +
                  ", cnName='" + cnName + '\'' +
                  ", description='" + description + '\'' +
                  '}';
      }
  }  
  




  static class Property {

    Property() {
    }

    Property(String name, String value) {
      this.name = name;
      this.value = value;
    }

    @XmlElement(name = "Name")
    String name;

    @XmlElement(name = "Value")
    String value;
  }

  static class Properties {

    @XmlElement(name = "Property")
    List<Property> entries = new ArrayList<Property>();
  }

  static class PropertyAdapter extends XmlAdapter<Properties, HashMap<String, String>> {

    @Override
    public HashMap<String, String> unmarshal(Properties in) throws Exception {
      if (in == null) {
        return null;
      }
      HashMap<String, String> hashMap = new HashMap<String, String>();
      for (Property entry : in.entries) {
        hashMap.put(entry.name, entry.value);
      }
      return hashMap;
    }

    @Override
    public Properties marshal(HashMap<String, String> map) throws Exception {
      if (map == null) {
        return null;
      }
      Properties props = new Properties();
      for (Map.Entry<String, String> entry : map.entrySet()) {
        props.entries.add(new Property(entry.getKey(), entry.getValue()));
      }
      return props;
    }
  }

  private ProjectInfoVo model;
  private DefaultBdosClient client;
  private static final String BDOS_MODULE_PRODUCT = "bdos";
  
  private static final String BDOS_MODULE_PRODUCT_ACTION_USE_PROJECT = "/bdos-base/project/info";

  private HashMap<String, String> properties = new HashMap<String, String>();

  Project(ProjectInfoVo model, DefaultBdosClient  client) {
    this.model = model;
    this.client = client;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void reload() throws BdosException {
//    String resource = ResourceBuilder.buildProjectResource(model.name);
//    Response resp = client.request(resource, "GET", null, null, null);
    
    CommonRequest  req = new CommonRequest(BDOS_MODULE_PRODUCT);
    req.setActionName(BDOS_MODULE_PRODUCT_ACTION_USE_PROJECT);
    req.putQueryParameter("code",model.code);
    CommonResponse rep = null;
    try {
    	rep = client.getAcsResponse(req);
    } catch (Exception e) {
    	throw new BdosException("cann't to get Project info " + model.code, e);
    }
    
    try {
    	model = GsonUtil.jsonToBean(rep.getData(), ProjectInfoVo.class);
    	
    } catch (Exception e) {
      throw new BdosException("Can't bind json to " + ProjectInfoVo.class, e);
    }
    setLoaded(true);
  }
  
//  /**
//   * 获取Project Code
//   *
//   * @return Project编号
//   */
//  public String getCode() {
//    return model.code;
//  } 
//
//  /**
//   * 获取Project名称
//   *
//   * @return Project名称
//   */
//  public String getName() {
//    return model.name;
//  }
//
//  /**
//   * 获取Project注释
//   *
//   * @return Project注释
//   */
//  public String getComment() {
//    if (model.comment == null) {
//      lazyLoad();
//    }
//    return model.comment;
//  }
//
//  /**
//   * 设置Project注释
//   *
//   * @param Project注释
//   */
//  void setComment(String comment) {
//    model.comment = comment;
//  }
//
//  /**
//   * 获得Project所属用户
//   *
//   * @return Project所属用户
//   */
//  public String getOwner() {
//    if (model.owner == null) {
//      lazyLoad();
//    }
//    return model.owner;
//  }
//
//  /**
//   * 获取Project创建时间
//   *
//   * @return Project创建时间
//   */
//  public Date getCreatedTime() {
//    if (model.creationTime == null) {
//      lazyLoad();
//    }
//    return model.creationTime;
//  }
//
//  /**
//   * 获取Project最后修改时间
//   *
//   * @return Project最后修改时间
//   */
//  public Date getLastModifiedTime() {
//    if (model.lastModified == null) {
//      lazyLoad();
//    }
//    return model.lastModified;
//  }
//
//  String getProjectGroupName() {
//    lazyLoad();
//    return model.projectGroupName;
//  }
//
//  /**
//   * 获取Project当前状态
//   *
//   * @return {@link Status}
//   */
//  public Status getStatus() {
//    if (model.state == null) {
//      lazyLoad();
//    }
//
//    Status status = null;
//
//    try {
//      status = Status.valueOf(model.state.toUpperCase());
//    } catch (Exception e) {
//      return Status.UNKOWN;
//    }
//    return status;
//  }
//
//  /**
//   * 获取Project配置信息
//   *
//   * @return 以key, value保存的配置信息
//   */
//  public Map<String, String> getProperties() {
//    lazyLoad();
//    return properties;
//  }
//
//
//  /**
//   * 查询Project指定配置信息
//   *
//   * @param key
//   *     配置项
//   * @return 配置项对应的值
//   */
//  public String getProperty(String key) {
//    lazyLoad();
//    return properties == null ? null : properties.get(key);
//  }

//  /**
//   * 获取 Project 扩展配置信息
//   *
//   * @return 以 key, value 保存的配置信息
//   */
//  public Map<String, String> getExtendedProperties() throws OdpsException {
//    HashMap<String, String> param = new HashMap<String, String>();
//    param.put("extended", null);
//    String resource = ResourceBuilder.buildProjectResource(model.name);
//    ProjectModel extendedModel = client.request(ProjectModel.class, resource, "GET", param);
//    return extendedModel.extendedProperties;
//  }

//  /**
//   * 获取{@link SecurityManager}对象
//   *
//   * @return {@link SecurityManager}对象
//   */
//  public SecurityManager getSecurityManager() {
//    if (securityManager == null) {
//      securityManager = new SecurityManager(model.name, client);
//    }
//    return securityManager;
//  }
//
//  public Map<String, String> getSystemVersion() throws OdpsException {
//    String resource = ResourceBuilder.buildProjectResource(model.name)  + "/system";
//    Response resp = client.request(resource, "GET", null, null, null);
//    try {
//      Map<String, String> map = JSON.parseObject(resp.getBody(), Map.class);
//      return map;
//    } catch (JSONException e) {
//      throw new OdpsException(e.getMessage(), e);
//    }
//  }
//
//  public String getTunnelEndpoint() throws OdpsException {
//    String protocol;
//    try {
//      URI u = new URI(client.getEndpoint());
//      protocol = u.getScheme();
//    } catch (URISyntaxException e) {
//      throw new OdpsException(e.getMessage(), e);
//    }
//
//    String resource = ResourceBuilder.buildProjectResource(model.name).concat("/tunnel");
//    HashMap<String, String> params = new HashMap<String, String>();
//    params.put("service", null);
//    Response resp = client.request(resource, "GET", params, null, null);
//
//    String tunnel;
//    if (resp.isOK()) {
//      tunnel = new String(resp.getBody());
//    } else {
//      throw new OdpsException("Can't get tunnel server address: " + resp.getStatus());
//    }
//
//    return protocol + "://" + tunnel;
//  }

}
