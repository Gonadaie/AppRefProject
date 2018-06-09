import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class App {
	
	private static final String SERVER_ADR = "localhost";
	private static final int PORT_AMA = 3000;
	private static final int PORT_PROG = 3500;
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Client a utiliser :\n1. Amateur\n2. Programmeur");
		int choice = sc.nextInt();
		sc.nextLine();
		
		try {
			Socket s;
			int port = 0;
			if(choice == 1)
				port = PORT_AMA;
			else if(choice == 2)
				port = PORT_PROG;
			
			s = new Socket(SERVER_ADR, port);
			SocketReader in = new SocketReader(s);
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			
			(new Thread(in)).start();
			while(true) {
				out.println(sc.nextLine());
			}
			
		} catch(IOException e) {e.printStackTrace();}
	}
}
