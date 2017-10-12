package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TestClient implements Runnable {
	private Socket socket = null;
	private DataOutputStream out = null;
	private DataInputStream in = null;
	
	@Override
	public void run() {
		try {
			socket = new Socket();
			InetSocketAddress endPoint = new InetSocketAddress("localhost", 8001);
			socket.connect(endPoint);
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
//			while(true) {
			for(int i=0; i<1; i++) {
				byte[] content = "hello world!".getBytes();
				int contentLength = content.length;
				short messageId = 1100;
				int length = 2 + 2 + 2 + contentLength;
//				System.out.println("length:" + length + ", messageId:" + messageId + ", content:" + (contentLength + 2));
				out.writeShort(length);
				out.writeShort(messageId);
				out.writeShort(contentLength);
				out.write(content);
				out.flush();
				
				int size = in.readShort();
				int mid = in.readShort();
				if(mid == 1000) {
					// 系统消息
					int errorCode = in.readInt();
					System.out.println("result : length is " + size + ", messageId is " + mid + ", errorCode is " + errorCode);
				} else {
					int bytesSize = in.readShort();
					byte[] result = new byte[bytesSize];
					in.read(result);
					System.out.println("result : length is " + size + ", messageId is " + mid + ", content size is " + bytesSize + " content is " + new String(result));
				}
				Thread.sleep(10000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		for(int i=0; i<10000; i++) {
			new Thread(new TestClient()).start();
		}
	}
}
