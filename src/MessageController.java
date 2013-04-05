import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;


public class MessageController extends JScrollPane implements KeyListener{
	private MessageModel model ;
	private JTextComponent messagePane ;
	private Boolean press_VK_SHIFT=false;
	private Boolean newLine=false;
	
	public MessageController() {
		messagePane = new JEditorPane();
		messagePane.addKeyListener(this);
		setViewportView(messagePane);
	}
	
	public MessageController(MessageModel messageModel) {
		this();
		setModel(messageModel);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_SHIFT:
				if(!press_VK_SHIFT)
					press_VK_SHIFT=true;
				break;
			case KeyEvent.VK_ENTER:
				String str = messagePane.getText();
				if(press_VK_SHIFT){
					messagePane.setText(str+"\n");
				}else{
					newLine=true;
					model.addMessage(str);
				}
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SHIFT:
			press_VK_SHIFT=false;
			break;
		case KeyEvent.VK_ENTER:
			if(newLine){
				messagePane.setText(null);
				newLine=false;
			}
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	public void setModel(MessageModel model) {
		 this.model = model;
	}
}
