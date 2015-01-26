package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import model.Ip;
import model.Processus;
import model.Tree;
import view.View;

/**
 * 
 * @author SARR Niébé / ADIB Ayoub
 *
 */
public class Controller {
	//dans controller on ajoute les modèles et vues associées! les modèles et vues 
	//ne doivent pas contenir de liens entre eux: tout passe par le controller qui les référencent!
	
	//Vue:
	private final View view;
	
	//Models:
	private final Tree tree;
	//private final Processus processus;

	public Controller(Tree tree, View view) {
		this.tree = tree;
		//this.processus = processus;
		this.view = view;
		view.setController(this);
	}

	public void start(Stage fenetre) throws Exception {
		view.launch(fenetre);
	}

	/*public TreeItem<Ip> getTree() {
		return tree.getTree();
	}

	public boolean addItemTree(Ip ip) {
		tree.addItem(ip);
		
		return true;
	}

	public boolean addNodeTree(Ip ip, String namedNode) {
		tree.addNode(ip,namedNode);
		
		return true;
	}*/
	
	public boolean generate() {
		Processus processus = new Processus();
		
		BufferedReader reader = processus.execTraceroute("www.google.com");
		String line;	
	    String regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"; 
	    int i = 0;
		try {
			line = reader.readLine();			
			//TODO: http://stackoverflow.com/questions/10541157/sscanf-equivalent-in-java
			while(line != null) {
				System.out.println(line);
				String lineSplit[] = line.split(" ");
				List<String> lineWithoutSpace = new ArrayList<String>();
				for(int j = 0; j < lineSplit.length; j++){	
					if(lineSplit[j].contains("[") && lineSplit[j].contains("]")){
						String sansPremierCrochet = lineSplit[j].split("\\[")[1];
						String ip = sansPremierCrochet.split("\\]")[0];
						lineSplit[j] = ip;
					}					
					else if(lineSplit[j].contains("(") && lineSplit[j].contains(")")){
						String sansPremierCrochet = lineSplit[j].split("\\(")[1];
						String ip = sansPremierCrochet.split("\\)")[0];
						lineSplit[j] = ip;						
					}
					if(lineSplit[j] != " "){
						if(lineSplit[j].matches(regex)){
							lineWithoutSpace.add(lineSplit[j]);
						}						
					}
				}
				if(lineWithoutSpace.size() >= 1){
					tree.addItems2Root(lineWithoutSpace);
					/*for(String list : lineWithoutSpace) {
						//tree.addINode(new Ip(list,""));
						//tree.addItem(new Ip(list,""));
						System.out.println(list);
					}	
					System.out.println("fin");*/
				}
				line = reader.readLine();
				i++;
				if(i == 7){
					reader.close();
					break;
				}
			}						
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;		
	}
}
