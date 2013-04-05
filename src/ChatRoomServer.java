import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.omg.PortableInterceptor.USER_EXCEPTION;


public class ChatRoomServer extends Thread{
	ServerSocket serverSocket;
	ExecutorService executor = Executors.newCachedThreadPool();
	ArrayList<severTask> userTask = new ArrayList<severTask>();
	
	MessageModel model;
	ArrayList<String> user = new ArrayList<String>();
	
	public static void main(String[] args) {
		System.out.println("Starting ChatRooms Server ver.1.0");
		ChatRoomServer server = new ChatRoomServer();
		server.start();
	}
	public ChatRoomServer() {
		try {
			serverSocket = new ServerSocket(11000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		
		try {
			while(true){
				System.out.print("Waiting...");
				Socket sc = serverSocket.accept();
				System.out.println("\r--Accept--");
				new severTask(sc).start();
				Thread.sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
		}
		
	}

	class severTask extends Thread {
		private String userName;
		
		private DataInputStream inputStream;
		private DataOutputStream OutputStream;
		
		public severTask(Socket socket) {
			try {
				inputStream = new DataInputStream(socket.getInputStream());
				OutputStream = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			System.out.println("run");
			while (true) {
				
				try {
					System.out.println("realy");
					String str = inputStream.readUTF();
					System.out.println(str);
					if(str.startsWith("/say ")){
						clientMessage(str);
					}else if(str.startsWith("/login ")){
						String userName=str.split(" ")[1];
						System.out.println("login : "+userName);
						if(userLogin(userName)){
							userTask.add(this);
							this.userName=userName;
							otherClientMessage("/login "+userName);
							ListUpdata();
						}else{
							message("/loginMiss");
							this.stop();
	
						}
					}else if(str.startsWith("/logout ")){
						String userName=str.split(" ")[1];
						userTask.remove(this);
						userLogout(userName);
						otherClientMessage("/logout "+userName);
						ListUpdata();
					}
					
				} catch (IOException e) {
					System.out.println("logout"+userName);
					userTask.remove(this);
					userLogout(userName);
					otherClientMessage("/logout "+userName);
					ListUpdata();
					this.stop();
				}
			}
		}
		private boolean userLogin(String userName){
			if(user.indexOf(userName)==-1){
				user.add(userName);
				return true;
			}
			return false;			
		}
		private void userLogout(String userName) {
			if(user.indexOf(userName)!=-1)
				user.remove(userName);
		}
		private void message(String message) {
			try {
				OutputStream.writeUTF(message);
				OutputStream.flush();
				System.out.println(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		private void ListUpdata() {
			System.out.println("ListUpdata");
			String message="/userList ";
			for(int i=0 ; i<user.size();i++){
				message+=user.get(i)+",";
			}
			message.substring(0, message.length()-1);
			clientMessage(message);
		}
		
		private void otherClientMessage(String message) {
			System.out.println("user size:"+userTask.size());
			for(int i=0;i<userTask.size() && i!=userTask.lastIndexOf(this);i++){
				userTask.get(i).message(message);
			}
		}
	}
	
	private void clientMessage(String message) {
		System.out.println("user size:"+userTask.size());
		for(int i=0;i<userTask.size();i++){
			userTask.get(i).message(message);
		}
	}
}
