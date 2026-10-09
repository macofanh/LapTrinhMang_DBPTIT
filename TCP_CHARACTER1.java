import java.io.*;
import java.net.*;
import java.util.*;

public class TCP_CHARACTER1 {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2208;
        String studentCode = "B23DCCN014";
        String qCode = "A9OSoV5A";

        try (Socket socket = new Socket(host, port)) {
            socket.setSoTimeout(5000); // tối đa 5s cho mỗi yêu cầu

            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            // a. Gửi studentCode;qCode
            out.write(studentCode + ";" + qCode);
            out.newLine();
            out.flush();

            // b. Nhận danh sách tên miền
            String data = in.readLine();
            System.out.println("Nhận: " + data);

            // c. Lọc các tên miền .edu
            List<String> eduList = new ArrayList<>();
            if (data != null) {
                for (String s : data.split(",")) {
                    String domain = s.trim();
                    if (domain.endsWith(".edu")) {
                        eduList.add(domain);
                    }
                }
            }
            String result = String.join(", ", eduList);
            System.out.println("Gửi: " + result);

            out.write(result);
            out.newLine();
            out.flush();

            // d. Đóng kết nối (try-with-resources tự động đóng socket)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}