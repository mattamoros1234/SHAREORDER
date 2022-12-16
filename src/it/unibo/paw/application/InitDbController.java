package it.unibo.paw.application;

import java.io.IOException;

import it.unibo.paw.hibernate.util.HibernateUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InitDbController {
	
	//stage e scene
	private Stage stage;
	private Scene scene;
	private Parent root;
	
    @FXML
    private Button btnConferma;
    
    @FXML
    private Button btnAnnulla;

    @FXML
    void annulla(ActionEvent event) throws IOException {
    	root = FXMLLoader.load(getClass().getResource("Home.fxml"));
    	stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }
    
    @FXML
    void conferma(ActionEvent event) throws IOException {
    	HibernateUtil.dropAndCreateTables();
    	root = FXMLLoader.load(getClass().getResource("Home.fxml"));
    	stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    	
    }

}

