package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Processus {
	private Process pr;
	private ProcessBuilder pb;
	//private BufferedReader reader;
	private Runtime r;
	
	public Processus() {
		//TODO ?
	}
	
	public BufferedReader execTraceroute(String url){		
		try {
			//Get current directory:
			//System.out.println("Working Directory = " + System.getProperty("user.dir"));
			pb = new ProcessBuilder("java", "-jar", "./lib/fakeroute.jar", url);
			pr = pb.start();
			//ou via commande terminal:
			/*r = Runtime.getRuntime();
			pr = r.exec("tracert " + url);*/
			
			return new BufferedReader(new InputStreamReader(pr.getInputStream()));
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("error\n");
			return null;
		}
	}
}
