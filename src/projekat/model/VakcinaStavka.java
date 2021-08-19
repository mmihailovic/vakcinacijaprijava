package projekat.model;

import java.io.Serializable;

public class VakcinaStavka implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2134985518257969179L;
	private String tip;
	private int broj;
	public VakcinaStavka(String tip) {
		super();
		this.tip = tip;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public int getBroj() {
		return broj;
	}
	public void setBroj(int broj) {
		this.broj = broj;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tip == null) ? 0 : tip.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof VakcinaStavka))
			return false;
		VakcinaStavka other = (VakcinaStavka) obj;
		if (tip == null) {
			if (other.tip != null)
				return false;
		} else if (!tip.equals(other.tip))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return tip;
	}
	
}
