package appli;

public class BRiLaunch {
	private final static int PORT_SERVICE_AMA = 3000;
	private final static int PORT_SERVICE_PROG = 3500;
	
	public static void main(String[] args) {
		
		System.out.println("Bienvenue dans votre gestionnaire dynamique d'activite BRi");
		
		new Thread(new ServeurBRi(PORT_SERVICE_AMA, ServiceServeurBRiAma.class)).start();
		new Thread(new ServeurBRi(PORT_SERVICE_PROG, ServiceServeurBRiProg.class)).start();
	}
}
