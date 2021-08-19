package projekat.validator;

public class JmbgValidator extends RegexValidator{
	public JmbgValidator() {
		super("[0-9]{13}");
	}
}
