package bri;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.Socket;
import java.util.List;

public class ServiceRegistry {
	// cette classe est un registre de services
	// partag�e en concurrence par les clients et les "ajouteurs" de services,
	// un Vector pour cette gestion est pratique

	static {
		servicesClasses = null;
	}
	private static List<Class<?>> servicesClasses;

// ajoute une classe de service apr�s contr�le de la norme BLTi
	public static void addService(Class<?> cl) throws ServiceBRiNonConformeException {
		// v�rifier la conformit� par introspection
		// si non conforme --> exception avec message clair
		// si conforme, ajout au vector
		if(!checkServiceBRiClass(cl)) throw new ServiceBRiNonConformeException();
	}
	
// renvoie la classe de service (numService -1)	
	public static void getServiceClass(int numService) {
		
	}
	
	private static boolean checkServiceBRiClass(Class<?> cl) {
		try {
			
			//Check if the class is implementing the ServiceBRi interface
			Class<?>[] interfaces = cl.getInterfaces();
			for(Class<?> c : interfaces) {
				if(c != ServiceBRi.class)
					return false;
			}
			
			//Check if the constructor take a Socket as parameter and does not throw any exception
			if(cl.getConstructor(Socket.class) != null) return false;
			if(cl.getConstructor(Socket.class).getExceptionTypes().length > 0) return false;
			
			//Check if the class is public and abstract
			if (	Modifier.isPublic(cl.getModifiers()) &&
					!Modifier.isAbstract(cl.getModifiers()))
				return false;
			
			//Check if there's a final socket
			Field[] fields = cl.getDeclaredFields();
			boolean finalSocket = false;
			for(Field f : fields) {
				if(Modifier.isFinal(f.getModifiers()))
					finalSocket = true;
			}
			if(!finalSocket) return false; 
			
			return true;
		}
		catch (NoSuchMethodException e) {}
		catch (SecurityException e) {}
		return false;
	}
	
// liste les activit�s pr�sentes
	public static String toStringue() {
		String result = "Activit�s pr�sentes :##";
		// todo
		return result;
	}

}
