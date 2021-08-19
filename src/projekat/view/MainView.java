package projekat.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import projekat.controller.Servis;
import projekat.model.VakcinaStavka;
import projekat.validator.EmailValidator;
import projekat.validator.ImePrezimeValidator;
import projekat.validator.JmbgValidator;
import projekat.validator.MobilniValidator;

public class MainView extends VBox {
	private Label lbltop1 = new Label("ePrijava za vakcinaciju");
	private Label lbltop2 = new Label("Institut za javno zdravlje Srbije");
	private Label lbltop3 = new Label("\"Dr Milan Jovanovic Batut\"");
	private Label lblDrzavljanstvo = new Label("Drzavljanstvo:");
	private Label lblJmbg = new Label("JMBG:");
	private Label lblIme = new Label("Ime:");
	private Label lblPrezime = new Label("Prezime:");
	private Label lblEmail = new Label("Elektronska posta");
	private Label lblTel = new Label("Mobilni telefon");
	private Label lblOboljenja = new Label("Specificna oboljenja");
	private Label lblOpstina = new Label("Opstina:");
	private Label lblUnosPrijave = new Label("Unos prijave");
	private Label lblTipVakcine = new Label("Tip vakcine:");
	private ComboBox<String> cmbDrzavljanstvo = new ComboBox<>();
	private ComboBox<String> cmbVakcine = new ComboBox<>();
	private TextField tfJmbg = new TextField();
	private TextField tfIme = new TextField();
	private TextField tfPrezime = new TextField();
	private TextField tfEmail = new TextField();
	private TextField tfTel = new TextField();
	private ComboBox<String> cmbOpstina = new ComboBox<>();
	private Button btnDodaj = new Button("Dodaj tip vakcine");
	private Button btnObrisi = new Button("Obrisi tip vakcine");
	private Button btnSacuvajPrijavu = new Button("Sacuvaj prijavu");
	private RadioButton rbDa = new RadioButton("Da");
	private RadioButton rbNe = new RadioButton("Ne");
	private ToggleGroup tg = new ToggleGroup();
	private TableView<VakcinaStavka> tv = new TableView<>();
	private List<VakcinaStavka> vakcine = new ArrayList<>();
	private Socket socket;
	
