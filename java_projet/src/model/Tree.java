package model;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
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
	public TreeItem<Ip> addChild2Root(Ip ip) {
		return this.addChild(root,ip);
	}
	
	//add Item to either node:
	public TreeItem<Ip> addChild(TreeItem<Ip> node, Ip ip) {
		if(node != null) {
			TreeItem<Ip> item = new TreeItem<Ip>(ip);
			node.setExpanded(true);
			node.getChildren().add(item);
			
			//Nous retournons l'enfant effectivement ajouté:
			return item;
		}
		
		return null;
	}
	
	//add Items to either node:
	//return boolean? todo:
	//description indique le niveau des items ajouté au noeuds:
	public ObservableList<TreeItem<Ip>> addChildren(TreeItem<Ip> node, List<String> listIp, ObservableList<TreeItem<Ip>> listParents) {
		ObservableList<TreeItem<Ip>> result = null;
		
		if(node != null) {
			if(listIp != null) {
				//on instancie notre observablelist qui contiendra tous les enfants effectivement ajoutés:
				result = FXCollections.observableList(new ArrayList<TreeItem<Ip>>());
				node.setExpanded(true);
				for(String ip : listIp) {
					result.add(this.addChild(node,new Ip(ip,listParents)));
				}
				return result;
			}
		}
		
		return null;
	}
	
	//Ajout d'items à tous les noeuds à partir de node ne disposant pas d'enfants:
	/*public void addAllItems(TreeItem<Ip> node, List<String> listIp, List<String> parents) {
		if(node != null) {
			//Si le noeud n'a pas d'enfant (ie isLeaf() == true), alors on lui ajoute la liste:
			if(node.isLeaf() == true) {
				this.addItems(node,listIp,parents);
			}
			else {
				//Récursivité: on met à jour le noeud courant sur lequel on checke s'il dispose d'enfants:
				for(TreeItem<Ip> currentNode : node.getChildren()) {
					//todo getParents!!!
					this.addAllItems(currentNode,listIp,description);
				}
			}
		}
		
		return;
	}*/
	
	public TreeItem<Ip> getLastChild(TreeItem<Ip> node) {
		ObservableList<TreeItem<Ip>> listChildren = null;
		
		if(node != null) {
			//notre fonction getChildren vérifiera si node a au moins un fils:
			listChildren = this.getChildren(node);
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
			//getParent() retourne null si pas de parent (par exemple n'a pas de parent donc null):
			return node.getParent();
		}
		return null;
	}
	
	//permet de récupérer tous les parents de fromNode (pour contrer les restrictions propre à TreeItem (un enfant ne peut avoir qu'un seul parent))
	//On récupère tous les parents via les infos du champs from du modèle Ip (listant les parents de l'ip):
	public ObservableList<TreeItem<Ip>> getParents(TreeItem<Ip> fromNode) {
		if(fromNode != null)
			return fromNode.getValue().getFrom();
		
		return null;
	}
	

	//addNode to another node or item:
	public TreeItem<Ip> addSearchedChild(Ip ip, String namedNode) {
		TreeItem<Ip> node = this.searchAll(root, namedNode);
		
		return this.addChild(node, ip);
	}
	
	/*
	 * Search for one node from fromNode (compare string object (ip name) vs String value to target) and return address to this node:
	 * Recherche par rapport au champs String ip du modèle Ip
	 */
	public TreeItem<Ip> searchAll(TreeItem<Ip> fromNode, String value) { 

		return this.searchInterval(fromNode,null,value);
	}
	
	//search node from fromNode (included) to toNode (excluded):
	//fromNode and toNode must be att different level in tree with toNode more deeper than fromNode:
	public TreeItem<Ip> searchInterval(TreeItem<Ip> fromNode, TreeItem<Ip> toNode, String value) { 
		//How to test Strings value (use equals!):
		//== tests for reference equality.
		//.equals() tests for value equality
		if(fromNode.getValue().getIp().equals(value) == true) {
			//System.out.println(currentNode.getValue().getIp() + " " + value + " " + (currentNode.getValue().getIp() == value));
			return fromNode;
		}
		else {
			if(toNode == null || fromNode != toNode) {
				for(TreeItem<Ip> childNode : fromNode.getChildren()) {
					//search(childNode, value); => seul ne fonctionne pas: on doit récupérer la valeur de retour de notre fonction récursive:
					TreeItem<Ip> findNode = searchInterval(childNode, toNode, value);
					//si l'on trouve le noeud voulu (ie findNode not null), on retourne sa valeur
					if(findNode != null) 
						return findNode;
				}
			}
			else
			//si aucun élément trouvé dans l'intervalle fromNode - toNode:
				return null;
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
