import java.net.Socket;

import javax.swing.JEditorPane;


public class ChatRoomsMessageView extends MessageView{
	ChatRooms chatrooms;
	
	public ChatRoomsMessageView(ChatRooms chatrooms) {
		this(chatrooms, null);
	}
	public ChatRoomsMessageView(ChatRooms chatrooms,MessageModel messagemodel) {
		this.chatrooms=chatrooms;
		setmodel(messagemodel);
	}
	
	
	/*@Override
	public void setmodel(MessageModel messagemodel) {
		this.model = messagemodel;
		
		chatrooms.addActionListener(this);
		
		messagePane=new JEditorPane();
		//messagePane.setContentType("text/html");  //可以放圖片
		messagePane.setEditable(false);
		messagePane.setText(messagemodel.getText());
		setViewportView(messagePane);
	}*/
	
}
