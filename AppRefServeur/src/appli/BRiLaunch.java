package appli;

import java.net.Socket;
import java.net.URLClassLoader;
import java.util.Scanner;

import bri.ServeurBRi;
import bri.ServiceServeurBRi;
import bri.ServiceServeurBRiClient;
import bri.ServiceBRiNonConformeException;

public class BRiLaunch {
	private final static int PORT_SERVICE_AMA = 3000;
	private final static Socket  sama = null;
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner clavier = new Scanner(System.in);
		
		// URLClassLoader sur ftp
		URLClassLoader urlcl = null;
		
		System.out.println("Bienvenue dans votre gestionnaire dynamique d'activit� BRi");
		System.out.println("Pour ajouter une activit�, celle-ci doit �tre pr�sente sur votre serveur ftp");
		System.out.println("A tout instant, en tapant le nom de la classe, vous pouvez l'int�grer");
		System.out.println("Les clients se connectent au serveur 3000 pour lancer une activit�");
		
		try {
			new Thread(new ServeurBRi(PORT_SERVICE_AMA, ServiceServeurBRiClient.class)).start();
		} catch(ServiceBRiNonConformeException e) {}
		
		while (true){
				try {
					String classeName = clavier.next();
					// charger la classe et la d�clarer au ServiceRegistry
				} catch (Exception e) {
					System.out.println(e);
				}
			}		
	}
}
