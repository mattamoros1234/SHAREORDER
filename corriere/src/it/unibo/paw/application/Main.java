package it.unibo.paw.application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;



public class Main extends Application {
	
	private static String emailAttiva;
	private static int idselezionato;
	
	
	
	public static String getEmailAttiva() {
		return emailAttiva;
	}

	public static void setEmailAttiva(String emailAttiva) {
		Main.emailAttiva = emailAttiva;
	}

	public static int getIdselezionato() {
		return idselezionato;
	}

	public static void setIdselezionato(int idselezionato) {
		Main.idselezionato = idselezionato;
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
