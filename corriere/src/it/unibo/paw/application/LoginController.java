package it.unibo.paw.application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import it.unibo.paw.hibernate.util.HibernateUtil;
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

public class LoginController implements Initializable {
	
	//stage e scene
	private Stage stage;
	private Scene scene;
	private Parent root;

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    private Button btnAccedi;

    @FXML
    void accedi(ActionEvent event) throws IOException {
    	System.out.println("accedi clicked!");
    	if(email.getText().equals("corriere@gmail.com") && password.getText().equals("ciao")) {
    		Main.setEmailAttiva(email.getText());
        	root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        	stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        	scene = new Scene(root);
        	stage.setScene(scene);
        	stage.show();
    	}
    	else {
    		System.out.println("errore inserimento credenziali");
    	}
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	System.out.println("siamo nel login!");
	}

}
