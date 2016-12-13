import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class DictionaryServiceImp extends java.rmi.server.UnicastRemoteObject implements DictionaryService {

    private static final long serialVersionUID = 3434060152387200042L;

    public DictionaryServiceImp() throws RemoteException {
        super();
    }

	@Override	
	public String search(String name) throws RemoteException {
		// TODO Auto-generated method stub
		return "hhh";
	}
	
	public String baiduGetMeaning(String word) throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //根据查找单词构造查找地址
        HttpGet getWordMean = new HttpGet("http://dict.baidu.com/s?wd="+word+"&device=pc&from=home&q="+word);
        CloseableHttpResponse response = httpClient.execute(getWordMean);//取得返回的网页源码

        String result = EntityUtils.toString(response.getEntity());
        response.close();
        
        //注意(?s)，意思是让'.'匹配换行符，默认情况下不匹配
        Pattern searchMeanPattern = Pattern.compile("<div><p><strong>(.+?)</strong><span>(.+?)</span></p></div>");
        Matcher m1 = searchMeanPattern.matcher(result);
        StringBuffer meaning = new StringBuffer();
        if (m1.find()) {
            String means1 = m1.group(1);//所有解释，包含网页标签
            meaning.append(means1);
            String means2 = m1.group(2);
            String[] means3 = means2.split("</span></p><p><strong>");
            for(int i=0;i<means3.length;i++){
            	String[] x = means3[i].split("</strong><span>");
            	for(int j=0;j<x.length;j++)
            		meaning.append(x[j]);
            }
        } else {
            meaning.append("未找到释义");
        }
        return meaning.toString();
 }

	public String biyingGetMeaning(String word) throws ClientProtocolException,IOException {
		if(word.equals("")) return "未找到释义";
		else{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //根据查找单词构造查找地址
        HttpGet getWordMean = new HttpGet("http://cn.bing.com/dict/search?q=" + word + "&go=%E6%90%9C%E7%B4%A2&qs=n&form=Z9LH5&pq="+word);
        CloseableHttpResponse response = httpClient.execute(getWordMean);//取得返回的网页源码

        String result = EntityUtils.toString(response.getEntity());
        response.close();
        
        //注意(?s)，意思是让'.'匹配换行符，默认情况下不匹配
        Pattern searchMeanPattern = Pattern.compile("<meta name=\"description\" content=(.*?) />");
        Matcher m1 = searchMeanPattern.matcher(result);
        StringBuffer meaning = new StringBuffer();
        if (m1.find()) {
        	String means = m1.group();//所有解释，包含网页标签
            String[] m2=means.split("，");
            //System.out.print("释义:");
            if(m2.length>=3) {
            	String[] p=m2[3].split("\"");
            	String[] m=p[0].split(" ");
            	for(int i=0;i*2<m.length;i++)
            		meaning.append(m[i*2]+" "+m[i*2+1]);
            		//System.out.println("\t"+m[i*2]+" "+m[i*2+1]);
            }
        } else {
            meaning.append("未找到释义");
        }
        return meaning.toString();}
	}
	
	public String youdaoGetMeaning(String word) { 
		   String meaning = new String();
	        try {
	            URL url = new URL("http://fanyi.youdao.com/openapi.do");
	            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	            connection.addRequestProperty("encoding", "UTF-8");
	            connection.setDoInput(true);
	            connection.setDoOutput(true);

	            connection.setRequestMethod("POST");

	            OutputStream os = connection.getOutputStream();
	            OutputStreamWriter osw = new OutputStreamWriter(os);
	            BufferedWriter bw = new BufferedWriter(osw);


	            bw.write("keyfrom=fadabvaa&key=522071532&type=data&doctype=json&version=1.1&q="+word);
	            bw.flush();

	            InputStream is = connection.getInputStream();
	            InputStreamReader isr = new InputStreamReader(is,"UTF-8");
	            BufferedReader br = new BufferedReader(isr);

	            String line;
	            StringBuilder builder = new StringBuilder();
	            while((line = br.readLine()) != null){
	                builder.append(line);
	            }

	            bw.close();
	            osw.close();
	            os.close();

	            br.close();
	            isr.close();
	            is.close();

	            //meaning = builder.toString();
	            String str1 = builder.toString();
	            Pattern pattern = Pattern.compile("\"explains\":(.+?)\"query\"");
	            Matcher matcher = pattern.matcher(str1);
	            if(matcher.find()){
	            	String x = matcher.group();
	            	String[] arg = x.split("\\[");
	            	String[] arg1 = arg[1].split("\\]");
	            	meaning = arg1[0];
	            }
	            else{
	            	meaning = "未找到释义";
	            }
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	       return meaning;
	}

	public String GetMeaning(String word){
		 String meaning = new String();
	        try {
	        	  URL url = new URL("http://dict-co.iciba.com/api/dictionary.php?key=3D16CAA5396303F119562B41EB34B40B&w="+word);
	            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	            connection.addRequestProperty("encoding", "UTF-8");
	            connection.setDoInput(true);
	            connection.setDoOutput(true);

	            connection.setRequestMethod("POST");

	            OutputStream os = connection.getOutputStream();
	            OutputStreamWriter osw = new OutputStreamWriter(os);
	            BufferedWriter bw = new BufferedWriter(osw);


	            bw.write("");
	            bw.flush();

	            InputStream is = connection.getInputStream();
	            InputStreamReader isr = new InputStreamReader(is,"UTF-8");
	            BufferedReader br = new BufferedReader(isr);

	            String line;
	            StringBuilder builder = new StringBuilder();
	            while((line = br.readLine()) != null){
	                builder.append(line);
	            }

	            bw.close();
	            osw.close();
	            os.close();

	            br.close();
	            isr.close();
	            is.close();

	            //meaning = builder.toString();
	            String str1 = builder.toString();
	            Pattern pattern = Pattern.compile("</pron><pos>(.*?)<sent>");
	            Matcher matcher = pattern.matcher(str1);
	            if(matcher.find()){
	            	String x = matcher.group();
	            	String[] m1 = x.split("<pos>");
	            	for(int i=1;i<m1.length;i++){
	            		String[] m2=m1[i].split("</pos><acceptation>");
	            		meaning += m2[0];
	            		String[] m3=m2[1].split("</acceptation>");
	            		meaning += m3[0];
	            	}
	            }
	            else{
	            	meaning = "未找到释义";
	            }
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	       return meaning;
	 }
    
    public boolean ifloginsuccess(String name,String password){
    	boolean result = true;
    	try {
    		String _password = null;
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("Driver loaded");
			java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/student","scott","tiger");
			//System.out.println("Database connected");
			java.sql.Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select password from user where username = '"+ name + "'");
			while(resultSet.next()){
			_password = resultSet.getString(1);}
			if(password.equals(_password)) ;
			else result = false;
			java.sql.Statement statement1 = connection.createStatement();
			statement1.execute("update user set online = 1 where username = '" + name + "'");
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
    }

    public boolean ifregistersuccess(String name,String password){
    	boolean result = true;
    	try {
    		String _user = null;
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("Driver loaded");
			java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/student","scott","tiger");
			//System.out.println("Database connected");
			java.sql.Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select username from user");
			while(resultSet.next()){
			_user = resultSet.getString(1);
			if(name.equals(_user)) {
				result = false;
				break;
			}
			}
			if(result){
				java.sql.Statement statement1 = connection.createStatement();
				statement1.execute("insert into user(username,password,online) values ('"+ name + "','" + password + "',0)");
			}
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	return result;
    }
    
    public boolean logout(String name)throws IOException,IOException{
    	boolean result = true;
    	try {
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("Driver loaded");
			java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/student","scott","tiger");
			//System.out.println("Database connected");
			java.sql.Statement statement = connection.createStatement();
            statement.execute("update user set online = 0 where username = '" + name + "'");
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	return result;
    }

    public ArrayList<String> alluser()throws IOException,IOException{
    	ArrayList<String> temp = new ArrayList<String>();
    	try{
		Class.forName("com.mysql.jdbc.Driver");
		//System.out.println("Driver loaded");
		java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/student","scott","tiger");
		//System.out.println("Database connected");
		java.sql.Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select username from user");
		while(resultSet.next()){
			temp.add(resultSet.getString(1));
		}
		}catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}   	
    	return temp;
    }

    public ArrayList<String> onlineuser()throws IOException,IOException{
    	ArrayList<String> temp = new ArrayList<String>();
    	try{
		Class.forName("com.mysql.jdbc.Driver");
		//System.out.println("Driver loaded");
		java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/student","scott","tiger");
		//System.out.println("Database connected");
		java.sql.Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select username from user where online = 1");
		while(resultSet.next()){
			temp.add(resultSet.getString(1));
		}
		}catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}   	
    	return temp;
    }

    public boolean addtime(String word,int type) throws IOException,IOException{
    	boolean result = false;
    	try {
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("Driver loaded");
			java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/student","scott","tiger");
			//System.out.println("Database connected");
			java.sql.Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select word from word");
			while(resultSet.next()){
			if(word.equals(resultSet.getString(1))){
				java.sql.Statement statement1 = connection.createStatement();
				if(type == 1)
				{statement1.execute("update word set baidutime = baidutime+1 where word = '" + word + "'");}
				else if(type == 2)
				{statement1.execute("update word set youdaotime = youdaotime+1 where word = '" + word + "'");}
				else if(type == 3)
				{statement1.execute("update word set biyingtime = biyingtime+1 where word = '" + word + "'");}
				
				result = true;
				break;
			}
			}
			if(result == false){
				java.sql.Statement statement2 = connection.createStatement();
				if(type == 1)
				{statement2.execute("insert into word (word,baidutime,youdaotime,biyingtime) values('" + word + "',1,0,0)");}
				else if(type == 2)
				{statement2.execute("insert into word (word,baidutime,youdaotime,biyingtime) values('" + word + "',0,1,0)");}
				else if(type == 3)
				{statement2.execute("insert into word (word,baidutime,youdaotime,biyingtime) values('" + word + "',0,0,1)");}
				result = true;
			}
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	return result;
    }

    public boolean reducetime(String word,int type) throws IOException,IOException{
    	boolean result = true;
    	try {
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("Driver loaded");
			java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/student","scott","tiger");
			//System.out.println("Database connected");
			java.sql.Statement statement = connection.createStatement();
			if(type == 1)
            {statement.execute("update word set baidutime = baidutime-1 where word = '" + word + "'");}
			else if(type == 2)
			{statement.execute("update word set youdaotime = youdaotime-1 where word = '" + word + "'");}
			else if(type == 3)
			{statement.execute("update word set biyingtime = biyingtime-1 where word = '" + word + "'");}
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	return result;
    }

    public boolean searchtime(String word) throws IOException,IOException{
    	boolean result = true;
    	try {
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("Driver loaded");
			java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/student","scott","tiger");
			//System.out.println("Database connected");
			java.sql.Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select baidutime,youdaotime,biyingtime from word where word ='"+word+"'");
			while(resultSet.next()){
				System.out.println(resultSet.getInt(1));
				System.out.println(resultSet.getInt(2));
				System.out.println(resultSet.getInt(3));
			}
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	return result;
    }

    public int time(String word,int type) throws IOException,IOException{
           int time = 0;
           try {
   			Class.forName("com.mysql.jdbc.Driver");
   			//System.out.println("Driver loaded");
   			java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/student","scott","tiger");
   			//System.out.println("Database connected");
   			java.sql.Statement statement = connection.createStatement();
   			ResultSet resultSet = statement.executeQuery("select baidutime,youdaotime,biyingtime from word where word ='"+word+"'");
   			if(type == 1)
   			{while(resultSet.next()) time = resultSet.getInt(1);}
   			else if(type == 2)
   			{while(resultSet.next()) time = resultSet.getInt(2);}
   			else if(type == 3)
   			{while(resultSet.next()) time = resultSet.getInt(3);}
   		} catch (ClassNotFoundException | SQLException e1) {
   			// TODO Auto-generated catch block
   			e1.printStackTrace();
   		}
           return time;
    }
}