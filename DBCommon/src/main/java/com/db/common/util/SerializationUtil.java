package com.db.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationUtil {
	/**
	 * 序列化
	 * 
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(oos != null) {
					oos.close();
				}
				if(baos != null) {
					baos.close();
				}
				oos = null;
				baos = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 反序列化
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object deserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(bais != null) {
					bais.close();
				}
				if(ois != null) {
					ois.close();
				}
				bais = null;
				ois = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		String hexStr = "AC ED 00 05 73 72 00 13 6A 61 76 61 2E 75 74 69 6C 2E 41 72 72 61 79 4C 69 73 74 78 81 D2 1D 99 C7 61 9D 03 00 01 49 00 04 73 69 7A 65 78 70 00 00 00 0A 77 04 00 00 00 0A 73 72 00 2B 63 6F 6D 2E 64 62 2E 63 6F 6D 6D 6F 6E 2E 6D 61 70 70 65 72 2E 75 73 65 72 2E 44 69 73 74 53 68 61 72 64 48 6F 73 74 42 65 61 6E 9E 2A B3 FA 10 D3 70 61 02 00 03 49 00 05 64 62 5F 69 64 49 00 0A 75 73 65 72 5F 63 6F 75 6E 74 4C 00 04 68 6F 73 74 74 00 12 4C 6A 61 76 61 2F 6C 61 6E 67 2F 53 74 72 69 6E 67 3B 78 70 00 00 00 00 00 00 00 00 74 00 0A 67 61 6D 65 53 68 61 72 64 31 73 71 00 7E 00 02 00 00 00 01 00 00 00 00 74 00 0A 67 61 6D 65 53 68 61 72 64 32 73 71 00 7E 00 02 00 00 00 02 00 00 00 00 74 00 0A 67 61 6D 65 53 68 61 72 64 33 73 71 00 7E 00 02 00 00 00 03 00 00 00 00 74 00 0A 67 61 6D 65 53 68 61 72 64 34 73 71 00 7E 00 02 00 00 00 04 00 00 00 00 74 00 0A 67 61 6D 65 53 68 61 72 64 35 73 71 00 7E 00 02 00 00 00 05 00 00 00 00 74 00 0A 67 61 6D 65 53 68 61 72 64 36 73 71 00 7E 00 02 00 00 00 06 00 00 00 00 74 00 0A 67 61 6D 65 53 68 61 72 64 37 73 71 00 7E 00 02 00 00 00 07 00 00 00 00 74 00 0A 67 61 6D 65 53 68 61 72 64 38 73 71 00 7E 00 02 00 00 00 08 00 00 00 00 74 00 0A 67 61 6D 65 53 68 61 72 64 39 73 71 00 7E 00 02 00 00 00 09 00 00 00 00 74 00 0B 67 61 6D 65 53 68 61 72 64 31 30 78";
//		String hexStr = "\\xAC\\xED\\x00\\x05sr\\x00\\x13java.util.ArrayListx\\x81\\xD2\\x1D\\x99\\xC7a\\x9D\\x03\\x00\\x01I\\x00\\x04sizexp\\x00\\x00\\x00\\x0Aw\\x04\\x00\\x00\\x00\\x0Asr\\x00+com.db.common.mapper.user.DistShardHostBean\\x9E*\\xB3\\xFA\\x10\\xD3pa\\x02\\x00\\x03I\\x00\\x05db_idI\\x00\\x0Auser_countL\\x00\\x04hostt\\x00\\x12Ljava/lang/String;xp\\x00\\x00\\x00\\x00\\x00\\x00\\x00\\x00t\\x00\\x0AgameShard1sq\\x00~\\x00\\x02\\x00\\x00\\x00\\x01\\x00\\x00\\x00\\x00t\\x00\\x0AgameShard2sq\\x00~\\x00\\x02\\x00\\x00\\x00\\x02\\x00\\x00\\x00\\x00t\\x00\\x0AgameShard3sq\\x00~\\x00\\x02\\x00\\x00\\x00\\x03\\x00\\x00\\x00\\x00t\\x00\\x0AgameShard4sq\\x00~\\x00\\x02\\x00\\x00\\x00\\x04\\x00\\x00\\x00\\x00t\\x00\\x0AgameShard5sq\\x00~\\x00\\x02\\x00\\x00\\x00\\x05\\x00\\x00\\x00\\x00t\\x00\\x0AgameShard6sq\\x00~\\x00\\x02\\x00\\x00\\x00\\x06\\x00\\x00\\x00\\x00t\\x00\\x0AgameShard7sq\\x00~\\x00\\x02\\x00\\x00\\x00\\x07\\x00\\x00\\x00\\x00t\\x00\\x0AgameShard8sq\\x00~\\x00\\x02\\x00\\x00\\x00\\x08\\x00\\x00\\x00\\x00t\\x00\\x0AgameShard9sq\\x00~\\x00\\x02\\x00\\x00\\x00\\x09\\x00\\x00\\x00\\x00t\\x00\\x0BgameShard10x";
		System.out.println(CHexConver.checkHexStr(hexStr));
		System.out.println(CHexConver.hexStr2Str(hexStr));
		System.out.println(CHexConver.hexStr2Bytes(hexStr));
		System.out.println(deserialize(CHexConver.hexStr2Bytes(hexStr)));
//		String str = "\\xAC\\xED\\x00\\x05sr\\x00\\x13java.util.ArrayListx\\x81\\xD2\\x1D\\x99\\xC7a\\x9D\\x03\\x00\\x01I\\x00\\x04sizexp\\x00\\x00\\x00\\x0Aw\\x04\\x00\\x00\\x00\\x0Asr\\x00+com.db.common.mapper.user.DistShardHostBean\\x9E*\\xB3\\xFA\\x10\\xD3pa\\x02\\x00\\x03I\\x00\\x05db_idI\\x00\\x0Auser_countL\\x00\\x04hostt\\x00\\x12Ljava/lang/String;xp\\x00\\x00\\x00\\x00\\x00\\x00\\x00\\x00t\\x00\\x0AgameShard1sq\\x00~\\x00\\x02\\x00\\x00\\x00\\x01\\x00\\x00\\x00\\x00t\\x00\\x0AgameShard2sq\\x00~\\x00\\x02\\x00\\x00\\x00\\x02\\x00\\x00\\x00\\x00t\\x00\\x0AgameShard3sq\\x00~\\x00\\x02\\x00\\x00\\x00\\x03\\x00\\x00\\x00\\x00t\\x00\\x0AgameShard4sq\\x00~\\x00\\x02\\x00\\x00\\x00\\x04\\x00\\x00\\x00\\x00t\\x00\\x0AgameShard5sq\\x00~\\x00\\x02\\x00\\x00\\x00\\x05\\x00\\x00\\x00\\x00t\\x00\\x0AgameShard6sq\\x00~\\x00\\x02\\x00\\x00\\x00\\x06\\x00\\x00\\x00\\x00t\\x00\\x0AgameShard7sq\\x00~\\x00\\x02\\x00\\x00\\x00\\x07\\x00\\x00\\x00\\x00t\\x00\\x0AgameShard8sq\\x00~\\x00\\x02\\x00\\x00\\x00\\x08\\x00\\x00\\x00\\x00t\\x00\\x0AgameShard9sq\\x00~\\x00\\x02\\x00\\x00\\x00\\x09\\x00\\x00\\x00\\x00t\\x00\\x0BgameShard10x";
//		try {
//			System.out.println(decode(str));
//			System.out.println(deserialize(decode(str).getBytes()));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
	}
}