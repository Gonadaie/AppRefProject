import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketReader implements Runnable {

	private Socket s;
	private BufferedReader in;
	
	public SocketReader(Socket s) throws IOException {
		this.s = s;
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
	}
	
	@Override
	public void run() {
		String msg;
		try {
			while((msg = in.readLine()) != null || msg.equals("exit"))
				System.out.println(msg);
		} catch (IOException e) { e.printStackTrace(); }
	}
}
