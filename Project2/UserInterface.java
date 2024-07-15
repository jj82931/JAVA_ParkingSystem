/**
 * @purpose
 * To help manage vehicle inventory by creating a parking management GUI system for a used car sales company.
 * The parking management system can create or delete parking slots, and park cars in these slots.
 * Additionally, it allows searching for parked cars by registration number or car make, listing all parked cars,
 * This class is responsible for handling actions related to the GUI.
 * It displays buttons with various functionalities within the appropriate frame and shows parking slots and parked cars in the GUI.
 * It can receive user input and display appropriate GUI messages box to the user.
 *
 * @author: Chaeyeon Im(104532390)
 * @version 2.0 (12.May.2024)
 *          2.1 (15.May.2024)
 *          2.2 (16.May.2024)
 *          2.3 (17.May.2024)
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class UserInterface {

    static JFrame frame; // Declare JFrame type for GUI
    static JPanel parkPanel; // Declare parkPanel at the class level
    static GridBagConstraints gbc; // Declare gbc as a global variable
    static JButton[] parkingSpots = new JButton[12]; // 3x4 parking slot button array

    // Create a reference variable that can format using Java's built-in DateTimeFormatter and the specified pattern. Example 2024/04/14 15:30:22.
    static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
    public static final Color parkedColor = new Color(191,227,171); // Color instance for parked slot color

    public static void main(String[] args) {

        frame = new JFrame("Parking Spot"); // Create a JFrame object and set the title
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the correct close operation
        frame.setSize(1280, 720); // Set HD frame size
        frame.setLayout(new GridBagLayout()); // Set layout as a GridBagLayout
        frame.getContentPane().setBackground(Color.black);

        GridBagConstraints maingbc = new GridBagConstraints(); // Constraints for the button panel and parking slot panels
        maingbc.fill = GridBagConstraints.BOTH; // Set fill to both horizontally and vertically
        maingbc.weightx = 1.0;
        maingbc.weighty = 1.0;


        JPanel buttonPanel = ButtonPanel();
        maingbc.gridx = 0; // Set the grid x position to the first column
        maingbc.gridy = 0; // Set the grid y position to the first column
        maingbc.weightx = 0.1; // Set the width ratio for the button panel
        frame.add(buttonPanel, maingbc);

        menuGUI(); // Create GUI for menu
        maingbc.gridx = 1; // Set the grid x position to the first column
        maingbc.weightx = 0.9; // Set the grid y position to the first column
        frame.add(parkPanel, maingbc);
        frame.setVisible(true); // Make the frame visible
    }

    /**
     * Method for creating the GUI of the menu buttons on the left.
     *
     * @return Returns a JPanel object with the menu buttons added to the left
     */
    private static JPanel ButtonPanel() {
        JPanel panel = new JPanel(new GridBagLayout()); // Panel layout for the menu buttons on the left
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridwidth = GridBagConstraints.REMAINDER; // Fill the row with buttons without any empty space to make it look pretty
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        gc.weighty = 1;

        // Each menu action is created using separate methods for maintainability and readability.
        JButton buttonAddParking = getButtonAddParking();
        panel.add(buttonAddParking, gc);
        JButton buttonDeleteParking = getButtonDeleteParking();
        panel.add(buttonDeleteParking, gc);
        JButton buttonListParking = getButtonListParking();
        panel.add(buttonListParking, gc);
        JButton buttonParkCar = getButtonParkCar();
        panel.add(buttonParkCar, gc);
        JButton buttonFindReg = getButtonFindReg();
        panel.add(buttonFindReg, gc);
        JButton buttonRemoveReg = getButtonRemoveReg();
        panel.add(buttonRemoveReg, gc);
        JButton buttonFindMake = getButtonFindMake();
        panel.add(buttonFindMake, gc);
        JButton buttonReset = getButtonReset();
        panel.add(buttonReset, gc);
        JButton buttonExit = getButtonExit();
        panel.add(buttonExit, gc);

        return panel;
    }

    /**
     * Method to create the 'Add a parking spot' button and set its action listener.
     *
     * @return Returns the button object
     */
    private static JButton getButtonAddParking() {
        JButton buttonAddParking = new JButton("1. Add a parking spot");
        buttonAddParking.setBackground(Color.darkGray); // Set the button background color
        buttonAddParking.setForeground(Color.white); // Set the button text color

        buttonAddParking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String slotID = JOptionPane.showInputDialog(frame, "Enter Slot ID"); // String variable to store user input
                String msg = CarPark.addSlot(slotID); // String variable to store the return value of the method
                if(msg.equals("Parking slot added successfully.")){ // If CarPark's addSlot method works correctly, it returns "Parking slot added successfully."
                    addSlotPanel(slotID); // Method to update the GUI
                }
                JOptionPane.showMessageDialog(frame, msg); // Show the value of msg to the user by calling the showMessageDialog method
            }
        });
        return buttonAddParking;
    }

    /**
     * Method to create the 'Delete a parking spot' button and set its action listener.
     *
     * @return Returns the button object
     */
    private static JButton getButtonDeleteParking() {
        JButton buttonDeleteParking = new JButton("2. Delete a parking spot");
        buttonDeleteParking.setBackground(Color.darkGray); // Set the button background color
        buttonDeleteParking.setForeground(Color.white); // Set the button text color

        buttonDeleteParking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String slotID = JOptionPane.showInputDialog(frame, "Enter Slot ID: ");
                String msg = CarPark.deleteSlot(slotID);
                if(msg.equals("Parking slot deleted successfully.")){ // If CarPark's deleteSlot method works correctly, it returns "Parking slot deleted successfully."
                    deleteSlotPanel(slotID);
                }
                JOptionPane.showMessageDialog(frame, msg);
            }
        });
        return buttonDeleteParking;
    }

    /**
     * Method to create the 'List all parking spots' button and set its action listener.
     *
     * @return Returns the button object
     */
    private static JButton getButtonListParking() {
        JButton buttonListParking = new JButton("3. List all parking spots");
        buttonListParking.setBackground(Color.darkGray); // Set the button background color
        buttonListParking.setForeground(Color.white); // Set the button text color

        buttonListParking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = CarPark.showSlots();
                JOptionPane.showMessageDialog(frame, msg);
            }
        });
        return buttonListParking;
    }

    /**
     * Method to create the 'Park a car' button and set its action listener.
     *
     * @return Returns the button object
     */
    private static JButton getButtonParkCar() {
        JButton buttonParkCar = new JButton("4. Park a car");
        buttonParkCar.setBackground(Color.darkGray); // Set the button background color
        buttonParkCar.setForeground(Color.white); // Set the button text color

        buttonParkCar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String slotID = JOptionPane.showInputDialog(frame, "What's the slot ID?");
                String carReg = JOptionPane.showInputDialog(frame, "What's the car registration number?");
                String carMake = JOptionPane.showInputDialog(frame, "What's the car make?");
                String carModel = JOptionPane.showInputDialog(frame, "What's the car model?");
                String carYear = JOptionPane.showInputDialog(frame, "What's the car Year?");
                LocalDateTime time = LocalDateTime.now();
                
            
                // If the user clicks cancel in the showInputDialog, it returns null, so display the corresponding message.
                if(slotID == null || carReg == null || carMake == null || carModel == null || carYear == null ||
                        slotID.isEmpty() || carReg.isEmpty() || carMake.isEmpty()  || carModel.isEmpty()  || carYear.isEmpty()){
                    JOptionPane.showMessageDialog(frame, "You canceled or input value was empty. Back to main menu.");
                }else{
                    String msg = CarPark.parkCar(slotID, carReg, carMake, carModel,Integer.parseInt(carYear));
                    JOptionPane.showMessageDialog(frame, msg);
                    if(msg.contains("The car parked successfully.")){ // If CarPark's parkCar method works correctly, it returns "The car parked successfully."
                        carParkPanel(slotID, carReg, carMake, carModel, carYear, time); // Method to update the GUI
                    }
                }
            }
        });
        return buttonParkCar;
    }

    /**
     * Method to create the 'Find car by registration number' button and set its action listener.
     *
     * @return Returns the button object
     */
    private static JButton getButtonFindReg() {
        JButton buttonFindReg = new JButton("5. Find car by registration number");
        buttonFindReg.setBackground(Color.darkGray); // Set the button background color
        buttonFindReg.setForeground(Color.white); // Set the button text color

        buttonFindReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String carReg = JOptionPane.showInputDialog(frame, "What's the car registration number?");
                String msg = CarPark.FindReg(carReg);
                JOptionPane.showMessageDialog(frame, msg);
            }
        });
        return buttonFindReg;
    }

    /**
     * Method to create the 'Remove car by registration number' button and set its action listener.
     *
     * @return Returns the button object
     */
    private static JButton getButtonRemoveReg() {
        JButton buttonRemoveReg = new JButton("6. Remove car by registration number");
        buttonRemoveReg.setBackground(Color.darkGray); // Set the button background color
        buttonRemoveReg.setForeground(Color.white); // Set the button text color

        buttonRemoveReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String carReg = JOptionPane.showInputDialog(frame, "What's the car registration number?");
                String msg = CarPark.RemoveCar(carReg);
                if (msg.equals("The car removed successfully.")){ // If CarPark's RemoveCar method works correctly, it returns "The car removed successfully."
                    carRemovePanel(carReg); // Method to update the GUI
                }
                JOptionPane.showMessageDialog(frame, msg);
            }
        });
        return buttonRemoveReg;
    }

    /**
     * Method to create the 'Find cars by make' button and set its action listener.
     *
     * @return Returns the button object
     */
    private static JButton getButtonFindMake() {
        JButton buttonFindMake = new JButton("7. Find cars by make");
        buttonFindMake.setBackground(Color.darkGray); // Set the button background color
        buttonFindMake.setForeground(Color.white); // Set the button text color

        buttonFindMake.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String carMake = JOptionPane.showInputDialog(frame, "What's the car make?");
                String msg = CarPark.FindMake(carMake);
                JOptionPane.showMessageDialog(frame, msg);
            }
        });
        return buttonFindMake;
    }

    /**
     * Method to create the 'Reset car park' button and set its action listener.
     *
     * @return Returns the button object
     */
    private static JButton getButtonReset() {
        JButton buttonReset = new JButton("8. Reset car park");
        buttonReset.setBackground(Color.darkGray); // Set the button background color
        buttonReset.setForeground(Color.white); // Set the button text color

        buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = null;
                int confirmFlag = JOptionPane.showConfirmDialog(frame, "Are you sure reset all?");

                if(confirmFlag == JOptionPane.YES_OPTION){ // If the user clicks Yes in the showConfirmDialog, update the GUI to reset all car parks.
                    msg = CarPark.ResetSlots();
                    carReset();
                }
                else if (confirmFlag == JOptionPane.NO_OPTION || confirmFlag == JOptionPane.CANCEL_OPTION) {
                    msg = "Canceled. Back to main menu";
                }
                JOptionPane.showMessageDialog(frame, msg);
            }
        });
        return buttonReset;
    }

    /**
     * Method to create the 'Exit' button and set its action listener.
     *
     * @return Returns the button object
     */
    private static JButton getButtonExit() {
        JButton buttonExit = new JButton("9. Exit");
        buttonExit.setBackground(Color.darkGray); // Set the button background color
        buttonExit.setForeground(Color.white); // Set the button text color

        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = null;
                int confirmFlag = JOptionPane.showConfirmDialog(frame, "Are you sure end program?");

                if(confirmFlag == JOptionPane.YES_OPTION){
                    JOptionPane.showMessageDialog(frame, "Program ends. Bye Bye");
                    System.exit(0); // Terminate system

                }
                else if (confirmFlag == JOptionPane.NO_OPTION || confirmFlag == JOptionPane.CANCEL_OPTION) {
                    msg = "Canceled. Back to main menu";
                }
                JOptionPane.showMessageDialog(frame, msg);
            }
        });
        return buttonExit;
    }

    /**
     * Method to initialize the parking slot button and its layout.
     */
    private static void menuGUI() {
        parkPanel = new JPanel(new GridBagLayout());
        parkPanel.setBackground(Color.white); // Set the background color of the parking panel to white
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // Components will expand to fill both horizontal and vertical space
        gbc.insets = new Insets(5, 5, 5, 5); // Set margins between components
        gbc.weightx = 1;
        gbc.weighty = 1;
        Dimension buttonSize = new Dimension(100, 100); // Predefine button size

        for (int i = 0; i < 12; i++) {
            parkingSpots[i] = new JButton();  // Create parking slot button
            parkingSpots[i].setEnabled(false); // Initially disable the buttons
            parkingSpots[i].setBackground(Color.white); // Set background color of button
            parkingSpots[i].setBorder(null); // Disable border of button
            parkingSpots[i].setPreferredSize(buttonSize); // Set button size
            gbc.gridx = i % 4; // It cycles through values 0, 1, 2, and 3 as integer i increments, ensuring that each row has up to 4 columns.
            gbc.gridy = i / 4; // It increases the row number after every 4 buttons, thus creating a new row, after filling 4 columns. This way, it achieves a 3x4 grid layout
            parkPanel.add(parkingSpots[i], gbc);
        }
    }

    /**
     * Method to add a parking slot button to the GUI when the user adds a slot.
     *
     * @param slotID The slot ID of the parking spot, provided as a String.
     */
    private static void addSlotPanel(String slotID) {

        for (JButton parkingSpot : parkingSpots) { // Find a disabled parking spot using a for-each loop
            if (!parkingSpot.isEnabled()) {
                parkingSpot.setText(slotID);  // add text the slot ID provided by the user
                parkingSpot.setEnabled(true); // Enable the button
                parkingSpot.setVerticalAlignment(SwingConstants.TOP); // Align the slot ID text to the top
                parkingSpot.setBackground(Color.lightGray); // Change the button color
                break;  // Exit the loop as an empty slot has been found
            }
        }
        parkPanel.revalidate();
        parkPanel.repaint();
    }

    /**
     * Method to remove a parking slot button from the GUI when the user deletes a slot.
     *
     * @param slotID The slot ID of the parking spot, provided as a String.
     */
    private static void deleteSlotPanel(String slotID) {

        for (JButton parkingSpot : parkingSpots) {
            if (parkingSpot.getText().equals(slotID)) {
                parkingSpot.setText("");            // delete text the slot ID
                parkingSpot.setEnabled(false);     // Disable the button
                parkingSpot.setBackground(Color.white); // Change the button color to the default color
                break;
            }
        }
        parkPanel.revalidate();
        parkPanel.repaint();
    }

    /**
     * Method to add the car information and image to the specified slot in the GUI as entered by the user.
     *
     * @param slotID The Slot ID as a String, provided by user input in the menu.
     * @param carReg The car registration number entered by the user in the menu. It is of type String.
     * @param carMake The car make entered by the user in the menu. It is of type String.
     * @param carModel The car model entered by the user in the menu. It is of type String.
     * @param carYear The car year entered by the user in the menu. It is of type String.
     * @param time The current time stored as a LocalDateTime variable.
     */
    private static void carParkPanel(String slotID, String carReg, String carMake, String carModel, String carYear, LocalDateTime time){
        // ImageIcon instance to load the image from the specified path to represent the car image.
        ImageIcon carImage = new ImageIcon(Objects.requireNonNull(UserInterface.class.getResource("car.png")));

        for (JButton parkingSpot : parkingSpots) {
            if (parkingSpot.getText().equals(slotID)){ // If the text in the parking spot matches the slot ID entered by the user, add car information and image to that slot.
                String setButtonText = "<html>" + slotID +
                        "<br/><br/>Registration: " + carReg +
                        "<br/>make: " + carMake +
                        "<br/>model: " + carModel +
                        "<br/>Year: " + carYear +
                        "<br/>Parking time: " + timeFormatter.format(time) +
                        "</html>";

                parkingSpot.setBackground(parkedColor);
                parkingSpot.setText(setButtonText);
                parkingSpot.setIcon(carImage); // Add car image
                break;
            }
        }
        parkPanel.revalidate();
        parkPanel.repaint();
    }

    /**
     * Method to remove the car information entered by the user from the parking slot button.
     *
     * @param carReg The car registration number entered by the user in the menu. It is of type String.
     */
    private static void carRemovePanel(String carReg) {
        for (JButton parkingSpot : parkingSpots){
            if (parkingSpot.getText().contains("Registration: " + carReg)) {
                String slotID = parkingSpot.getText().split("<br/>")[0].replace("<html>", "").trim();
                /* Code to extract the Slot ID from the button to be removed.
                The split() method is used to split the string by <br> tag and store it in an array.
                Then, the first(zero) index is selected. According to the carParkPanel() method, the Slot ID is displayed in the first line, so selecting the first(zero) index gives us the Slot ID.
                The selected string then has the <html> tag replaced with "", effectively removing the html tag.
                Finally, the trim() method is used to remove any leading or trailing whitespace, giving us the Slot ID. */

                parkingSpot.setText(slotID);  // Set the button text back to the slot ID
                parkingSpot.setBackground(Color.lightGray); // Change the color back to light gray
                parkingSpot.setIcon(null); // Remove car image
                break;
            }
        }
        parkPanel.revalidate();
        parkPanel.repaint();
    }

    /**
     * Method to reset the text and image of parked car slots and return the parking slot button to its original state.
     */
    private static void carReset() {
        for (JButton parkingSpot : parkingSpots){
            String slotID = parkingSpot.getText().split("<br/>")[0].replace("<html>", "").trim(); // Store the Slot ID in a string variable to display it back on the button parking slot

            // If the button slot contains car information, it means the slot is occupied, so calling setText(slotID) makes it look like the car is removed from the slot.
            if(parkingSpot.getText().contains("Registration:")){
                parkingSpot.setText(slotID);  // Set the button text back to the slot ID
                parkingSpot.setBackground(Color.lightGray); // Change the color back to light gray
                parkingSpot.setIcon(null); // Remove car image
            }
        }
        parkPanel.revalidate();
        parkPanel.repaint();
    }
}