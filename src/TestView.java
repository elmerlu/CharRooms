import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;


public class TestView extends Frame{
	MessageModel model;
	
	MessageView view;
	MessageController controller;
	public static void main(String[] args){
		System.out.println("89  \r  fefe");
		TestView view=new TestView();
		view.setSize(400,400);
		view.setVisible(true);
	}
	
	public TestView() {
		model = new MessageModel("<html> <body> ^^ </body></html>");
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		view=new MessageView(model);
		panel.add(view,BorderLayout.CENTER);
		
		controller = new MessageController(model);
		
		panel.add(controller,BorderLayout.SOUTH);
		
		add(panel);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		//new ChatRooms(model).setVisible(true);
		
	}
	
	
}
