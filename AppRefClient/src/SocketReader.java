import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

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
			while((msg = in.readLine()) != null || msg.equals("exit"))
				System.out.println(msg);

		}
		catch (IOException e) { e.printStackTrace(); }
		catch (NullPointerException e) { System.out.println("Interruption de la communication"); }

		try { in.close(); } catch(IOException e) {e.printStackTrace();}

		done = true;
	}

	public boolean isDone() {
	    return done;
    }
}
