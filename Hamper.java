package edu.ucalgary.ensf409;

import java.util.ArrayList;

class Hamper {
    private ClientList hamperClients;
    private ArrayList<FoodAttributes> hamperFood = new ArrayList<FoodAttributes>();

    /**
     * Hamper method
     * @param clients
     */
    public Hamper(ClientList clients){
        this.hamperClients = clients;
    }

    /**
     * fill Hamper method
     * @param food
     */
    public void fillHamper(FoodAttributes food){
        hamperFood.add(food);
    }

    /**
     * get client Types method
     * @return string
     */
    public String getClientTypes(){
        String formatted = "";
        for(int i = 0; i < 4; i++){

            // formatted += hamperClients.numOf(hamperClients.get(i).getClientID()) + " " + hamperClients.get(i).getClientType() + ", ";
            formatted += hamperClients.GetClientType(i + 1) + " ";
        }
        // System.out.println(formatted.substring(0, formatted.length() - 2));
        return formatted.substring(0, formatted.length() - 1);
    }

    /**
     * get Hamper Food method
     * @return
     */
    public String getHamperFood(){
        String frmtd = "";
        for(int i = 0; i < hamperFood.size(); i++){
            frmtd += "\n" + hamperFood.get(i).getFoodID() + "\t\t" + hamperFood.get(i).getName();
        }
        return frmtd;
    }

}