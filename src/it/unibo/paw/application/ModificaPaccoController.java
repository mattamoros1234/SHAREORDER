package it.unibo.paw.application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import it.unibo.paw.hibernate.Corriere;
import it.unibo.paw.hibernate.Gruppo;
import it.unibo.paw.hibernate.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class ModificaPaccoController implements Initializable {
	
	//stage e scene
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private ObservableList<Gruppo> gruppi = FXCollections.observableArrayList();

    @FXML
    private Button btnConferma;

    @FXML
    private Button btnAnnulla;

    @FXML
    private ChoiceBox<String> select;

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
    	root = FXMLLoader.load(getClass().getResource("Home.fxml"));
    	stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Session session = null;
		Transaction tx = null;
		try {			
			session = HibernateUtil.getSessionFactory().openSession();
			/*tx = session.beginTransaction();
			
			tx.commit();*/
			
			Query firstQuery = session.createQuery("from " + Gruppo.class.getSimpleName());
			List<Gruppo> gruppiRes = firstQuery.list();
			
			gruppi.addAll(gruppiRes);
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

		for(Gruppo g: gruppi) {
			select.getItems().add(g.getNomeGruppo());
		}

		
	}

}

