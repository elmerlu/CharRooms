import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.text.Document;


public class MessageView extends JScrollPane implements ActionListener{
	private JScrollPane jScrollPane;
	protected MessageModel model ;
	protected JEditorPane messagePane ;
	EditorPaneResized editorPaneResized = new EditorPaneResized();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Document doc = model.getDocument();
		messagePane.setDocument(doc);
		repaint();
		messagePane.setSize(new Dimension(
				jScrollPane.getWidth()-20,jScrollPane.getHeight()-20));
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		verticalScrollBar.setValue(verticalScrollBar.getMaximum());
		validate();
	}
	
	public MessageView() {
		jScrollPane=this;
	}
	
	public MessageView(MessageModel messagemodel) {
		this();
		setBackground(Color.white);
		setmodel(messagemodel);
		repaint();
	}
		
	public void setmodel(MessageModel messagemodel) {
		this.model = messagemodel;
				
		if(this.model != null)
			this.model.addActionListener(this);
		
		messagePane=new JEditorPane();
		//messagePane.setContentType("text/html");  //�臭誑�曉���
		messagePane.setEditable(false);
		messagePane.setText(messagemodel.getText());
		setViewportView(messagePane);
		componentAddEditorPaneResized(this);
		
	}
	
	public void componentAddEditorPaneResized(Component c){
		c.addComponentListener(editorPaneResized);
	}
	
	class EditorPaneResized implements ComponentListener,AdjustmentListener{
		@Override
		public void componentHidden(ComponentEvent arg0) {}
		@Override
		public void componentMoved(ComponentEvent arg0) {}
		@Override
		public void componentShown(ComponentEvent arg0) {}

		@Override
		public void componentResized(ComponentEvent arg0) {
			editorPaneResized();
		}
		
		@Override
		public void adjustmentValueChanged(AdjustmentEvent e) {
			editorPaneResized();
		}

		
		private void editorPaneResized() {
			messagePane.setSize(new Dimension(
					jScrollPane.getWidth()-20,jScrollPane.getHeight()-20));
			repaint();
		}
		
	}
}
