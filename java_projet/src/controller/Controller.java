package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.graphstream.graph.Graph;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import model.Ip;
import model.Processus;
import model.Tree;
import view.Window;


/**
 * 
 * @author SARR Niébé / ADIB Ayoub
 *
 */
public class Controller {
	//dans controller on ajoute les modèles et vues associées! les modèles et vues 
	//ne doivent pas contenir de liens entre eux: tout passe par le controller qui les référencent!
	
	//Vue:
	private final Window view;
	
	//Models:
	private final Tree tree;
	//private final Processus processus;

	public Controller(TreeItem<Ip> rootTree) {
		this.tree = new Tree(rootTree);
		this.view = new Window(this, rootTree);
		//view.setController(this);
	}

	public void start(Stage fenetre) throws Exception {
		view.launch(fenetre);
	}

	//DEBUG pour la vue:
	public TreeItem<Ip> addItem(TreeItem<Ip> node, Ip ip) {
		return this.tree.addChild(node,ip);
	}
	
	public TreeItem<Ip> addItem2Root(Ip ip) {
		return tree.addChild2Root(ip);
	}

	public TreeItem<Ip> addSearchedItem(Ip ip, String namedNode) {
		return tree.addSearchedChild(ip,namedNode);
	}
	
	public TreeItem<Ip> getLastChild(TreeItem<Ip> node) {
		return this.tree.getLastChild(node);
	}
	
	public TreeItem<Ip> getParent(TreeItem<Ip> node) {
		return this.tree.getParent(node);
	}
	
	public TreeItem<Ip> search(TreeItem<Ip> currentNode, String value) {
		return this.tree.searchAll(currentNode,value);
	}
	
	public TreeItem<Ip> searchInterval(TreeItem<Ip> fromNode, TreeItem<Ip> toNode, String value) {
		return this.tree.searchInterval(fromNode,toNode,value);
	}
	
	public TreeItem<Ip> getRoot() {
		return this.tree.getRoot();
	}
	
	public boolean editItem(TreeItem<Ip> node, Ip ip) {
		return this.tree.editItem(node,ip);
	}
	
	public List<TreeItem<Ip>> getParents(TreeItem<Ip> fromNode) {
		return this.tree.getParents(fromNode);
	}
	
	
	
	//On aurait pu mettre generate dans un Task pour réaliser les opération de fakeroute en arrière plan
	//Mais traceroute ne fait pas de long calcul (très rapide) donc inutile ici d'utilise un mode Concurrency
	//cf pour plus d'informations: http://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm
	public boolean generateTree(String hostName) {
		Processus processus = null;
		BufferedReader reader = null;
		String line = null;
		String regex = null;
		TreeItem<Ip> currentNode = null;
		
		//On instancie notre classe processus et on lance le jar fakeroute en récupérant l'output dans reader:
		processus = new Processus();
		reader = processus.execTraceroute(hostName);
		regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		//currentNode permet le noeud à partir duquel on ajoute les enfant au fur et à mesure que notre output fakeroute est lu:
		currentNode = this.tree.getRoot();
		ObservableList<TreeItem<Ip>> parent = FXCollections.observableList(new ArrayList<TreeItem<Ip>>());
		parent.add(currentNode);
		int i = 0;
		try {
			line = reader.readLine();
			while(line != null) {
				System.out.println(line);
				
				String lineSplit[] = line.split(" ");
				List<String> lineWithoutSpace = new ArrayList<String>();
				for(int j = 0; j < lineSplit.length; j++){	
					//fakeroute
					//Si notre output line matche le regex définit:
					if(lineSplit[j].matches(regex)){
						lineWithoutSpace.add(lineSplit[j]);
					}
				}
				//we ignore first line with i > 0:
				if(lineWithoutSpace.size() >= 1 && i > 0){
					//On génére notre arbre via addItems à partir de currentNode (débutant à Root)
					parent = this.tree.addChildren(currentNode,lineWithoutSpace,parent);
					//Update currentNode:
					currentNode = this.tree.getLastChild(currentNode);
					//this.tree.addAllItems(this.tree.getRoot(),lineWithoutSpace,"");
				}
				line = reader.readLine();
				i++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;		
	}
	
	public boolean generate(Graph graph, String hostName) {
		if(this.generateTree(hostName) == true) {
			this.generateGraph(graph, this.tree, this.tree.getRoot());
			
			return true;
		}
		
		return false;
	}
	
	public void generateGraph(Graph graph,Tree tree, TreeItem<Ip> node) {
		if(node != null) {
			//si le noeud existe déjà on ignore:
			String nodeLabel = null;
			String edgeLabel = null;
			
			nodeLabel = node.getValue().getIp();
			//un noeud est unique, on en crée que s'il n'existe pas (ie null):
			if(graph.getNode(nodeLabel) == null) {
				graph.addNode(nodeLabel);
				//Label:
				graph.getNode(nodeLabel).setAttribute("ui.label",nodeLabel);
			}
			//Création des liens entre noeud (getParents()):
			//on exclue root comme il n'a pas de parent:
			if(node != this.tree.getRoot())  {
				for(TreeItem<Ip> parent:tree.getParents(node)) {
					//System.out.println(parent);
					edgeLabel = parent.getValue().getIp()+" to "+node.getValue().getIp();
					//System.out.println(edgeLabel);
					if(graph.getEdge(edgeLabel) == null)
						graph.addEdge(edgeLabel,parent.getValue().getIp(), node.getValue().getIp(), true);
				}
			}
			for(TreeItem<Ip> currentNode: node.getChildren()) {
				//System.out.println(currentNode.getValue().getIp());
				//Récursivité:
				this.generateGraph(graph, tree, currentNode);
			}	
		}
		
		return;
	}
	
	public boolean generateSimpleGraph(Graph graph) {
		this.generateGraph(graph, this.tree, this.tree.getRoot());
		
		return true;
	}
	
	public String randomizeIPHostname(int min, int max) {
		String ipHostname = null;
		Random rand = new Random();
		//Ip v4: so 4 fields
		int randomVal1 = rand.nextInt((max-min)+1)+min;
		int randomVal2 = rand.nextInt((max-min)+1)+min;
		int randomVal3 = rand.nextInt((max-min)+1)+min;
		int randomVal4 = rand.nextInt((max-min)+1)+min;
		
		//String.format equivalent at sprintf from C:
		ipHostname = String.format("%d.%d.%d.%d",randomVal1,randomVal2,randomVal3,randomVal4);
		
		return ipHostname;
	}
}
