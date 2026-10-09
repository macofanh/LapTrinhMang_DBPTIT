package TCP;
import java.io.*;
import java.net.Socket;

public class TCP_OBJECT1 {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2209;
        String studentCode = "B23DCCN014";
        String qCode = "VN3dNHK8";

        try (Socket socket = new Socket(host, port)) {
            socket.setSoTimeout(5000);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            oos.writeObject(studentCode + ";" + qCode);
            oos.flush();

            Laptop laptop = (Laptop) ois.readObject();

            String name = laptop.name.trim();
            String[] words = name.split("\\s+");
            if (words.length > 1) {
                String temp = words[0];
                words[0] = words[words.length - 1];
                words[words.length - 1] = temp;
            }
            laptop.name = String.join(" ", words);

            int q = laptop.quantity;
            int rs = 0;
            while (q > 0) {
                rs = rs * 10 + q % 10;
                q /= 10;
            }
            laptop.quantity = rs;

            oos.writeObject(laptop);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}