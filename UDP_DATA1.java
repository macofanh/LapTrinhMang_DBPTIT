import java.net.*;
import java.util.*;

public class UDP_DATA1 {
    public static void main(String[] args) {
        String host = "36.50.135.242";   // đổi thành IP/hostname của server
        int port = 2207;
        String studentCode = "B23DCCN014";
        String qCode = "WFDuuIoM";

        try(DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(host);

            // a. Gửi ;studentCode;qCode
            String request = ";" + studentCode + ";" + qCode;
            byte[] sendData = request.getBytes();
            socket.send(new DatagramPacket(sendData, sendData.length, address, port));

            // b. Nhận requestId;n;A1,A2,...
            byte[] buffer = new byte[4096];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(receivePacket);
            String response = new String(receivePacket.getData(), 0,receivePacket.getLength()).trim();
            System.out.println("Nhan: " + response);

            String[] parts = response.split(";");
            String requestId = parts[0];
            int n = Integer.parseInt(parts[1].trim());

            boolean[] present = new boolean[n + 1];
            if (parts.length > 2 && !parts[2].trim().isEmpty()) {
                for (String s : parts[2].split(",")) {
                    s = s.trim();
                    if (s.isEmpty()) continue;
                    int v = Integer.parseInt(s);
                    if (v >= 1 && v <= n) present[v] = true;
                }
            }

            // c. Tìm các giá trị còn thiếu trong [1..n]
            StringBuilder missing = new StringBuilder();
            for (int i = 1; i <= n; i++) {
                if (!present[i]) {
                    if (missing.length() > 0) missing.append(",");
                    missing.append(i);
                }
            }

            String result = requestId + ";" + missing;
            System.out.println("Gui: " + result);
            byte[] resData = result.getBytes();
            socket.send(new DatagramPacket(resData, resData.length, address, port));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}