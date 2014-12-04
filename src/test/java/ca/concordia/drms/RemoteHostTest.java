package ca.concordia.drms;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class RemoteHostTest {

	public static void main(String[] args) {
		try {
			byte[] buff = new String("Hello World!").getBytes();
			int port = 2222;
			DatagramSocket remote = new DatagramSocket();
			InetAddress address = InetAddress.getByName("192.168.2.26");
			address = InetAddress.getByName("Amritansh-HP");
			address = InetAddress.getByName("gost-via-mac");
			DatagramPacket request = new DatagramPacket(buff, buff.length, address, port);
			remote.send(request);
			byte[] buf = new byte[1000];
			DatagramPacket recv = new DatagramPacket(buf, buf.length);
			remote.receive(recv);
			System.out.println(recv.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
