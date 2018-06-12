package prog;

public class Programmeur {
	private String login;
	private String mdp;
	private String ftpAdress;
	
	public Programmeur(String login, String mdp, String ftpAdress){
		this.login = login;
		this.mdp = mdp;
		this.ftpAdress = ftpAdress;
	}
	
	public String getLogin() {
		return login;
	}

	public String getMdp() {
		return mdp;
	}

	public String getFTPAdress() {
		return ftpAdress;
	}

	public void changeFTPAdress(String newAdress) {
		this.ftpAdress = newAdress;
	}
	
}
