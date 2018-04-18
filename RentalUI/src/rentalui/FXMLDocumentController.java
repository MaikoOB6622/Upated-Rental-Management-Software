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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


/**
 *This is a simple rental schedule recording program that gets input 
 *from the user and saves the data to sqlite database.
 * 
 * @author Mike
 */
public class FXMLDocumentController implements Initializable {
    
    
    @FXML
    private ImageView b_Payment;
    @FXML
    private ImageView b_House;
    @FXML
    private ImageView b_monthly;
    @FXML
    private AnchorPane h_PaymentDetails;
    @FXML
    private AnchorPane h_HouseDetails;
    @FXML
    private AnchorPane h_MonthlyExpenditure;
    
    
    
    @FXML
    private void handleButtonAction(MouseEvent event) {
        if (event.getTarget() == b_House) {
            h_HouseDetails.setVisible(true);
            h_PaymentDetails.setVisible(false);
            h_MonthlyExpenditure.setVisible(false);
            
        } else 
        if (event.getTarget() == b_Payment){
            h_PaymentDetails.setVisible(true);
            h_HouseDetails.setVisible(false);
            h_MonthlyExpenditure.setVisible(false);
        }else
        if (event.getTarget() == b_monthly){
            h_MonthlyExpenditure.setVisible(true);
            h_HouseDetails.setVisible(false);
            h_PaymentDetails.setVisible(false);
        }
    }
    
    ObservableList<String>HouseNumber = FXCollections.observableArrayList("A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10", "A11", "A12");
    ObservableList<String>MonthPaid = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    
    @FXML
    private ComboBox SelectHouseBox;
    
    
    
    @FXML
    private ComboBox HouseNumberBox;
    @FXML
    private void initializeHNoBox(){
        HouseNumberBox.setItems(HouseNumber);
        HouseNumberBox.setEditable(true);
    }
    
    @FXML
    private ComboBox MonthBox;
    @FXML
    private void initializeMonthBox(){
        MonthBox.setItems(MonthPaid);
        MonthBox.setEditable(true);
    }
    
    @FXML
    private TextField Amount;
    @FXML
    private void getAmount(){
        Amount.getText();
    }
    
    @FXML
    private TextField Name;
    @FXML
    private void getName(){
        Name.getText();
    }
    
    @FXML
    private TextField TName;
    @FXML
    private void getTName(){
        TName.getText();
    }
    
    @FXML
    private TextField Repairs;
    @FXML
    private void getRepairs(){
        Repairs.getText();
    }
    
    @FXML
    private TextField repairCost;
    @FXML
    private void getrepairCost(){
        repairCost.getText();
    }
    
    @FXML
    private TextField miscellaneous;
    @FXML
    private void getmiscellaneous(){
        miscellaneous.getText();
    }
    
