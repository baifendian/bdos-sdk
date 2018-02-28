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
package com.bfd.bdos.reader;

import java.util.HashMap;
import java.util.Map;

public class HtmlReader implements Reader {

    Map<String, String> map = new HashMap<String, String>();

    @Override
    public Map<String, String> read(String response, String endpoint) {
    	map.put("data",  response);
        return map;
    }
    
    @Override
    public Map<String, String> readForHideArrayItem(String response, String endpoint) {
    	map.put("data",  response);
        return map;
    }

}