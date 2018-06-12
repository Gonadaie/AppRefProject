package bri;


import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.*;


public class ServiceServeurBRiAma implements ServiceServeurBRi {
	
	private Socket client;
	
	public ServiceServeurBRiAma(Socket socket) {
		client = socket;
	}

	public void run() {
		try {
		    BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);

			out.println("Bienvenue sur la plateforme de service Brette !");
			out.println("Veuillez choisir un service : ");
			out.println(ServiceRegistry.toStringue());

			int choix = Integer.parseInt(in.readLine());
			
			// instancier le service numero "choix" en lui passant la socket "client"
			// invoquer run() pour cette instance ou la lancer dans un thread a part 
            Class<? extends Service> serviceClass = ServiceRegistry.getServiceClass(choix);
            try {
                serviceClass.getConstructor(Socket.class).newInstance(client).run();
            } catch(NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) { e.printStackTrace(); }
        }
		catch (IOException e) {
			//Fin du service
		}

		try {client.close();} catch (IOException e2) {}
	}
	
	protected void finalize() throws Throwable {
		 client.close(); 
	}

	// lancement du service
	public void start() {
		(new Thread(this)).start();		
	}

}
