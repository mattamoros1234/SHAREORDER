package it.unibo.paw.application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;



public class Main extends Application {
	private static String emailAttiva;
	private static int idSelezionato;
	private static int idGruppo;
	
	
	
	public static int getIdGruppo() {
		return idGruppo;
	}

	public static void setIdGruppo(int idGruppo) {
		Main.idGruppo = idGruppo;
	}

	public static int getIdSelezionato() {
		return idSelezionato;
	}

	public static void setIdSelezionato(int idSelezionato) {
		Main.idSelezionato = idSelezionato;
	}

	public static String getEmailAttiva() {
		return emailAttiva;
	}

	public static void setEmailAttiva(String emailAttiva) {
		Main.emailAttiva = emailAttiva;
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(root,600,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
