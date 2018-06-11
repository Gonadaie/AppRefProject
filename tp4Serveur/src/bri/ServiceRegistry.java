package bri;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class ServiceRegistry {
	// cette classe est un registre de services
	// partag�e en concurrence par les clients et les "ajouteurs" de services,
	// un Vector pour cette gestion est pratique
	
	private static List<Class <? extends Runnable>> servicesClasses = new ArrayList<Class<? extends Runnable>>();

// ajoute une classe de service apr�s contr�le de la norme BLTi
	public static void addService(Class<?> classload) throws InstantiationException, IllegalAccessException {
		// v�rifier la conformit� par introspection
		// si non conforme --> exception avec message clair
		// si conforme, ajout au vector
		servicesClasses.add((Class<? extends Runnable>) classload);
	}
	
// renvoie la classe de service (numService -1)	
	public static Class<? extends Runnable> getServiceClass(int numService) {
		return servicesClasses.get(numService);
	}
	
// liste les activit�s pr�sentes
	public static String toStringue() {
		String result = "Activit�s pr�sentes :##"+ servicesClasses.toString();
		// todo
		return result;
	}

}
