package com.game.common.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;

/**
 * http访问工具类
 * @author lxs
 *
 */
public class HttpAccessUtil {
	public static final String XML_CONTENT_TYPE = "application/xml;";
	public static final String JSON_CONTENT_TYPE = "application/json;";
	public static final String X_WWW_FORM_ULENCODED = "application/x-www-form-urlencoded;";
	private static final String CHARSET = "UTF-8";
	
	/**
	 * 调用post
	 * @param urlName
	 * @param paramStr
	 * @param contentType
	 * @return
	 */
	public static String callPost(String urlName, String paramStr, String contentType) {
		OutputStream oStream = null;
		BufferedReader in = null;
		try {
			URL url = new URL(urlName);
			URLConnection urlConn = url.openConnection();
			HttpURLConnection con = (HttpURLConnection) urlConn;
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type", contentType + " charset=" + CHARSET);
			
			oStream = new DataOutputStream(con.getOutputStream());
			oStream.write(paramStr.getBytes("UTF-8"));
			oStream.flush();

			int responseCode = con.getResponseCode();
			if (responseCode == 200) {
				in = new BufferedReader(new InputStreamReader(con.getInputStream(), CHARSET));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}

				return response.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(oStream != null) {
					oStream.close();
				}
				if(in != null) {
					in.close();
				}
				oStream = null;
				in = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	/**
	 * 调用get
	 * @param urlName
	 * @param contentType
	 * @return
	 */
	public static String callGet(String urlName, String contentType) {
		BufferedReader in = null;
		try {
			URL url = new URL(urlName);
			URLConnection urlConn = url.openConnection();
			HttpURLConnection con = (HttpURLConnection) urlConn;
			con.setRequestMethod("GET");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type", contentType + " charset=" + CHARSET);
			
			int responseCode = con.getResponseCode();
			if (responseCode == 200) {
				in = new BufferedReader(new InputStreamReader(con.getInputStream(), CHARSET));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}

				return response.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(in != null) {
					in.close();
				}
				in = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	/**
	 * 接收到的结果
	 * @param request
	 * @return
	 */
	public static String receiveResult(HttpServletRequest request) {
		StringBuffer response = new StringBuffer();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(request.getInputStream(), CHARSET));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new StringBuffer();
		} finally {
			try {
				if(in != null) {
					in.close();
				}
				in = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return response.toString();
	}
}
