package model;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

//Notre modèle Tree : crée des noeuds, gère la recherche de noeuds, gestion noeud parent/fils [via TreeItem et Modèle NodeIP]:
public class Tree {
	private TreeItem<NodeIP> root;
	
	public Tree(TreeItem<NodeIP> root) {
		//Root: point d'origine de notre Tree:
		this.root = root; 
		this.root.setExpanded(true);
	}
	
	//addChild 2 root:
	public TreeItem<NodeIP> addChild2Root(NodeIP ip) {
		return this.addChild(root,ip);
	}
	
	//Ajout d'un enfant à n'importe quel noeud:
	public TreeItem<NodeIP> addChild(TreeItem<NodeIP> node, NodeIP ip) {
		if(node != null) {
			TreeItem<NodeIP> item = new TreeItem<NodeIP>(ip);
			node.setExpanded(true);
			node.getChildren().add(item);
			
			//Nous retournons l'enfant effectivement ajouté:
			return item;
		}
		
		return null;
	}
	
	//ajout d'enfants à n'importe quel noeud (node):
	public ObservableList<TreeItem<NodeIP>> addChildren(TreeItem<NodeIP> node, List<String> listIp, ObservableList<TreeItem<NodeIP>> listParents) {
		ObservableList<TreeItem<NodeIP>> result = null;
		
		if(node != null) {
			if(listIp != null) {
				//on instancie notre observablelist qui contiendra tous les enfants effectivement ajoutés:
				result = FXCollections.observableList(new ArrayList<TreeItem<NodeIP>>());
				node.setExpanded(true);
				for(String ip : listIp) {
					result.add(this.addChild(node,new NodeIP(ip,listParents)));
				}
				return result;
			}
		}
		
		return null;
	}
	
	//Récupération du dernier enfant à partir de node:
	public TreeItem<NodeIP> getLastChild(TreeItem<NodeIP> node) {
		ObservableList<TreeItem<NodeIP>> listChildren = null;
		
		if(node != null) {
			//notre fonction getChildren vérifiera si node a au moins un fils:
			listChildren = this.getChildren(node);
			if(listChildren != null) {
				//tmpChildNode prendra au fur et à mesure les noeuds de chaque fils jusqu'au dernier:
				TreeItem<NodeIP> tmpChildNode = null;
				for(TreeItem<NodeIP> childNode : listChildren) {
					tmpChildNode = childNode;
				}
				//on retourne le dernier fils:
				if(tmpChildNode != null)
					return tmpChildNode;
			}
		}
		
		return null;
	}
	
	//Récupération des enfants:
	public ObservableList<TreeItem<NodeIP>> getChildren(TreeItem<NodeIP> node) {
		if(node != null) {
			//Si le noeud a au moins un enfant:
			if(node.isLeaf() == false) {
				return node.getChildren();
			}
		}
		
		return null;
	}
	
	//Récupération du père d'un noeud (père unique ici):
	public TreeItem<NodeIP> getParent(TreeItem<NodeIP> node) {
		if(node != null) {
			//getParent() retourne null si pas de parent (par exemple n'a pas de parent donc null):
			return node.getParent();
		}
		return null;
	}
	
	//Récupération de tous les parents de fromNode (pour contrer les restrictions propre à TreeItem (un enfant ne peut avoir qu'un seul parent))
	//On récupère tous les parents via les infos du champs from du modèle Ip (listant les parents de l'ip):
	public ObservableList<TreeItem<NodeIP>> getParents(TreeItem<NodeIP> fromNode) {
		if(fromNode != null)
			return fromNode.getValue().getFrom();
		
		return null;
	}
	

	//addNode to another node or item:
	public TreeItem<NodeIP> addSearchedChild(NodeIP ip, String namedNode) {
		TreeItem<NodeIP> node = this.searchAll(root, namedNode);
		
		return this.addChild(node, ip);
	}
	
	/*
	 * Search for one node from fromNode (compare string object (ip name) vs String value to target) and return address to this node:
	 * Recherche par rapport au champs String ip du modèle Ip
	 */
	public TreeItem<NodeIP> searchAll(TreeItem<NodeIP> fromNode, String value) { 

		return this.searchInterval(fromNode,null,value);
	}
	
	//search node from fromNode (included) to toNode (excluded):
	//fromNode and toNode must be att different level in tree with toNode more deeper than fromNode:
	public TreeItem<NodeIP> searchInterval(TreeItem<NodeIP> fromNode, TreeItem<NodeIP> toNode, String value) { 
		//How to test Strings value (use equals!):
		//== tests for reference equality.
		//.equals() tests for value equality
		if(fromNode.getValue().getIp().equals(value) == true) {
			//System.out.println(currentNode.getValue().getIp() + " " + value + " " + (currentNode.getValue().getIp() == value));
			return fromNode;
		}
		else {
			if(toNode == null || fromNode != toNode) {
				for(TreeItem<NodeIP> childNode : fromNode.getChildren()) {
					//search(childNode, value); => seul ne fonctionne pas: on doit récupérer la valeur de retour de notre fonction récursive:
					TreeItem<NodeIP> findNode = searchInterval(childNode, toNode, value);
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
	
	public boolean editItem(TreeItem<NodeIP> node, NodeIP ip) {
		if(node != null) {
			node.setValue(ip);
			
			return true;
		}
		
		return false;
	}
	
	public TreeItem<NodeIP> getRoot() {
		return this.root;
	}
	
	public boolean setRoot(TreeItem<NodeIP> root) {
		if(root != null) {
			this.root = root;
			
			return true;
		}
		return false;
	}
}
