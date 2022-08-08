package edu.ucalgary.ensf409;

import java.util.ArrayList;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;
import javax.swing.text.StyledEditorKit;

//import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

//import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

import java.math.*;

class SumsOfNutrients{
    private int sumPro = 0;
    private int sumGrain = 0;
    private int sumFV = 0;
    private int sumOther = 0;
    private int sumCal = 0;

    public SumsOfNutrients(){
        sumPro = 0;
        sumGrain = 0;
        sumFV = 0;
        sumOther = 0;
        sumCal = 0;
    }

    public void UpdateSums(FoodAttributes food) {
        sumPro += food.getProtienContent();
        sumGrain += food.getGrainContent();
        sumFV += food.getFVContent();
        sumCal += food.getCalorieContent();
        sumOther += food.getOtherContent();
    }

    public int GetPro(){
        return sumPro;
    }
    public int GetGrain(){
        return sumGrain;
    }
    public int GetOther(){
        return sumOther;
    }public int GetFV(){
        return sumFV;
    }public int GetCal(){
        return sumCal;
    }

    public String ToString(){
        return "Sum Pro: " + sumPro + "\n" + "Sum Grain: " + sumGrain + "\n" + "Sum FV: " + sumFV + "\n" + "Sum OTHER: " + sumOther + "\n" + "Sum CAL: " + sumCal;
    }
    public int GetType(FoodType type){
        switch(type){
            case PRO:
            return sumPro;
            
            case GRA:
            return sumGrain;
            
            case FV:
            return sumFV;
            
            case OTHER:
            return sumOther;
            
            case CAL:
            return sumCal;
            
        }
        
        return -1;
    }
    public void ClearData() {
        sumPro = 0;
        sumGrain = 0;
        sumFV = 0;
        sumOther = 0;
        sumCal = 0;
    }

}


public class NutritionalFinder {

    private FoodInventory foodInv;
    SumsOfNutrients son;
    ArrayList<FoodAttributes> ToAdd;

    public NutritionalFinder(FoodInventory inventory) {
        this.foodInv = inventory;
        son = new SumsOfNutrients(); 
        son.ClearData();
        ToAdd = new ArrayList<>();
    }

    public void ClientNutFinder(ClientList client) {

        ArrayList<FoodAttributes> OverLimitItems = new ArrayList<>();
        //ArrayList<FoodAttributes> proList = foodInv.GetProtienSortedInventory();

        FoodAttributes food;
        

        int proTarget = client.getTotalRecommendedProteinForWeek();
        int otherTarget = client.getTotalRecommendedOtherForWeek();
        int grainTarget = client.getTotalRecommendedGrainForWeek();
        int FVTarget = client.getTotalRecommendedFVForWeek();
        int calTarget = client.getTotalRecommendedCaloriesForWeek();
        
        
        //PROTIEN
        for(int i = 0; i < foodInv.generalInventory.size(); i++) {
            
            food = findClosest(foodInv.protienSortedInventory, proTarget, FoodType.PRO);
            if(food == null) {
                
                return;
            }
            DistrubuteFood(foodInv.protienSortedInventory, OverLimitItems, ToAdd, food, proTarget, otherTarget, grainTarget, FVTarget, calTarget, FoodType.PRO);
    
            proTarget -= food.getProtienContent();
            otherTarget -= food.getOtherContent();
            grainTarget -= food.getGrainContent();
            FVTarget -= food.getFVContent();
            calTarget -= food.getCalorieContent();
    
            //GRAIN
            food = findClosest(foodInv.grainSortedInventory, grainTarget, FoodType.GRA);
            if(food == null) {
                
                return;
            }
            DistrubuteFood(foodInv.grainSortedInventory, OverLimitItems, ToAdd, food, proTarget, otherTarget, grainTarget, FVTarget, calTarget, FoodType.GRA);
    
            proTarget -= food.getProtienContent();
            otherTarget -= food.getOtherContent();
            grainTarget -= food.getGrainContent();
            FVTarget -= food.getFVContent();
            calTarget -= food.getCalorieContent();
    
            //FV
            food = findClosest(foodInv.FVSortedInventory, FVTarget, FoodType.FV);
            if(food == null) {
                
                return;
            }
            DistrubuteFood(foodInv.FVSortedInventory, OverLimitItems, ToAdd, food, proTarget, otherTarget, grainTarget, FVTarget, calTarget, FoodType.FV);
    
            proTarget -= food.getProtienContent();
            otherTarget -= food.getOtherContent();
            grainTarget -= food.getGrainContent();
            FVTarget -= food.getFVContent();
            calTarget -= food.getCalorieContent();

            //OTHER
            food = findClosest(foodInv.otherSortedInventory, otherTarget, FoodType.OTHER);
            if(food == null) {
                
                return;
            }
            DistrubuteFood(foodInv.otherSortedInventory, OverLimitItems, ToAdd, food, proTarget, otherTarget, grainTarget, FVTarget, calTarget, FoodType.OTHER);
    
            proTarget -= food.getProtienContent();
            otherTarget -= food.getOtherContent();
            grainTarget -= food.getGrainContent();
            FVTarget -= food.getFVContent();
            calTarget -= food.getCalorieContent();
    
            //CAL
            food = findClosest(foodInv.calorieSortedInventory, calTarget, FoodType.CAL);
            if(food == null) {
                
                return;
            }
            DistrubuteFood(foodInv.calorieSortedInventory, OverLimitItems, ToAdd, food, proTarget, otherTarget, grainTarget, FVTarget, calTarget, FoodType.CAL);
    
            proTarget -= food.getProtienContent();
            otherTarget -= food.getOtherContent();
            grainTarget -= food.getGrainContent();
            FVTarget -= food.getFVContent();
            calTarget -= food.getCalorieContent();
        }

    

        for(FoodAttributes item : ToAdd) {
            son.UpdateSums(item);
        }
        
        foodInv.ResetInventories();


        int proDifference = client.getTotalRecommendedProteinForWeek() - son.GetPro();
        int grainDifference = client.getTotalRecommendedGrainForWeek() - son.GetGrain();
        int fvDifference = client.getTotalRecommendedFVForWeek() - son.GetFV();
        int otherDifference = client.getTotalRecommendedOtherForWeek() - son.GetOther();
        int calDifference = client.getTotalRecommendedCaloriesForWeek() - son.GetCal();
        
        while(grainDifference > 0) {
            grainDifference = client.getTotalRecommendedGrainForWeek() - son.GetGrain();

            FoodAttributes foodToAdd = foodInv.GetGrainSortedInventory().get(0);
            ToAdd.add(foodToAdd);
            DeleteFromDb(foodToAdd);


            son.UpdateSums(foodToAdd);
            foodInv.RemoveFromList(foodToAdd);
            if(foodInv.GetGrainSortedInventory().isEmpty()) {
                System.out.println("Ran out of food");
                return;
            }
        }


        while(fvDifference > 0) {
            fvDifference = client.getTotalRecommendedFVForWeek() - son.GetFV();

            FoodAttributes foodToAdd = foodInv.GetFVSortedInventory().get(0);
            son.UpdateSums(foodToAdd);
            ToAdd.add(foodToAdd);
            
            DeleteFromDb(foodToAdd);

            foodInv.RemoveFromList(foodToAdd);
            if(foodInv.GetFVSortedInventory().isEmpty()) {
                System.out.println("Ran out of food");
                return;
            }
        }
        

        while(otherDifference > 0) {
            otherDifference = client.getTotalRecommendedOtherForWeek() - son.GetOther();

            FoodAttributes foodToAdd = foodInv.GetOtherSortedInventory().get(0);
            son.UpdateSums(foodToAdd);
            ToAdd.add(foodToAdd);

            DeleteFromDb(foodToAdd);

            foodInv.RemoveFromList(foodToAdd);
            if(foodInv.GetOtherSortedInventory().isEmpty()) {
                System.out.println("Ran out of food");
                return;
            }
        }


        while(calDifference > 0) {
            calDifference = client.getTotalRecommendedCaloriesForWeek() - son.GetCal();

            FoodAttributes foodToAdd = foodInv.GetCalorieSortedInventory().get(0);
            son.UpdateSums(foodToAdd);
            ToAdd.add(foodToAdd);

            DeleteFromDb(foodToAdd);

            foodInv.RemoveFromList(foodToAdd); 
            if(foodInv.GetCalorieSortedInventory().isEmpty()) {
                System.out.println("Ran out of food");
                return;
            }
        }


        while(proDifference > 0) {
            proDifference = client.getTotalRecommendedProteinForWeek() - son.GetPro();


            FoodAttributes foodToAdd = foodInv.GetProtienSortedInventory().get(0);
            son.UpdateSums(foodToAdd);
            ToAdd.add(foodToAdd);
            DeleteFromDb(foodToAdd);
            foodInv.RemoveFromList(foodToAdd);
            if(foodInv.GetProtienSortedInventory().isEmpty()) {
                System.out.println("Ran out of food");
                return;
            }
                   
        }
       
    }

