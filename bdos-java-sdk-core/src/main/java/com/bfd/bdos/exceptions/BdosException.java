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

package com.bfd.bdos.exceptions;

/**
 * ODPS SDK产生的异常
 *
 * @author shenggong.wang@alibaba-inc.com
 */
@SuppressWarnings("serial")
public class BdosException extends Exception {

  private String requestId;
  String code;

  public String getErrorCode() {
    return code;
  }

  public BdosException() {

  }

  public BdosException(String msg) {
    super(msg);
  }

  public BdosException(String code, String msg) {
    super(msg);
    this.code = code;
  }

  public BdosException(String msg, Throwable t) {
    super(msg, t);
//    if (t instanceof RestException) {
//      this.code = ((RestException) t).getErrorMessage().getErrorcode();
//      this.requestId = ((RestException) t).getErrorMessage().getRequestId();
//    }
  }

  public BdosException(String code, String msg, Throwable t) {
    super(msg, t);
    this.code = code;
  }

  public BdosException(Exception e) {
    super(e);
  }

  /**
   * 获取 失败请求的 RequestID, 如果不是网络请求 返回 null
   *
   * @return requestID
   */
  public String getRequestId() {
    return requestId;
  }
}
