package appli;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;

import bri.ServeurBRi;
import bri.ServiceRegistry;

public class BRiLaunch {
	private final static int PORT_SERVICE = 3000;
	
	public static void main(String[] args) throws Exception {
		@SuppressWarnings("resource")
		Scanner clavier = new Scanner(System.in);
		
		// URLClassLoader sur ftp
		String ftpDir = "ftp://localhost:2121/";
			URLClassLoader urlcl = new URLClassLoader(new URL[] {new URL(ftpDir)});
			
		
		
		System.out.println("Bienvenue dans votre gestionnaire dynamique d'activit� BRi");
		System.out.println("Pour ajouter une activit�, celle-ci doit �tre pr�sente sur votre serveur ftp");
		System.out.println("A tout instant, en tapant le nom de la classe, vous pouvez l'int�grer");
		System.out.println("Les clients se connectent au serveur 3000 pour lancer une activit�");
		
		new Thread(new ServeurBRi(PORT_SERVICE)).start();
		
		while (true){
				try {
					String classeName = clavier.next();
					// charger la classe et la d�clarer au ServiceRegistry
					Class<?> classecharge = urlcl.loadClass(classeName);
					ServiceRegistry.addService(classecharge);
				} catch (Exception e) {
					e.printStackTrace();;
				}
			}		
	}
}
