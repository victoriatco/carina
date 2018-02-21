/*******************************************************************************
 * Copyright 2013-2018 QaProSoft (http://www.qaprosoft.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.qaprosoft.carina.grid.servlets;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.codehaus.jettison.json.JSONArray;
import org.openqa.grid.internal.Registry;
import org.openqa.grid.internal.RemoteProxy;
import org.openqa.grid.web.servlet.RegistryBasedServlet;

/**
 * Servlet that retrieves information about connected nodes.
 * 
 * @author Alex Khursevich (alex@qaprosoft.com)
 */
public class ProxyInfo extends RegistryBasedServlet {
	private static final long serialVersionUID = 1224921425278259572L;

	public ProxyInfo() {
        this(null);
    }

    public ProxyInfo(Registry registry) {
        super(registry);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    protected void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONArray data = new JSONArray();
        Iterator<RemoteProxy> itr = this.getRegistry().getAllProxies().iterator();
        while(itr.hasNext())
        {
        		RemoteProxy proxy = itr.next();
        		data.put(proxy.getOriginalRegistrationRequest().toJson());
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.SC_OK);
        response.getWriter().print(data);
	    response.getWriter().close();
    }
}