package bri;


import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.*;


class ServiceBRi implements Runnable {
	
	private Socket client;
	
	ServiceBRi(Socket socket) {
		client = socket;
	}

	public void run() {
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
			out.println(ServiceRegistry.toStringue()+"##Tapez le num�ro de service d�sir� :");
			int choix = Integer.parseInt(in.readLine());
			Class<? extends Runnable> choixclass = (Class<? extends Runnable>) ServiceRegistry.getServiceClass(choix);
			//new Thread((Runnable) choixclass.newInstance()).start();
			try {
				new Thread(choixclass.getConstructor(Socket.class).newInstance(this.client)).start();
				
			}
			// instancier le service num�ro "choix" en lui passant la socket "client"
			// invoquer run() pour cette instance ou la lancer dans un thread � part 
			catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			}
		catch (IOException e) {
			//Fin du service
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//try {client.close();} catch (IOException e2) {}
	}
	
	protected void finalize() throws Throwable {
		 client.close(); 
	}

	// lancement du service
	public void start() {
		(new Thread(this)).start();		
	}

}
