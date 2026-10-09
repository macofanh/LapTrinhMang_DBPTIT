import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class TCP_BYTE1 {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2206;
        String studentCode = "B23DCCN014";
        String qCode = "iEhsiYZT";

        try(Socket s = new Socket(host, port)) {
            s.setSoTimeout(5000);
            InputStream in = s.getInputStream();
            OutputStream out = s.getOutputStream();

            String req = studentCode + ";" + qCode;
            out.write(req.getBytes());
            out.flush();

            byte[] buffer = new byte[4096];
            int len = in.read(buffer);
            String data = new String(buffer, 0, len).trim();
            System.out.println("Nhận: " + data);

            String[] parts = data.split(",");
            int a[] = new int[parts.length];
            for(int i = 0; i < parts.length; i++){
                a[i] = Integer.parseInt(parts[i].trim());
            }
            Arrays.sort(a);

            int maxx = Integer.MAX_VALUE;
            int minVal = 0;
            int maxVal = 0;

            for(int i = 0; i + 1 < a.length; i++){
                int d = a[i + 1] - a[i];
                if(d <= maxx){
                    maxx = d;
                    minVal = a[i];
                    maxVal = a[i + 1];
                }
            }

            String res = maxx + "," + minVal + "," + maxVal;
            System.out.println("Gửi: " + res);

            out.write(res.getBytes());
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
