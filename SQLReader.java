package edu.ucalgary.ensf409;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.jar.Attributes.Name;

import javax.swing.SwingUtilities;

import java.awt.EventQueue;

public class SQLReader {
    static FoodInventory foodInv = new FoodInventory();
    static ClientList possibleClients = new ClientList();
    static OrderForm testForm = new OrderForm();
    static NutritionalFinder finder;
    public final String DBURL;
    public final String USERNAME;
    public final String PASSWORD;

    private static Connection dbConnect;
    private ResultSet results;

    public SQLReader(String url, String user, String pw) {

        // Database URL
        this.DBURL = url;

        // Database credentials
        this.USERNAME = user;
        this.PASSWORD = pw;
    }

    // Must create a connection to the database, no arguments, no return value
    public static Connection initializeConnection() throws ClassNotFoundException {
        try {

            // Class.forName("com.mysql.jdbc.Driver");
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/food_inventory", "root", "Nour22086001?");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbConnect;
    }

    String getDburl() {
        return this.DBURL;
    }

    String getUsername() {
        return this.USERNAME;
    }

    String getPassword() {
        return this.PASSWORD;
    }

    

    public static void delete(String Name) throws ClassNotFoundException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/food_inventory", "root", "Nour22086001?");
            PreparedStatement st = connection.prepareStatement("DELETE FROM AVAILABLE_FOOD WHERE Name = ?");
            st.setString(1,Name);
            st.executeUpdate(); 
         
    } catch(Exception e) {
        System.out.println(e);
    }
}
              
    public String selectAllFoods(String tableName) {

        // HashMap<Key, List of food attributes> foodList = new HashMap<>();

        StringBuffer names = new StringBuffer();
        try {
            int i = 1;
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM AVAILABLE_FOOD");
            while (results.next()) {
                FoodAttributes food = new FoodAttributes(i, results.getString("Name"), results.getInt("GrainContent"),
                        results.getInt("FVContent"), results.getInt("ProContent"), results.getInt("Other"),
                        results.getInt("Calories"));
                foodInv.addingToTheList(food);
                i++;

                //System.out.println("TEST ID: " + food.getFoodID());

            }

            myStmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return names.toString();
    }

    public void selectClients() {
        try {
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM DAILY_CLIENT_NEEDS");
            while (results.next()) {
                Client newClient = new Client(results.getString("Client"), results.getInt("WholeGrains"),
                        results.getInt("FruitVeggies"),
                        results.getInt("Protein"), results.getInt("Other"), results.getInt("Calories"));

                possibleClients.addToClientNeeds(newClient);
            }

            myStmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void close() {
        try {
            results.close();
            dbConnect.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {

        SQLReader myJDBC = new SQLReader("jdbc:mysql://localhost/food_inventory", "root", "Nour22086001?");
        myJDBC.initializeConnection();
        System.out.println(myJDBC.selectAllFoods("AVAILABLE_FOOD"));

        
        GUIHamper gui = new GUIHamper();
        EventQueue.invokeLater(() -> {
        new GUIHamper().setVisible(true);        
    });

        myJDBC.selectClients();
        foodInv.SetBase();
        foodInv.sortByNutrtion();
       
    }

    public static void OnSubmission(GUIHamper gui) throws IOException {
        Hamper hamperTest = new Hamper(gui.family);
        finder = new NutritionalFinder(foodInv);
        finder.ClientNutFinder(gui.family);
        
        for(int i = 0; i < finder.ToAdd.size(); i++){
            hamperTest.fillHamper(finder.ToAdd.get(i));
           
        }

        testForm.addHamper(hamperTest);
        testForm.writeToOutput();
        gui.family.ClearClients();
    }
}
