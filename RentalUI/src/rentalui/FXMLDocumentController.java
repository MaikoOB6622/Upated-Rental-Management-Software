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
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
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
 *from the user and saves the data to SQlite database.
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
    private AnchorPane titledPaneAnchor;
    @FXML
    private TitledPane HouseBlocks;
    @FXML
    private TitledPane HouseBlocks2;
    @FXML
    private ImageView b_TenantDetails;
    @FXML
    private AnchorPane h_TenantDetails;
    
    
   
    @FXML
    private void handleButtonAction(MouseEvent event) {
        if (event.getTarget() == b_House) {
            h_HouseDetails.setVisible(true);
            h_PaymentDetails.setVisible(false);
            h_MonthlyExpenditure.setVisible(false);
            h_TenantDetails.setVisible(false);
        } else if (event.getTarget() == b_Payment) {
            h_PaymentDetails.setVisible(true);
            h_HouseDetails.setVisible(false);
            h_MonthlyExpenditure.setVisible(false);
            h_TenantDetails.setVisible(false);
        } else if (event.getTarget() == b_monthly) {
            h_MonthlyExpenditure.setVisible(true);
            h_HouseDetails.setVisible(false);
            h_PaymentDetails.setVisible(false);
            h_TenantDetails.setVisible(false);
        } else if (event.getTarget() == b_TenantDetails){
            h_TenantDetails.setVisible(true);
            h_HouseDetails.setVisible(false);
            h_MonthlyExpenditure.setVisible(false);
            h_PaymentDetails.setVisible(false);
        }
    }
    
    ObservableList<String>HouseNumber2 = FXCollections.observableArrayList("B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "B11", "B12");
    ObservableList<String>HouseNumber = FXCollections.observableArrayList("A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10", "A11", "A12");
    ObservableList<String>HouseNumber3 = FXCollections.observableArrayList("C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10");
    ObservableList<String>HouseNumber4 = FXCollections.observableArrayList("Top House", "Bottom House");
    ObservableList<String>MonthPaid = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    
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
    private TextField TNameDetails;
    
    @FXML
    private TextField TenantPhoneNumber;
    
    @FXML
    private TextField RentAmount;
    
    @FXML
    private TextField DueDate;
    
    @FXML
    private TextField RentDeposit;
    
    @FXML
    private TextField MoveInDate;
    
    @FXML
    private TextField MoveOutDate;
    
    @FXML
    private TextField LeaseStartDate;
    
    @FXML
    private TextField LeaseEndDate;
    
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
    
    @FXML
    private void foreignKeyTableTest(){
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
        String sql2 = "CREATE TABLE IF NOT EXISTS JatomAptsDetails (HNumber text PRIMARY KEY, TenantName text, RepairsOnHouse text, CostOfRepair text, MiscellaneousExpenses text, FOREIGN KEY (TenantName) REFERENCES JatomApts (HouseNumber))";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement stmt = conn.prepareStatement(sql2);
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void createTable2(String HNumber, String TenantName, String RepairsOnHouse, String CostOfRepair, String MiscellaneousExpenses){
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
        String sql2 = "CREATE TABLE IF NOT EXISTS JatomAptsDetails (HNumber text PRIMARY KEY, TenantName text, RepairsOnHouse text, CostOfRepair text, MiscellaneousExpenses text, FOREIGN KEY (TenantName) REFERENCES JatomApts (HouseNumber))";
       try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement stmt = conn.prepareStatement(sql2);
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error while saving to database. Please ensure there is no tenant entry for house number");
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
    
    private void createTenantDetailstable (String HouseNumber, String TenantName, String TenantPhoneNumber, String RentAmount, String DueDate, String Deposit, String MoveInDate, String MoveOutDate, String LeaseStartDate, String LeaseEndDate){
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
        String sql = "CREATE TABLE IF NOT EXISTS JatomTenantDetails (HouseNumber text, TenantName text, TenantPhoneNumber text, RentAmount text, DueDate text, Deposit text, MoveInDate text, MoveOutDate text, LeaseStartDate text, LeaseEndDate text)";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql1 = "INSERT INTO JatomTenantDetails (HouseNumber, TenantName, TenantPhoneNumber, RentAmount, DueDate, Deposit, MoveInDate, MoveOutDate, LeaseStartDate, LeaseEndDate) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, HouseNumber);
            pstmt.setString(2, TenantName);
            pstmt.setString(3, TenantPhoneNumber);
            pstmt.setString(4, RentAmount);
            pstmt.setString(5, DueDate);
            pstmt.setString(6, Deposit);
            pstmt.setString(7, MoveInDate);
            pstmt.setString(8, MoveOutDate);
            pstmt.setString(9, LeaseStartDate);
            pstmt.setString(10, LeaseEndDate);
            pstmt.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void tableInsertTenantDetails(){
        if (checkComboBoxDetails == "BlockA"){
            this.createTenantDetailstable((String) selectHouseBoxDetails.getSelectionModel().getSelectedItem(), TNameDetails.getText(), TenantPhoneNumber.getText(), RentAmount.getText(), DueDate.getText(), RentDeposit.getText(), MoveInDate.getText(), MoveOutDate.getText(), LeaseStartDate.getText(), LeaseEndDate.getText());
            selectHouseBoxDetails.setValue(null);
            TNameDetails.setText("");
            TenantPhoneNumber.setText("");
            RentAmount.setText("");
            DueDate.setText("");
            RentDeposit.setText("");
            MoveInDate.setText("");
            MoveOutDate.setText("");
            LeaseStartDate.setText("");
            LeaseEndDate.setText("");
        }else if (checkComboBoxDetails == "BlockB") {
            this.createTenantDetailstable((String)selectBlockBDetails.getSelectionModel().getSelectedItem(), TNameDetails.getText(), TenantPhoneNumber.getText(), RentAmount.getText(), DueDate.getText(), RentDeposit.getText(), MoveInDate.getText(), MoveOutDate.getText(), LeaseStartDate.getText(), LeaseEndDate.getText());
            selectBlockBDetails.setValue(null);
            TNameDetails.setText("");
            TenantPhoneNumber.setText("");
            RentAmount.setText("");
            DueDate.setText("");
            RentDeposit.setText("");
            MoveInDate.setText("");
            MoveOutDate.setText("");
            LeaseStartDate.setText("");
            LeaseEndDate.setText("");
             
        }else if (checkComboBoxDetails == "BlockC"){
            this.createTenantDetailstable((String) selectBlockCDetails.getSelectionModel().getSelectedItem(), TNameDetails.getText(), TenantPhoneNumber.getText(), RentAmount.getText(), DueDate.getText(), RentDeposit.getText(), MoveInDate.getText(), MoveOutDate.getText(), LeaseStartDate.getText(), LeaseEndDate.getText());
            selectBlockCDetails.setValue(null);
            TNameDetails.setText("");
            TenantPhoneNumber.setText("");
            RentAmount.setText("");
            DueDate.setText("");
            RentDeposit.setText("");
            MoveInDate.setText("");
            MoveOutDate.setText("");
            LeaseStartDate.setText("");
            LeaseEndDate.setText("");
        }else if (checkComboBoxDetails == "NasraBlock"){
            this.createTenantDetailstable((String) selectNasraBlockDetails.getSelectionModel().getSelectedItem(), TNameDetails.getText(), TenantPhoneNumber.getText(), RentAmount.getText(), DueDate.getText(), RentDeposit.getText(), MoveInDate.getText(), MoveOutDate.getText(), LeaseStartDate.getText(), LeaseEndDate.getText());
            selectNasraBlockDetails.setValue(null);
            TNameDetails.setText("");
            TenantPhoneNumber.setText("");
            RentAmount.setText("");
            DueDate.setText("");
            RentDeposit.setText("");
            MoveInDate.setText("");
            MoveOutDate.setText("");
            LeaseStartDate.setText("");
            LeaseEndDate.setText("");
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
    private void TableInsertButton2() {
        if (checkComboBox == "BlockA") {
            this.createTable2((String) SelectHouseBox.getSelectionModel().getSelectedItem(), TName.getText(), Repairs.getText(), repairCost.getText(), miscellaneous.getText());
            SelectHouseBox.setValue(null);
            TName.setText("");
            Repairs.setText("");
            repairCost.setText("");
            miscellaneous.setText("");
        }else if (checkComboBox == "BlockB"){
            this.createTable2((String)SelectBlockB.getSelectionModel().getSelectedItem(), TName.getText(), Repairs.getText(), repairCost.getText(), miscellaneous.getText());
            SelectBlockB.setValue(null);
            TName.setText("");
            Repairs.setText("");
            repairCost.setText("");
            miscellaneous.setText("");
        }else if (checkComboBox == "BlockC"){
            this.createTable2((String)SelectBlockC.getSelectionModel().getSelectedItem(), TName.getText(), Repairs.getText(), repairCost.getText(), miscellaneous.getText());
            SelectBlockC.setValue(null);
            TName.setText("");
            Repairs.setText("");
            repairCost.setText("");
            miscellaneous.setText("");
        }else if (checkComboBox == "NasraBlock"){
            this.createTable2((String)SelectNasraBlock.getSelectionModel().getSelectedItem(), TName.getText(), Repairs.getText(), repairCost.getText(), miscellaneous.getText());
            SelectNasraBlock.setValue(null);
            TName.setText("");
            Repairs.setText("");
            repairCost.setText("");
            miscellaneous.setText("");
        }

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
    private ComboBox SelectHouseBox;
    @FXML
    private void initializeSelectHouseBox() {
        SelectHouseBox.setItems(HouseNumber);   
    }
    
    @FXML
    private ComboBox SelectBlockB;
    @FXML
    private void initializeSelectBlockB(){
        SelectBlockB.setItems(HouseNumber2);
    }
    
    @FXML
    private ComboBox SelectBlockC;
    @FXML
    private void initializeSelectBlockC(){
        SelectBlockC.setItems(HouseNumber3); 
    }
   
    @FXML
    private ComboBox SelectNasraBlock;
    @FXML
    private void initializeSelectNasraBlock(){
        SelectNasraBlock.setItems(HouseNumber4);
        
    }
    
    @FXML
    private ComboBox selectHouseBoxDetails;
    @FXML
    private void initializeHouseBoxDetails(){
        selectHouseBoxDetails.setItems(HouseNumber);
    }
    
    @FXML
    private ComboBox selectBlockBDetails;
    @FXML
    private void initializeBlockBDetails(){
        selectBlockBDetails.setItems(HouseNumber2);
    }
    
    @FXML
    private ComboBox selectBlockCDetails;
    @FXML
    private void initializeBlockCDetails(){
        selectBlockCDetails.setItems(HouseNumber3);
    }
    
    @FXML
    private ComboBox selectNasraBlockDetails;
    @FXML
    private void initializeNasraBlockDetails(){
        selectNasraBlockDetails.setItems(HouseNumber4);
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
    
    String checkComboBox;
    String checkComboBoxDetails;
    
    public ObservableList<HouseRepairsModel> getHouseRepairsData() {
        
        ObservableList<HouseRepairsModel> data;
        data = FXCollections.observableArrayList();
        if (checkComboBox == "BlockA") {
            try {
                String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                String select = "select * from JatomAptsDetails where HNumber = ?";
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(select);
                pstmt.setString(1, (String) SelectHouseBox.getSelectionModel().getSelectedItem());
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
                e.printStackTrace();
            }
        } else if (checkComboBox == "BlockB") {
            try {
                String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                String select = "select * from JatomAptsDetails where HNumber = ?";
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(select);
                pstmt.setString(1, (String) SelectBlockB.getSelectionModel().getSelectedItem());
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
                e.printStackTrace();
            }
        }else if (checkComboBox == "BlockC"){
            try {
                String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                String select = "select * from JatomAptsDetails where HNumber = ?";
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(select);
                pstmt.setString(1, (String) SelectBlockC.getSelectionModel().getSelectedItem());
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
                e.printStackTrace();
            }
        }else if (checkComboBox == "NasraBlock"){
            try {
                String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                String select = "select * from JatomAptsDetails where HNumber = ?";
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(select);
                pstmt.setString(1, (String) SelectNasraBlock.getSelectionModel().getSelectedItem());
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
                e.printStackTrace();
            }
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
        
        //Handling mouse clicked event on titledpane
        HouseBlocks.setOnMouseClicked((MouseEvent e) -> {
            SelectHouseBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                Label label = new Label();
                label.setText((String) SelectHouseBox.getSelectionModel().getSelectedItem());
                try {
                    String ur = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                    String query = "select * from JatomAptsDetails where HNumber = ?";
                    Connection conn = DriverManager.getConnection(ur);
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, (String) SelectHouseBox.getSelectionModel().getSelectedItem());
                    ResultSet rs = pstmt.executeQuery();

                    while (rs.next()) {
                        TName.setText(rs.getString("TenantName"));
                        Repairs.setText(rs.getString("RepairsOnHouse"));
                        repairCost.setText(rs.getString("CostOfRepair"));
                        miscellaneous.setText(rs.getString("MiscellaneousExpenses"));
                    }
                    HouseBlocks.setGraphic(label);
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
                checkComboBox = "BlockA";
                HouseBlocks.setExpanded(false);
                Platform.runLater(() -> {

                });
            });
            SelectBlockB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                Label label = new Label();
                label.setText((String) SelectBlockB.getSelectionModel().getSelectedItem());
                try {
                    String url1 = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                    String select = "select * from JatomAptsDetails where HNumber = ?";
                    Connection conn = DriverManager.getConnection(url1);
                    PreparedStatement pstmt = conn.prepareStatement(select);
                    pstmt.setString(1, (String) SelectBlockB.getSelectionModel().getSelectedItem());
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        TName.setText(rs.getString("TenantName"));
                        Repairs.setText(rs.getString("RepairsOnHouse"));
                        repairCost.setText(rs.getString("CostOfRepair"));
                        miscellaneous.setText(rs.getString("MiscellaneousExpenses"));
                    }
                    HouseBlocks.setGraphic(label);
                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                checkComboBox = "BlockB";
                HouseBlocks.setExpanded(false);
            });
            SelectBlockC.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                Label label = new Label();
                label.setText((String) SelectBlockC.getSelectionModel().getSelectedItem());
                try {
                    String url2 = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                    String select = "select * from JatomAptsDetails where HNumber = ?";
                    Connection conn = DriverManager.getConnection(url2);
                    PreparedStatement pstmt = conn.prepareStatement(select);
                    pstmt.setString(1, (String) SelectBlockC.getSelectionModel().getSelectedItem());
                    ResultSet rs = pstmt.executeQuery();

                    while (rs.next()) {
                        TName.setText(rs.getString("TenantName"));
                        Repairs.setText(rs.getString("RepairsOnHouse"));
                        repairCost.setText(rs.getString("CostOfRepair"));
                        miscellaneous.setText(rs.getString("MiscellaneousExpenses"));
                    }
                    HouseBlocks.setGraphic(label);
                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                checkComboBox = "BlockC";
               HouseBlocks.setExpanded(false);
            });
            SelectNasraBlock.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                Label label = new Label();
                label.setText((String) SelectNasraBlock.getSelectionModel().getSelectedItem());
                try {
                    String url3 = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                    String select = "select * from JatomAptsDetails where HNumber = ?";
                    Connection conn = DriverManager.getConnection(url3);
                    PreparedStatement pstmt = conn.prepareStatement(select);
                    pstmt.setString(1, (String) SelectNasraBlock.getSelectionModel().getSelectedItem());
                    ResultSet rs = pstmt.executeQuery();
                    
                    while (rs.next()) {                        
                        TName.setText(rs.getString("TenantName"));
                        Repairs.setText(rs.getString("RepairsOnHouse"));
                        repairCost.setText(rs.getString("CostOfRepair"));
                        miscellaneous.setText(rs.getString("MiscellaneousExpenses"));
                    }
                    HouseBlocks.setGraphic(label);
                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                checkComboBox = "NasraBlock";
                HouseBlocks.setExpanded(false);
            });
            
        });
        
        //Handling HouseBlocks2 mouse clicked event
        HouseBlocks2.setOnMouseClicked((MouseEvent e) -> {
            selectHouseBoxDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                Label label = new Label();
                label.setText((String)selectHouseBoxDetails.getSelectionModel().getSelectedItem());
                try {
                    String url4 = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                    String select = "SELECT * FROM JatomTenantDetails where HouseNumber = ?";
                    Connection conn = DriverManager.getConnection(url4);
                    PreparedStatement pstmt = conn.prepareStatement(select);
                    pstmt.setString(1, (String)selectHouseBoxDetails.getSelectionModel().getSelectedItem());
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        TNameDetails.setText(rs.getString("TenantName"));
                        TenantPhoneNumber.setText(rs.getString("TenantPhoneNumber"));
                        RentAmount.setText(rs.getString("RentAmount"));
                        DueDate.setText(rs.getString("DueDate"));
                        RentDeposit.setText(rs.getString("Deposit"));
                        MoveInDate.setText(rs.getString("MoveInDate"));
                        MoveOutDate.setText(rs.getString("MoveOutDate"));
                        LeaseStartDate.setText(rs.getString("LeaseStartDate"));
                        LeaseEndDate.setText(rs.getString("LeaseEndDate"));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                checkComboBoxDetails = "BlockA";
                HouseBlocks2.setGraphic(label);
                HouseBlocks2.setExpanded(false);
            });
            selectBlockBDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                Label label = new Label();
                label.setText((String)selectBlockBDetails.getSelectionModel().getSelectedItem());
                try {
                    String url5 = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                    String select = "SELECT * FROM JatomTenantDetails where HouseNumber = ?";
                    Connection conn = DriverManager.getConnection(url5);
                    PreparedStatement pstmt = conn.prepareStatement(select);
                    pstmt.setString(1, (String)selectBlockBDetails.getSelectionModel().getSelectedItem());
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {                        
                        TNameDetails.setText(rs.getString("TenantName"));
                        TenantPhoneNumber.setText(rs.getString("TenantPhoneNumber"));
                        RentAmount.setText(rs.getString("RentAmount"));
                        DueDate.setText(rs.getString("DueDate"));
                        RentDeposit.setText(rs.getString("Deposit"));
                        MoveInDate.setText(rs.getString("MoveInDate"));
                        MoveOutDate.setText(rs.getString("MoveOutDate"));
                        LeaseStartDate.setText(rs.getString("LeaseStartDate"));
                        LeaseEndDate.setText(rs.getString("LeaseEndDate"));    
                    }
                } catch (Exception ex) {
                    ex.printStackTrace(); 
                }
                checkComboBoxDetails = "BlockB";
                HouseBlocks2.setGraphic(label);
                HouseBlocks2.setExpanded(false);
            });
            selectBlockCDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                Label label = new Label();
                label.setText((String)selectBlockCDetails.getSelectionModel().getSelectedItem());
                try {
                    String url5 = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                    String select = "SELECT * FROM JatomTenantDetails where HouseNumber = ?";
                    Connection conn = DriverManager.getConnection(url5);
                    PreparedStatement pstmt = conn.prepareStatement(select);
                    pstmt.setString(1, (String)selectBlockCDetails.getSelectionModel().getSelectedItem());
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {                        
                        TNameDetails.setText(rs.getString("TenantName"));
                        TenantPhoneNumber.setText(rs.getString("TenantPhoneNumber"));
                        RentAmount.setText(rs.getString("RentAmount"));
                        DueDate.setText(rs.getString("DueDate"));
                        RentDeposit.setText(rs.getString("Deposit"));
                        MoveInDate.setText(rs.getString("MoveInDate"));
                        MoveOutDate.setText(rs.getString("MoveOutDate"));
                        LeaseStartDate.setText(rs.getString("LeaseStartDate"));
                        LeaseEndDate.setText(rs.getString("LeaseEndDate"));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                checkComboBoxDetails = "BlockC";
                HouseBlocks2.setGraphic(label);
                HouseBlocks2.setExpanded(false);
            });
            selectNasraBlockDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                Label label = new Label();
                label.setText((String)selectNasraBlockDetails.getSelectionModel().getSelectedItem());
                try {
                    String url6 = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                    String select = "SELECT * FROM JatomTenantDetails where HouseNumber = ?";
                    Connection conn = DriverManager.getConnection(url6);
                    PreparedStatement pstmt = conn.prepareStatement(select);
                    pstmt.setString(1, (String)selectNasraBlockDetails.getSelectionModel().getSelectedItem());
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {                        
                        TNameDetails.setText(rs.getString("TenantName"));
                        TenantPhoneNumber.setText(rs.getString("TenantPhoneNumber"));
                        RentAmount.setText(rs.getString("RentAmount"));
                        DueDate.setText(rs.getString("DueDate"));
                        RentDeposit.setText(rs.getString("Deposit"));
                        MoveInDate.setText(rs.getString("MoveInDate"));
                        MoveOutDate.setText(rs.getString("MoveOutDate"));
                        LeaseStartDate.setText(rs.getString("LeaseStartDate"));
                        LeaseEndDate.setText(rs.getString("LeaseEndDate"));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                checkComboBoxDetails = "NasraBlock";
                HouseBlocks2.setGraphic(label);
                HouseBlocks2.setExpanded(false);
            });
            
        });
        h_HouseDetails.setOnMouseClicked((Event e) -> {
            if (HouseBlocks.isExpanded()){
                HouseBlocks.setExpanded(false);
            }
        });
        topbar.setOnMouseClicked((Event e) ->{
            if (HouseBlocks.isExpanded()){
                HouseBlocks.setExpanded(false);
            }
        });
        if (!HouseBlocks2.isExpanded()){
            titledPaneAnchor.setVisible(false);
        }
        HouseBlocks.setExpanded(false);
        HouseBlocks.setAnimated(true);
        HouseBlocks2.setExpanded(false);
        HouseBlocks2.setAnimated(true);
    }

}
