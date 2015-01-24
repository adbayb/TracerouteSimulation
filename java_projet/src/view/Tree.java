package view;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class Tree extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTree tree;
	DefaultMutableTreeNode root;
	
	public Tree(List<String> firstElem){
		createFrame();
		createTree(firstElem);
		JPanel panel = new JPanel();
		panel.add(tree);
		frame.add(panel);
		this.pack();
		frame.setVisible(true);
	}
	
	public void createTree(List<String> firstElem){
		root = new DefaultMutableTreeNode("Traceroute (click to display)");
		tree = new JTree(root);	
		root.add(new DefaultMutableTreeNode(firstElem.get(firstElem.size() - 1)));
	}
	
	public void addElemTree(List<String> elem){
		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
		root.add(new DefaultMutableTreeNode(elem.get(0)));
		model.reload(root);
	}
	
	public void createFrame(){		
		frame = new JFrame("Traceroute");
		frame.setSize(400,300);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Traceroute");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}

