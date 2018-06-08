package clientProg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ApplicationProg {
	private final static int PORT_SERVICE = 3500;
	private final static String HOST = "localhost"; 
	
	public static void main(String []args){
		Socket s = null;
		try{
			s= new Socket(HOST, PORT_SERVICE);
			
			BufferedReader sin = new BufferedReader (new InputStreamReader(s.getInputStream ( )));
			PrintWriter sout = new PrintWriter (s.getOutputStream ( ), true);
			BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("Connect� au serveur " + s.getInetAddress() + ":"+ s.getPort() + "\n");
			System.out.println("Entrez votre login : \n");
			sout.println(clavier.readLine());
			
			
		}catch(IOException e){
			
		}
	}
}