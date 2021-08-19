package projekat.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Prijava implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1569640367443876943L;
	private int id;
	private int idOpstina;
	private String drzavljanstvo;
	private String imePrezime;
	private String jmbg;
	private String opstina;
	private String email;
	private String mobilni;
	private String specificnaoboljenja;
	private List<VakcinaStavka> vakcine = new ArrayList<>();
	public Prijava(int id,String drzavljanstvo, String imePrezime, String jmbg, int idOpstina, String email, String mobilni,
			String specificnaoboljenja) {
		super();
		this.id = id;
		this.drzavljanstvo = drzavljanstvo;
		this.imePrezime = imePrezime;
		this.jmbg = jmbg;
		this.idOpstina = idOpstina;
		this.email = email;
		this.mobilni = mobilni;
		this.specificnaoboljenja = specificnaoboljenja;
		this.opstina = nadjiOpstinu(idOpstina);
	}
	public Prijava(int id,String drzavljanstvo, String imePrezime, String jmbg, int idOpstina, String email, String mobilni,
			String specificnaoboljenja,List<VakcinaStavka> vakcine) {
		super();
		this.id = id;
		this.drzavljanstvo = drzavljanstvo;
		this.imePrezime = imePrezime;
		this.jmbg = jmbg;
		this.idOpstina = idOpstina;
		this.email = email;
		this.mobilni = mobilni;
		this.specificnaoboljenja = specificnaoboljenja;
		this.opstina = nadjiOpstinu(idOpstina);
		this.vakcine = vakcine;
	}
	public Prijava(String drzavljanstvo, String imePrezime, String jmbg, int idOpstina, String email, String mobilni,
			String specificnaoboljenja, List<VakcinaStavka> vakcine) {
		super();
		this.drzavljanstvo = drzavljanstvo;
		this.imePrezime = imePrezime;
		this.jmbg = jmbg;
		this.idOpstina = idOpstina;
		this.email = email;
		this.mobilni = mobilni;
		this.specificnaoboljenja = specificnaoboljenja;
		this.vakcine = vakcine;
		this.opstina = nadjiOpstinu(idOpstina);
	}

	public String getDrzavljanstvo() {
		return drzavljanstvo;
	}
	public void setDrzavljanstvo(String drzavljanstvo) {
		this.drzavljanstvo = drzavljanstvo;
	}
	public String getImePrezime() {
		return imePrezime;
	}
	public void setImePrezime(String imePrezime) {
		this.imePrezime = imePrezime;
	}
	
	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public String getOpstina() {
		return opstina;
	}
	public void setOpstina(String opstina) {
		this.opstina = opstina;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobilni() {
		return mobilni;
	}
	public void setMobilni(String mobilni) {
		this.mobilni = mobilni;
	}
	public String getSpecificnaoboljenja() {
		return specificnaoboljenja;
	}
	public void setSpecificnaoboljenja(String specificnaoboljenja) {
		this.specificnaoboljenja = specificnaoboljenja;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdOpstina() {
		return idOpstina;
	}
	public void setIdOpstina(int idOpstina) {
		this.idOpstina = idOpstina;
	}
	
	public List<VakcinaStavka> getVakcine() {
		return vakcine;
	}

	public void setVakcine(List<VakcinaStavka> vakcine) {
		this.vakcine = vakcine;
	}

	private String nadjiOpstinu(int opstinaId) {
		String url = "jdbc:mysql://localhost:3306/baza";
		String username = "root";
		String password = "BazaPoDaTaKa@MaRe-2002";
		String pronadjenaopstina = " ";
		try {
			Connection connection = DriverManager.getConnection(url,username,password);
			String sql = "SELECT Naziv from opstina where idOpstina = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, opstinaId);
			ResultSet result = statement.executeQuery();
			while(result.next()) 
			{  
				pronadjenaopstina = result.getString(1); 
			}
			statement.close();
			connection.close();
			result.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("GRESKA");
			e.printStackTrace();
		}
		
		return pronadjenaopstina;
	}

	@Override
	public String toString() {
		return "Prijave [id=" + id + ", idOpstina=" + idOpstina + ", drzavljanstvo=" + drzavljanstvo + ", imePrezime="
				+ imePrezime + ", jmbg=" + jmbg + ", opstina=" + opstina + ", email=" + email + ", mobilni=" + mobilni
				+ ", specificnaoboljenja=" + specificnaoboljenja + ", vakcine=" + vakcine + "]";
	}
	
}
