/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rentalui;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


/**
 *This is a simple rental schedule recording program that gets input 
 *from the user and saves the data to sqlite database.
 * 
 * @author Mike
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private AnchorPane motherPane;
    @FXML
    private AnchorPane topbar;
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
    private TitledPane HouseBlocks;
    
    
   
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
    
    ObservableList<String>HouseNumber2 = FXCollections.observableArrayList("B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "B11", "B12");
    ObservableList<String>HouseNumber = FXCollections.observableArrayList("A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10", "A11", "A12");
    ObservableList<String>HouseNumber3 = FXCollections.observableArrayList("C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10");
    ObservableList<String>HouseNumber4 = FXCollections.observableArrayList("Top House", "Bottom House");
    ObservableList<String>MonthPaid = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    
    @FXML
    private ComboBox SelectHouseBox;
    
    
    @FXML
    private ComboBox HouseNumberBox;
    @FXML
    private void initializeHNoBox(){
        HouseNumberBox.setItems(HouseNumber);
        try {
            String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
            String select = "select * from JatomApts where HouseNumber = ?";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(select);
            pstmt.setString(1, (String)HouseNumberBox.getSelectionModel().getSelectedItem());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {                
                Amount.setText(rs.getString("Amount"));
                Name.setText(rs.getString("PayerName"));
                MonthBox.setValue(rs.getString("Month"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private ComboBox MonthBox;
    @FXML
    private void initializeMonthBox(){
        MonthBox.setItems(MonthPaid);
    }
    
    @FXML
    public ComboBox MonthBox1;
    
    @FXML
    private ComboBox MonthBox2;
    
    @FXML
    private TextField Amount;
    
    @FXML
    private TextField Name;
    
    @FXML
    private TextField TName;
    
    @FXML
    private TextField Repairs;
    
    @FXML
    private TextField repairCost;
    
    @FXML
    private TextField miscellaneous;
   
    @FXML
    private TextField WaterBill;
    
    @FXML
    private TextField ElectricityBill;
    
    @FXML
    private TextField Expenditure;
    
    @FXML
    private TextField ReasonForExpense;
    
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
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
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
            PreparedStatement stmt = conn.prepareStatement(sql2);
            stmt.executeUpdate();
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
    
    
    private void createTable3(String Month, String WaterBill, String ElectricityBill){
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
        String sql4 = "CREATE TABLE IF NOT EXISTS RecurrentVariableMonthlyExpenditure(\n"
                +"Month text, \n"
                +"WaterBill text, \n"
                +"ElectricityBill text \n"
                +");";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql4);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        String sql5 = "INSERT INTO RecurrentVariableMonthlyExpenditure (Month, WaterBill, ElectricityBill) VALUES(?,?,?)";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql5);
            pstmt.setString(1, Month);
            pstmt.setString(2, WaterBill);
            pstmt.setString(3, ElectricityBill);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    @FXML
    private void createTable4(String Month, String Expenditure, String ReasonForExpense){
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
        String sql6 = "CREATE TABLE IF NOT EXISTS OtherMonthlyExpenditures (\n"
                +"Month text, \n"
                +"Expenditure text, \n"
                +"ReasonForExpense text \n"
                +");";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql6);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        String sql7 = "INSERT INTO OtherMonthlyExpenditures (Month, Expenditure, ReasonForExpense) VALUES(?,?,?)";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql7);
            pstmt.setString(1, Month);
            pstmt.setString(2, Expenditure);
            pstmt.setString(3, ReasonForExpense);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private Button submit2;
    @FXML
    private void TableInsertButton4(){
        this.createTable4((String)MonthBox2.getSelectionModel().getSelectedItem(), Expenditure.getText(), ReasonForExpense.getText());
        MonthBox2.setValue(null);
        Expenditure.setText("");
        ReasonForExpense.setText("");
    }
    
    @FXML
    private Button submit1;
    @FXML
    private void TableInsertButton3(){
        this.createTable3((String)MonthBox1.getSelectionModel().getSelectedItem(), WaterBill.getText(), ElectricityBill.getText());
        MonthBox1.setValue(null);
        WaterBill.setText("");
        ElectricityBill.setText("");
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
    private ComboBox SelectBlockB;
    @FXML
    private void initializeSelectBlockB(){
        SelectBlockB.setItems(HouseNumber2);
        try {
            String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
            String select = "select * from JatomAptsDetails where HNumber = ?";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(select);
            pstmt.setString(1, (String)SelectBlockB.getSelectionModel().getSelectedItem());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {                
                TName.setText(rs.getString("TenantName"));
                Repairs.setText(rs.getString("RepairsOnHouse"));
                repairCost.setText(rs.getString("CostOfRepair"));
                miscellaneous.setText(rs.getString("MiscellaneousExpenses"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private ComboBox SelectBlockC;
    @FXML
    private void initializeSelectBlockC(){
        SelectBlockC.setItems(HouseNumber3);
        try {
            String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
            String select = "select * from JatomAptsDetails where HNumber = ?";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(select);
            pstmt.setString(1, (String)SelectBlockC.getSelectionModel().getSelectedItem());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {                
                TName.setText(rs.getString("TenantName"));
                Repairs.setText(rs.getString("RepairsOnHouse"));
                repairCost.setText(rs.getString("CostOfRepair"));
                miscellaneous.setText(rs.getString("MiscellaneousExpenses"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private ComboBox SelectNasraBlock;
    @FXML
    private void initializeSelectNasraBlock(){
        SelectNasraBlock.setItems(HouseNumber4);
        try {
            String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
            String select = "select * from JatomAptsDetails where HNumber = ?";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(select);
            pstmt.setString(1, (String)SelectNasraBlock.getSelectionModel().getSelectedItem());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {                
                TName.setText(rs.getString("TenantName"));
                Repairs.setText(rs.getString("RepairsOnHouse"));
                repairCost.setText(rs.getString("CostOfRepair"));
                miscellaneous.setText(rs.getString("MiscellaneousExpenses"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    EventHandler<MouseEvent> filter = new EventHandler<MouseEvent>() {
        @Override
                public void handle(MouseEvent event) {
                    if (!SelectHouseBox.getSelectionModel().isEmpty()){
                        Label label = new Label();
                        label.setText((String) SelectHouseBox.getSelectionModel().getSelectedItem());
                        HouseBlocks.setGraphic(label);
                    }
                }
            };
    
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
            SelectHouseBox.setOnAction(e -> {
               HouseBlocks.addEventFilter(MouseEvent.MOUSE_CLICKED, filter);
               HouseBlocks.setExpanded(false);
           });

            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }
    
    
    
         
    @FXML
    private void initiaiizeMonthBox1(){
        MonthBox1.setItems(MonthPaid);
        try {
            String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
            String query = "select * from RecurrentVariableMonthlyExpenditure where Month = ?";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, (String)MonthBox1.getSelectionModel().getSelectedItem());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {                
                WaterBill.setText(rs.getString("WaterBill"));
                ElectricityBill.setText(rs.getString("ElectricityBill"));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
   
    @FXML
    private void initializeMonthBox2(){
        MonthBox2.setItems(MonthPaid);
        try {
            String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
            String query = "select * from OtherMonthlyExpenditures where Month = ?";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, (String)MonthBox2.getSelectionModel().getSelectedItem());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {                
                Expenditure.setText(rs.getString("Expenditure"));
                ReasonForExpense.setText(rs.getString("ReasonForExpense"));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     
    
    
    @FXML
    private Button b_AddRepairs;
    
    @FXML
    private Button b_AddRepairCost;
    
    @FXML
    private Button b_AddMiscellaneous;
    
    @FXML
    private Button b_updateWater;
    
    @FXML
    private Button b_updateElectricity;
    
    @FXML
    private Button b_Expenditure;
    
    @FXML
    private Button b_ReasonForExpense;
    
    @FXML
    private void handleRepairButton(){
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
        String update  = "update JatomAptsDetails set RepairsOnHouse = ? where HNumber = ? and TenantName = ? ";
        try {
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(update);
                pstmt.setString(1, Repairs.getText());
                pstmt.setString(2, (String)SelectHouseBox.getSelectionModel().getSelectedItem());
                pstmt.setString(3, TName.getText());
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
        String update  = "update JatomAptsDetails set CostOfRepair = ? where HNumber = ? and TenantName = ?";
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                try {
                    Connection conn = DriverManager.getConnection(url);
                    PreparedStatement pstmt = conn.prepareStatement(update);
                    pstmt.setString(1, repairCost.getText());
                    pstmt.setString(2, (String)SelectHouseBox.getSelectionModel().getSelectedItem());
                    pstmt.setString(3, TName.getText());
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
    String update = "update JatomAptsDetails set MiscellaneousExpenses = ? where HNumber = ? and TenantName = ?";   
                try {
                    Connection conn = DriverManager.getConnection(url);
                    PreparedStatement pstmt = conn.prepareStatement(update);
                    pstmt.setString(1, miscellaneous.getText());
                    pstmt.setString(2,(String)SelectHouseBox.getSelectionModel().getSelectedItem() );
                    pstmt.setString(3, TName.getText());
                    int executeUpdate = pstmt.executeUpdate();
                    HouseNumberBox.setValue(null);
                    miscellaneous.setText("");
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
    private void updateWater(){
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
        String update = "update RecurrentVariableMonthlyExpenditure set WaterBill = ? where Month = ? and ElectricityBill = ?";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(update);
            pstmt.setString(1, WaterBill.getText());
            pstmt.setString(2, (String)MonthBox1.getSelectionModel().getSelectedItem());
            pstmt.setString(3, ElectricityBill.getText());
            int executeUpdate = pstmt.executeUpdate();
            if (executeUpdate > 0){
                System.out.println("Updated");
            }else
                System.out.println("Not Updated");
            WaterBill.setText("");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void updateElectricity(){
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
        String update = "update RecurrentVariableMonthlyExpenditure set ElectricityBill = ? where Month = ? and WaterBill = ?";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(update);
            pstmt.setString(1, ElectricityBill.getText());
            pstmt.setString(2, (String)MonthBox1.getSelectionModel().getSelectedItem());
            pstmt.setString(3, WaterBill.getText());
            pstmt.executeUpdate();
            ElectricityBill.setText("");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void updateExpenditure(){
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
        String update = "update OtherMonthlyExpenditures set Expenditure = ? where Month = ? and ReasonForExpense = ?";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(update);
            pstmt.setString(1, Expenditure.getText());
            pstmt.setString(2, (String)MonthBox2.getSelectionModel().getSelectedItem());
            pstmt.setString(3, ReasonForExpense.getText());
            pstmt.executeUpdate();
            Expenditure.setText("");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void updateReasonForExpense(){
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
        String update = "update OtherMonthlyExpenditures set ReasonForExpense = ? where Month = ? and Expenditure = ?";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(update);
            pstmt.setString(1, ReasonForExpense.getText());
            pstmt.setString(2, (String)MonthBox2.getSelectionModel().getSelectedItem());
            pstmt.setString(3, Expenditure.getText());
            pstmt.executeUpdate();
            ReasonForExpense.setText("");
            conn.close();
        } catch (SQLException e) {
           e.printStackTrace();
        }
    }
    
    public ObservableList<RecurrentExpenditure> getData() {
        ObservableList<RecurrentExpenditure> data;
        data = FXCollections.observableArrayList();
        try {
            String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
            String select = "select * from RecurrentVariableMonthlyExpenditure where Month = ?";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(select);
            pstmt.setString(1, (String) MonthBox1.getSelectionModel().getSelectedItem());
            //Execute query and store result in a result set.
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String M = rs.getString("Month");
                String W = rs.getString("WaterBill");
                String E = rs.getString("ElectricityBill");
                RecurrentExpenditure row = new RecurrentExpenditure(M, W, E);
                data.add(row);
            }
            conn.close();
            rs.close(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    
    @FXML
    private Button viewTableRecurrentExpenditure;
    
    //When this method is called, it passes a recurrent expenditure object to tableview and displays it
    @FXML
    private void tableButton() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("tabView.fxml"));
        TabViewController subcontroller = new TabViewController(this);
        loader.setController(subcontroller);
        Parent root = loader.load();
        Scene tablescene = new Scene(root);
        Stage window = new Stage();
        window.setScene(tablescene);
        window.show();
    }
    
    public ObservableList<OtherExpenditure> getOtherTableData(){
        ObservableList<OtherExpenditure>data;
        data = FXCollections.observableArrayList();
        try {
            String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
            String select = "select * from OtherMonthlyExpenditures where Month = ?";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(select);
            pstmt.setString(1, (String)MonthBox2.getSelectionModel().getSelectedItem());
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                String Mon = rs.getString("Month");
                String Exp = rs.getString("Expenditure");
                String Reas = rs.getString("ReasonForExpense");
                OtherExpenditure other = new OtherExpenditure(Mon, Exp, Reas);
                data.add(other);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    
    @FXML
    private Button viewTableOtherExpenditure;
    
    //Method called to pass OtherExpenidture object to tableview and displays it.
    @FXML
    private void otherexpenditureButton() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("tableOtherExpenditure.fxml"));
        TableOtherExpenditureController controller = new TableOtherExpenditureController(this);
        loader.setController(controller);
        Parent root = loader.load();
        Scene othertableScene = new Scene(root);
        Stage window  = new Stage();
        window.setScene(othertableScene);
        window.show();
    }
    
    @FXML
    private Button payerDetailsTableButton;
    
    public ObservableList<PayerDetails> getPayerDetailsData(){
        ObservableList<PayerDetails>data;
        data = FXCollections.observableArrayList();
        try {
            String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
            String query = "select * from JatomApts where HouseNumber = ?";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, (String)HouseNumberBox.getSelectionModel().getSelectedItem());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {                
                String HouseNo = rs.getString("HouseNumber");
                String Paid   = rs.getString("Amount");
                String Payer = rs.getString("PayerName");
                String Month = rs.getString("Month");
                
                PayerDetails pay = new PayerDetails(HouseNo, Paid, Payer, Month);
                data.add(pay);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    
    @FXML
    private void payerDetailsButton()throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PayerDetailsTable.fxml"));
        PayerDetailsTableController controller = new PayerDetailsTableController(this);
        loader.setController(controller);
        Parent root = loader.load();
        Scene PayerDetailsScene = new Scene(root);
        Stage window = new Stage();
        window.setScene(PayerDetailsScene);
        window.show();
    }
    
    @FXML
    private Button viewHouseTableButton;
    
    public ObservableList<HouseRepairsModel> getHouseRepairsData(){
        ObservableList<HouseRepairsModel>data;
        data = FXCollections.observableArrayList();
        try {
            String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
            String select = "select * from JatomAptsDetails where HNumber = ?";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(select);
            pstmt.setString(1, (String)SelectHouseBox.getSelectionModel().getSelectedItem());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {                
                String H = rs.getString("HNumber");
                String TN = rs.getString("TenantName");
                String R = rs.getString("RepairsOnHouse");
                String RC = rs.getString("CostOfRepair");
                String M = rs.getString("MiscellaneousExpenses");
                
                HouseRepairsModel rm = new HouseRepairsModel(H, TN, R, RC, M);
                data.add(rm);
            }
        } catch (Exception e) {
        }
        return data;
    }
    
    @FXML
    private void houseRepairTableButton() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("HouseRepairsTable.fxml"));
        HouseRepairsTableController controller = new HouseRepairsTableController(this);
        loader.setController(controller);
        Parent root = loader.load();
        Scene repairtableScene = new Scene(root);
        Stage window = new Stage();
        window.setScene(repairtableScene);
        window.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        h_HouseDetails.prefWidthProperty().bind(motherPane.widthProperty());
        h_PaymentDetails.prefWidthProperty().bind(motherPane.widthProperty());
        h_MonthlyExpenditure.prefWidthProperty().bind(motherPane.widthProperty());
        topbar.prefWidthProperty().bind(motherPane.widthProperty());
        h_HouseDetails.prefHeightProperty().bind(motherPane.heightProperty());
        h_PaymentDetails.prefHeightProperty().bind(motherPane.heightProperty());
        h_MonthlyExpenditure.prefHeightProperty().bind(motherPane.heightProperty());
        
        HouseBlocks.setExpanded(false);
        HouseBlocks.setAnimated(true);
    }
    
}
