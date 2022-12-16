package it.unibo.paw.application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrazioneController implements Initializable{

	//stage e scene
	private Stage stage;
	private Scene scene;
	private Parent root;
	
    @FXML
    private TextField email;

    @FXML
    private TextField confermaPassword;

    @FXML
    private Button brnRegistrati;

    @FXML
    private TextField nome;

    @FXML
    private TextField cognome;

    @FXML
    private TextField password;

    @FXML
    void registrati(ActionEvent event) throws IOException {
    	System.out.println("registrazione clicked!");
    	root = FXMLLoader.load(getClass().getResource("Login.fxml"));
    	stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
