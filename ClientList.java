package edu.ucalgary.ensf409;

import java.util.ArrayList;

/** 
* The following ClientList class creates an ArrayList of Client Types depending on what clients the user 
* wants, and how many of each client, this list also has a maximum limit (which is 10), and Users cannot 
* create a ClientList with a negative number of Clients  
*/

public class ClientList{
    private final int MAX_CAPACITY = 10;

    private Integer numOfAdMales = 0;
    private Integer numOfAdFemales = 0;
    private Integer numOfChOv8 = 0;
    private Integer numOfChUn8 = 0;

    private ArrayList<Client> listOfClients = new ArrayList<>();
    private ArrayList<Client> clientNeeds = new ArrayList<>();

    /**
        Takes the following parameters

        @param ClientID The ID associated with the type of client that is being added 
        @param numOfClientsToAdd The number of specific clients that are to be added

        And creates the specified Client, adds the specified number of that client to the list 
     */
    public void createClientToAdd(int ClientID, int numOfClientsToAdd){
        if(numOfClientsToAdd == 0){return;}

        if(numOfClientsToAdd < 0 || numOfClientsToAdd > MAX_CAPACITY){
            throw new IllegalArgumentException("Invalid Number of Clients provided. Please ensure that the total number of clients does not exceed 10.");
        }

        if(ClientID == 1){
            Client maleAd = SQLReader.possibleClients.getNeeds(0);
            Client toAdd = new Client(maleAd.getClientType(), maleAd.getGrain(), maleAd.getFV(), maleAd.getProtein(), maleAd.getOther(), maleAd.getCalories());
            for(int i = 0; i < numOfClientsToAdd; i++){
                addToList(toAdd);
            }
            numOfAdMales = numOfClientsToAdd;
        }
        
        if(ClientID == 2){
            Client femaleAd = SQLReader.possibleClients.getNeeds(1);
            Client toAdd = new Client(femaleAd.getClientType(), femaleAd.getGrain(), femaleAd.getFV(), femaleAd.getProtein(), femaleAd.getOther(), femaleAd.getCalories());
            for(int i = 0; i < numOfClientsToAdd; i++){
               addToList(toAdd);
            }
            numOfAdFemales = numOfClientsToAdd;
        }

        if(ClientID == 3){
            Client childOver8 = SQLReader.possibleClients.getNeeds(2);
            Client toAdd = new Client(childOver8.getClientType(), childOver8.getGrain(), childOver8.getFV(), childOver8.getProtein(), childOver8.getOther(), childOver8.getCalories());
            for(int i = 0; i < numOfClientsToAdd; i++){
               addToList(toAdd);
            }
            numOfChOv8 = numOfClientsToAdd;
        }

        if(ClientID == 4){
            Client childUnder8 = SQLReader.possibleClients.getNeeds(3);
            Client toAdd = new Client(childUnder8.getClientType(), childUnder8.getGrain(), childUnder8.getFV(), childUnder8.getProtein(), childUnder8.getOther(), childUnder8.getCalories());
            for(int i = 0; i < numOfClientsToAdd; i++){
               addToList(toAdd);
            }
            numOfChUn8 = numOfClientsToAdd;
        }
    }

    /**
     * The Following method takes in param
     * @param clientToAdd variable of type Client (has all attributes that a Client object should have)
     * and adds it to the Existing client list
     */

    public void addToList(Client clientToAdd){
        listOfClients.add(clientToAdd);
    }
    public void addToClientNeeds(Client clientToAdd){
        clientNeeds.add(clientToAdd);
    }

    /**
     * Takes the param 
     * @param index To determine what index of the Client List to access
     * and 
     * @return a Type Client corresponding to said index in the Client List 
     */

    public Client get(int index){
        return listOfClients.get(index);
    }
    public Client getNeeds(int index) {
        return clientNeeds.get(index);
    }
    

    /**
     * Takes no parameters, but uses the Client List to calculate the total required grain for the entire List (Family)
     * @return an integer value containing the total required grain for the week
     */

    public int getTotalRecommendedGrainForWeek(){
        Client[] possibleClients = {SQLReader.possibleClients.getNeeds(0), SQLReader.possibleClients.getNeeds(1), 
                                SQLReader.possibleClients.getNeeds(2), SQLReader.possibleClients.getNeeds(3)};
                                
        int testing = 7*((numOfAdMales * possibleClients[0].getGrain()) + (numOfAdFemales * possibleClients[1].getGrain()) + 
                        (numOfChOv8 * possibleClients[2].getGrain()) + (numOfChUn8 * possibleClients[3].getGrain()));

                        return testing;
    }
    

