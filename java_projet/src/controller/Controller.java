package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.graphstream.graph.Graph;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import model.NodeIP;
import model.Processus;
import model.Tree;
import view.Window;


/**
 * 
 * @author SARR Ni�b� / ADIB Ayoub
 *
 */
 
public class Controller {
	//Vue:
	private Window view;
	//Models:
	private Tree tree;
	//Progression de nos traitements:
	private ProgressBar progressBar;
	private int progressNow;
	private int progressHopsMax;
	
	//Constructeur: association de nos diff�rents mod�les et vues via le controller:
	public Controller(TreeItem<NodeIP> rootTree) {
		this.tree = new Tree(rootTree);
		this.view = new Window(this, rootTree);
		this.progressBar = new ProgressBar(0);
		this.progressHopsMax = 0;
		this.progressNow = 0;
	}
	
	/**
	 * @brief Fonction permettant de lancer notre Stage (fenetre) JavaFX:
	 * @param fenetre qui va �tre affich�e
	 * @throws Exception
	 */
	public void start(Stage fenetre) throws Exception {
		
		view.launch(fenetre);
	}
	
	/**
	 * @brief Execution du traceroute et generation de l'arbre
	 * @param hostName : url (ou ip) qui va �tre pass� au Traceroute
	 * @return true si la generation s'est bien faite, false sinon
	 */
	public boolean generateTree(String hostName) {
		Processus processus = null;
		BufferedReader reader = null;
		String line = null;
		String regex = null;
		TreeItem<NodeIP> currentNode = null;
		
		//On instancie notre classe processus et on lance le jar fakeroute en r�cup�rant l'output dans reader:
		processus = new Processus();
		reader = processus.execTraceroute(hostName);
		//Notre expression r�guli�re permettant l'isolement d'Ip sur une ligne de l'output du traceroute:
		regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		
		
		//currentNode permet d'indiquer le noeud � partir duquel on ajoute les 
		//enfants au fur et � mesure que notre output traceroute est lu:
		currentNode = this.tree.getRoot();
		ObservableList<TreeItem<NodeIP>> parent = FXCollections.observableList(new ArrayList<TreeItem<NodeIP>>());
		parent.add(currentNode);
		this.progressHopsMax += 30;
		int numLine = 0;
		try {
			line = reader.readLine();
			while(line != null) {
				//Affichage de l'output traceroute en Console:
				System.out.println(line);
				
				String lineSplit[] = line.split(" ");
				List<String> lineWithoutSpace = new ArrayList<String>();
				for(int j = 0; j < lineSplit.length; j++){	
					//PARSING TRACERT:
					//Pour enlever les parenth�ses du tracert:
					if(lineSplit[j].contains("[") && lineSplit[j].contains("]")){
						String sansPremierCrochet = lineSplit[j].split("\\[")[1];
						String ip = sansPremierCrochet.split("\\]")[0];
						lineSplit[j] = ip;
					}
					//PARSING TRACEROUTE:
					//Pour enlever les parenth�ses du traceroute:
					if(lineSplit[j].contains("(") && lineSplit[j].contains(")")){
						String sansPremierCrochet = lineSplit[j].split("\\(")[1];
						String ip = sansPremierCrochet.split("\\)")[0];
						lineSplit[j] = ip;
					}
					//PARSING FINAL:
					//Si notre output modifi� (parenth�se supprim� ou crochet supprim�s) matche le regex d�finit:
					if(lineSplit[j].matches(regex)){
						//Pour �viter les doublons dans le cas de traceroute:
						if(lineWithoutSpace.contains(lineSplit[j]) == false)
							lineWithoutSpace.add(lineSplit[j]);
					}
				}
				//Nous ignorons la premi�re ligne avec numLine > 0:
				if(lineWithoutSpace.size() >= 1 && numLine > 0){
					//On g�n�re notre arbre via addItems � partir de currentNode (d�butant � Root)
					//on stocke dans notre list parent, les ips qui ont �t� ajout�e, parent sera r�utilis� 
					//� la prochaine it�ration dans le prochain instance d'ip et sotckera les parents du fils ip:
					parent = this.tree.addChildren(currentNode,lineWithoutSpace,parent);
					//Update currentNode: nous ajoutons toujours les prochains enfants sur le dernier enfant (pour des soucis de performance):
					currentNode = this.tree.getLastChild(currentNode);
					//this.tree.addAllItems(this.tree.getRoot(),lineWithoutSpace,"");
				}
				line = reader.readLine();
				//numLine est seulement utile pour v�rifier la premier ligne et ignore le titre de la commande:
				if(numLine < 2)
					numLine++;
				//Nous augmentons l'avanc� de notre progressBar:
				this.progressNow++;
				progressBar.setProgress((double)this.progressNow/(double)this.progressHopsMax);
			}
			//Nous mettons notre progressBar � 1 lorsque la t�che est finie:
			progressBar.setProgress(1.0);
			//Fermeture du reader et nettoyage des ressources:
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;		
	}
	
	/**
	 * @brief Generation d'un graphe � partir d'une url (ou ip)
	 * @param graph : Graphe � modifier
	 * @param hostName : url (ou ip) qui va �tre pass� au Traceroute
	 * @return true si la generation s'est bien faite, false sinon
	 */
	public boolean generate(Graph graph, String hostName) {
		if(generateTree(hostName) == true) {
			//on lance notre thread swing (graphstream) plus tard car la politique de threading 
			//swing emp�che la cr�ation d'un thread raphique dans un autre UI (ici javafx):
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					generateGraph(graph, tree, tree.getRoot());
				}
				
			});
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * @brief Ajout des noeuds graphiques (Arbre graphique) via GraphStream
	 * @param graph : Graphe qui doit �tre modifi�
	 * @param tree : Arbre qui fut g�ner� precedemment
	 * @param node : Liste d'adresses IP
	 */
	public void generateGraph(Graph graph,Tree tree, TreeItem<NodeIP> node) {
		if(node != null) {
			//si le noeud existe d�j� on ignore:
			String nodeLabel = null;
			String edgeLabel = null;
			
			nodeLabel = node.getValue().getIp();
			//un noeud est unique, on en cr�e que s'il n'existe pas (ie null):
			if(graph.getNode(nodeLabel) == null) {
				graph.addNode(nodeLabel);
				//Label:
				graph.getNode(nodeLabel).setAttribute("ui.label",nodeLabel);
			}
			//Cr�ation des liens entre noeud (getParents()):
			//on exclue root comme il n'a pas de parent:
			if(node != this.tree.getRoot())  {
				for(TreeItem<NodeIP> parent:tree.getParents(node)) {
					//System.out.println(parent);
					edgeLabel = parent.getValue().getIp()+" to "+node.getValue().getIp();
					//System.out.println(edgeLabel);
					if(graph.getEdge(edgeLabel) == null)
						graph.addEdge(edgeLabel,parent.getValue().getIp(), node.getValue().getIp(), true);
				}
			}
			for(TreeItem<NodeIP> currentNode: node.getChildren()) {
				//System.out.println(currentNode.getValue().getIp());
				//R�cursivit�:
				this.generateGraph(graph, tree, currentNode);
			}	
		}
		
		return;
	}
	
	/**
	 * @brief R�cup�ration de la progress Bar pour la Vue:
	 * @return la progress bar
	 */
	public ProgressBar getProgressBar() {
		return this.progressBar;
	}
	
	/**
	 * @brief Fonction permettant de g�n�rer une IP (v4) al�atoire:
	 * @param min
	 * @param max
	 * @return une adresse IP
	 */
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
