package bri;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ServiceRegistry {
	// cette classe est un registre de services
	// partagee en concurrence par les clients et les "ajouteurs" de services,
	// un Vector pour cette gestion est pratique

	private static List<Class<? extends Service>> servicesClasses;
	static {
		servicesClasses = new LinkedList<Class <? extends Service>>();
	}

// ajoute une classe de service apres controle de la norme BLTi
	public static void addService(Class<?> cl) throws ServiceNonConformeException {
		// verifier la conformite par introspection
		// si non conforme --> exception avec message clair
		// si conforme, ajout au vector
		if(!checkServiceBLTiClass(cl)) throw new ServiceNonConformeException();
		servicesClasses.add((Class<? extends Service>)cl);
	}
	
// renvoie la classe de service (numService -1)	
	public static Class<? extends Service> getServiceClass(int numService) {
		return servicesClasses.get(numService-1);
	}
	
	private static boolean checkServiceBLTiClass(Class<?> cl) {
		try {
			
			//Check if the class is implementing the Service interface
			Class<?>[] interfaces = cl.getInterfaces();
			for(Class<?> c : interfaces) {
				if(c != Service.class)
					return false;
			}

			System.out.println("Il est passé par ici");
			
			//Check if the constructor take a Socket as parameter and does not throw any exception
			if(cl.getConstructor(Socket.class) == null) return false;
			System.out.println("Et si c'était ici ???");
			if(cl.getConstructor(Socket.class).getExceptionTypes().length > 0) return false;

			System.out.println("Il repassera par là");

			//Check if the class is public and not abstract
			if (	Modifier.isPublic(cl.getModifiers()) &&
					Modifier.isAbstract(cl.getModifiers()))
				return false;
			System.out.println("Il court il court le furet");
			//Check if there's a final socket
			Field[] fields = cl.getDeclaredFields();
			boolean finalSocket = false;
			for(Field f : fields) {
				if(f.getType() == Socket.class)
                    if(Modifier.isFinal(f.getModifiers()))
                        finalSocket = true;
			}
			if(!finalSocket) return false;

			System.out.println("Le furet du bois joliii");

			//Check if there is a public static method toStringue throwing no exceptions
		    Method toStringue = cl.getMethod("toStringue");
			if(toStringue == null ||
                    !Modifier.isStatic(toStringue.getModifiers()) ||
                    !Modifier.isPublic(toStringue.getModifiers()) ||
                    toStringue.getExceptionTypes().length > 0)
			    return false;
			
			return true;
		}
		catch (NoSuchMethodException e) {}
		catch (SecurityException e) {}
		return false;
	}
	
// liste les activites presentes
	public static String toStringue() {
		String result = "Activites presentes :";
		int count = 0;
		for(Class<?> cl : servicesClasses) {
			result = result + "\n" + (++count) + ". " + cl.getName();
		}
		return result;
	}

}
