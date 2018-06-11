package loadjar;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;

public class TestFileJarLoading {

	// chargement dynamique d'un jarfile contenant une seule classe
	// cette classe impl�mente l'interface VerySimple

	public static void main(String[] args) throws Exception {
		Scanner clavier = new Scanner(System.in);
// myclasses est un r�pertoire dans le WS courant
// dans lequel il faut placer les classes d'activit�s compil�es
		String fileDir = "/solown/Documents/myclasses/"; 
		String fileDirURL = "file:"+fileDir;  // ou file:///z:/myworkspace/etc en adressage absolu
		URLClassLoader urlcl = 
				new URLClassLoader(new URL[] {new URL(fileDirURL)});// � faire
		
		System.out.println("Bienvenue dans votre gestionnaire dynamique d'activit�");
		System.out.println("1 : ajouter une activit� nouvelle");
		System.out.println("2 : lancer une activit� d�j� charg�e");
		System.out.println("3 : faire un bilan des activit�s"); // exercice 2
		
		while (true){
			System.out.print(">");
			
			switch (clavier.next()){
			case "1" : 
				System.out.print("Nom de la classe :");
				String classeName = clavier.next();
				Class<?> classeCharge = urlcl.loadClass(classeName); // � faire
				RunnableManager.addRunnable(classeCharge);
				break;
			case "2" : 
				System.out.println(RunnableManager.toStringue());
				System.out.print("N� de l'activit� :");
				RunnableManager.lance(Integer.parseInt(clavier.next())-1);
				break;
			case "3" : // exercice 2
				System.out.println(RunnableManager.bilan());
				break;
			}
		}
		
		
	}
}
