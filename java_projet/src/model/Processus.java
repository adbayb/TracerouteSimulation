package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 
 * @author SARR Niébé / ADIB Ayoub
 *
 */

//Classe permettant de gzrant l'exécution de commande système traceroute, tracert ou jar:
public class Processus {
	private Process pr;
	//private ProcessBuilder pb;
	//private BufferedReader reader;
	private Runtime r;
	
	public Processus() {
		
	}
	
	/**
	 * @brief Execution du traceroute et récuperation du résultat
	 * @param url : L'url (ou ip) qui va être passé en parametre au traceroute
	 * @return un BufferedReader contenant le résultat du traceroute
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