    @FXML
    private void createTable(String HouseNumber, String Amount, String PayerName, String Month){
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
        String sql = "CREATE TABLE IF NOT EXISTS JatomApts (\n"
                +"HouseNumber text PRIMARY KEY, \n"
                +"Amount text NOT NULL, \n"
                +"PayerName text NOT NULL, \n"
                +"Month text NOT NULL\n"
                +");";
        
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        String sql1 = "INSERT INTO JatomApts(HouseNumber, Amount, PayerName, Month) VALUES(?,?,?,?)";
        try{
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, HouseNumber);
            pstmt.setString(2, Amount);
            pstmt.setString(3, PayerName);
            pstmt.setString(4, Month);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }  
    }
    
    private void createTable2(String HNumber, String TenantName, String RepairsOnHouse, String CostOfRepair, String MiscellaneousExpenses){
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
        String sql2 = "CREATE TABLE IF NOT EXISTS JatomAptsDetails (\n"
                +"HNumber text, \n"
                +"TenantName text, \n"
                +"RepairsOnHouse text, \n"
                +"CostOfRepair text, \n"
                +"MiscellaneousExpenses\n"
                +");";
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql2);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        String sql3 = "INSERT INTO JatomAptsDetails (HNumber, TenantName, RepairsOnHouse, CostOfRepair, MiscellaneousExpenses) VALUES(?,?,?,?,?)";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql3);
            pstmt.setString(1, HNumber);
            pstmt.setString(2, TenantName);
            pstmt.setString(3, RepairsOnHouse);
            pstmt.setString(4, CostOfRepair);
            pstmt.setString(5, MiscellaneousExpenses);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private Button Submit1;
    @FXML
    private void TableInsertButton2(){
        this.createTable2((String)SelectHouseBox.getSelectionModel().getSelectedItem(), TName.getText(), Repairs.getText(), repairCost.getText(), miscellaneous.getText());
        SelectHouseBox.setValue(null);
        TName.setText("");
        Repairs.setText("");
        repairCost.setText("");
        miscellaneous.setText("");
    }
    
    @FXML
    private Button Submit;
    @FXML
    private void TableInsertButton(){
        this.createTable((String)HouseNumberBox.getSelectionModel().getSelectedItem(), Amount.getText(), Name.getText(), (String)MonthBox.getSelectionModel().getSelectedItem());
        HouseNumberBox.setValue(null);
        Amount.setText("");
        Name.setText("");
        MonthBox.setValue(null);
    }
    
    @FXML
    private void initializeSelectHouseBox(){
        SelectHouseBox.setItems(HouseNumber);
       try {
            String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
            String query = "select * from JatomAptsDetails where HNumber = ?";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, (String)SelectHouseBox.getSelectionModel().getSelectedItem());
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()){
                 TName.setText(rs.getString("TenantName"));
                 Repairs.setText(rs.getString("RepairsOnHouse"));
                 repairCost.setText(rs.getString("CostOfRepair"));
                 miscellaneous.setText(rs.getString("MiscellaneousExpenses"));
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private Button b_AddRepairs;
    
    @FXML
    private Button b_AddRepairCost;
    
    @FXML
    private Button b_AddMiscellaneous;
    
    @FXML
    private void handleRepairButton(){
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
        String update  = "update JatomAptsDetails set RepairsOnHouse = ? where HNumber = ? ";
        try {
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(update);
                pstmt.setString(1, Repairs.getText());
                pstmt.setString(2, (String)SelectHouseBox.getSelectionModel().getSelectedItem());
                pstmt.executeUpdate();
                HouseNumberBox.setValue(null);
                Repairs.setText("");
                int executeUpdate = pstmt.executeUpdate();
                conn.setAutoCommit(true);
                    if (executeUpdate > 0){
                        System.out.println("Updated");
                    }else
                        System.out.println("Not updated");
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
    
    @FXML
    private void handlerepairCostButton(){
        String update  = "update JatomAptsDetails set CostOfRepair = ? where HNumber = ?";
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                try {
                    Connection conn = DriverManager.getConnection(url);
                    PreparedStatement pstmt = conn.prepareStatement(update);
                    pstmt.setString(1, repairCost.getText());
                    pstmt.setString(2, (String)SelectHouseBox.getSelectionModel().getSelectedItem());
                    pstmt.executeUpdate();
                    HouseNumberBox.setValue(null);
                    repairCost.setText("");
                    int executeUpdate = pstmt.executeUpdate();
                        if (executeUpdate > 0){
                            System.out.println("Updated");
                        }else
                            System.out.println("Not updated");
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
    }
    
    @FXML
    private void handlemiscellaneousButton(){
    String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
    String update = "update JatomAptsDetails set MiscellaneousExpenses = ? where HNumber = ?";   
                try {
                    Connection conn = DriverManager.getConnection(url);
                    PreparedStatement pstmt = conn.prepareStatement(update);
                    pstmt.setString(1, miscellaneous.getText());
                    pstmt.setString(2,(String)SelectHouseBox.getSelectionModel().getSelectedItem() );
                    HouseNumberBox.setValue(null);
                    miscellaneous.setText("");
                    int executeUpdate = pstmt.executeUpdate();
                    conn.setAutoCommit(true);
                    if (executeUpdate > 0){
                        System.out.println("Updated");
                    }else
                        System.out.println("Not updated");
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
