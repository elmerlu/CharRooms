import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;


public class ChatRoomsMessageModel extends MessageModel {
	String currentMessage;
	private ArrayList<ActionListener> clientActionListenersList;
	
	public ChatRoomsMessageModel(String string) {
		super(string);
	}
	public ChatRoomsMessageModel() {
		super();
	}
		
	@Override
	public void addMessage(String message) {
		currentMessage=message;
		clientProcessEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "message"));
		
	}
	
	public String getCurrentMessage() {
		return currentMessage;
	}
	
	public synchronized void addClientActionListener(ActionListener l) {
		if(clientActionListenersList == null)
			clientActionListenersList = new ArrayList<ActionListener>();
		
		clientActionListenersList.add(l);
	}
	
	public synchronized void removeClientActionListener(ActionListener l) {
		if(clientActionListenersList!=null && clientActionListenersList.contains(l))
			clientActionListenersList.remove(l);
	}
	
	protected void clientProcessEvent(ActionEvent e) {
		System.out.println("processEvent");
		ArrayList<ActionListener> list;
		
		synchronized (this) {
			if(clientActionListenersList == null)
				return;
			list = (ArrayList<ActionListener>) clientActionListenersList.clone();
		}
		
		for(int i = 0 ; i < list.size() ; i++){
			ActionListener listener = list.get(i);
			listener.actionPerformed(e);
		}
	}
}
