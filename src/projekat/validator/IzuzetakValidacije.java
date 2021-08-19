package projekat.validator;

public class IzuzetakValidacije extends Exception{

	public IzuzetakValidacije() {
		super();
	}
	
	public IzuzetakValidacije(String message) {
		super(message);
	}
	
	public IzuzetakValidacije(String message,Throwable cause) {
		super(message,cause);
	}
}
