package edu.ucalgary.ensf409;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.*;

import java.awt.event.*;
import java.io.IOException;
import java.awt.FlowLayout;

/**
 * The following GUIHamper class creates a GUI that takes in input from a user for number
 * of family members and validates their input by checking for invalid input.
 */

public class GUIHamper extends JFrame implements ActionListener, MouseListener{

    int adultM;
    int adultF;
    int over8;
    int under8;

    // The following are the ClientIDs (used to add family members)
    private int mID = 1;
    private int fID = 2;
    private int o8ID = 3;
    private int u8ID = 4; 

    private JLabel instructions;
    private JLabel amLabel;
    private JLabel afLabel;
    private JLabel o8Label;
    private JLabel u8Label;
    private JLabel numFamLabel;

    private JTextField amInput;
    private JTextField afInput;
    private JTextField o8Input;
    private JTextField u8Input;

    JFrame frame2 = new JFrame("Request new order");

    JButton submitInfo = new JButton("Submit");
    JButton yes = new JButton("Yes");
    JButton no = new JButton("No");

    public GUIHamper(){
        super("Hamper Order");
        setupGUI();
        setSize(500,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        
    }

    /**
     * Takes no parameters, method used for GUI layout and adding listners for user interation
     */
    public void setupGUI(){

        instructions = new JLabel("Please fill in the following information to create an appropriate hamper order for your family.");
        amLabel = new JLabel("# of adult males in the family:");
        afLabel = new JLabel("# of adult females in the family:");
        o8Label = new JLabel("# of children over 8 in the family:");
        u8Label = new JLabel("# of children under 8 in the family:");
        numFamLabel = new JLabel("Note: number of family members cannot exceed 10.");

        amInput = new JTextField("Please enter a number", 15);
        afInput = new JTextField("Please enter a number", 15);
        o8Input = new JTextField("Please enter a number", 15);
        u8Input = new JTextField("Please enter a number", 15);

        amInput.addMouseListener(this);
        afInput.addMouseListener(this);
        o8Input.addMouseListener(this);
        u8Input.addMouseListener(this);

        submitInfo.addActionListener(this);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());

        JPanel clientPanel = new JPanel();
        clientPanel.setLayout(new FlowLayout());

        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout());

        headerPanel.add(instructions);
        clientPanel.add(amLabel);
        clientPanel.add(amInput);
        clientPanel.add(afLabel);
        clientPanel.add(afInput);
        clientPanel.add(o8Label);
        clientPanel.add(o8Input);
        clientPanel.add(u8Label);
        clientPanel.add(u8Input);
        clientPanel.add(numFamLabel);
        submitPanel.add(submitInfo);

        this.add(headerPanel, BorderLayout.NORTH);
        this.add(clientPanel, BorderLayout.CENTER);
        this.add(submitPanel, BorderLayout.PAGE_END);
    }

    /**
     * Reads in text field and carries out instructions based on which button has been pressed.
     * @param event Once button is clicked, event is passed in automatically and executes
     * behaviour through actionPerformed.
     */
    public void actionPerformed(ActionEvent event){

        JButton actionSource = (JButton) event.getSource();
        if(actionSource.equals(submitInfo)){
            try{
                adultM = Integer.parseInt(amInput.getText());
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, " Input for number of adult males must be a number!");
            }
            try{
                adultF = Integer.parseInt(afInput.getText());
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, " Input for number of adult females must be a number!");
            }
            try{
                over8 = Integer.parseInt(o8Input.getText());
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, " Input for number of children over 8 must be a number!");
            }
            try{
                under8 = Integer.parseInt(u8Input.getText());
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, " Input for number of children under 8 must be a number!");
            }
            
            if(validateInput()){
                createFamily(adultM, adultF, over8, under8);
                try {
                    SQLReader.OnSubmission(this);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                frame2.setVisible(true);
                frame2.setSize(600,100);
                JLabel label = new JLabel("Hamper order successful! Would you like to request another hamper?");
                JPanel panel = new JPanel();
                yes.addActionListener(this);
                no.addActionListener(this);
                frame2.add(panel);
                panel.add(label);
                panel.add(yes);
                panel.add(no);
            }
        }else if (actionSource.equals(yes)){
            super.dispose();
            frame2.dispose();
            EventQueue.invokeLater(() -> {
                new GUIHamper().setVisible(true);        
            });

        }else if(actionSource.equals(no)){
            System.exit(0);
        }
    }

    /**
     * If a mouse event occurs, check source of event and set that specific text field to blank
     * @param event Once text field is clicked on by mouse, event is passed in automatically
     * and executes behavour thorough mouseClicked
     */
    public void mouseClicked(MouseEvent event){
        
        if(event.getSource().equals(amInput))
            amInput.setText("");

        if(event.getSource().equals(afInput))
            afInput.setText("");

        if(event.getSource().equals(o8Input))
            o8Input.setText("");

        if(event.getSource().equals(u8Input))
            u8Input.setText("");
                
    }

    /**
     * Methods for differnt mouse actions
     * Does nothing but need to have these methods as place holders due to interface
     */
    public void mouseEntered(MouseEvent event){
        
    }

    public void mouseExited(MouseEvent event){
        
    }

    public void mousePressed(MouseEvent event){
        
    }

    public void mouseReleased(MouseEvent event){
        
    }

    /**
     * Checks to see if user input is valid and shows user invalid input message.
     * E.g. user inputs a negative number.
     * @return true or false indicating whether user input is valid or not.
     */
    private boolean validateInput(){
        
        boolean allInputValid = true;

        if(adultM < 0 || adultM > 10){
            allInputValid = false;
            JOptionPane.showMessageDialog(this, adultM + " is an invalid input! Please enter a positive number from 0 - 10.");
        }

        if(adultF < 0 || adultF > 10){
            allInputValid = false;
            JOptionPane.showMessageDialog(this, adultF + " is an invalid input! Please enter a positive number from 0 - 10.");
        }

        if(over8 < 0 || over8 > 10){
            allInputValid = false;
            JOptionPane.showMessageDialog(this, over8 + " is an invalid input! Please enter a positive number from 0 - 10.");
        }

        if(under8 < 0 || under8 > 10){
            allInputValid = false;
            JOptionPane.showMessageDialog(this, under8 + " is an invalid input! Please enter a positive number from 0 - 10.");
        }

        if((adultM + adultF + over8 + under8) > 10 || (adultM + adultF + over8 + under8) < 1){
            allInputValid = false;
            JOptionPane.showMessageDialog(this,"The total number of family members cannot exceed 10.");
        }
        
        return allInputValid;
        
    }

    ClientList family = new ClientList();

    public void createFamily(int numAdultM, int numAdultF, int numOver8, int numUnder8){
        family.createClientToAdd(mID, numAdultM);
        family.createClientToAdd(fID, numAdultF);
        family.createClientToAdd(o8ID, numOver8);
        family.createClientToAdd(u8ID, numUnder8);
    }

    // public void invokeAndWait(Runnable event) {
    //     try {
    //       EventQueue.invokeAndWait(event);
    //     } catch (Exception e) {
    //       throw new RuntimeException(e);
    //     }
    //   }

    //   static Runnable testing = new Runnable() {
    //     public void run() {
    //         System.out.println("We are testing this shit" + Thread.currentThread());

    //     }
    //   };
      

    // public static void main(String[] args) {
        
    //     EventQueue.invokeLater(() -> {
    //         new GUIHamper().setVisible(true);        
    //     });
    // }
    
}
