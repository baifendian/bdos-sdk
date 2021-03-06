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

package com.bfd.bdos.auth;

/**
 * @author yunchat
 * Simple session credentials with AccessKeyID, AccessKeySecret and session token.
 */
public class BasicSessionCredentials implements BdosCredentials {

    protected final long roleSessionDurationSeconds;
    private long sessionStartedTimeInMilliSeconds = 0;
    private final double expireFact = 0.8;
    
    private final String accessKeyId;
    private final String accessKeySecret;
    private String sessionToken;
    private String refreshToken;

    public BasicSessionCredentials(String accessKeyId, String accessKeySecret,
                                   String sessionToken, String refreshToken) {
        this(accessKeyId, accessKeySecret, sessionToken, refreshToken, 0);
    }

    public BasicSessionCredentials(String accessKeyId, String accessKeySecret,
                                   String sessionToken, String refreshToken, long roleSessionDurationSeconds) {
        if (accessKeyId == null) {
            throw new IllegalArgumentException("Access key ID cannot be null.");
        }
        if (accessKeySecret == null) {
            throw new IllegalArgumentException("Access key secret cannot be null.");
        }

        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.sessionToken = sessionToken;
        this.refreshToken = refreshToken;
        this.roleSessionDurationSeconds = roleSessionDurationSeconds;
        this.sessionStartedTimeInMilliSeconds = System.currentTimeMillis();
    }

    @Override
    public String getAccessKeyId() {
        return accessKeyId;
    }

    @Override
    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public String getSessionToken() {
        return sessionToken;
    }
    
    public String getRefreshToken() {
    	return refreshToken;
    }

    public boolean willSoonExpire() {
        if (roleSessionDurationSeconds == 0) {
            return false;
        }
        long now = System.currentTimeMillis();
        return roleSessionDurationSeconds * expireFact < (now - sessionStartedTimeInMilliSeconds) / 1000.0;
    }
    
    @Override
    public String toString() {
    	return "BasicSessionCredentials [accessKeyId=" + accessKeyId + ", accessKeySecret=" + accessKeySecret +   ", sessionToken=" + sessionToken + " ]";
    }

}
