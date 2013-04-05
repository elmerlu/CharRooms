import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;



public class ChatRoomsClient extends Thread
	implements ActionListener{
	Socket socket;
	DataInputStream inputStream = null;
	DataOutputStream OutputStream = null;
	ChatRoomsMessageModel messageModel=new ChatRoomsMessageModel("歡迎使用聊天室!");;
	ChatRooms rooms;
	String userName;
	
	int i=0;
	

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("NO."+i++);
		try {
			OutputStream.writeUTF("/say "+userName+":"+messageModel.getCurrentMessage());
			OutputStream.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public ChatRoomsClient(Socket socket,String userName){
		this.userName=userName;
		this.socket=socket;
		try {
			inputStream = new DataInputStream(socket.getInputStream());
			OutputStream = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		rooms=new ChatRooms(messageModel);
		rooms.setClient(this);
		this.start();
		rooms.setVisible(true);
		rooms.repaint();
	}
	@Override
	public void run() {
		System.out.println("run");
		while (true) {
			try {
				String str = inputStream.readUTF();
				System.out.println(str);
				if(str.startsWith("/say ")){
					String message=str.substring(5);
					messageModel.append("\n"+"<"+
							new SimpleDateFormat("a/h:mm:ss").format(new Date())
							+ "> " +message);
				}else if(str.startsWith("/userList ")){
					String[] userString=str.split(" ")[1].split(",");
					rooms.userListSetData(userString);
				}else if(str.startsWith("/login ")){
					messageModel.append("\n"+"< "+str.substring(7)+" >已登入!");
				}else if(str.startsWith("/logout ")){
					messageModel.append("\n"+"< "+str.substring(7)+" >已登出!");
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
