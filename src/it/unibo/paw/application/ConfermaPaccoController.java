package it.unibo.paw.application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

public class ConfermaPaccoController implements Initializable{
	
	//stage e scene
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private Pacco paccoSelezionato;

    @FXML
    private TextField indirizzoDestinatario;

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
    	
    	
    	Session session = null;
		Transaction tx = null;
		try {
			
			session = HibernateUtil.getSessionFactory().openSession();
			
			//getPacco con idSelezionato
			
			Query firstQuery = session.createQuery("from " + Pacco.class.getSimpleName());
			List<Pacco> pacchiRes = firstQuery.list();
			for(Pacco p: pacchiRes) {
				if(p.getId()==Main.getIdSelezionato()) {
					paccoSelezionato=p;
				}
			}
			//modifica stato del pacco selezionato
			tx = session.beginTransaction();
			if(paccoSelezionato.getStatoPacco().equals("registrato")) {
    			paccoSelezionato.setStatoPacco("confermato");
    			paccoSelezionato.setIndirizzoDestinatario(indirizzoDestinatario.getText());
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

