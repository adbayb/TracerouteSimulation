package view;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;

public class Gui extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTree tree;
	private JTextField url;
	private JButton bouton;
	
	public Gui() {
		//System.out.println("GUI cr�e");
		//on crée notre fen�tre (frame) avec JFrame:
		frame = new JFrame("test");
		frame.setVisible(true);
		frame.setSize(300,150);
		//position au centre:
		frame.setLocationRelativeTo(null);
		frame.setTitle("Fen�tre");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createPanel();
	}
	
	public void createPanel() {
		JPanel panel = new JPanel();
		//permet de d�finir la fa�on qu'un �l�ment se positionne (gridLayout, FlowLayout (permet de placer tous les �l�ments sur une m�me ligne tant qu'il y a de la place)):
		panel.setLayout(new FlowLayout());

		url = new JTextField(10);

		url.setBounds(10,40,150,25);
	    
	    bouton = new JButton("test");
	    
	    panel.add(url);
	    //panel.add(new JLabel("First name : "));
		panel.add(bouton);
		
		//on ajoute notre panel � notre frame:
		frame.add(panel);
		  //JOptionPane.showConfirmDialog(null, panel, "Family and first name : ", JOptionPane.OK_CANCEL_OPTION);
		
		//return panel;
	}
	
	public String getUrl(){
		return url.getText();
	}
	
	/*public void addAction(ActionListener a){
		bouton.addActionListener(a);
	}*/
}
