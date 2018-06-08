package bri;


import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.*;


public class ServeurBRi implements Runnable {
	private ServerSocket listen_socket;
	private Class<? extends ServiceServeurBRi> cl;
	
	// Cree un serveur TCP - objet de la classe ServerSocket
	public ServeurBRi(int port, Class<? extends ServiceServeurBRi> cl ) throws ServiceBRiNonConformeException {
		try {
			listen_socket = new ServerSocket(port);
			this.cl = cl;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// Le serveur ecoute et accepte les connections.
	// pour chaque connection, il cree un ServiceInversion, 
	// qui va la traiter.
	public void run() {
		try {
			while(true){
				new Thread(cl.getConstructor(Socket.class).newInstance(listen_socket)).start();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	 // restituer les ressources --> finalize
	protected void finalize() throws Throwable {
		try {this.listen_socket.close();} catch (IOException e1) {}
	}

	// lancement du serveur
	public void lancer() {
		(new Thread(this)).start();		
	}
}
