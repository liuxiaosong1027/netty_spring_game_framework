package test;

import java.util.TreeMap;

public class Test {
	
	public static void main(String[] args) {
		TreeMap<String, Object> signMap = new TreeMap<>();
		signMap.put("serverCode", "hunan_db");
//		signMap.put("openId", "owILrwFSgc-GVO-vIbFW1_w1rRvk");
		signMap.put("userId", "2");
		signMap.put("unionId", "1");
		signMap.put("gameId", "10001");
//		signMap.put("regTime", "1499225808");
		String scrit = "1234567890987654321";
//		String scrit = "we34RtF";
		
		
//		String KEY_PADDING_STR = "we34RtF";
//		   String _sign =  MD5.md5(KEY_PADDING_STR + "238195hell2001hainan_db88888100");
//		   System.out.println(_sign);
		
//		TreeMap<String, Object> signMap = new TreeMap<>();
//		signMap.put("serverCode", "anhui_db");
//		signMap.put("userId", 1);
//		signMap.put("unionId", "ofb5dvwNLWqTYDmjavekZfnROkYc");
//		signMap.put("gameId", 17002);
//		String scrit = "1234567890987654321";
//		String md5Sign = MD5.getMD5Sign(signMap, scrit);
//		System.out.println(md5Sign);
		
//		TreeMap<String, Object> signMap = new TreeMap<>();
//		signMap.put("aaa", "1");
//		String scrit = "123";
		String md5Sign = MD5.getMD5Sign(signMap, scrit);
		System.out.println(md5Sign);
	}
}