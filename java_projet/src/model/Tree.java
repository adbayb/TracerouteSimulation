package model;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

//Notre modèle Tree : crée des noeuds, gère la recherche de noeuds, gestion noeud parent/fils [via TreeItem]:
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
			
			return node.getChildren().add(item);
		}
		
		return false;
	}
	
	//add Items to either node:
	//return boolean? todo:
	//description indique le niveau des items ajouté au noeuds:
	public void addItems(TreeItem<Ip> node, List<String> listIp, String description) {
		if(node != null) {
			if(listIp != null) {
				node.setExpanded(true);
				for(String ip : listIp) {
					this.addItem(node,new Ip(ip,description));
				}
			}
		}
		
		return;
	}
	
	//Ajout d'items à tous les noeuds à partir de node ne disposant pas d'enfants:
	public void addAllItems(TreeItem<Ip> node, List<String> listIp, String description) {
		if(node != null) {
			//Si le noeud n'a pas d'enfant (ie isLeaf() == true), alors on lui ajoute la liste:
			if(node.isLeaf() == true) {
				this.addItems(node,listIp,description);
			}
			else {
				//Récursivité: on met à jour le noeud courant sur lequel on checke s'il dispose d'enfants:
				for(TreeItem<Ip> currentNode : node.getChildren()) {
					this.addAllItems(currentNode,listIp,description);
				}
			}
		}
		
		return;
	}
	
	public TreeItem<Ip> getLastChild(TreeItem<Ip> node) {
		ObservableList<TreeItem<Ip>> listChildren = this.getChildren(node);
		
		if(listChildren != null) {
			//tmpChildNode prendra au fur et à mesure les noeuds de chaque fils jusqu'au dernier:
			TreeItem<Ip> tmpChildNode = null;
			for(TreeItem<Ip> childNode : listChildren) {
				tmpChildNode = childNode;
			}
			//on retourne le dernier fils:
			if(tmpChildNode != null)
				return tmpChildNode;
		}
		
		return null;
	}
	
	public ObservableList<TreeItem<Ip>> getChildren(TreeItem<Ip> node) {
		if(node != null) {
			//Si le noeud a au moins un enfant:
			if(node.isLeaf() == false) {
				return node.getChildren();
			}
		}
		
		return null;
	}
	
	public TreeItem<Ip> getParent(TreeItem<Ip> node) {
		if(node != null) {
			//test si c'est un fils?
			return node.getParent();
		}
		return null;
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
	public TreeItem<Ip> search(TreeItem<Ip> fromNode, String value) { 
		//How to test Strings value (use equals!):
		//== tests for reference equality.
		//.equals() tests for value equality
		if(fromNode.getValue().getIp().equals(value) == true) {
			//System.out.println(currentNode.getValue().getIp() + " " + value + " " + (currentNode.getValue().getIp() == value));
			return fromNode;
		}
		else {
			for(TreeItem<Ip> childNode : fromNode.getChildren()) {
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
	
	public boolean editItem(TreeItem<Ip> node, Ip ip) {
		if(node != null) {
			node.setValue(ip);
			
			return true;
		}
		
		return false;
	}
	
	public TreeItem<Ip> getRoot() {
		return this.root;
	}
	
	public boolean setRoot(TreeItem<Ip> root) {
		if(root != null) {
			this.root = root;
			
			return true;
		}
		return false;
	}
}
