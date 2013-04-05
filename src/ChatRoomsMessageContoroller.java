import javax.swing.JEditorPane;


public class ChatRoomsMessageContoroller extends MessageController{

	public ChatRoomsMessageContoroller() {
		super();
	}
	
	public ChatRoomsMessageContoroller(MessageModel messageModel) {
		this();
		setModel(messageModel);
	}
}
