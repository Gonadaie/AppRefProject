import java.io.*;
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
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			out.println("Entrez l'adresse de votre serveur ftp et le chemin du fichier");
			String fileUrl = in.readLine();
			URL ftpurl = new URL(fileUrl);

			try {
				java.net.URLConnection ftpconnect = ftpurl.openConnection();

				BufferedReader in2 = new BufferedReader(new InputStreamReader(ftpconnect.getInputStream()));
				String line;
				String testFile = in2.readLine();
				while ((line = in2.readLine()) != null) {
					testFile = testFile + line;
					out.println(line);
				}
				out.println(testFile);
				out.println(XMLIsConform(testFile));

			} catch (Exception e) {
				out.println("L'URL est incorrecte");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//String url = "ftp://localhost:2121/client/";

	}
	public static String toStringue() {
		return "Analyse de fichier XML";
	}
	public String XMLIsConform(String fileString) {
		if ((fileString.contains("<xml>") &&
				fileString.contains("</xml>")) &&
				((fileString.substring(0,5).equals("<xml>"))) &&
				(fileString.substring(fileString.length()-6, fileString.length()).equals("</xml>")))
			return "Fichier conforme";
		else
			return "Votre fichier ne contient pas de balise <xml> ou elles ne sont pas dans le bonne ordre";
	}
}
