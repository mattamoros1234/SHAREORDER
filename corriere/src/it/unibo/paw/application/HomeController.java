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
import javafx.embed.swt.FXCanvas;
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

public class HomeController implements Initializable{
	
	//stage e scene
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private ObservableList<Pacco> pacchi = FXCollections.observableArrayList();
	private Pacco paccoSelezionato;

    @FXML
    private TableView<Pacco> tableView;

    @FXML
    private TableColumn<Pacco, Integer> idPacco;

    @FXML
    private TableColumn<Pacco, String> statoPacco;

    @FXML
    private TableColumn<Pacco, String> indirizzoMittente;

    @FXML
    private TableColumn<Pacco, String> indirizzoDestinatario;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println(Main.getEmailAttiva());
		Session session = null;
		Transaction tx = null;
		try {
			
			session = HibernateUtil.getSessionFactory().openSession();
			
			Query firstQuery = session.createQuery("from " + Pacco.class.getSimpleName());
			List<Pacco> pacchiRes = firstQuery.list();
			for(Pacco p:pacchiRes) {
				if(p.getEmailCorriere()!=null && p.getEmailCorriere().equals(Main.getEmailAttiva())) {
					pacchi.add(p);
				}
			}
			showPacchi();
			addButtonRitiroToTable();
			addButtonConsegnaToTable();
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
	
	private void addButtonRitiroToTable() {
		//creo la colonna
        TableColumn<Pacco, Void> colBtn = new TableColumn("Ritiro");

        Callback<TableColumn<Pacco, Void>, TableCell<Pacco, Void>> cellFactory = new Callback<TableColumn<Pacco, Void>, TableCell<Pacco, Void>>() {
            @Override
            public TableCell<Pacco, Void> call(final TableColumn<Pacco, Void> param) {
            	//viene creata una cella
                final TableCell<Pacco, Void> cell = new TableCell<Pacco, Void>() {
                	//viene creato il bottone all'interno di una cella
                    private final Button btn = new Button("Ritiro");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Pacco paccoTemp = getTableView().getItems().get(getIndex());
                            if(paccoTemp.getStatoPacco().equals("assegnato")){
                            	System.out.println("vuoi ritirare?, devi modificare lo stato nel db");
                            	Main.setIdselezionato(paccoTemp.getId());
                            	
                            	Session session = null;
                        		Transaction tx = null;
                        		try {
                        			
                        			session = HibernateUtil.getSessionFactory().openSession();
                        			//getPacco con idSelezionato
                        			Query firstQuery = session.createQuery("from " + Pacco.class.getSimpleName());
                        			List<Pacco> pacchiRes = firstQuery.list();
                        			for(Pacco p: pacchiRes) {
                        				if(p.getId()==Main.getIdselezionato()) {
                        					paccoSelezionato=p;
                        				}
                        			}
                        			//modifica stato del pacco selezionato
                        			tx = session.beginTransaction();
                        			if(paccoSelezionato.getStatoPacco().equals("assegnato")) {
                            			paccoSelezionato.setStatoPacco("ritirato");
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
                            	
                               	System.out.println("refresh clicked");
                            	try {
									root = FXMLLoader.load(getClass().getResource("Home.fxml"));
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
	
	private void addButtonConsegnaToTable() {
		//creo la colonna
        TableColumn<Pacco, Void> colBtn = new TableColumn("Consegna");

        Callback<TableColumn<Pacco, Void>, TableCell<Pacco, Void>> cellFactory = new Callback<TableColumn<Pacco, Void>, TableCell<Pacco, Void>>() {
            @Override
            public TableCell<Pacco, Void> call(final TableColumn<Pacco, Void> param) {
            	//viene creata una cella
                final TableCell<Pacco, Void> cell = new TableCell<Pacco, Void>() {
                	//viene creato il bottone all'interno di una cella
                    private final Button btn = new Button("Consegna");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	 Pacco paccoTemp = getTableView().getItems().get(getIndex());
                             if(paccoTemp.getStatoPacco().equals("ritirato")){
                             	System.out.println("vuoi consegnare?, devi modificare lo stato nel db");
                             	Main.setIdselezionato(paccoTemp.getId());
                             	
                             	Session session = null;
                         		Transaction tx = null;
                         		try {
                         			
                         			session = HibernateUtil.getSessionFactory().openSession();
                         			//getPacco con idSelezionato
                         			Query firstQuery = session.createQuery("from " + Pacco.class.getSimpleName());
                         			List<Pacco> pacchiRes = firstQuery.list();
                         			for(Pacco p: pacchiRes) {
                         				if(p.getId()==Main.getIdselezionato()) {
                         					paccoSelezionato=p;
                         				}
                         			}
                         			//modifica stato del pacco selezionato
                         			tx = session.beginTransaction();
                         			if(paccoSelezionato.getStatoPacco().equals("ritirato")) {
                             			paccoSelezionato.setStatoPacco("consegnato");
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
                             	
                                	System.out.println("refresh clicked");
                             	try {
 									root = FXMLLoader.load(getClass().getResource("Home.fxml"));
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
	
	public void showPacchi() {
		idPacco.setCellValueFactory(new PropertyValueFactory<Pacco, Integer>("id"));
		statoPacco.setCellValueFactory(new PropertyValueFactory<Pacco, String>("statoPacco"));
		indirizzoMittente.setCellValueFactory(new PropertyValueFactory<Pacco, String>("indirizzoMittente"));
		indirizzoDestinatario.setCellValueFactory(new PropertyValueFactory<Pacco, String>("indirizzoDestinatario"));
		
		tableView.setItems(pacchi);
	}

}