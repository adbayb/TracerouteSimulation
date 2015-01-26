package model;

import java.util.List;

import javafx.scene.control.TreeItem;

public class Tree {
	private TreeItem<Ip> root;
	
	public Tree(TreeItem<Ip> root) {
		//TODO: à implémenter si besoin
		//deuxième argument pour image: cf http://docs.oracle.com/javafx/2/ui_controls/tree-view.htm
		this.root = root; 
		this.root.setExpanded(true);
	}
	
	//addItem 2 root:
	public boolean addItem2Root(Ip ip) {
		return this.addItem(root,ip);
	}
	
	//add Item to either node:
	public boolean addItem(TreeItem<Ip> node, Ip ip) {
		if(node != null) {
			TreeItem<Ip> item = new TreeItem<Ip>(ip);
			node.setExpanded(true);
			node.getChildren().add(item);
			
			return true;
		}
		
		return false;
	}
	
	public void addItems(TreeItem<Ip> node, List<String> listIp) {
		if(node != null) {
			if(node.isLeaf()) {
				for(String ip : listIp) {
					node.setExpanded(true);
					node.getChildren().add(new TreeItem<Ip>(new Ip(ip,"")));
				}
			}
			else {
				//Récursivité: on met à jour le noeud courant sur lequel on checke s'il dispose d'enfants:
				for(TreeItem<Ip> currentNode : node.getChildren()) {
					this.addItems(currentNode,listIp);
				}
			}
		}
		
		return;
	}
	

	//addNode to another node or item:
	public boolean addSearchedItem(Ip ip, String namedNode) {
		TreeItem<Ip> node = search(root, namedNode);
		
		return this.addItem(node, ip);
	}
	
	/*
	 * Search for one node (compare string object (ip name) vs String value to target) and return address to this node:
	 * Recherche par rapport au champs String ip du modèle Ip
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
				//search(childNode, value); => seul ne fonctionne pas: on doit récupérer la valeur de retour de notre fonction récursive:
				TreeItem<Ip> findNode = search(childNode, value);
				//si l'on trouve le noeud voulu (ie findNode not null), on retourne sa valeur
				if(findNode != null) 
					return findNode;
			}
		}
		//Si aucune occurrence n'est trouvée, on retourne null:
		return null;
	}
	
	public TreeItem<Ip> getRootTree() {
		return this.root;
	}
}
