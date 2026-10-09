import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCP1WcqlHoC {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2206;
        String studentCode = "B23DCCN014";
        String qCode = "1WcqlHoC";

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

            int max1 = Integer.MIN_VALUE, idx1 = -1;
            int max2 = Integer.MIN_VALUE, idx2 = -1;

            for(int i = 0; i < a.length; i++){
                if(max1 < a[i]){
                    max2 = max1;
                    idx2 = idx1;
                    max1 = a[i];
                    idx1 = i;
                } else if(max2 < a[i] && a[i] < max1){
                    max2 = a[i];
                    idx2 = i;
                }
            }

            String res = max2 + "," + idx2;
            System.out.println("Gửi: " + res);

            out.write(res.getBytes());
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
