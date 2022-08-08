package edu.ucalgary.ensf409;

/**
* The following Client Class creates a Client based on one of 4 types (As presented in the SQL Database)
* Adult Male, Adult Female, Child Under 8, and Child Over 8
* Each Client type has a name (Describing which client they are of the 4)
* Each Client also as an ID (another identifier), and recommended daily nutritional values 
* (Protein, Grain, Other, Fruits and Veggies, and Calories)
* These values are also retrieved from the Database 
 */

public class Client {
    
    private String ClientType;
    private int ClientID;
    private int recommendedProtein;
    private int recommendedGrain;
    private int recommendedOther;
    private int recommendedFV;
    private int recommendedCalories;

/**
 * Constructor 
 * @param clientType String which contains the name of the Client Type (Out of the 4 types mentioned Above)
 * @param grain int which contains the total amount of Grain this client needs daily
 * @param FV int which contains the total amount of Fruits and Veggies this client needs daily
 * @param protein int which contains the total amount of Protein this client needs daily
 * @param other int which contains the total amount of Other nutrition this client needs daily
 * @param calories int which contains the total amount of Calories this client needs daily
 * Sets the client ID based on what client type was passed in the ctor
 */

public Client(String clientType, int grain, int FV, int protein, int other, int calories) {
    this.ClientType = clientType;
    this.recommendedGrain = grain;
    this.recommendedFV = FV;
    this.recommendedProtein = protein;
    this.recommendedOther = other;
    this.recommendedCalories = calories;

    // Set clientID
    if(clientType.equalsIgnoreCase("Adult Male")){ClientID = 1;}
    if(clientType.equalsIgnoreCase("Adult Female")){ClientID = 2;}
    if(clientType.equalsIgnoreCase("Child Over 8")){ClientID = 3;}
    if(clientType.equalsIgnoreCase("Child Under 8")){ClientID = 4;}
    
}

/**
 * Simple getter method 
 * @return int containing the Client ID
 */

public int getClientID(){

    return ClientID;
}

/**
 * Simple getter method 
 * @return String containing the Client Type 
 */
public String getClientType() {
    return this.ClientType;
}

/**
 * Simple getter method 
 * @return int containing the recommended/required protein content
 */
public int getProtein() {
    return this.recommendedProtein;
}

/**
 * Simple getter method 
 * @return int containing the recommended/required grain content
 */
public int getGrain() {
    return this.recommendedGrain;
}

/**
 * Simple getter method 
 * @return int containing the recommended/required Other content
 */
public int getOther() {
    return this.recommendedOther;
}

/**
 * Simple getter method 
 * @return int containing the recommended/required Fruits and Veggies content
 */
public int getFV() {
    return this.recommendedFV;
    
}

/**
 * Simple getter method 
 * @return int containing the recommended/required Calorie content
 */
public int getCalories() {
    return this.recommendedCalories;
}

/**
 * This method takes no parameters
 * @return a String containing all of the client details in a nice formatted way
 */
public String printClientDetails(){
    return "Type: " + ClientType + "\nClient ID: " + ClientID +"\nRecommended Grain: " + recommendedGrain + "\nRecommended Fruits and Veggies: " + recommendedFV + 
            "\nRecommended Protein: " + recommendedProtein +  "\nRecommended Other: " + recommendedOther + "\nRecommended Calories: " + recommendedCalories;
}

}
