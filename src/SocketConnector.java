import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class SocketConnector {
	private Socket socket=null;
	private DataInputStream dataInput = null;
	private DataOutputStream dataOutput = null;
	
	public SocketConnector(Socket socket){
		this.socket=socket;
		try {
			dataInput=new DataInputStream(socket.getInputStream());
			dataOutput = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static SocketConnector creatConnector(String host,int port) 
			throws UnknownHostException, IOException{
		return new SocketConnector(new Socket(host, port));
	}

	public Socket getSocket() {
		return socket;
	}

	public DataInputStream getDataInputStream() {
		return dataInput;
	}

	public DataOutputStream getDataOutputStream() {
		return dataOutput;
	}
	
	
}
