import java.net.*;
import java.io.*;

public class UDP_STRING2 {
public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2208;
        String studentCode = "B23DCCN014";
        String qCode = "XyVqlxMY";
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(host);
            String request = ";" + studentCode + ";" + qCode;
            byte[] sendData = request.getBytes();
            socket.send(new DatagramPacket(sendData, sendData.length, address, port));
            byte[] buffer = new byte[4096];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(receivePacket);
            String response = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
            System.out.println("Nhan: " + response);

            String[] parts = response.split(";");
            String requestId = parts[0];
            String data = parts[1];
            StringBuilder res = new StringBuilder();

            for(String i: parts[1].split("\\s+")){
                res.append(Character.toUpperCase(i.charAt(0))).append(i.substring(1).toLowerCase()).append(" ");
            }

            String result = requestId + ";" + res;
            System.out.println("Gui: " + result);
            byte[] resData = result.getBytes();
            socket.send(new DatagramPacket(resData, resData.length, address, port));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}