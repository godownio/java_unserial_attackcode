//package org.example;
//
//import org.apache.shiro.crypto.AesCipherService;
//import org.apache.shiro.util.ByteSource;
//
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
//public class ShiroEncode550 {
//    public static void main(String []args) throws Exception {
//        byte[] payloads = Files.readAllBytes(Paths.get("E:\\CODE_COLLECT\\Idea_java_ProTest\\Test\\cc6.ser"));
//        AesCipherService aes = new AesCipherService();
//        byte[] key = java.util.Base64.getDecoder().decode("kPH+bIxk5D2deZiIxcaaaA==");
//        ByteSource ciphertext = aes.encrypt(payloads, key);
//        System.out.printf(ciphertext.toString());
//    }
//}
