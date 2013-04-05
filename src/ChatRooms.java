import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Dimension;
import java.util.ArrayList;

public class ChatRooms extends JFrame{	
	private ArrayList<ActionListener> actionListenersList;
	private MessageController controller;
	private MessageView view;
	private ChatRoomsMessageModel model;
	private JList<String> userList=new JList<String>();
	
	
	
	/**
	 * @wbp.parser.constructor
	 */
	public ChatRooms(ChatRoomsMessageModel model) {
		this.model=model;
		initWindow();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	public ChatRooms(ChatRoomsMessageModel model,String[] userlist) {
		this(model);
		userListSetData(userlist);
	}
	
	private void initWindow() {
		setSize(new Dimension(600, 600));
		
		JTextPane textPane= new JTextPane();
		JPanel upPane=new JPanel();
		JPanel downPane=new JPanel();
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,true,upPane,downPane);
		JPanel textpane = new JPanel(new BorderLayout());
		JButton exitbt = new JButton("EXIT!");
		
		view = new ChatRoomsMessageView(this,model);
		view.componentAddEditorPaneResized(this);
		
		controller = new ChatRoomsMessageContoroller(model);	
		
		downPane.setLayout(new BorderLayout(9, 0));
		upPane.setLayout(new BorderLayout(3, 0));
		upPane.setBackground(Color.BLACK);
		textPane.setEditable(false);
		downPane.add(textpane, BorderLayout.CENTER);
		upPane.add(userList, BorderLayout.EAST);
		textpane.add(controller,BorderLayout.CENTER);
		upPane.add(view, BorderLayout.CENTER);
		
		view.setFont(new Font("新細明體", Font.PLAIN, 24));
		controller.setFont(new Font("新細明體", Font.PLAIN, 24));
		
		exitbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		downPane.add(exitbt, BorderLayout.EAST);
		
		
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(500);
		getContentPane().add(splitPane, BorderLayout.CENTER);
	}
	
	public void userListSetData(String[] userlist) {
		this.userList.setListData(userlist);
	}
	
	public void userListSetModel(ListModel<String> model) {
		this.userList.setModel(model);
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
	
	private void processEvent(ActionEvent e) {
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
	public void setClient(ActionListener l) {
		model.addClientActionListener(l);
	}

	
}
