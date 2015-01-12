package view;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Gui extends JFrame {
	
	private JFrame frame;
	private JPanel panel;
	
	public Gui() {
		//System.out.println("GUI cr�e");
		//on cr�e notre fen�tre (frame) avec JFrame:
		frame = new JFrame("test");
		frame.setVisible(true);
		frame.setSize(300,150);
		//position au centre:
		frame.setLocationRelativeTo(null);
		frame.setTitle("Fen�tre");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createPanel();
		//on ajoute notre panel � notre frame:
		frame.add(panel);
	}
	
	public void createPanel() {
		panel = new JPanel();
		//permet de d�finir la fa�on qu'un �l�ment se positionne (gridLayout, FlowLayout (permet de placer tous les �l�ments sur une m�me ligne tant qu'il y a de la place)):
		panel.setLayout(new FlowLayout());

		JTextField familyName = new JTextField(10);
		JTextField firstName = new JTextField(10);

	    familyName.setBounds(10,40,150,25);
	    firstName.setBounds(10,70,150,25);
	    
	    JButton bouton = new JButton("test");
	    
	    //panel.add(new JLabel("Family name :"));
	    panel.add(familyName);
	    //panel.add(new JLabel("First name : "));
	    panel.add(firstName);
		panel.add(bouton);

		  //JOptionPane.showConfirmDialog(null, panel, "Family and first name : ", JOptionPane.OK_CANCEL_OPTION);
		
		//return panel;
	}
}
