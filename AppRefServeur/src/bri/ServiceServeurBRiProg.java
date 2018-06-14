package bri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
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
		pList.add(new Programmeur("raphael","password", "ftp://localhost:2121/raphael/"));
		pList.add(new Programmeur("thibaud", "password", "ftp://localhost:2121/thibaud/"));
		pList.add(new Programmeur("brette", "password", "ftp://localhost:2121/brette/"));
	}

	public void run() {
		try {
			BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter (client.getOutputStream(), true);
			
			out.println("Entrez votre login");
			String login = in.readLine();
			
			out.println("Entrez votre mot de passe");
			String mdp = in.readLine();

			Programmeur prog = auth(login, mdp);
			if (prog == null){
				out.println("Echec de la connexion, mot de passe ou login incorrect");
				out.println("exit");
				return;
			}

			out.println("Bienvenue " + login + "\n\nSouhaitez-vous :\n");
			boolean end = false;
			while(!end) {
						out.println("\n "
						+ "\n 1. Fournir un nouveau service"
						+ "\n 2. Mise a jour du service"
						+ "\n 3. Changement d'adresse de votre serveur ftp"
						+ "\n 4. Quitter");
				String response = in.readLine();
				
				// instancier le service numero "choix" en lui passant la socket "client"
				// invoquer run() pour cette instance ou la lancer dans un thread a part 
				
				switch(Integer.valueOf(response).intValue()) {

                    case 1:
                    	/*!!!!!!!!!!!A faire:Verification que la classe n'existe pas déjà !!!!!!!!!!!*/ 
                        out.println("Entrez le nom du fichier .class de votre serveur FTP : ");
                        String className = in.readLine();
                        URL[] urls = new URL[]{ new URL(prog.getFTPAdress()) };
                        URLClassLoader ucl = new URLClassLoader(urls);
                        /*URLClassLoader ucl = new URLClassLoader(new URL[] {
                        		new URL(prog.getFTPAdress())}) {
                        	public Class<?> loadClass(String name) throws ClassNotFoundException{
                        		//if(ServiceRegistry.getServiceList().contains())
                        		List<Class<? extends Service>> list = ServiceRegistry.getServiceList();
                        		for(List<Class<? extends Service>> item : list){
                        			if(item.getName().equals(name)){
                        				return findclass(name);
                        			}
                        		}
                        		return super.loadclass(name);
                        		
                        	}
                        })*/
                        try {
                            ServiceRegistry.addService(ucl.loadClass(className));
                        }
                        catch(ClassNotFoundException e) { out.println("La classe " + className + " n'est pas trouvable"); }
                        catch (ServiceNonConformeException e) { out.println(e.getMessage()); }
                        break;

                    case 2:
                    	out.println("Choisissez un service dans la liste de ceux existant");
                        out.println(ServiceRegistry.toStringue());
                        int choice = Integer.valueOf(in.readLine()).intValue();
                        String ClassNameMaj = ServiceRegistry.getClassName(choice);
                        ServiceRegistry.RemoveIndexList(choice-1);
                        URL[] urlsNew = new URL[]{ new URL(prog.getFTPAdress()) };
                        URLClassLoader uclNew = new URLClassLoader(urlsNew);
                        try {
                            ServiceRegistry.addService(uclNew.loadClass(ClassNameMaj));
                        }catch(ClassNotFoundException e){out.println("La classe " + ClassNameMaj + " n'est pas trouvable");}
                        catch(Exception e) {
                        	e.printStackTrace();
                        }
						break;
                    case 3:
                        out.println("Entrez la nouvelle URL de votre serveur FTP : ");
                        String URL = in.readLine();
                        prog.changeFTPAdress(URL);
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

	/**
	 * @brief Authentifie un programmeur grace a son login/mdp
	 * @return le Programmeur s'il existe que que le duo login/mdp est correct, null sinon
	 */
	private Programmeur auth(String login, String mdp) {
		for(Programmeur p : pList)
			if (p.getLogin().equals(login) && p.getMdp().equals(mdp))
				return p;
		return null;
	}

	protected void finalize() throws Throwable {
		 client.close(); 
	}

	// lancement du service
	public void start() {
		(new Thread(this)).start();		
	}
	

}
