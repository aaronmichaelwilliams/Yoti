package com.yoti.response.testing;

import org.json.simple.JSONArray;

public class TestScenario {
	
	private int testID;
	private String method;
	private String userName;
	private String password;
	private int expectedResponse;
	private String applicationName;
	private String URL;
	private JSONArray permissions;
	private String requestBody;
	private int actualResponse;
	private String responseBody;
	private String domain;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public int getActualResponse() {
		return actualResponse;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	

	public void setActualResponse(int actualResponse) {
		this.actualResponse = actualResponse;
	}
	
	public JSONArray getPermissions() {
		return permissions;
	}
	
	public void setPermissions(JSONArray permissions) {
		this.permissions = permissions;
	}
	

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getRequestBody() {
		return requestBody;
	}
	
	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}
	
	public String getResponseBody() {
		return responseBody;
	}
	
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	
	public int getTestID() {
		return testID;
	}
	
	public void setTestID(int testID) {
		this.testID = testID;
	}
	
	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	
	public int getExpectedResponse() {
		return expectedResponse;
	}
	
	public void setExpectedResponse(int expectedResponse) {
		this.expectedResponse = expectedResponse;
	}
	
	public String getApplicationName() {
		return applicationName;
	}
	
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

}