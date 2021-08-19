package projekat.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import projekat.controller.Servis;
import projekat.model.Prijava;
import projekat.model.ServerWorker;

public class ServerView extends VBox implements Runnable{
	private Label lbltop1 = new Label("ePrijava za vakcinaciju");
	private Label lbltop2 = new Label("Institut za javno zdravlje Srbije");
	private Label lbltop3 = new Label("\"Dr Milan Jovanovic Batut\"");
	private Label lblUnosPrijave = new Label("Pregled prijava");
	private Button btnPonistiPrijavu = new Button("Ponisti prijavu");
	private TableView<Prijava> tv = new TableView<>();
	private List<Prijava> prijave = new ArrayList<>();
	private ServerSocket serverSocket;
	public ServerView() {
		Thread t = new Thread(this);
		t.start();
		InputStream stream;
		try {
			stream = new FileInputStream("C:\\Users\\marko\\Desktop\\logo_.jpg");
			Image image = new Image(stream);
			ImageView imageView = new ImageView();
			imageView.setImage(image);
			VBox vboxtop = new VBox(5);
			vboxtop.getChildren().addAll(lbltop1,lbltop2,lbltop3);
			HBox hboxtop = new HBox(50);
			hboxtop.getChildren().addAll(imageView,vboxtop);
			tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			tv.setPrefHeight(300);
			TableColumn<Prijava,String> tcImePrezime = new TableColumn<>("Ime i prezime");
			tcImePrezime.setCellValueFactory(new PropertyValueFactory<>("imePrezime"));
			TableColumn<Prijava,String> tcJmbg = new TableColumn<>("JMBG");
			tcJmbg.setCellValueFactory(new PropertyValueFactory<>("jmbg"));
			TableColumn<Prijava,String> tcDrzavljanstvo = new TableColumn<>("Drzavljanstvo");
			tcDrzavljanstvo.setCellValueFactory(new PropertyValueFactory<>("drzavljanstvo"));
			TableColumn<Prijava,String> tcOpstina = new TableColumn<>("Opstina");
			tcOpstina.setCellValueFactory(new PropertyValueFactory<>("Opstina"));
			TableColumn<Prijava,String> tcEmail = new TableColumn<>("El. posta");
			tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
			TableColumn<Prijava,String> tcMobilni = new TableColumn<>("Mobilni");
			tcMobilni.setCellValueFactory(new PropertyValueFactory<>("mobilni"));
			TableColumn<Prijava,String> tcSpecOboljenja = new TableColumn<>("Specificna oboljenja");
			tcSpecOboljenja.setCellValueFactory(new PropertyValueFactory<>("specificnaoboljenja"));
			tv.getColumns().addAll(tcImePrezime,tcJmbg,tcDrzavljanstvo,tcOpstina,tcEmail,tcMobilni,tcSpecOboljenja);
			getChildren().addAll(hboxtop,lblUnosPrijave,tv,btnPonistiPrijavu);
			setSpacing(10);
			initActions();
			setPadding(new Insets(10));
			try {
				serverSocket = new ServerSocket(6666);
				System.out.println("Konektovan na server");
				System.out.println("Cekanje na klijenta");
				Thread ts = new Thread() {
					@Override
					public void run() {
						while(true) {
							Socket socket;
							try {
								socket = serverSocket.accept();
								System.out.println("Konektovan klijent " + socket);
								ServerWorker serverWorker = new ServerWorker(socket);
								serverWorker.start();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				};
				ts.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void initActions() {
		btnPonistiPrijavu.setOnAction(e1 -> {
			if(tv.getSelectionModel().getSelectedItem() != null) {
				int index = tv.getSelectionModel().getSelectedItem().getId();
				Servis.ponistiPrijavu(index);
				/*String url = "jdbc:mysql://localhost:3306/baza";
				String username = "root";
				String password = "BazaPoDaTaKa@MaRe-2002";
				try {
					Connection connection = DriverManager.getConnection(url,username,password);
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
				}*/
			}
		});
		tv.setOnMouseClicked(e -> {
			if(e.getClickCount() == 2) {
				Prijava p = tv.getSelectionModel().getSelectedItem();
				Alert alert = new Alert(AlertType.INFORMATION,"Ime i prezime: " + p.getImePrezime()
					+ "\nVakcine: " + p.getVakcine() + "\n",ButtonType.OK);
				alert.setTitle("INFORMACIJE O PRIJAVI");
				alert.setHeaderText(" ");
				alert.showAndWait();
			}
		});
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) 
		{
			//System.out.println(LocalDateTime.now());
			//String url = "jdbc:mysql://localhost:3306/baza";
			//String username = "root";
			//String password = "BazaPoDaTaKa@MaRe-2002";
			//Connection connection;
			/*try {
				//connection = DriverManager.getConnection(url,username,password);
				Connection connection = Servis.konektovanje();
				String upit = "SELECT * from prijava";
				Statement statementUbaci = connection.createStatement();
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
				tv.getItems().clear();
				tv.getItems().addAll(prijave);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			List<Prijava> prijave = Servis.ucitajPrijave();
			tv.getItems().clear();
			tv.getItems().addAll(prijave);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
