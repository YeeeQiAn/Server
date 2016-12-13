
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


public class Server {

    public static void main(String[] args) throws AlreadyBoundException {
        try {
        	System.setProperty("java.rmi.server.hostname","172.26.211.85");
        	 DictionaryServiceImp dictionaryservice = new DictionaryServiceImp();
            LocateRegistry.createRegistry(1098);
            Naming.rebind("rmi://172.26.211.85:1098/DictionaryService", dictionaryservice);
            System.out.print("success");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}