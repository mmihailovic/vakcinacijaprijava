package projekat.model;

import java.io.Serializable;

public class Poruka implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7172124111860477706L;
	private String poruka;

	public Poruka(String poruka) {
		super();
		this.poruka = poruka;
	}

	public String getPoruka() {
		return poruka;
	}

	public void setPoruka(String poruka) {
		this.poruka = poruka;
	}
	
}
