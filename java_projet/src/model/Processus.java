package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Processus {
	private Process pr;
	private ProcessBuilder pb;
	//private BufferedReader reader;
	private Runtime r;
	
	public BufferedReader execTraceroute(String url){		
		try {
			//Get current directory:
			//System.out.println("Working Directory = " + System.getProperty("user.dir"));
			r = Runtime.getRuntime();
			/*pb = new ProcessBuilder("java", "-jar", "./fakeroute.jar", url);
			pr = pb.start();*/
			//ou via commande terminal:
			r = Runtime.getRuntime();
			pr = r.exec("tracert " + url);
			return new BufferedReader(new InputStreamReader(pr.getInputStream()));
			
			/*line = reader.readLine();
			while(line != null) {
				System.out.println(line);
				line = reader.readLine();
				
			}*/
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("error\n");
			return null;
		}
	}
}
