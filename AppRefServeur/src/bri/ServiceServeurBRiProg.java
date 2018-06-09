package bri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import prog.Programmeur;

public class ServiceServeurBRiProg implements ServiceServeurBRi {
	
	private Socket client;
	private List<Programmeur> pList = new ArrayList<Programmeur>();
	
	public ServiceServeurBRiProg(Socket socket) {
		client = socket;
		pList.add(new Programmeur("raphael","password", "onsaitpasencore"));
		pList.add(new Programmeur("thibaud", "password", "onsaitpasencore"));
		pList.add(new Programmeur("brette", "password", "onsaitpasencore"));
	}

	public void run() {
		try {
			BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter (client.getOutputStream(), true);
			
			out.println("Entrez votre login");
			String login = in.readLine();
			
			out.println("Entrez votre mot de passe");
			String mdp = in.readLine();
			
			if (!auth(login,mdp)){
				out.println("Echec de la connexion, mot de passe ou login incorrect");
				out.println("exit");
				return;
			}
			
			boolean end = false;
			while(!end) {
				out.println("Bienvenue " + login + "\n\nSouhaitez-vous :\n"
						+ "\n 1. Fournir un nouveau service"
						+ "\n 2. Mise a jour du service"
						+ "\n 3. Changement d'adresse de votre serveur ftp"
						+ "\n 4. Quitter");
				String response = in.readLine();
				
				// instancier le service numero "choix" en lui passant la socket "client"
				// invoquer run() pour cette instance ou la lancer dans un thread a part 
				
				switch(Integer.valueOf(response).intValue()) {
				case 1:
					out.println("Entrez le nom du fichier .class de votre serveur FTP : ");
					String className = in.readLine();
					URL[] urls = new URL[]{ new URL(new String("file:///home/raphael/Code/Java/AppRefProject/AppRefServeur/" + login + "/")) };
					URLClassLoader ucl = new URLClassLoader(urls);
					try {
						ServiceRegistry.addService(ucl.loadClass(className));
					}
					catch(ClassNotFoundException e) { out.println("La classe " + className + " n'est pas trouvable"); }
					catch (ServiceNonConformeException e) { out.println(e.getMessage()); }
					break;
				case 2:
					out.println(ServiceRegistry.toStringue());
					int choice = Integer.valueOf(in.readLine()).intValue();
					
					break;
				case 3:
					break;
				case 4:
					end = true;
					break;
				default:
					out.println("Option " + response + " non disponible");
					break;
				}
			}
			out.println("exit");
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
