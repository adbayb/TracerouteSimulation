package model;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

//Classe permettant de représenter un IP constitué d'un String et de ses parents:
public class NodeIP {
	private String ip;
	//stockera les adresses Ip d'origine:
	private ObservableList<TreeItem<NodeIP>> from;
	
	public NodeIP(String ip, ObservableList<TreeItem<NodeIP>> from) {
		this.ip = ip;
		this.from = from;
	}
	
	/**
	 * @brief Récuperation de l'IP
	 * @return l'IP
	 */
	public String getIp() {
		return this.ip;
	}
	
	/**
	 * @brief Récupération des Ips d'origine (IP parent)
	 * @return la liste d'IP
	 */
	public ObservableList<TreeItem<NodeIP>> getFrom() {
		return this.from;
	}
	
	public String toString() {
		//si from est vide, l'ip n'a pas d'origine donc pas de "parent":
		List<String> parents = new ArrayList<String>();
		if(this.from != null) {
			for(TreeItem<NodeIP> parent : this.from) {
				parents.add(parent.getValue().getIp());
			}
			
			return this.getIp()+" --->>> "+parents;
		}
		
		return this.getIp()+" --->>> No Parent";
	}
}