    /**
     * Takes no parameters, but uses the Client List to calculate the total required grain for the entire List (Family)
     * @return an integer value containing the total required grain for the week
     */

    public int getTotalRecommendedFVForWeek(){
        Client[] possibleClients = {SQLReader.possibleClients.getNeeds(0), SQLReader.possibleClients.getNeeds(1), 
            SQLReader.possibleClients.getNeeds(2), SQLReader.possibleClients.getNeeds(3)};
            
        int testing =  7*((numOfAdMales * possibleClients[0].getFV()) + (numOfAdFemales * possibleClients[1].getFV()) + 
            (numOfChOv8 * possibleClients[2].getFV()) + (numOfChUn8 * possibleClients[3].getFV()));
        return testing;
    }


    /**
     * Takes no parameters, but uses the Client List to calculate the total required Protein for the entire List (Family)
     * @return an integer value containing the total required gProtein r for the week
     */

    public int getTotalRecommendedProteinForWeek(){
        Client[] possibleClients = {SQLReader.possibleClients.getNeeds(0), SQLReader.possibleClients.getNeeds(1), 
            SQLReader.possibleClients.getNeeds(2), SQLReader.possibleClients.getNeeds(3)};
            
        int testing =  7*((numOfAdMales * possibleClients[0].getProtein()) + (numOfAdFemales * possibleClients[1].getProtein()) + 
            (numOfChOv8 * possibleClients[2].getProtein()) + (numOfChUn8 * possibleClients[3].getProtein()));
        return testing;

    }


    /**
     * Takes no parameters, but uses the Client List to calculate the total required Other content for the entire List (Family)
     * @return an integer value containing the total required Other content for the week
     */

    public int getTotalRecommendedOtherForWeek(){
        Client[] possibleClients = {SQLReader.possibleClients.getNeeds(0), SQLReader.possibleClients.getNeeds(1), 
            SQLReader.possibleClients.getNeeds(2), SQLReader.possibleClients.getNeeds(3)};
            
        return 7*((numOfAdMales * possibleClients[0].getOther()) + (numOfAdFemales * possibleClients[1].getOther()) + 
            (numOfChOv8 * possibleClients[2].getOther()) + (numOfChUn8 * possibleClients[3].getOther()));
    }


    /**
     * Takes no parameters, but uses the Client List to calculate the total required Calories for the entire List (Family)
     * @return an integer value containing the total required Calories for the week
     */

    public int getTotalRecommendedCaloriesForWeek(){
        Client[] possibleClients = {SQLReader.possibleClients.getNeeds(0), SQLReader.possibleClients.getNeeds(1), 
            SQLReader.possibleClients.getNeeds(2), SQLReader.possibleClients.getNeeds(3)};
            
        return 7*((numOfAdMales * possibleClients[0].getCalories()) + (numOfAdFemales * possibleClients[1].getCalories()) + 
            (numOfChOv8 * possibleClients[2].getCalories()) + (numOfChUn8 * possibleClients[3].getCalories()));
    }


    /**
     * Takes no params
     * @return the size of the list of Clients (i.e. how many Clients there are total in this List)
     */

    public int size(){
        return listOfClients.size();
    }


    /**
     * @param id The id corresponding to a specific Client type 
     * @return a String containing the number of Clients in the List corresponding to t
     */

    public String numOf(int id){
        if(id != 1 && id != 2 && id != 3 && id != 4){throw new IllegalArgumentException();}
        if(id == 1){return numOfAdMales.toString();}
        else if(id == 2){return numOfAdFemales.toString();}
        else if(id == 3){return numOfChOv8.toString();}
        else{return numOfChUn8.toString();}
    }
    public String GetClientType(int id){
        if(id != 1 && id != 2 && id != 3 && id != 4){throw new IllegalArgumentException();}
        if(id == 1){return numOfAdMales + " Adult Male,";}
        else if(id == 2){return numOfAdFemales + " Adult Female,";}
        else if(id == 3){return numOfChOv8 + " Child Over 8,";}
        else{return numOfChUn8 + " Child Under 8";}
    }

    /**
     * Simply clears the Client List (to use for the next Hamper)
     */
     
    public void ClearClients(){
        listOfClients.clear();
        numOfAdMales = 0;
        numOfAdFemales = 0;
        numOfChOv8 = 0;
        numOfChUn8 = 0;
    }

}