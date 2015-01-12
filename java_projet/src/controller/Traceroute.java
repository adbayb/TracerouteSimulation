package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import view.Gui;

public class Traceroute {
	
	public static void main(String[] args) {
		//System.out.println("testt\n");
		Process pr = null;
		ProcessBuilder pb = null;
		BufferedReader reader = null;
		Runtime r = null;
		String line = null;
		
		Gui g = new Gui();
		
		try {
			//Get current directory:
			//System.out.println("Working Directory = " + System.getProperty("user.dir"));
			r = Runtime.getRuntime();
			pb = new ProcessBuilder("java", "-jar", "./fakeroute.jar", "google.fr");
			pr = pb.start();
			//ou via commande terminal:
			/*r = Runtime.getRuntime();
			pr = r.exec("tracert fr.wikipedia.org");*/
			reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			
			line = reader.readLine();
			while(line != null) {
				System.out.println(line);
				line = reader.readLine();
				
			}
		}
		catch(Exception e) {
			System.out.println("error\n");
		}
		
		
	}
}

