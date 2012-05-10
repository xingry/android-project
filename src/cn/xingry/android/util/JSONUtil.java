package cn.xingry.android.util;

import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONObject;

import android.util.Log;
/**
 * JSON处理工具类，用于从指定URL通过HTTP连接获取JSON对象。
 * @author xingruyi
 *
 */
public class JSONUtil {
	private static final String TAG =  JSONUtil.class.getSimpleName();
	
	/**
	 * 获取JSON内容
	 */
	public static JSONObject getJSON(String url) throws Exception {
		return new JSONObject(getRequest(url));
	}
	/**
	 * 发送请求获取服务端返回内容
	 */
	private static String getRequest(String url) throws Exception {
		return getRequest(url,new DefaultHttpClient(new BasicHttpParams()));
	}
	/**
	 * 发送请求获取服务端内容
	 */
	private static String getRequest(String url , DefaultHttpClient client	) throws Exception {
		String result = null;
		int statusCode = 0;
		HttpGet getMethod = new HttpGet(url);
		Log.d(TAG,"do the getRequest,url=" + url);
		try {
			HttpResponse response = client.execute(getMethod);
			statusCode = response.getStatusLine().getStatusCode();
			Log.d(TAG,"statusCode = " + statusCode	);
			result = retrieveInputStream(response.getEntity());
		}catch(Exception e	) {
			Log.e(TAG,e.getMessage());
			throw new Exception(e);
		}finally {
			getMethod.abort();
		}
		return result;
	}
	
	/**
	 * 将服务端响应转换为字符串
	 */
	private static String retrieveInputStream(HttpEntity entity) {
		int length = (int)entity.getContentLength();
		if(length < 0) length = 10000;
		StringBuffer buffer = new StringBuffer(length);
		try {
			InputStreamReader reader = new InputStreamReader(entity.getContent());
			char[] buf = new char[length];
			int count;
			while(( count = reader.read(buf,0,length-1)) >0 ) {
				buffer.append(buf,0,count);
			}
		}catch(Exception ex) {
			Log.e(TAG, ex.getMessage());
		}
		return buffer.toString();
	} 
	
	
	
}
