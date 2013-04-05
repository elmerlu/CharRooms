import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;


public class MessageModel extends JTextPane{
	private ArrayList<ActionListener> actionListenersList;
	
	public MessageModel() {
	}
	
	public MessageModel(String Text) {
		setText(Text);
	}
        
    public void append(String str) {
        Document doc = getDocument();
        if (doc != null) {
            try {
                doc.insertString(doc.getLength(), str, null);
            } catch (BadLocationException e) {
            }
        }
        processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "newMessage"));
    }
	
	public void addMessage(String message) {
		System.out.println("addmessage");
		append(message);
	}
	
	public synchronized void addActionListener(ActionListener l) {
		if(actionListenersList == null)
			actionListenersList = new ArrayList<ActionListener>();
		
		actionListenersList.add(l);
	}
	
	public synchronized void removeActionListener(ActionListener l) {
		if(actionListenersList!=null && actionListenersList.contains(l))
			actionListenersList.remove(l);
	}
	
	protected void processEvent(ActionEvent e) {
		System.out.println("processEvent");
		ArrayList<ActionListener> list;
		
		synchronized (this) {
			if(actionListenersList == null)
				return;
			list = (ArrayList<ActionListener>) actionListenersList.clone();
		}
		
		for(int i = 0 ; i < list.size() ; i++){
			ActionListener listener = list.get(i);
			listener.actionPerformed(e);
		}
	}
}
