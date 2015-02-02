package model;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

//Notre mod�le Tree : cr�e des noeuds, g�re la recherche de noeuds, gestion noeud parent/fils [via TreeItem et Mod�le NodeIP]:
public class Tree {
	private TreeItem<NodeIP> root;
	
	public Tree(TreeItem<NodeIP> root) {
		//Root: point d'origine de notre Tree:
		this.root = root; 
		this.root.setExpanded(true);
	}
	
	/**
	 * @brief Ajout d'un node au root
	 * @param ip : ip qui va �tre ajout�
	 * @return l'arbre d'IP
	 */
	public TreeItem<NodeIP> addChild2Root(NodeIP ip) {
		return this.addChild(root,ip);
	}
	
	/**
	 * @brief Ajout d'un enfant � n'importe quel noeud:
	 * @param node : Arbre d'IP
	 * @param ip : ip qui va �tre ajout�
	 * @return l'arbre d'IP
	 */
	public TreeItem<NodeIP> addChild(TreeItem<NodeIP> node, NodeIP ip) {
		if(node != null) {
			TreeItem<NodeIP> item = new TreeItem<NodeIP>(ip);
			node.setExpanded(true);
			node.getChildren().add(item);
			
			//Nous retournons l'enfant effectivement ajout�:
			return item;
		}
		
		return null;
	}
	
	/**
	 * @brief ajout d'enfants � n'importe quel noeud (node):
	 * @param node 
	 * @param listIp
	 * @param listParents
	 * @return l'arbre d'IP
	 */
	public ObservableList<TreeItem<NodeIP>> addChildren(TreeItem<NodeIP> node, List<String> listIp, ObservableList<TreeItem<NodeIP>> listParents) {
		ObservableList<TreeItem<NodeIP>> result = null;
		
		if(node != null) {
			if(listIp != null) {
				//on instancie notre observablelist qui contiendra tous les enfants effectivement ajout�s:
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
	
	/**
	 * @brief R�cup�ration du dernier enfant � partir de node:
	 * @param node
	 * @return l'enfant correspondant ou null
	 */
	public TreeItem<NodeIP> getLastChild(TreeItem<NodeIP> node) {
		ObservableList<TreeItem<NodeIP>> listChildren = null;
		
		if(node != null) {
			//notre fonction getChildren v�rifiera si node a au moins un fils:
			listChildren = this.getChildren(node);
			if(listChildren != null) {
				//tmpChildNode prendra au fur et � mesure les noeuds de chaque fils jusqu'au dernier:
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
	
	/**
	 * @brief R�cup�ration des enfants:
	 * @param node ou la recherche commence
	 * @return la liste d'Enfants
	 */
	public ObservableList<TreeItem<NodeIP>> getChildren(TreeItem<NodeIP> node) {
		if(node != null) {
			//Si le noeud a au moins un enfant:
			if(node.isLeaf() == false) {
				return node.getChildren();
			}
		}
		
		return null;
	}
	
	/**
	 * @brief R�cup�ration du p�re d'un noeud (p�re unique ici):
	 * @param node
	 * @return le p�re
	 */
	public TreeItem<NodeIP> getParent(TreeItem<NodeIP> node) {
		if(node != null) {
			//getParent() retourne null si pas de parent (par exemple n'a pas de parent donc null):
			return node.getParent();
		}
		return null;
	}
	
	/**
	 * @brief R�cup�ration de tous les parents de fromNode (pour contrer les restrictions propre � TreeItem (un enfant ne peut avoir qu'un seul parent))
	 * 		On r�cup�re tous les parents via les infos du champs from du mod�le Ip (listant les parents de l'ip)
	 * @param fromNode
	 * @return liste de Noeuds
	 */
	public ObservableList<TreeItem<NodeIP>> getParents(TreeItem<NodeIP> fromNode) {
		if(fromNode != null)
			return fromNode.getValue().getFrom();
		
		return null;
	}
	

	/**
	 * @brief : Ajout d'un node � un node de l'arbre
	 * @param ip : node � ajouter
	 * @param namedNode : ip du node � modifier
	 * @return
	 */
	public TreeItem<NodeIP> addSearchedChild(NodeIP ip, String namedNode) {
		TreeItem<NodeIP> node = this.searchAll(root, namedNode);
		
		return this.addChild(node, ip);
	}
	
	/*
	 * 
	 * 
	 */
	/**
	 * @brief Search for one node from fromNode (compare string object (ip name) vs String value to target) and return address to this node:
	 * 		Recherche par rapport au champs String ip du mod�le Ip
	 * @param fromNode
	 * @param value
	 * @return la liste de nodes
	 */
	public TreeItem<NodeIP> searchAll(TreeItem<NodeIP> fromNode, String value) { 

		return this.searchInterval(fromNode,null,value);
	}
	
	/**
	 * @brief search node from fromNode (included) to toNode (excluded):
	 * 		fromNode and toNode must be att different level in tree with toNode more deeper than fromNode:
	 * @param fromNode
	 * @param toNode
	 * @param value
	 * @return la liste de nodes
	 */
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
					//search(childNode, value); => seul ne fonctionne pas: on doit r�cup�rer la valeur de retour de notre fonction r�cursive:
					TreeItem<NodeIP> findNode = searchInterval(childNode, toNode, value);
					//si l'on trouve le noeud voulu (ie findNode not null), on retourne sa valeur
					if(findNode != null) 
						return findNode;
				}
			}
			else
			//si aucun �l�ment trouv� dans l'intervalle fromNode - toNode:
				return null;
		}
		//Si aucune occurrence n'est trouv�e, on retourne null:
		return null;
	}
	
	/**
	 * @brief Modifier un node
	 * @param node
	 * @param ip
	 * @return true si la modification s'est bien pass�e, false sinon
	 */
	public boolean editItem(TreeItem<NodeIP> node, NodeIP ip) {
		if(node != null) {
			node.setValue(ip);
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * @brief R�cuperation du root de l'arbre
	 * @return le root
	 */
	public TreeItem<NodeIP> getRoot() {
		return this.root;
	}

	/**
	 * @brief Modification du root de l'arbre
	 * @return true si la modification s'est bien pass�e, false sinon
	 */
	public boolean setRoot(TreeItem<NodeIP> root) {
		if(root != null) {
			this.root = root;
			
			return true;
		}
		return false;
	}
}
