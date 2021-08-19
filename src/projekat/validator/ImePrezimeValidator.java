package projekat.validator;

public class ImePrezimeValidator extends RegexValidator{

	public ImePrezimeValidator() {
		super("[A-Z][a-z]{1,}([ -][A-Z][a-z]*)*");
	}
}