    private void DeleteFromDb(FoodAttributes foodToAdd) {
        try {
            SQLReader.delete(foodToAdd.getName());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void GetFoodInToAdd(ArrayList<FoodAttributes> toAdd){
        for(FoodAttributes food : toAdd){
            System.out.println(food.ToString());
        }
    }


    private void DistrubuteFood(ArrayList<FoodAttributes>inv, ArrayList<FoodAttributes> OverLimitItems, ArrayList<FoodAttributes> ToAdd,
                        FoodAttributes food, int proTarget, int otherTarget, int grainTarget, int FVTarget, int calTarget, FoodType type) {
       
        
        if(DoesGoOverLimitExcept(food, proTarget, grainTarget, FVTarget, otherTarget, calTarget,  type)) {
                OverLimitItems.add(food);
        }
        else {
            ToAdd.add(food);
            foodInv.RemoveFromList(food);
        }

        DeleteFromDb(food);
        
    }

    private boolean DoesGoOverLimitExcept(FoodAttributes food, int pro, int grain, int fv, int other, int cal, FoodType type) {
        if(food.getCalorieContent() > cal && type != FoodType.CAL) {
            return true;
        }
        if(food.getProtienContent() > pro && type != FoodType.PRO) {
            return true;
        }
        if(food.getGrainContent() > grain && type != FoodType.GRA) return true;
        if(food.getOtherContent() > other && type != FoodType.OTHER) return true;
        if(food.getFVContent() > fv && type != FoodType.FV) return true;
        else return false;
    }

    private FoodAttributes findClosest(ArrayList<FoodAttributes> arr, int target, FoodType foodType)
    {
        
        if(arr.isEmpty()) {
            return null;
        }
        //arr.get(x).GetType(foodType);
        int min = Math.abs(arr.get(0).GetType(foodType) - target);
        

        FoodAttributes minFood = arr.get(0);

        int distance = min;
        for (int  i = 0; i < arr.size(); i++) {

            distance = Math.abs(arr.get(i).GetType(foodType) - target);

            if(distance < min && arr.get(i).numberInInventory > 0) {
                min = distance;
                minFood = arr.get(i);
            }
        }
        
        return minFood;
        
    }


    
}
