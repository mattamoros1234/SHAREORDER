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

public class AssegnaPacchiController implements Initializable {
	
	//stage e scene
	private Stage stage;
	private Scene scene;
	private Parent root;
	

	private ObservableList<Corriere> corrieri = FXCollections.observableArrayList();
	private Pacco paccoSelezionato;

    @FXML
    private Button btnConferma;

    @FXML
    private Button btnAnnulla;

    @FXML
    private ChoiceBox<String> selectCorrieri;

    @FXML
    void annulla(ActionEvent event) throws IOException {
    	System.out.println("annulla clicked!");
    	System.out.println(selectCorrieri.getValue());
    	root = FXMLLoader.load(getClass().getResource("Home.fxml"));
    	stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }

    @FXML
    void conferma(ActionEvent event) throws IOException {
    	//controllo che la select non sia vuota
    	System.out.println(selectCorrieri.getValue());
    	//modifica al db Pacco(idSelezionato, emailCorriere, statoPacco)
    	Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();	
			Query firstQuery = session.createQuery("from " + Pacco.class.getSimpleName());
			List<Pacco> pacchiRes = firstQuery.list();
			for(Pacco p: pacchiRes) {
				if(p.getId()==Main.getIdselezionato()) {
					paccoSelezionato=p;
				}
			}
			tx = session.beginTransaction();
			if(paccoSelezionato.getStatoPacco().equals("confermato")) {
    			paccoSelezionato.setStatoPacco("assegnato");
    			paccoSelezionato.setEmailCorriere(selectCorrieri.getValue());
    			session.update(paccoSelezionato);	
			}		
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
    	
    	System.out.println("conferma clicked!");
    	root = FXMLLoader.load(getClass().getResource("Home.fxml"));
    	stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	//prendo i corrieri
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();	
			Query firstQuery = session.createQuery("from " + Corriere.class.getSimpleName());
			List<Corriere> corrieriRes = firstQuery.list();
			corrieri.addAll(corrieriRes);
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
    	//aggiungo le email dei corrieri alla select
		for(Corriere c: corrieri) {
			selectCorrieri.getItems().add(c.getEmailCorriere());
		}
	}

}

