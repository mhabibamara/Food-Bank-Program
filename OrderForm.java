package edu.ucalgary.ensf409;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class OrderForm{
    private ArrayList<Hamper> listOfHampers = new ArrayList<Hamper>();
    static int numberOfhampers;
    private int increment = 0;
    File output = new File("orderform.txt");

    public void addHamper(Hamper hamper){
        numberOfhampers++;
        listOfHampers.clear();
        listOfHampers.add(hamper);
    }

    public Hamper getHamper(int index){
        return listOfHampers.get(index);
    }

    public void writeToOutput() throws IOException{
        try {
            File output = new File("output.txt");
            FileWriter writer = new FileWriter(output, true);
            writer.write("Example Food Bank\nHamper Order Form\n\nName:\nDate:\n\nOriginal Request\n");
            
            writer.write("Hamper " + numberOfhampers + ": " + listOfHampers.get(0).getClientTypes());

            writer.write("\n\nHamper " + numberOfhampers + " Items: ");
            writer.write(listOfHampers.get(0).getHamperFood());

            // for(int i = 0; i < listOfHampers.size(); i++){
            //     writer.write("Hamper " + i + ": " + listOfHampers.get(i).getClientTypes());
            // }

            // for(int i = 0; i < listOfHampers.size(); i++){
            //     writer.write("\n\nHamper " + i + " Items: ");
            //     writer.write(listOfHampers.get(i).getHamperFood());
            // }

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException();
        }
    }
    
}