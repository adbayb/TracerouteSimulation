package controller;

import view.Gui;
import view.Tree;
import model.Processus;

public class Main {
	public static void main(String[] args) {
		try {
			Processus p = new Processus();
			Gui g = new Gui();
			//Tree t = new Tree();
			Traceroute tracert = new Traceroute(p,g,null);
			
			tracert.exec();
		}
		catch(Exception e) {
			System.out.printf("Main error");
		}
	}
}
