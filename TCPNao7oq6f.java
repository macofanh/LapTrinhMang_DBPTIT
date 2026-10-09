import java.io.*;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

public class TCPNao7oq6f {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2208;
        String studentCode = "B23DCCN014";
        String qCode = "Nao7oq6f";

        try(Socket s = new Socket(host, port)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

            out.write(studentCode + ";" + qCode);
            out.newLine();
            out.flush();

            String data = in.readLine();
            data = data.trim();
            System.out.println("Nhận" + data);

            Map<Character, Integer> m = new LinkedHashMap<>();
            for(Character i : data.toCharArray()){
                m.put(i, m.getOrDefault(i, 0) + 1);
            }

            StringBuilder res = new StringBuilder();

            for(Map.Entry<Character, Integer> e: m.entrySet()){
                if(e.getValue() > 1){
                    res.append(e.getKey() + ":" + e.getValue() + ",");
                }
            }

            System.out.println(res.toString());
            out.write(res.toString());
            out.newLine();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
