import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class SocketReader implements Runnable {

	private Socket s;
	private BufferedReader in;
	private boolean done;

	public SocketReader(Socket s) throws IOException {
		this.s = s;
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		done = false;
	}
	
	@Override
	public void run() {
		String msg;
		try {
			while ((msg = in.readLine()) != null) {
				if (msg.equals("exit")) {
					System.out.println("Fin de la communication");
					break;
				}
				System.out.println(msg);
			}
		}
		catch (NullPointerException | SocketException e) { System.out.println("Interruption de la communication"); }
		catch (IOException e) { e.printStackTrace(); }

		System.out.println("Appuyez sur entree pour quitter");
		try {
			close();
		} catch(IOException e) {e.printStackTrace();}
	}

	public synchronized boolean isDone() {
		return done;
	}

	public void close() throws IOException {
		in.close();
		done = true;
	}
}
