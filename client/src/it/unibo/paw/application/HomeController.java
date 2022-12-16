package it.unibo.paw.application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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

//import db
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import it.unibo.paw.hibernate.Pacco;
import it.unibo.paw.hibernate.util.HibernateUtil;

public class HomeController implements Initializable {

	//stage e scene
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	//dati da visualizzare
	private ObservableList<Pacco> pacchi = FXCollections.observableArrayList();

	
    @FXML
    private Button gruppi;

    @FXML
    private Button nuovoPaccoButton;
    
    @FXML
    private Button btnInitDb;

    @FXML
    private TableView<Pacco> tableView;

    @FXML
    private TableColumn<Pacco, Integer> idPacco;

    @FXML
    private TableColumn<Pacco, String> statoPacco;

    @FXML
    void toGruppi(ActionEvent event) throws IOException {//cambiare scena
    	System.out.println("toGruppi clicked!");
    	root = FXMLLoader.load(getClass().getResource("Gruppi.fxml"));
    	stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }
    
    @FXML
    void toInitDb(ActionEvent event) throws IOException {
    	System.out.println("toinitdb clicked!");
    	root = FXMLLoader.load(getClass().getResource("InitDb.fxml"));
    	stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }

    @FXML
    void toNuovoPacco(ActionEvent event) throws IOException {
    	System.out.println("toNUovoPacco clicked!");
    	root = FXMLLoader.load(getClass().getResource("NuovoPacco.fxml"));
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
			tx = session.beginTransaction();

			
			tx.commit();
			
			Query firstQuery = session.createQuery("from " + Pacco.class.getSimpleName());
			//firstQuery.setString(0, "Bologna");
			List<Pacco> pacchiRes = firstQuery.list();
			for(Pacco p:pacchiRes) {
				if(p.getEmailDestinatario().equals(Main.getEmailAttiva()) || p.getEmailMittente().equals(Main.getEmailAttiva())) {
					pacchi.add(p);
				}
			}
			
			//pacchi.addAll(pacchiRes);
			showPacchi();
			addButtonConfirmToTable();
			addButtonModifyToTable();
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

	private void addButtonConfirmToTable() {
		//creo la colonna
        TableColumn<Pacco, Void> colBtn = new TableColumn("Confirm");

        Callback<TableColumn<Pacco, Void>, TableCell<Pacco, Void>> cellFactory = new Callback<TableColumn<Pacco, Void>, TableCell<Pacco, Void>>() {
            @Override
            public TableCell<Pacco, Void> call(final TableColumn<Pacco, Void> param) {
            	//viene creata una cella
                final TableCell<Pacco, Void> cell = new TableCell<Pacco, Void>() {
                	//viene creato il bottone all'interno di una cella
                    private final Button btn = new Button("Confirm");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Pacco paccoTemp = getTableView().getItems().get(getIndex());
                            if(paccoTemp.getEmailMittente().equals(Main.getEmailAttiva())){
                                System.out.println("questo pacco ti appartiene non può essere confermato");
                            }else if(paccoTemp.getStatoPacco().equals("registrato")){
                            	System.out.println("vuoi confermare?, devi modificare lo stato nel db");
                            	Main.setIdSelezionato(paccoTemp.getId());
                               	System.out.println("toConfermaPacco clicked, id: " + Main.getIdSelezionato());
                            	try {
									root = FXMLLoader.load(getClass().getResource("ConfermaPacco.fxml"));
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
                            	stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                            	scene = new Scene(root);
                            	stage.setScene(scene);
                            	stage.show();
                            }
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
	
	private void addButtonModifyToTable() {
		//creo la colonna
        TableColumn<Pacco, Void> colBtn = new TableColumn("Modify");

        Callback<TableColumn<Pacco, Void>, TableCell<Pacco, Void>> cellFactory = new Callback<TableColumn<Pacco, Void>, TableCell<Pacco, Void>>() {
            @Override
            public TableCell<Pacco, Void> call(final TableColumn<Pacco, Void> param) {
            	//viene creata una cella
                final TableCell<Pacco, Void> cell = new TableCell<Pacco, Void>() {
                	//viene creato il bottone all'interno di una cella
                    private final Button btn = new Button("Modify");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Pacco paccoTemp = getTableView().getItems().get(getIndex());
                            //qua facciamo il controllo se il bottone dopo essere cliccato deve fare 
                            //qualcosa oppure no
                            System.out.println("selectedData: " + paccoTemp);
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
		idPacco.setCellValueFactory(new PropertyValueFactory<Pacco, Integer>("id"));
		statoPacco.setCellValueFactory(new PropertyValueFactory<Pacco, String>("statoPacco"));
		
		tableView.setItems(pacchi);
	}

}
