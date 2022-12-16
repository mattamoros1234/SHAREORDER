package it.unibo.paw.application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import it.unibo.paw.hibernate.Corriere;
import it.unibo.paw.hibernate.Pacco;
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

public class RegistrazioneCorriereController implements Initializable {
	
	//stage e scene
	private Stage stage;
	private Scene scene;
	private Parent root;

    @FXML
    private TextField emailCorriere;

    @FXML
    private TextField confermaPassword;

    @FXML
    private Button btnRegistrati;

    @FXML
    private TextField nome;

    @FXML
    private TextField cognome;

    @FXML
    private TextField password;

    @FXML
    private Button btnAnnulla;

    @FXML
    void annulla(ActionEvent event) throws IOException {
    	System.out.println("annulla clicked!");
    	root = FXMLLoader.load(getClass().getResource("Home.fxml"));
    	stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }

    @FXML
    void registrati(ActionEvent event) throws IOException {
    	//chek dati
		Session session = null;
		Transaction tx = null;
    	try {
			session = HibernateUtil.getSessionFactory().openSession();	
			tx = session.beginTransaction();
			
			Corriere corriere = new Corriere();
			corriere.setEmailCorriere(emailCorriere.getText());
			session.save(corriere);
			
			tx.commit();	
		} catch (Exception e1) {
			if (tx != null) {
				try {
					tx.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			e1.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
    	System.out.println("registraCorriere clicked!");
    	root = FXMLLoader.load(getClass().getResource("Home.fxml"));
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

