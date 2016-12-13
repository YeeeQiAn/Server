
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface DictionaryService extends java.rmi.Remote {

    String search(String name) throws RemoteException;
    String baiduGetMeaning(String word) throws IOException, IOException;
    String biyingGetMeaning(String word) throws IOException, IOException;
    String youdaoGetMeaning(String word)throws IOException, IOException;
    String GetMeaning(String word) throws IOException,IOException;
    boolean ifloginsuccess(String name,String password)throws IOException,IOException;
    boolean ifregistersuccess(String name,String password)throws IOException,IOException;
    boolean logout(String name)throws IOException,IOException;
    ArrayList<String> alluser()throws IOException,IOException;
    ArrayList<String> onlineuser() throws IOException,IOException;
    boolean addtime(String word,int type) throws IOException,IOException;
    boolean reducetime(String word,int type) throws IOException,IOException;
    boolean searchtime(String word) throws IOException,IOException;
    int time(String word,int type) throws IOException,IOException;
}