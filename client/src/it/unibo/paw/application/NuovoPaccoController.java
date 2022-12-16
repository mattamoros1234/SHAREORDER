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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class NuovoPaccoController implements Initializable {
	
	//stage e scene
	private Stage stage;
	private Scene scene;
	private Parent root;

    @FXML
    private TextField emailDestinatario;

    @FXML
    private TextField indirizzoMittente;

    @FXML
    private ChoiceBox<String> tipoPacco;
    
    @FXML
    private Button btnRegistra;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tipoPacco.getItems().add("piccolo");
		tipoPacco.getItems().add("medio");
		tipoPacco.getItems().add("grande");
	}
	
    @FXML
    void registra(ActionEvent event) throws IOException {
    	System.out.println("Ciao" + emailDestinatario.getText());
    	//int max=0;
    	//salvo nel db il nuovo pacco
    	Session session = null;
		Transaction tx = null;
		try {
			//HibernateUtil.dropAndCreateTables();
			
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			
			//trova max id
			/*Query getPacchiQuery = session.createQuery("from " + Pacco.class.getSimpleName());
			List<Pacco> pacchiRes = getPacchiQuery.list();
			if(pacchiRes.size()==0) {
				max=0;
				System.out.println("size 0");
			}else {
				for(Pacco p: pacchiRes) {
					if(p.getId()>max) {
						max=p.getId();
						System.out.println("size non 0, " + max );
					}
				}
			}*/
			
			Pacco pacco1= new Pacco();
			//pacco1.setId(max+1);
			pacco1.setStatoPacco("registrato");
			pacco1.setEmailMittente(Main.getEmailAttiva());
			pacco1.setEmailDestinatario(emailDestinatario.getText());
			pacco1.setIndirizzoMittente(indirizzoMittente.getText());
			session.persist(pacco1);
			
			
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
    

}
