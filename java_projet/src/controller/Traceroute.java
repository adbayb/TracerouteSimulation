package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Processus;
import view.Gui;
import view.Tree;

public class Traceroute {
	
	//Vues attachés au controller:
	private Gui g;
	private Tree t;
	//Modèles attaché au controller:
	private Processus gp;

	public Traceroute(Processus gp, Gui g, Tree t) {
		this.gp = gp;
		this.t = null;
		this.g = g;
		//view.setController(this);
	}
	
	public void exec() {		
		//g.addAction(new ButtonClickListener());	
		BufferedReader reader = gp.execTraceroute("www.google.com");
		String line;	
	    String regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"; 
		int i = 1;
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
					if(lineSplit[j] != " "){
						if(lineSplit[j].matches(regex)){
							lineWithoutSpace.add(lineSplit[j]);
						}						
					}
				}
				if(i>3 && lineWithoutSpace.size() >= 1){
					if(t == null){
						t = new Tree(lineWithoutSpace);
					}
					else{
						t.addElemTree(lineWithoutSpace);
					}				
				}
				line = reader.readLine();
				i++;
			}						
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}

