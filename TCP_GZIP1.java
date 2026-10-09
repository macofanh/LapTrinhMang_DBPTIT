import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class TCP_GZIP1 {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2210;
        String studentCode = "B23DCCN014";
        String qCode = "JNOHeGoL";       

        try (Socket socket = new Socket(host, port)) {
            socket.setSoTimeout(5000);
            OutputStream rawOut = socket.getOutputStream();

            // a. Gửi thông điệp 1 như một luồng GZIP hoàn chỉnh
            GZIPOutputStream gz1 = new GZIPOutputStream(rawOut, true);
            gz1.write((studentCode + ";" + qCode + "\n").getBytes(StandardCharsets.UTF_8));
            gz1.finish();      // ghi phần kết thúc GZIP (trailer)
            rawOut.flush();

            // b. Nhận
            GZIPInputStream gzIn = new GZIPInputStream(socket.getInputStream());
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(gzIn, StandardCharsets.UTF_8));
            String received = reader.readLine();
            System.out.println("Nhận được: " + received);

            // c. Gửi thông điệp 2 bằng một luồng GZIP mới
            String reversed = new StringBuilder(received).reverse().toString();
            String base64 = Base64.getEncoder()
                    .encodeToString(reversed.getBytes(StandardCharsets.UTF_8));
            String result = reversed + "|" + base64;

            GZIPOutputStream gz2 = new GZIPOutputStream(rawOut, true);
            gz2.write((result + "\n").getBytes(StandardCharsets.UTF_8));
            gz2.finish();
            rawOut.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}