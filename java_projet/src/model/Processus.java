package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 
 * @author SARR Ni�b� / ADIB Noeud
 *
 */

//Classe permettant de g�rant l'ex�cution de commande syst�me traceroute, tracert ou jar:
public class Processus {
	private Process pr;
	//private ProcessBuilder pb;
	//private BufferedReader reader;
	private Runtime r;
	
	public Processus() {
		
	}
	
	/**
	 * @brief Execution du traceroute et r�cuperation du r�sultat
	 * @param url : L'url (ou ip) qui va �tre pass� en parametre au traceroute
	 * @return un BufferedReader contenant le r�sultat du traceroute
	 */
	public BufferedReader execTraceroute(String url){		
		try {
			//Get current directory:
			//System.out.println("Working Directory = " + System.getProperty("user.dir"));
			//pb = new ProcessBuilder("java", "-jar", "./lib/fakeroute.jar", url);
			//pr = pb.start();
			//ou via commande terminal:
			r = Runtime.getRuntime();
			pr = r.exec("traceroute " + url);
			
			return new BufferedReader(new InputStreamReader(pr.getInputStream()));
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("error\n");
			return null;
		}
	}
}
