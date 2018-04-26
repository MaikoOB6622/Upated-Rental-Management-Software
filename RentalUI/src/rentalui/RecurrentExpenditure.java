/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rentalui;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Mike
 */
public class RecurrentExpenditure {
    public final SimpleStringProperty MonthChoose;
    public final SimpleStringProperty Water;
    public final SimpleStringProperty Electricity;
    
   public RecurrentExpenditure(String MChoose, String Wat, String Elec){
       this.MonthChoose = new SimpleStringProperty(MChoose);
       this.Water = new SimpleStringProperty(Wat);
       this.Electricity = new SimpleStringProperty(Elec);
   }
    
    public String getMonthChoose(){
       return MonthChoose.get();
    }
    public void setMonthChoose(String MChoose){
        MonthChoose.set(MChoose);
    }
    
    public String getWater(){
        return Water.get();
    }
    public void setWater(String Wat){
        Water.set(Wat);
    }
    
    public String getElectricity(){
        return Electricity.get();
    }
    public void setElectricity(String Elec){
        Electricity.set(Elec);
    }
}
