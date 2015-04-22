/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crm_faf;

import Data.DbConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class StatusWidget {

    private  TableView table;
    private  List<WidgetEntry> entryData = new ArrayList<WidgetEntry>();
    private  ObservableList<WidgetEntry> entries = FXCollections.observableList(entryData); 
    private  boolean hasFeed = false;
    private  List<String> problemData = new ArrayList<String>();
    
    StatusWidget() {
        Setup();
    }
    
    public void  Setup() {
 //       this.getChildren().add(this);
        this.table = new TableView();
        table.setEditable(false);
        TableColumn sourceColumn = new TableColumn("Source");
        sourceColumn.setPrefWidth(100);
        sourceColumn.setCellValueFactory(
                new PropertyValueFactory<>("source"));
        TableColumn actionColumn = new TableColumn("Action");
        actionColumn.setPrefWidth(100);
        actionColumn.setCellValueFactory(
                new PropertyValueFactory<>("action"));
        TableColumn statusColumn = new TableColumn("Status");
        statusColumn.setPrefWidth(200);
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<>("status"));
	TableColumn notesColumn = new TableColumn("Notes");
	notesColumn.setPrefWidth(150);
        notesColumn.setCellValueFactory(
                new PropertyValueFactory<>("notes"));
	TableColumn importanceColumn = new TableColumn("Needs Attention");
	importanceColumn.setPrefWidth(50);
        importanceColumn.setCellValueFactory(
                new PropertyValueFactory<>("NAN"));
        table.getColumns().addAll(sourceColumn, actionColumn, statusColumn, 
                notesColumn, importanceColumn);
 
    }
    public void update(WidgetEntry wEntry) {
        entries.add(wEntry);
       
    }
    public void updateAll(ArrayList<WidgetEntry> wEntries) {
        entries.addAll(wEntries);
    }
    public  TableView getTable() {
        return table;
    }


    private boolean checkStatus() {
        try {
            DbConnection db = new DbConnection();
            ResultSet inventoryCheck = db.selectDataColumn("jobs", "*", "1");
            for (int x = 2; inventoryCheck.getMetaData().getColumnCount() > x; x++)
                while(inventoryCheck.next()) {
                    if (inventoryCheck.getInt(x) <= 0) {
                        new WidgetEntry("Inventory", "inventory empty", "unresolved", "", "yes" );
                    }
                }
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(StatusWidget.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
