package model;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class Ip {
	private String ip;
	//stockera les adresses Ip d'origine:
	private ObservableList<TreeItem<Ip>> from;
	
	public Ip(String ip, ObservableList<TreeItem<Ip>> from) {
		//TODO
		this.ip = ip;
		this.from = from;
	}
	
	public String getIp() {
		return this.ip;
	}
	
	public ObservableList<TreeItem<Ip>> getFrom() {
		return this.from;
	}
	
	public String toString() {
		//si from est vide, l'ip n'a pas d'origine donc pas de "parent":
		List<String> parents = new ArrayList<String>();
		if(this.from != null) {
			for(TreeItem<Ip> parent : this.from) {
				parents.add(parent.getValue().getIp());
			}
			
			return this.getIp()+" --->>> "+parents;
		}
		
		return this.getIp()+" --->>> No Parent";
	}
}
