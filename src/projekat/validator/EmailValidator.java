package projekat.validator;

public class EmailValidator extends RegexValidator{

	public EmailValidator() {
		super("[a-z0-9]{3,}@[a-z0-9]{3,}\\.[a-z]{2,6}");
	}
}
