package bri;

public class ServiceNonConformeException extends Exception {
	public ServiceNonConformeException() {
		super("Le service est non conforme a la norme BLTi");
	}
}
