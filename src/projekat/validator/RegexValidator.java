package projekat.validator;

import java.util.regex.Pattern;

public class RegexValidator implements Validator{
	private Pattern pattern;

	public RegexValidator(Pattern pattern) {
		super();
		this.pattern = pattern;
	}
	
	public RegexValidator(String pattern) {
		super();
		this.pattern = Pattern.compile(pattern);
	}

	@Override
	public void validiraj(Object obj, String poruka) throws IzuzetakValidacije {
		// TODO Auto-generated method stub
		String s = String.valueOf(obj);
		if(!pattern.matcher(s).matches()) {
			throw new IzuzetakValidacije(poruka);
		}
	}
	
	
}
