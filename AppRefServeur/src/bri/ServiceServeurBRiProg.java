package bri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import prog.Programmeur;

public class ServiceServeurBRiProg implements ServiceServeurBRi{
	private Socket client;
	private List<Programmeur> pList = new ArrayList<Programmeur>();
	public ServiceServeurBRiProg(Socket socket) {
		client = socket;
		pList.add(new Programmeur("raphael","password", "onsaitpasencore"));
		pList.add(new Programmeur("thibaud", "password", "onsaitpasencore"));
		pList.add(new Programmeur("brette", "password", "onsaitpasencore"));
	}

	public void run() {
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
			out.println("Entrez votre login");
			String login = in.readLine();
			out.println("\n Entrez votre mot de passe");
			String mdp = in.readLine();
			if (!auth(login,mdp)){
				out.println("\n Echec de la connexion, mot de passe ou login incorrect");
				return;
			}
			out.println("\n 1-Fournir un nouveau service"
					+ "\n 2-Mise � jour du service"
					+ "\n 3-Changement d'adresse de votre serveur ftp");
			String reponse = in.readLine();
			
			// instancier le service num�ro "choix" en lui passant la socket "client"
			// invoquer run() pour cette instance ou la lancer dans un thread � part 
				
			}
		catch (IOException e) {
			//Fin du service
		}

		try {client.close();} catch (IOException e2) {}
	}
	
	private boolean auth(String login, String mdp) {
		for(Programmeur p : pList)
			if (p.getLogin().equals(login) && p.getMdp().equals(mdp))
				return true;
		return false;
	}

	protected void finalize() throws Throwable {
		 client.close(); 
	}

	// lancement du service
	public void start() {
		(new Thread(this)).start();		
	}
	

}
