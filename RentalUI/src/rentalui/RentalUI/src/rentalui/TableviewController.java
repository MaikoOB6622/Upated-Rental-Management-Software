/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rentalui;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Mike
 */
public class TableviewController implements Initializable {
    
    
    
    @FXML
    public TableView<RecurrentExpenditure> tableview;
    @FXML
    public TableColumn MonthCol;
    @FXML
    public TableColumn WaterCol;
    @FXML
    public TableColumn ElectricityCol;
    

    /**
     * Initializes the controller class.
     */
    FXMLDocumentController fxmldocumentcontroller;
    public void setFxmlDocumentController(FXMLDocumentController fXMLDocumentController){
        this.fxmldocumentcontroller = fxmldocumentcontroller;
    }
    
    public void initializeTable(){
            
            MonthCol.setCellValueFactory(new PropertyValueFactory<>("MonthChoose"));
            WaterCol.setCellValueFactory(new PropertyValueFactory<>("Water"));
            ElectricityCol.setCellValueFactory(new PropertyValueFactory<>("Electricity"));  
      
        try {
            ObservableList<RecurrentExpenditure> data;
            data = FXCollections.observableArrayList();
            String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
            String select = "select * from RecurrentVariableMonthlyExpenditure where Month = ?";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(select);
            pstmt.setString(1, (String)fxmldocumentcontroller.MonthBox1.getSelectionModel().getSelectedItem());
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()){
                data.add(new RecurrentExpenditure(
                        rs.getString("Month"),
                        rs.getString("WaterBill"),
                        rs.getString("ElectricityBill")));
                tableview.setItems(data);
            }
           
            conn.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setText(String Combo){
        String Combo1 = (String)this.fxmldocumentcontroller.MonthBox1.getSelectionModel().getSelectedItem();
        Combo = Combo1;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        
    }
}    
