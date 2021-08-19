package projekat.model;

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
import java.sql.Timestamp;
import java.time.LocalDateTime;

import projekat.controller.Servis;

public class ServerWorker extends Thread{
	private Socket clientSocket;

	public ServerWorker(Socket clientSocket) {
		super();
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		handleClientSocket();
	}
	
	private void handleClientSocket() {
		try {
			ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
			ObjectInputStream inStream = new ObjectInputStream(clientSocket.getInputStream());
			boolean dodato = false;
			try {
				Prijava paket = (Prijava)inStream.readObject();
				try {
					Connection connection = Servis.konektovanje();
					
					String drzavljanstvo = paket.getDrzavljanstvo();
					String jmbg = paket.getJmbg();
					String ime = paket.getImePrezime().substring(0,paket.getImePrezime().indexOf(' '));
					String prezime = paket.getImePrezime().substring(paket.getImePrezime().indexOf(' ') + 1,paket.getImePrezime().length());
					String email = paket.getEmail();
					String mobilni = paket.getMobilni();
					String specificnaOboljenja = paket.getSpecificnaoboljenja();
					String opstina = paket.getOpstina();
					String sql = "INSERT INTO prijava (Drzavljanstvo,JMBG,Ime,Prezime,ElektronskaPosta,Mobilnitelefon,SpecificnaOboljenja,DatumPrijave,OpstinaID) VALUES(?,?,?,?,?,?,?,?,?)";
					
					PreparedStatement statement = connection.prepareStatement(sql);
					statement.setString(1, drzavljanstvo);
					statement.setString(2, jmbg);
					statement.setString(3, ime);
					statement.setString(4, prezime);
					statement.setString(5, email);
					statement.setString(6, mobilni);
					statement.setString(7, specificnaOboljenja);
					statement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
					statement.setInt(9, paket.getIdOpstina());
					statement.executeUpdate();
					statement.close();
					System.out.println("UBACENO");
					
					Statement indexstatement = connection.createStatement();
					String sqll = "SELECT MAX(idPrijava) FROM prijava";
					ResultSet indices = indexstatement.executeQuery(sqll);
					int index = 0;
					while(indices.next()) {
						System.out.println(indices.getInt(1));
						index = indices.getInt(1);
					}
					indexstatement.close();
					System.out.println("Indeks je " + index);
					String insert = "INSERT INTO stavkaprijave (PrijavaID,RB,TipVakcine) VALUES (?,?,?)";
					PreparedStatement statement1 = connection.prepareStatement(insert);
					for(VakcinaStavka v:paket.getVakcine()) {
						statement1.setInt(1, index);
						statement1.setInt(2, v.getBroj());
						statement1.setString(3, v.getTip());
						statement1.executeUpdate();
					}
					System.out.println("UBACENO");
					statement1.close();
					connection.close();
				} catch (SQLException e1) {
					System.out.println("GRESKA");
					e1.printStackTrace();
				}
				dodato = true;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Poruka poruka;
			if(dodato)poruka = new Poruka("Uspesno dodato!");
			else poruka = new Poruka("Nije uspesno dodato!");
			outStream.writeObject(poruka); // salje clientu poruku
			//outStream.close();
			//serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
