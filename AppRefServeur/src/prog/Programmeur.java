package prog;

public class Programmeur {
	private String login;
	private String mdp;
	private String ftplink;
	
	public Programmeur(String login, String mdp, String ftplink){
		this.login = login;
		this.mdp = mdp;
		this.ftplink = ftplink;		
	}
	
	public String getLogin() {
		return login;
	}
	public String getMdp() {
		return mdp;
	}
	public String getFtplink() {
		return ftplink;
	}
	
	
}