	public MainView() {
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
			GridPane gp = new GridPane();
			gp.addRow(0, lblUnosPrijave);
			gp.add(lblDrzavljanstvo, 0, 1); gp.add(cmbDrzavljanstvo, 1, 1,5,1);
			gp.add(lblJmbg, 0, 2); gp.add(tfJmbg, 1, 2,5,1);
			gp.add(lblIme, 0, 3); gp.add(tfIme, 1, 3,5,1);
			gp.add(lblPrezime, 0, 4); gp.add(tfPrezime, 1, 4,5,1);
			gp.add(lblEmail, 0, 5); gp.add(tfEmail, 1, 5,5,1);
			gp.add(lblTel, 0, 6); gp.add(tfTel, 1, 6,5,1);
			HBox hboxr = new HBox(5);
			hboxr.getChildren().addAll(rbDa,rbNe);
			gp.add(lblOboljenja, 0, 7); gp.add(hboxr, 1, 7);
			gp.add(lblOpstina, 0, 8); gp.add(cmbOpstina, 1, 8,5,1);
			gp.add(lblTipVakcine, 0, 9); gp.add(cmbVakcine, 1, 9,5,1);
			gp.add(btnDodaj, 0, 10); gp.add(btnObrisi, 1, 10);
			getChildren().addAll(hboxtop,gp,tv,btnSacuvajPrijavu);
			gp.setHgap(20);
			gp.setVgap(10);
			tv.setPrefWidth(50);
			tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			tg.getToggles().addAll(rbDa,rbNe);
			tv.setPrefHeight(100);
			btnSacuvajPrijavu.setStyle("-fx-font-weight:bold");
			cmbDrzavljanstvo.getItems().add("Drzavljanstvo Republike Srbije");
			cmbDrzavljanstvo.getItems().add("Strani drzavljanin sa boravkom u RS");
			cmbDrzavljanstvo.getItems().add("Strani drzavljanin bez boravka u RS");
			cmbVakcine.getItems().add("Pfizer"); cmbVakcine.getItems().add("Sputnik");
			cmbVakcine.getItems().add("Sinopharm"); cmbVakcine.getItems().add("Moderna");
			cmbVakcine.getItems().add("Bilo koji tip");
			cmbDrzavljanstvo.getSelectionModel().selectFirst();
			cmbDrzavljanstvo.setPrefWidth(250);
			cmbOpstina.setPrefWidth(250);
			cmbVakcine.setPrefWidth(250);
			TableColumn<VakcinaStavka,Integer> tcRB = new TableColumn<>("RB");
			tcRB.setCellValueFactory(new PropertyValueFactory<>("broj"));
			TableColumn<VakcinaStavka,String> tcTip = new TableColumn<>("Tip vakcine");
			tcTip.setCellValueFactory(new PropertyValueFactory<>("tip"));
			tv.getColumns().addAll(tcRB,tcTip);
			setSpacing(10);
			
			try {
				socket = new Socket("localhost",6666);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setPadding(new Insets(10));
		Servis.ubaciOpstine(cmbOpstina);
		initActions();
	}
	private void initActions() {
		btnDodaj.setOnAction(e -> {
			String vakcina = cmbVakcine.getSelectionModel().getSelectedItem();
			if(!vakcine.contains(new VakcinaStavka(vakcina))) {
				VakcinaStavka v = new VakcinaStavka(vakcina);
				vakcine.add(v);
				Servis.ubaciUTv(vakcine, tv);
			}
		});
		btnObrisi.setOnAction(e -> {
			int index = tv.getSelectionModel().getSelectedIndex();
			vakcine.remove(index);
			Servis.ubaciUTv(vakcine, tv);
		});
		btnSacuvajPrijavu.setOnAction(e -> {
			String drzavljanstvo = cmbDrzavljanstvo.getSelectionModel().getSelectedItem();
			String jmbg = tfJmbg.getText();
			String ime = tfIme.getText();
			String prezime = tfPrezime.getText();
			String email = tfEmail.getText();
			String mobilni = tfTel.getText();
			String specificnaOboljenja = rbDa.isSelected()? "Da":"Ne";
			int idOpstina = cmbOpstina.getSelectionModel().getSelectedIndex() + 1;
			Servis.sacuvaj(socket, vakcine, drzavljanstvo, jmbg, ime, prezime, email, mobilni, specificnaOboljenja, idOpstina);
			
		});
		tfIme.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				ImePrezimeValidator validator = new ImePrezimeValidator();
				try {
					validator.validiraj(newValue, "Ime nije validno");
					tfIme.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,null,null)));
				} catch (Exception ex) {
					tfIme.setBackground(new Background(new BackgroundFill(Color.RED,null,null)));
				}
			}
		});
		tfPrezime.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				ImePrezimeValidator validator = new ImePrezimeValidator();
				try {
					validator.validiraj(newValue, "Prezime nije validno");
					tfPrezime.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,null,null)));
				} catch (Exception ex) {
					tfPrezime.setBackground(new Background(new BackgroundFill(Color.RED,null,null)));
				}
			}
		});
		tfEmail.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				EmailValidator validator = new EmailValidator();
				try {
					validator.validiraj(newValue, "Email nije validan");
					tfEmail.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,null,null)));
				} catch (Exception ex) {
					tfEmail.setBackground(new Background(new BackgroundFill(Color.RED,null,null)));
				}
			}
		});
		tfTel.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				MobilniValidator validator = new MobilniValidator();
				try {
					validator.validiraj(newValue, "Telefon nije validan");
					tfTel.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,null,null)));
				} catch (Exception ex) {
					tfTel.setBackground(new Background(new BackgroundFill(Color.RED,null,null)));
				}
			}
		});
		tfJmbg.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				JmbgValidator validator = new JmbgValidator();
				try {
					validator.validiraj(newValue, "Jmbg nije validan");
					tfJmbg.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,null,null)));
				} catch (Exception ex) {
					tfJmbg.setBackground(new Background(new BackgroundFill(Color.RED,null,null)));
				}
			}
		});
	}
}
