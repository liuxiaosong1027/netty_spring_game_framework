package test;

import java.security.MessageDigest;

public class GameUtil {
	/**
	 * 取得md5�?
	 * @param str
	 * @return
	 */
	public static String getMd5String(String str) {
		try {
			// 拿到�?个MD5转换器（如果想要SHA1参数换成”SHA1”）  
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			// 输入的字符串转换成字节数�?
			byte[] inputByteArray = str.getBytes("UTF-8");
			// inputByteArray是输入字符串转换得到的字节数�?
			messageDigest.update(inputByteArray);
			// 转换并返回结果，也是字节数组，包�?16个元�?
			byte[] resultByteArray = messageDigest.digest();
			// 字符数组转换成字符串返回  
			return byteArrayToHex(resultByteArray);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 字节数组转十六进制字符串
	 * @param byteArray
	 * @return
	 */
	private static String byteArrayToHex(byte[] byteArray) {
		// 首先初始化一个字符数组，用来存放每个16进制字符
		char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'a','b','c','d','e','f' };
		// new�?个字符数组，这个就是用来组成结果字符串的（解释一下：�?个byte是八位二进制，也就是2位十六进制字符（2�?8次方等于16�?2次方））
		char[] resultCharArray = new char[byteArray.length * 2];
		// 遍历字节数组，�?�过位运算（位运算效率高），转换成字符放到字符数组中�?
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b& 0xf];
		}
		// 字符数组组合成字符串返回
		return new String(resultCharArray);
	}
}
