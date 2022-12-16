package it.unibo.paw.application;

import java.io.IOException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import it.unibo.paw.hibernate.Gruppo;
import it.unibo.paw.hibernate.util.HibernateUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DeleteGruppoController {
	
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
    	System.out.println("annulla clicked!");
    	root = FXMLLoader.load(getClass().getResource("Gruppi.fxml"));
    	stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }

    @FXML
    void conferma(ActionEvent event) throws IOException {
    	System.out.println("conferma clicked!");
    	Session session = null;
		Transaction tx = null;
		try {			
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			
			
			Query firstQuery = session.createQuery("from " + Gruppo.class.getSimpleName());
			List<Gruppo> gruppiRes = firstQuery.list();
			for(Gruppo g: gruppiRes) {
				if(g.getId()==Main.getIdGruppo()) {
					session.delete(g);
				}
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
    	root = FXMLLoader.load(getClass().getResource("Gruppi.fxml"));
    	stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }

}

