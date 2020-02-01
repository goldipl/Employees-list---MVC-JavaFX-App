package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Person;

public class MainViewController {

	@FXML private TextField roomNumberTextField;
	@FXML private TextField nameTextField;
	@FXML private TextField endTimeTextField;
	@FXML private TextField surnameTextField;
	@FXML private TextField startTimeTextField;
	@FXML private Label roomNumberLabel;
	@FXML private Label nameTextLabel;
	@FXML private Label endTimeLabel;
	@FXML private Label surnameLabel;
	@FXML private Label startTimeLabel;
	@FXML private Button reportButton;
	@FXML private Button deleteButton;
	@FXML private Button loadButton;
	@FXML private Button addButton;
	@FXML private Button saveButton;
	@FXML private TableColumn<Person, String> surnameColumn;
	@FXML private TableColumn<Person, String> nameColumn;
	@FXML private TableColumn<Person, String> roomColumn;
	@FXML private TableColumn<Person, String> startTime;
	@FXML private TableColumn<Person, String> endTime;
	@FXML private TableView<Person> table;
	@FXML private Canvas canvas;

	private ObservableList<Person> personList = FXCollections.observableArrayList();
	private PrintWriter save;
	
	@FXML
	void addButtonAction(ActionEvent event) {

			personList.add(new Person(nameTextField.getText(), 
					surnameTextField.getText(),
					roomNumberTextField.getText(), 
					startTimeTextField.getText(), 
					endTimeTextField.getText()));
			nameTextField.clear();
			surnameTextField.clear();
			startTimeTextField.clear();
			endTimeTextField.clear();
			roomNumberTextField.clear();	
	}

	@FXML
	void saveButtonAction() throws IOException {
		System.out.println("saveButton pressed");
		saveFile();
	}

	@FXML
	void reportButtonAction() throws IOException {
		System.out.println("reportButton pressed");
		saveReportFile();
	}
	
	@FXML
	void loadButtonAction() throws IOException {
		System.out.println("loadButton pressed");
		loadTextFile();
		setTable();
	}


	// --- WCZYTANIE PLIKU TEKSTOWEGO Z FOLDERU PROJEKTU "Homework3" --- \\
	private void loadTextFile() throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File("file.txt")));  // UTWORZENIE BUFORA WEJSCIOWEGO DO ODCZYTU STRUMIENIA WEJSCIA PLIKU .TXT
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (br != null) {							  // CZYTANIE PLIKU TEKSTOWEGO AZ DO WYCZERPANIA					
			String str;								  // DEKLARACJA ZMIENNEJ str PRZYPISANEJ KOLEJNO DO ODCZYTYWANIA LINII TEKSTU
			while ((str = br.readLine()) != null) {   // WCZYTYWANIE LINII TEKSTU DOPOKI TEKST SIE NIE SKONCZY/LINIA NIE BEDZIE NULL'EM
				addPersonToList(str.split(" "));      // DZIELNIE OBIEKTOW STRING NA TABLICE STRINGOW - split() method splits 
													  // a String object into an array of strings by separating the string 
			}
		}
	}
	
	// --- ZAPIS PLIKU TEKSTOWEGO W FOLDERZE PROJEKTU "Homework3" --- \\
	private void saveFile() throws IOException {
		save = new PrintWriter("file.txt");
		for (Person p : personList) {
			save.printf("%s %s %s %s %s %n", 		//%s - interpretuje argument jako ³ancuch znakow  |  %n – umieszczenie znaku nowej linii
					p.getName(), 
					p.getSurname(), 
					p.getRoom(), 
					p.getStartHour(), 
					p.getEndHour());
		}
		if (save != null)
			save.close();
	}
	
	// --- ZAPIS RAPORTU TEKSTOWEGO --- \\
	private void saveReportFile() throws IOException {
		Stage secondaryStage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Zapisz raport");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files","*.txt"));
		File file = fileChooser.showSaveDialog(secondaryStage);
		if (file != null) {
			save = new PrintWriter(new FileWriter(file));
			sort();
			for (Person p : personList) {
				save.printf("%s %s %s %s %s %n", 
						p.getName(), 
						p.getSurname(), 
						p.getRoom(), 
						p.getStartHour(),
						p.getEndHour());
			}
			if (save != null)
				save.close();
		}
	}
		
	// --- SORTOWANIE LISTY PRACOWNIKOW --- \\
	private void sort() {
		System.out.println("Przed sortowaniem:");
		for (Person person : personList) {  // person przypisujemy kolejne elementy personList
			System.out.println("Imie: " + person.getName() + 
					" | Nazwisko: " + person.getSurname() + 
					" | Czas pracy: " + person.getWorkTime() + " h.");
		}
		
		Collections.sort(personList, new Comparator<Person>() {
		public int compare(Person p1, Person p2) {
			return p1.getWorkTime() - p2.getWorkTime();
		}
	});
		
		System.out.println("Po sortowaniu:");
		for (Person person : personList) {
			System.out.println("Imie: " + person.getName() + 
					" | Nazwisko: " + person.getSurname() + 
					" | Czas pracy: " + person.getWorkTime() + " h.");
		}
	}
	
	// --- DODANIE PRACOWNIKA DO LISTY --- \\
	
	private void addPersonToList(String[] person) {
			personList.add(new Person(person[0], person[1], person[2], person[3], person[4]));
	}
	
	// --- DODAWANIE POSZCZEGOLNYCH ELEMENTOW DO TABELI --- \\
	private void setTable() {
	
		nameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
		surnameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("surname"));
		roomColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("room"));
		startTime.setCellValueFactory(new PropertyValueFactory<Person, String>("startHour"));
		endTime.setCellValueFactory(new PropertyValueFactory<Person, String>("endHour"));

		table.getSelectionModel().selectedItemProperty().addListener((ov, oldVal, newVal) -> {
			nameTextField.setText(newVal.getName());
			surnameTextField.setText(newVal.getSurname());
			startTimeTextField.setText(newVal.getStartHour());
			endTimeTextField.setText(newVal.getEndHour());
			roomNumberTextField.setText(newVal.getRoom());
		});
		table.setItems(personList);
	}

}