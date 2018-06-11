package loadjar;

import java.util.ArrayList;
import java.util.List;

public class RunnableManager {
	// cette classe g�re des activit�s (objets Runnable) 
	// et (exercice 2) les espions associ�s qui comptent le nombre d'utilisations

	static {
		runnables = new ArrayList<Runnable>();
		espions = new ArrayList<RunnableInvocationCpt>(); // exercice 2
	}
	private static List<Runnable> runnables;
	private static List<RunnableInvocationCpt> espions; // exercice 2

// ajoute la runnableClass au RunnableManager
	public static void addRunnable(Class<?> runnableClass) {
		try {
			Runnable r = (Runnable)runnableClass.newInstance();
			runnables.add(r);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
// lance le runnable numRunnable
	public static void lance(int numRunnable) {
		Runnable test = runnables.get(numRunnable);
		test.run();
	}
	
// liste les activit�s pr�sentes
	public static String toStringue() {
			return "Liste des actvit�s";
	}
	
// liste le nombre d'utilisations de chaque activit�
	public static String bilan() {
		return "Exercice 2";
	}

}
