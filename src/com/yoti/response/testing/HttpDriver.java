package com.yoti.response.testing;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Base64;

public class HttpDriver {

	private URL url;
	private HttpURLConnection httpCon;

	public URL getUrl() {
		return url;
	}
	
	public void setOutPutStream(String outputJson) {
		try {
			outputJson = outputJson.replace("\\/", "\\");
			DataOutputStream printout;
			byte[] data = outputJson.getBytes("UTF-8"); 
			try {
			printout = new DataOutputStream(httpCon.getOutputStream());
			printout.write(data);
			printout.flush();
			printout.close();
			} catch (ConnectException ex) {
				System.err.println("Unable to Connect!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getInputStream() {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
			    sb.append(line);
			}
			return sb.toString();
		} catch (IOException e) {
			sb.append("Time Out!");
			e.printStackTrace();
		}
		return sb.toString();
    }

	public void setUrl(String url) throws MalformedURLException {
		this.url = new URL(url);
	}
	
	public void openConnection() throws IOException {
		this.httpCon = (HttpURLConnection) url.openConnection();
		this.httpCon.setDoOutput(true);
		this.httpCon.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
	}
	
	public void setAutherizationCredentials(String userName, String password) {
		String userCredentials = userName+":"+password;
		String  authorization = "Basic " + new String(userCredentials);
		byte[] encodedBytes = Base64.getEncoder().encode(authorization.getBytes());
		httpCon.setRequestProperty ("Authorization", encodedBytes.toString());
	}

	public HttpURLConnection getHttpCon() {
		return httpCon;
	}

	public void setRequestMethod(String requestMethod) throws ProtocolException {
		this.httpCon.setRequestMethod(requestMethod);
	}
	
	public void setRequestProperty(String requestProperty) {
		this.httpCon.setRequestProperty("Content-Type", requestProperty);
	}
	
	public int getResponseCode() throws IOException {
		int responseCode = 0;
		try {
		return this.httpCon.getResponseCode();
		} catch (ConnectException e) {
			return responseCode;
		}
	}

}