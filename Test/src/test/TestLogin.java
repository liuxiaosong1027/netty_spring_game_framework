package test;

import org.json.JSONObject;

public class TestLogin {
	private String urlStr = "http://127.0.0.1:9001/loginServer/main";
//	private String urlStr = "http://192.168.10.44/loginServer/main";
	
	public String buildMessage() {
		String key = "bcaa52929c8289a1834c8d53126e0ba1";
		JSONObject json = new JSONObject();
		json.put("user_no", 1);
		json.put("os_type", 0);
		json.put("version", "0.0.1");
		json.put("accessToken", "");
		
		JSONObject obj = new JSONObject();
		// test
		int messageNo = 1;
		json.put("message_no", messageNo);
		obj.put("content", "hello");
		
		json.put("message_data", obj);
		
		String signStr = obj.toString() + key;
		json.put("sign", GameUtil.getMd5String(signStr));
		
		return json.toString();
	}
	
	
	public void call() {
		System.out.println(HttpAccessUtil.call(urlStr, buildMessage(), HttpAccessUtil.JSON_CONTENT_TYPE));
	}
	public static void main(String[] args) {
		new TestLogin().call();
	}
}
