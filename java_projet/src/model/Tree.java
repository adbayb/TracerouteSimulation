package model;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class Tree {
	private TreeItem<Ip> root;
	//private TreeItem<String> tree;
	//private ArrayList<TreeItem<Ip>> nodes1;
	
	public Tree(TreeItem<Ip> root) {
		//TODO: � impl�menter si besoin
		//deuxi�me argument pour image: cf http://docs.oracle.com/javafx/2/ui_controls/tree-view.htm
		this.root = root; 
		this.root.setExpanded(true);
		
		//nodes1 stocke les noeuds de premiers niveau dans le but d'ajouter ensuite des sous-noeuds etc...
		//this.nodes1 = null;
		//passer par search plus simple et �conomique si le noeud est unique!
	}
	
	//addItem 2 root:
	public TreeItem<Ip> addItem2Root(Ip ip) {
		TreeItem<Ip> item = new TreeItem<Ip>(ip);
		root.getChildren().add(item);
		
		return item;
	}
	
	//addNode to another node or item:
	/*public TreeItem<Ip> addNode(Ip ip, TreeItem<Ip> node) {
		//System.out.println(node);
		TreeItem<Ip> newNode = null;
		if(node != null) {
			newNode = new TreeItem<Ip>(ip);
			//on active le d�pilage automatique:
			node.setExpanded(true);
			node.getChildren().add(newNode);
		}
		
		return newNode;
	}*/
	
	//add multiple items to root:
	
	//TODO: to fix: affecte seulement les enfants associ� directement au root le reste ne fonctionne pas!
	public boolean addItems2Root(List<String> listIp) {
		if(root != null) {
			if(root.isLeaf() == true) {
				for(String ip : listIp) {
					root.setExpanded(true);
					root.getChildren().add(new TreeItem<Ip>(new Ip(ip,"")));
				}
				
				return true;
			}
			else {
				for(TreeItem<Ip> node : root.getChildren()) {
					if(node.isLeaf()) {
						//System.out.println(node.getValue().getIp()+" "+node.isLeaf());
						for(String ip : listIp) {
							node.setExpanded(true);
							node.getChildren().add(new TreeItem<Ip>(new Ip(ip,"")));
						}
					}
					else{
						addItemToLeafChildren(node.getChildren(), listIp);
					}
				}
				return true;
			}
			
		}
		
		return false;
	}
	
	public void addItemToLeafChildren(ObservableList<TreeItem<Ip>> children, List<String> listIp){
		for(TreeItem<Ip> node : children) {
			if(node.isLeaf()) {
				//System.out.println(node.getValue().getIp()+" "+node.isLeaf());
				for(String ip : listIp) {
					node.setExpanded(true);
					node.getChildren().add(new TreeItem<Ip>(new Ip(ip,"")));
				}
			}
			else{
				addItemToLeafChildren(node.getChildren(), listIp);
			}
		}		
	}
	
	//addNode to another node or item:
	public boolean addSearchedItem(Ip ip, String namedNode) {
		TreeItem<Ip> node = search(root, namedNode);
		//System.out.println(node);
		//si le noeud ou l'item a �t� trouv�:
		if(node != null) {
			//on active le d�pilage automatique:
			node.setExpanded(true);
			node.getChildren().add(new TreeItem<Ip>(ip));
		}
		
		return true;
	}
	
	/*
	 * Search for one node (compare string object (ip name) vs String value to target) and return address to this node:
	 * Recherche par rapport au champs String ip du mod�le Ip
	 */
	private TreeItem<Ip> search(TreeItem<Ip> currentNode, String value) { 
		//How to test Strings value (use equals!):
		//== tests for reference equality.
		//.equals() tests for value equality
		if(currentNode.getValue().getIp().equals(value) == true) {
			//System.out.println(currentNode.getValue().getIp() + " " + value + " " + (currentNode.getValue().getIp() == value));
			return currentNode;
		}
		else {
			for(TreeItem<Ip> childNode : currentNode.getChildren()) {
				//search(childNode, value); => seul ne fonctionne pas: on doit r�cup�rer la valeur de retour de notre fonction r�cursive:
				TreeItem<Ip> findNode = search(childNode, value);
				//si l'on trouve le noeud voulu (ie findNode not null), on retourne sa valeur
				if(findNode != null) 
					return findNode;
			}
		}
		//Si aucune occurrence n'est trouv�e, on retourne null:
		return null;
	}
	
	public TreeItem<Ip> getRootTree() {
		return this.root;
	}
}
