import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class test {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Socket socket = new Socket("123.206.76.17", 808);
	    // 向服务端程序发送数据 同步测试
	    OutputStream ops = socket.getOutputStream();    
	    OutputStreamWriter opsw = new OutputStreamWriter(ops);
	    BufferedWriter bw = new BufferedWriter(opsw);
	      
	    bw.write("1");
	    bw.flush();
	      
	    // 从服务端程序接收数据
	    InputStream ips = socket.getInputStream();
	    InputStreamReader ipsr = new InputStreamReader(ips);
	    BufferedReader br = new BufferedReader(ipsr);
	    String s = "";    
	    while((s = br.readLine()) != null)
	      System.out.println(s);    
	    socket.close();

	}

}
