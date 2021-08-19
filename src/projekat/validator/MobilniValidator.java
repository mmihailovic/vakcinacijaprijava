package projekat.validator;

public class MobilniValidator extends RegexValidator{
	public MobilniValidator() {
		super("[0-9]{9,10}");
	}
}
