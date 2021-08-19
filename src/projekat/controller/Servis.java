package projekat.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import projekat.model.Poruka;
import projekat.model.Prijava;
import projekat.model.VakcinaStavka;

public class Servis {
	public static Connection konektovanje() {
		String url = "jdbc:mysql://localhost:3306/baza";
		String user = "root";
		String password = "";
		try {
			Connection connection = DriverManager.getConnection(url,user,password);
			return connection;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static void ubaciUTv(List<VakcinaStavka> vakcine,TableView<VakcinaStavka> tv) {
		int num = 1;
		if(vakcine.size() == 4 || vakcine.get(vakcine.size() - 1).getTip().equals("Bilo koji tip"))
		{
			vakcine.clear();
			vakcine.add(new VakcinaStavka("Bilo koji tip"));
		}
		else if(vakcine.get(0).getTip().equals("Bilo koji tip") && vakcine.size() > 1)
		{
			vakcine.remove(0);
		}
		for(VakcinaStavka v:vakcine) {
			v.setBroj(num++);
		}
		tv.getItems().clear();
		tv.getItems().addAll(vakcine);
	}
	public static void sacuvaj(Socket socket,List<VakcinaStavka> vakcine,String drzavljanstvo,String jmbg,String ime,String prezime,String email,String mobilni,String specificnaOboljenja,int idOpstina) {
		try {
			ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
			Prijava p = new Prijava(drzavljanstvo,ime + " " + prezime,jmbg,idOpstina,email,mobilni,specificnaOboljenja,vakcine);
			outStream.writeObject(p);
			Poruka poruka;
			try {
				poruka = (Poruka)inStream.readObject();
				Alert alert = new Alert(AlertType.INFORMATION,poruka.getPoruka(),ButtonType.OK);
				alert.showAndWait();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public static void ubaciOpstine(ComboBox<String> cmbOpstina) {
		try {
			Connection connection = konektovanje();
			String sql = "SELECT Naziv from opstina";
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			List<String> opstine = new ArrayList<>();
			while(result.next()) {
				String naziv = result.getString(1);
				opstine.add(naziv);
			}
			connection.close();
			statement.close();
			result.close();
			cmbOpstina.getItems().addAll(opstine);
			System.out.println("Konektovan na bazu");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Nije konektovan na bazu");
			e.printStackTrace();
		}
	}
	public static List<Prijava> ucitajPrijave() {
		List<Prijava> prijave = new ArrayList<>();
		Connection connection = Servis.konektovanje();
		String upit = "SELECT * from prijava";
		Statement statementUbaci;
		try {
			statementUbaci = connection.createStatement();
			ResultSet result = statementUbaci.executeQuery(upit);
			prijave.clear();
			while(result.next()) {
				List<VakcinaStavka> vakcine = new ArrayList<>();
				int idPrijava = result.getInt(1);
				String drzavljanstvoo = result.getString(2);
				String jmbgg = result.getString(3);
				String imeprezime = result.getString(4) + " " + result.getString(5);
				String emaill = result.getString(6);
				String mob = result.getString(7);
				String spec = result.getString(8);
				int opstinaid = result.getInt(10);
				
				String vakcineUpit = "SELECT TipVakcine from stavkaprijave WHERE PrijavaID = ?";
				PreparedStatement statementVakcine = connection.prepareStatement(vakcineUpit);
				statementVakcine.setInt(1, idPrijava);
				ResultSet vakcineRez = statementVakcine.executeQuery();
				while(vakcineRez.next()) {
					vakcine.add(new VakcinaStavka(vakcineRez.getString(1)));
				}
				statementVakcine.close();
				prijave.add(new Prijava(idPrijava,drzavljanstvoo,imeprezime,jmbgg,opstinaid,emaill,mob,spec,vakcine)); 
			}
			statementUbaci.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prijave;
	}
	public static void ponistiPrijavu(int index) {
		try {
			Connection connection = Servis.konektovanje();
			String sql = "DELETE FROM stavkaprijave WHERE PrijavaID = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, index);
			int rowcount = statement.executeUpdate();
			System.out.println("Obrisano redova " + rowcount);
			
			sql = "DELETE FROM prijava WHERE idPrijava = ?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, index);
			rowcount = statement.executeUpdate();
			System.out.println("Obrisano redova " + rowcount);
			
			connection.close();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("GRESKA");
			e.printStackTrace();
		}
	}
}
