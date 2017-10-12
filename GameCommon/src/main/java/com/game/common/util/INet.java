package com.game.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class INet {
	public static long inetAton(String ip) {
		try {
			InetAddress address = InetAddress.getByName(ip);
			byte[] bytes = address.getAddress();
			long val = 0;
			for (int i = 0; i < bytes.length; i++) {
				val <<= 8;
				val |= bytes[i] & 0xff;
			}
			return val;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static String inetNtoa(long id) {
		byte[] b = new byte[] {(byte)(id >> 24), (byte)(id >> 16), (byte)(id >> 8), (byte)id};
	    try {
	        return InetAddress.getByAddress(b).getHostAddress();
	    } catch (UnknownHostException e) {
	    	e.printStackTrace();
	    }
	    return null;
	}
}
