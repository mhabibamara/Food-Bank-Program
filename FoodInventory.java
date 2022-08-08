package edu.ucalgary.ensf409;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

// import com.mysql.cj.protocol.a.MysqlBinaryValueDecoder;

public class FoodInventory {
    
    ArrayList<FoodAttributes> generalInventory = new ArrayList<FoodAttributes>();
    ArrayList<FoodAttributes> protienSortedInventory = new ArrayList<FoodAttributes>();
    ArrayList<FoodAttributes> grainSortedInventory = new ArrayList<FoodAttributes>();
    ArrayList<FoodAttributes> FVSortedInventory = new ArrayList<FoodAttributes>();
    ArrayList<FoodAttributes> otherSortedInventory = new ArrayList<FoodAttributes>();
    ArrayList<FoodAttributes> calorieSortedInventory = new ArrayList<FoodAttributes>();
    
    int totalCal = 0;
/**
 * Adding to the list method
 * @param foodAttributes
 */
public void addingToTheList(FoodAttributes foodAttributes){
    
    totalCal += foodAttributes.getCalorieContent();
    if(!generalInventory.contains(foodAttributes)) {
        
        generalInventory.add(foodAttributes);
    }
    
    generalInventory.get(generalInventory.lastIndexOf(foodAttributes)).IncrementAmount();
}

public void TotalCal() {
    System.out.println("The total caloreis is: " + totalCal);
}

public void SetBase() {
    for(FoodAttributes food : generalInventory) {
        food.SetBase();
    }
}
public void ResetBase() {
    for(FoodAttributes food : generalInventory) {
        food.ResetBase();
    }
}

/**
 * Remove from list method
 * @param food
 */
public void RemoveFromList(FoodAttributes food) {

    food.DecrementAmount();
    if(food.getAmount() < 1) {
        generalInventory.remove(food);
        protienSortedInventory.remove(food);
        grainSortedInventory.remove(food);
        otherSortedInventory.remove(food);
        calorieSortedInventory.remove(food);
        FVSortedInventory.remove(food);
    }

}

public void sortByNutrtion() {

    ResetBase();
    ResetInventories();

}

//Clears the inventories and sets them all up with the sorting from the general inventory\
public void ResetInventories() {

    protienSortedInventory.clear();
    protienSortedInventory = (ArrayList)generalInventory.clone();
    Collections.sort(protienSortedInventory, new InverseProComparator());
    otherSortedInventory.clear();
    otherSortedInventory = (ArrayList)generalInventory.clone();
    Collections.sort(otherSortedInventory, new InverseOtherComparator());
    grainSortedInventory.clear();
    grainSortedInventory = (ArrayList)generalInventory.clone();
    Collections.sort(protienSortedInventory, new InverseGrainComparator());
    FVSortedInventory.clear();
    FVSortedInventory = (ArrayList)generalInventory.clone();
    Collections.sort(otherSortedInventory, new InverseFVComparator());
    calorieSortedInventory.clear();
    calorieSortedInventory = (ArrayList)generalInventory.clone();
    Collections.sort(protienSortedInventory, new InverseCalComparator());

}

/**
 * To string method
 * @return string
 */
public String ToString(){
    String returnString = "";
    for (FoodAttributes foodAttributes : generalInventory) {
        returnString += foodAttributes.ToString() + "\n";
    }
    return returnString;
}
public ArrayList<FoodAttributes> GetGeneralInventory(){
    return this.generalInventory;
}

public ArrayList<FoodAttributes> GetGrainSortedInventory(){
    return this.grainSortedInventory;
}
public ArrayList<FoodAttributes> GetProtienSortedInventory(){
    return this.protienSortedInventory;
}

public ArrayList<FoodAttributes> GetFVSortedInventory(){
    return this.FVSortedInventory;
}

public ArrayList<FoodAttributes> GetOtherSortedInventory(){
    return this.otherSortedInventory;
}

public ArrayList<FoodAttributes> GetCalorieSortedInventory(){
    return this.calorieSortedInventory;
}

/*

HOW THE .contains works: 

FoodAttribute foodToFind;
foreach(FoodAttribute food : generalInventory) {
    if(foodToFind.equals(food)) {
        return true;
    }

}
return false;
*/


/*
How The comperator .compare works 
foreach(FoodAttribute food : generalInventory) {
    if(foodToFind.compareTo(food) > 1) {
        return true;
    }

}
*/
}
