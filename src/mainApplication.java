import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;


public class mainApplication implements ActionListener{
	Socket socket=null;
	String ip;
	String userName;
	LoginView loginView=null;
	
	public static void main(String[] args){
		mainApplication application = new mainApplication();
		
	}
	public mainApplication() {
		loginView=new LoginView();
		loginView.setVisible(true);
		loginView.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		loginView.setVisible(false);
		ChatRoomsClient chatRoomsClient=
				new ChatRoomsClient(loginView.getScoket(),loginView.getUserName());
		chatRoomsClient.rooms.userListSetModel(loginView.getLoginUser().getModel());
		
	}
}
