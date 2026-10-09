import java.io.*;
import java.net.Socket;

public class TCP32hV9ZKM {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2207;
        String studentCode = "B23DCCN014";
        String qCode = "32hV9ZKM";

        try(Socket s = new Socket(host, port)) {
            s.setSoTimeout(5000);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            String res = studentCode + ";" + qCode;
            out.writeUTF(res);
            out.flush();

            int a = in.readInt();
            int b = in.readInt();

            int sum = a + b;
            int product = a * b;

            out.writeInt(sum);
            out.writeInt(product);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
