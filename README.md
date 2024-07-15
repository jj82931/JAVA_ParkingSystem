# JAVA_ParkingSystem
Project Summary

A company for selling used cars (maximum 20 years old) has a car park site. A small system is required that will help manage used cars at a parking site for the company. It should has following four classes:

• Application class
• CarPark class
• ParkingSpot class
• Car class

Application is the Console (Text Based) Interface class including the main() method and handling all inputs and outputs. CarPark is responsible for maintaining a list of available parking spots. 
You should be able to add a spot, delete a spot, provide a list of all spots included in the car park, park a car, find / remove a car by registration number, find cars by the make and reset car park. 
A parking spot must have an identifier, which starts with a capital letter, followed by a three- digit number e.g. “D001”, “E127”. A parking spot should also know if a car is parked in the spot. 
You must be able to add a car to the spot and remove a car from the spot.A car will be identified by its registration number. A registration number always starts with a capital letter, followed by a four-digit number e.g. “T1234”. A car should also have its make, model, and year.

A. Component list

- JFrame: Part of the javax.swing package, it is used to create the main window for a application. In Project 2, a window frame with a size of 1280x720 (HD) was created. The layout was then set using GridBagLayout.
- GridBagLayout: This layout was used to precisely adjust the positions of the components.
- GridBagConstraints: Used to control the position, size, and margins of each component. In the code, the properties gridx, gridy, weightx, and weighty are used to impose constraints on the components.
- JButton: A clickable button used for the menu on the left and for creating parking slots.
- JOptionPane: A dialog box used to get input from the user or to display messages to the user.
- ImageIcon: Used to display a car image in the slot when a car is parked.


B.	Event handling list

- getButtonAddParking(): When the user clicks the "Add a parking spot" button, it prompts the user to enter a slot ID and adds a new parking slot. In the code calls the addSlotPanel(slotID) method to add the new parking slot to the GUI.-
- getButtonDeleteParking(): When the user clicks the "Delete a parking spot" button, it prompts the user to enter a slot ID and deletes the specified parking slot. Similar to the previous event handling, it calls the deleteSlotPanel(slotID) method to remove the specified parking slot from the GUI.
- getButtonListParking(): When the user clicks the "List all parking spots" button, it shows the number of occupied and empty parking slots through a JOptionPane component. It also displays the car information in the occupied slots.
- getButtonParkCar(): When the user clicks the "Park a car" button, a dialog box appears via the JOptionPane component where the user can enter the Slot ID, Registration number, Make, Model, and Year of the car they want to park. If entered correctly, the method carParkPanel(slotID, carReg, carMake, carModel, carYear, time) is called to add the car information to the GUI. This method uses the ImageIcon component to add a car image to the specified slot when the car is parked.
- getButtonFindReg(): When the user clicks the "Find car by registration number" button, a dialog box appears via the JOptionPane component. The user can enter the registration number they want to find, and a dialog box with the results appears.
- getButtonRemoveReg(): When the user clicks the "Remove car by registration number" button, a dialog box appears via the JOptionPane component. The user can enter the registration number they want to remove, and a dialog box with the results appears. Then, the method carRemovePanel(carReg) is called to remove the car with the specified registration number from the slot in the GUI. This method also removes the car image from the parking slot using the ImageIcon component.
- getButtonFindMake(): When the user clicks the "Find cars by make" button, a dialog box appears via the JOptionPane component where the user can enter the car make they want to find, and a dialog box with the results appears.
- getButtonReset(): When the user clicks the "Reset car park" button, it calls the carReset() method to remove all parked cars and update the GUI to show all slots as an empty slot.
- getButtonExit(): When the user clicks the "Exit" button, it calls the System.exit() method to terminate the current program.

