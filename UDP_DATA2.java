import java.net.*;
import java.util.*;

public class UDP_DATA2 {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2207;
        String studentCode = "B23DCCN014";
        String qCode = "pcJbSqSY";

        try(DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(host);
            String request = ";" + studentCode + ";" + qCode;
            byte[] sendData = request.getBytes();
            socket.send(new DatagramPacket(sendData, sendData.length, address, port));

            byte[] buffer = new byte[4096];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(receivePacket);
            String response = new String(receivePacket.getData(), 0,receivePacket.getLength()).trim();
            System.out.println("Nhan: " + response);

            String[] parts = response.split(";");
            String requestId = parts[0];

            Integer maxx = Integer.MIN_VALUE;
            Integer minn = Integer.MAX_VALUE;
            for(String i: parts[1].split(",")){
                int v = Integer.parseInt(i);
                if(v > maxx){
                    maxx = v;
                }
                if(v < minn){
                    minn = v;
                }
            }

            String result = requestId + ";" + maxx + "," + minn;
            System.out.println("Gui: " + result);
            byte[] resData = result.getBytes();
            socket.send(new DatagramPacket(resData, resData.length, address, port));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
