package bri;

public class ServiceBRiNonConformeException extends Exception {
	public ServiceBRiNonConformeException() {
		super("Le service est non conforme a la norme BLTi");
	}
}
