package it.unibo.paw.application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import it.unibo.paw.hibernate.Gruppo;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class GruppiController implements Initializable{
	
	//stage e scene
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private ObservableList<Gruppo> gruppi = FXCollections.observableArrayList();

    @FXML
    private Button btnHome;

    @FXML
    private Button btnNuovoGruppo;

    @FXML
    private TableView<Gruppo> tableView;

    @FXML
    private TableColumn<Gruppo, String> nomeGruppo;

    @FXML
    private TableColumn<Gruppo, String> creatore;

    @FXML
    void toHome(ActionEvent event) throws IOException {
    	System.out.println("home clicked!");
    	root = FXMLLoader.load(getClass().getResource("Home.fxml"));
    	stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }
    
    @FXML
    void toNuovoGruppo(ActionEvent event) throws IOException {
    	System.out.println("nuovogruppo clicked!");
    	root = FXMLLoader.load(getClass().getResource("CreaGruppo.fxml"));
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
			showPacchi();
			addButtonDeleteToTable();
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
	}
	private void addButtonDeleteToTable() {
		//creo la colonna
        TableColumn<Gruppo, Void> colBtn = new TableColumn("Delete");

        Callback<TableColumn<Gruppo, Void>, TableCell<Gruppo, Void>> cellFactory = new Callback<TableColumn<Gruppo, Void>, TableCell<Gruppo, Void>>() {
            @Override
            public TableCell<Gruppo, Void> call(final TableColumn<Gruppo, Void> param) {
            	//viene creata una cella
                final TableCell<Gruppo, Void> cell = new TableCell<Gruppo, Void>() {
                	//viene creato il bottone all'interno di una cella
                    private final Button btn = new Button("Delete");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	Gruppo gruppoTemp = getTableView().getItems().get(getIndex());
                        	Main.setIdGruppo(gruppoTemp.getId());
                        	System.out.println("deletegruppo clicked!");
                        	try {
								root = FXMLLoader.load(getClass().getResource("DeleteGruppo.fxml"));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                        	stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                        	scene = new Scene(root);
                        	stage.setScene(scene);
                        	stage.show();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }

        };

        colBtn.setCellFactory(cellFactory);

        tableView.getColumns().add(colBtn);

    }
	
	
	public void showPacchi() {
		nomeGruppo.setCellValueFactory(new PropertyValueFactory<Gruppo, String>("nomeGruppo"));
		creatore.setCellValueFactory(new PropertyValueFactory<Gruppo, String>("emailCreatore"));
		
		tableView.setItems(gruppi);
	}
}
