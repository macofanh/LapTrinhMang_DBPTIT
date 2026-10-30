package UDP;
import java.net.*;
import java.io.*;

class Student implements Serializable {
    private static final long serialVersionUID = 20171107;
    public String id;
    public String code;
    public String name;
    public String email;

    public Student(String id, String code, String name, String email){
        this.id = id;
        this.code = code;
        this.name = name;
        this.email = email;
    }

    public Student(String code) {
        this.code = code;
    }
}

public class STRING1 {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2209;
        String studentCode = "B23DCCN014";
        String qCode = "CDfy5JLF";
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(host);
            String request = ";" + studentCode + ";" + qCode;
            byte[] sendData = request.getBytes();
            socket.send(new DatagramPacket(sendData, sendData.length, address, port));

            byte[] receiveBuffer = new byte[2048];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            byte[] fullData = receivePacket.getData();
            
            // Tách 8 byte đầu lấy requestId
            String requestId = new String(fullData, 0, 8);

            // Đọc đối tượng Student từ các byte còn lại (từ vị trí byte thứ 8)
            ByteArrayInputStream bais = new ByteArrayInputStream(fullData, 8, receivePacket.getLength() - 8);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Student student = (Student) ois.readObject();

            // c. Xử lý chuẩn hóa tên và tạo email
            String rawName = student.name.trim().toLowerCase();
            String[] words = rawName.split("\\s+");

            // 1. Chuẩn hóa tên (Chữ cái đầu in hoa, chữ còn lại in thường)
            StringBuilder normalizedName = new StringBuilder();
            for (String word : words) {
                normalizedName.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
            }
            student.name = (normalizedName.toString().trim());

            // 2. Tạo email: Tên chính + chữ cái đầu Họ & Tên đệm + @ptit.edu.vn
            // Ví dụ: nguyen van tuan nam -> nam + n + v + t -> namnvt@ptit.edu.vn
            String mainName = words[words.length - 1]; // Từ cuối cùng là tên chính
            StringBuilder emailBuilder = new StringBuilder(mainName);

            for (int i = 0; i < words.length - 1; i++) {
                emailBuilder.append(words[i].charAt(0));
            }
            emailBuilder.append("@ptit.edu.vn");
            student.email = emailBuilder.toString();

            // Đóng gói dữ liệu gửi lại: 8 byte requestId + các byte Object Student
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            
            // Ghi 8 byte requestId trước
            baos.write(requestId.getBytes());
            
            // Ghi Object Student vào sau
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(student);
            oos.flush();

            byte[] responseData = baos.toByteArray();
            socket.send(new DatagramPacket(responseData, responseData.length, receivePacket.getAddress(), receivePacket.getPort()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}