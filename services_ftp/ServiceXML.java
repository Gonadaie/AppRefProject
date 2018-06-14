import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

import bri.Service;



public class ServiceXML implements Service{
	
	private final Socket client;

	
	public ServiceXML(Socket client) {
		this.client = client;
	}
	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
			out.println("Entrez l'adresse de votre serveur ftp et le chemin du fichier");
			String fileUrl= in.readLine();
			URL ftpurl = new URL(fileUrl);
			
			java.net.URLConnection ftpconnect = ftpurl.openConnection();
			 
			BufferedReader in2 = new BufferedReader (new InputStreamReader(ftpconnect.getInputStream ( )));
			String line;
			String testFile = in2.readLine();
			while((line = in2.readLine()) != null) {
				testFile = testFile + line;
				out.println(line);
			}
			out.println(testFile);
			out.println(XMLIsConformed(testFile));		
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//String url = "ftp://localhost:2121/client/";
		
	}
	public static String toStringue() {
		return null;
	}
	public String XMLIsConformed(String fileString) {
		if ((fileString.contains("<xml>") && 
				fileString.contains("</xml>")) && 
				((fileString.substring(0,5).equals("<xml>"))) && 
						(fileString.substring(fileString.length()-6, fileString.length()).equals("</xml>")))
			return "Fichier conforme";
		else 
			return "Votre fichier ne contient pas de balise <xml> ou elle sont pas dans le bonne ordre";
	}
}
