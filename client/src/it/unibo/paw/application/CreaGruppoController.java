package it.unibo.paw.application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.hibernate.Transaction;

import it.unibo.paw.hibernate.Gruppo;
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

public class CreaGruppoController implements Initializable {

	//stage e scene
	private Stage stage;
	private Scene scene;
	private Parent root;
	
    @FXML
    private TextField nomeGruppo;

    @FXML
    private Button btnCrea;
    
    @FXML
    private Button btnAnnulla;

    @FXML
    void annulla(ActionEvent event) throws IOException {
    	root = FXMLLoader.load(getClass().getResource("Gruppi.fxml"));
    	stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }

    @FXML
    void creaGruppo(ActionEvent event) throws IOException {
    	System.out.println("Ciao " + nomeGruppo.getText());
    	
    	Session session = null;
		Transaction tx = null;
		try {
			
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			
			Gruppo gruppo= new Gruppo();
			gruppo.setNomeGruppo(nomeGruppo.getText());
			gruppo.setEmailCreatore("andrea@gmail.com");
			session.persist(gruppo);
			
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
    	root = FXMLLoader.load(getClass().getResource("Gruppi.fxml"));
    	stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

}

