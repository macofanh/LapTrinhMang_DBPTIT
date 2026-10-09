import java.io.*;
import java.net.Socket;

public class TCP_DATA2 {

    public static char shiftChar(char c, int shift) {
        int s = (shift % 26 + 26) % 26;

        if (c >= 'a' && c <= 'z') {
            return (char) ((c - 'a' - s + 26) % 26 + 'a');
        }
        if (c >= 'A' && c <= 'Z') {
            return (char) ((c - 'A' - s + 26) % 26 + 'A');
        }
        return c;
    }

    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2207;
        String studentCode = "B23DCCN014";
        String qCode = "VudhbZPM";

        try (Socket s = new Socket(host, port)) {
            s.setSoTimeout(5000);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            String res = studentCode + ";" + qCode;
            out.writeUTF(res);
            out.flush();

            String str = in.readUTF();
            int x = in.readInt();

            System.out.println("Nhận từ Server: Str = " + str + ", X = " + x);

            StringBuilder rs = new StringBuilder();
            for (char i : str.toCharArray()) {
                rs.append(shiftChar(i, x));
            }

            System.out.println("Chuỗi giải mã: " + rs.toString());
            
            out.writeUTF(rs.toString());
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}