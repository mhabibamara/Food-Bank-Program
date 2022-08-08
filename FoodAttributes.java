package edu.ucalgary.ensf409;

import java.sql.*;
import java.util.EnumSet;

// import com.mysql.cj.protocol.a.MysqlBinaryValueDecoder;


//example tomato sauce, 1, 120, 4
class FoodAttributes {
    
private int ID;
private String NAME;
private int GRAIN_CONTENT;
private int FV_CONTENT;
private int PROTEIN_CONTENT;
private int OTHER_CONTENT;
private int FOOD_CALORIES;
private int baseNumerbInInventory;

int numberInInventory;

/**
 * Food attributes method
 * @param ID
 * @param name
 * @param grain_content
 * @param fv_content
 * @param protein_content
 * @param other_content
 * @param food_calories
 */
public FoodAttributes(int ID, String name, int grain_content, int fv_content, int protein_content, int other_content, int food_calories) {
    this.ID = ID;
    this.NAME = name;
    this.GRAIN_CONTENT = grain_content;
    this.FV_CONTENT = fv_content;
    this.PROTEIN_CONTENT = protein_content;
    this.OTHER_CONTENT = other_content;
    this.FOOD_CALORIES = food_calories;
    numberInInventory = 0;
}

public void SetBase() {
    baseNumerbInInventory = numberInInventory;
}
public void ResetBase() {
    numberInInventory = baseNumerbInInventory;
}

public void IncrementAmount(){
    numberInInventory++;
}

public void DecrementAmount(){
    
    numberInInventory--;
}

/**
 * 
 * @return
 */
public int getAmount(){
    return numberInInventory;
}

public int getGrainContent(){
    return this.GRAIN_CONTENT;
}
public int getProtienContent(){
    return this.PROTEIN_CONTENT;
}

public int getFVContent(){
    return this.FV_CONTENT;
}

public int getOtherContent(){
    return this.OTHER_CONTENT;
}

public int getCalorieContent(){
    return this.FOOD_CALORIES;
}

public int getFoodID() {
    return this.ID;
}

public String getName(){
    return this.NAME;
}
/**
 * Get type method
 * @param foodType
 * @return
 */
public int GetType(FoodType foodType) {
    switch(foodType){
        case PRO:
        return this.getProtienContent();
        
        case GRA:
        return this.getGrainContent();
        
        case FV:
        return this.getFVContent();
        
        case OTHER:
        return this.getOtherContent();
        
        case CAL:
        return this.getCalorieContent();
        
    }
    return -1;
}

/**
 * Get Sum Except method
 * @param type
 * @return
 */
public int GetSumExcept(FoodType type) {
    int sum = 0;
    for (FoodType food : FoodType.values()) {
        if(food != type && type != FoodType.CAL) {
            sum += GetType(food);
        }
    }
    return sum;
}

//Custom equals method
//Comparing the name of the food attribute 
@Override
public boolean equals(Object foodToCompare) {
    
    if(foodToCompare instanceof FoodAttributes) {
        FoodAttributes other = (FoodAttributes) foodToCompare;
        if(this.NAME.equals(other.getName())) {
            return true;
        }

    }
 
    return false;

}


public String ToString() {
    return "ID: " + this.ID + ", " + NAME + " , " + "Num in Inventory: " + numberInInventory + ", GRAIN: " + this.GRAIN_CONTENT + ", FV " + this.FV_CONTENT + ", " + " PRO: " + this.PROTEIN_CONTENT + ", OTHER " + this.OTHER_CONTENT + ", CAL " + this.FOOD_CALORIES;
}


    
}
