package TCP;

import java.io.*;
import java.net.Socket;

class Customer implements Serializable {
    private static final long serialVersionUID = 20170711L;
    public int id;
    public String code;
    public String name;
    public String dayOfBirth;
    public String userName;

    Customer(int id, String code, String name, String dayOfBirth, String userName){
        this.id = id;
        this.code = code;
        this.name = name;
        this.dayOfBirth = dayOfBirth;
        this.userName = userName;
    }

    public void processAll() {
        String[] w = this.name.trim().toLowerCase().split("\\s+");

        StringBuilder newName = new StringBuilder();
        newName.append(w[w.length - 1].toUpperCase()).append(",");
        for (int i = 0; i < w.length - 1; i++) {
            newName.append(" ").append(Character.toUpperCase(w[i].charAt(0))).append(w[i].substring(1));
        }
        this.name = newName.toString();

        String[] dob = this.dayOfBirth.split("-");
        this.dayOfBirth = dob[1] + "/" + dob[0] + "/" + dob[2];

        StringBuilder newUserName = new StringBuilder();
        for (int i = 0; i < w.length - 1; i++) {
            newUserName.append(w[i].charAt(0));
        }
        newUserName.append(w[w.length - 1]);
        this.userName = newUserName.toString();
    }
}

public class TCP_OBJECT2 {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2209;
        String studentCode = "B23DCCN014";
        String qCode = "sUmCNWR1";

        try(Socket s = new Socket(host, port)) {
            s.setSoTimeout(5000);
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());

            out.writeObject(studentCode + ";" + qCode);
            out.flush();

            Customer customer = (Customer) in.readObject();
            customer.processAll();
            out.writeObject(customer);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}