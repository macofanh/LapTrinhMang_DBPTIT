import java.net.*;

public class UDP_STRING1 {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2208;
        String studentCode = "B23DCCN014";
        String qCode = "E5FC7lN2";

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
            String data = parts[1];

            int[] cnt = new int[256];
            int maxx = 0;
            char maxChar = ' ';

            for(char i: data.toCharArray()){
                cnt[i]++;
                if(cnt[i] > maxx){
                    maxx = cnt[i];
                    maxChar = i;
                }
            }

            StringBuilder pos = new StringBuilder();
            for(int i = 0; i < data.length(); i++){
                if(maxChar == data.charAt(i)){
                    pos.append(i + 1).append(",");
                }
            }

            String result = requestId + ";" + maxChar + ":" + pos.toString();
            System.out.println("Gui: " + result);
            byte[] resData = result.getBytes();
            socket.send(new DatagramPacket(resData, resData.length, address, port));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}