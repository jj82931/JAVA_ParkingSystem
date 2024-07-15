/**
 * Manages the operations of a car park system, including adding and deleting parking slots, parking and removing cars and reset all slots.
 * Additionally, parked vehicles can be searched by their registration number and make.
 * This class handles all user input coming from the GUI class, which is responsible for the user interface.
 *
 * @author: Chaeyeon Im(104532390)
 * @version 1.0 (8.Apr.2024)
 *          1.1 (11.Apr.2024)
 *          1.2 (12.Apr.2024)
 *          1.3 (14.Apr.2024)
 *          2.0 (12.May.2024)
 *          2.1 (15.May.2024)
 *          2.3 (17.May.2024)
 */

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CarPark {
    static ArrayList<ParkingSpot> slotsArray = new ArrayList<>(); // ArrayList to store parking slots.
    static ArrayList<Car> carsArray = new ArrayList<>(); // ArrayList to store cars.
    static ParkingSpot parkingSpot;
    static Car car;
    // Create a reference variable that can format using Java's built-in DateTimeFormatter and the specified pattern. Example 2024/04/14 15:30:22.
    static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");

    /**
     * Method to check if a given Slot ID exists.
     *
     * @param slotID The Slot ID as a String.
     * @return Returns a boolean. true if the slot ID exists, false otherwise.
     */
    public static boolean checkSlot(String slotID){
        for (ParkingSpot spot : slotsArray) { // For loop to iterate through the ArrayList to check if the user's entered slotID exists.
            if (spot.getSpotID().equals(slotID)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to check if a given car registration number is already parked.
     *
     * @param carReg The car registration number as a String.
     * @return Returns a boolean. true if the registration number is parked, false otherwise.
     */
    public static boolean checkParking(String carReg){
        for (Car car : carsArray) {
            if(car.getRegistration().equals(carReg)){
                return true;
            }
        }
        return false;
    }

    /**
     * Method to display the parking duration or the duration the car was parked.
     *
     * @param time Parameter to store the time when this method is called.
     * @param carTime Parameter to receive the time (start of parking) stored in the Car class.
     * @return The String variable msg is returned to convey the result or message from this method.
     */
    public static String TimeLength(LocalDateTime time, LocalDateTime carTime){
        String msg;
        Duration duration = Duration.between(carTime, time); // Create a Duration reference variable and calculate the time difference using the between() function.

        // Output the calculated time using Duration's built-in functions. Reference: https://mkyong.com/java/java-how-to-get-current-date-time-date-and-calender/
        msg = "Time Length : "+duration.toHours()+" hours "+duration.toMinutesPart()+" minutes and "+duration.toSecondsPart()+" seconds";
        return msg;
    }

    /**
     * Method to add a parking slot based on user-entered Slot ID. It checks for existence, duplication, and format of the Slot ID.
     *
     * @param slotID The Slot ID as a String, provided by user input in the Application class.
     * @return The String variable msg is returned to convey the result or message from this method.
     */
    public static String addSlot(String slotID) {
        String msg;
        if(slotID == null){
            msg = "Canceled. Back to main menu";
            return msg;
        }
        if(checkSlot(slotID)){ // If checkSlot method returns true, it means the user's entered slotID exists, hence an error message is displayed.
            msg = "Slot ID already exists.";
        }
        //Checks if the Slot ID matches the regular expression pattern, where it starts with an uppercase letter (A-Z) followed by exactly three digits (0-9).
        else if(slotID.matches("[A-Z]\\d{3}")){
            parkingSpot = new ParkingSpot(slotID, "Empty"); // Create a new ParkingSpot object with the given slotID marked as "Empty", indicating that the slot is available for parking.
            slotsArray.add(parkingSpot);
            msg = "Parking slot added successfully.";
        } else {
            msg = "Invalid Slot format.";
        }
        return msg;
    }

    /**
     * Method to remove a parking slot based on a user-entered Slot ID. It checks if the Slot ID exists and if any car is parked.
     *
     * @param slotID The Slot ID as a String, provided by user input in the Application class.
     * @return The String variable msg is returned to convey the result or message from this method.
     */
    public static String deleteSlot(String slotID){
        String msg;
        ParkingSpot spotToRemove = null; // A reference to the slot to remove

        for (ParkingSpot spot : slotsArray) { // For loop to iterate through the ArrayList to check if the user's entered slotID exists.
            if (spot.getSpotID().equals(slotID)) {
                if (spot.getOccupy().equals("Occupied")){ // If the slot is occupied, display this message.
                    return "Slot is occupied.";
                }
                spotToRemove = spot; // Store the spot to remove
                break;
            }
        }

        if (spotToRemove != null) {
            slotsArray.remove(spotToRemove);
            msg = "Parking slot deleted successfully.";
        } else {
            msg = "Slot does not exist.";
        }
        return msg;
    }

    /**
     * Displays all slots along with details of vehicles parked in them and the time information.
     * Also shows the total number of slots, and the count of available and unavailable slots.
     *
     * @return The StringBuilder's instance msg is returned to convey the result or message from this method.
     */
    public static String showSlots(){
        // Created a StringBuilder instance to convey messages or values to the UserInterface class, and appended messages or values to it.
        StringBuilder msg = new StringBuilder();

        int totalSlot, emptySlot=0, occupiedSlot=0; // Variables to show the total number of slots, and the count of occupied and unoccupied slots.
        LocalDateTime time = LocalDateTime.now(); // Create a LocalDateTime reference variable to store the current time.
        totalSlot = slotsArray.size(); // Use size() method on ArrayList to determine the number of parking slots.

        if(totalSlot == 0){ // If there are no slots, display this message.
            return "Please add parking slot first.";

        }

        msg.append("List of Parking Spots\n-----------------------------------\n");

        /* This for loop iterates over slotsArray and carsArray. It uses the getOccupy() getter method of ParkingSpot to retrieve the value,
         * and compares it with the string "Occupied" using the equals() method. If true, it means a car is parked, so increase occupiedSlot by one.
         * Then, to get the parked car information, it checks if the car's parking slot ID matches the parking slot ID from slotsArray.
         * If they match, it displays the parked car using the Car class's ShowList() method. */
        for (ParkingSpot spot : slotsArray) {
            if (spot.getOccupy().equals("Occupied")){
                occupiedSlot++;

                for(Car car : carsArray){
                    if(car.slotID.equals(spot.getSpotID())){
                        msg.append(spot.toString())
                                .append(car.ShowList())
                                .append("\n")
                                .append(TimeLength(time, car.getTime()))
                                .append("\n");
                    }
                }

            } else {
                emptySlot++; // If none of the conditions are met, the slot is unoccupied, so increment emptySlot.
                msg.append(spot.toString()).append("\n");
            }
        }
        msg.append("\n-----------------------------------\n")
                .append("Total slots: ").append(totalSlot)
                .append("\nOccupied slots: ").append(occupiedSlot)
                .append("\nUnoccupied slots: ").append(emptySlot);

        return msg.toString();
    }

    /**
     * Method to park a car in a slot. This method checks the slot, if the user-entered car is already parked, validates the car registration number format, and checks the car year.
     *
     * @param slotID The Slot ID as a String, provided by user input in the Application class.
     * @param carReg The car registration number entered by the user in the Application class. It is of type String.
     * @param carMake The car make entered by the user in the Application class. It is of type String.
     * @param carModel The car model entered by the user in the Application class. It is of type String.
     * @param carYear The car year entered by the user in the Application class. It is of type integer.
     * @return The String variable msg is returned to convey the result or message from this method.
     */
    public static String parkCar(String slotID, String carReg, String carMake, String carModel, int carYear){
        String msg="";
        LocalDateTime time; // Reference variable to store the current time

        if(!checkSlot(slotID)){ // Check if the slot exists by calling the checkSlot() method.
            msg = "Slot does not exist.";
            return msg;
        } else if (checkParking(carReg)) { // Check if the entered car's registration number is already parked by calling the checkParking() method.
            msg = "Car is already parked.";
            return msg;
        }
        else if(!carReg.matches("[A-Z]\\d{4}")){ // Validate the registration number format using Regular Expression.
            msg = "Invalid car registration format.";
            return msg;
        }
        else if(carYear < 2004 || carYear > 2024){
            msg = "Invalid car year format. Allows only between 2004~2024.\n";
            return msg;
        } else {
            for (ParkingSpot spot : slotsArray) {
                if (spot.getSpotID().equals(slotID)) {
                    // Check if the slot is occupied by another car.
                    if (spot.getOccupy().equals("Occupied")) {
                        msg = "Slot is occupied by another car.";

                    } else {
                        spot.setOccupy("Occupied"); // If the slot is empty, set it to "Occupied" using the ParkingSpot class's setter method.
                        car = new Car(carReg, carMake, carModel, carYear); // Create a car object.
                        car.SetParkingSlotID(slotID); // Store the slotID in the car object using the SetParkingSlotID() method of the Car class.
                        carsArray.add(car);

                        time = LocalDateTime.now(); // Get the current time and store.
                        car.SetTime(time); // Call the SetTime() method of the Car class to store the current time indicating when the car was parked.

                        msg = "The car parked successfully."+
                                "\nCurrent time : " + timeFormatter.format(time);
                    }
                    break; // If the slot is occupied, display the message and exit from loop.
                }
            }
        }
        return msg;
    }

    /**
     * Method to remove a car parked in a slot.
     *
     * @param carReg The car registration number entered by the user in the Application class. It is of type String.
     * @return The String variable msg is returned to convey the result or message from this method.
     */
    public static String RemoveCar(String carReg){
        Car removeCar = null; // Temporary reference variable created to remove the registration number entered by the user from the slot.
        String msg;

        for (Car car : carsArray) {
            if (car.getRegistration().equals(carReg)) {
                removeCar = car; // The car object corresponding to the registration number entered by the user is stored in the temporary reference variable.
                break;
            }
        }
        if(removeCar != null){ // If not null, if the registration number entered by the user exists.
            for (ParkingSpot spot : slotsArray) {
                if (spot.getSpotID().equals(removeCar.getSlotID())) {
                    spot.setOccupy("Empty"); // if the spot's slot ID matches the slot ID in the current Car object, the setter method is used to change it to "empty" and the loop exits.
                    break;
                }
            }
            carsArray.remove(removeCar); // Then, the registration number is removed from the ArrayList.
            msg = "The car removed successfully.";

        } else {
            msg = "Car not found.";
        }
        return msg;
    }

    /**
     * Method to find a car parked by its registration number.
     *
     * @param carReg The car registration number entered by the user in the Application class. It is of type String.
     * @return msg String variable to convey the results, messages, and errors of methods in the UserInterface class.
     */
    public static String FindReg(String carReg){
        boolean flag = false; // Variable to determine if the car is found, initialized to false.
        String msg = null;
        
        for (Car car : carsArray) {
            if (car.getRegistration().equals(carReg)) {
                msg = "Car found at Slot ID: " + car.getSlotID();
                flag = true;
                break;
            }
        }
        if (!flag) {
            msg = "Car not found.";
        }
        return msg;
    }

    /**
     * Method to find cars parked by car make. This method displays information about the car and the parking time.
     *
     * @param carMake The car make entered by the user in the Application class. It is of type String.
     * @return The StringBuilder's instance msg is returned to convey the result or message from this method.
     */
    public static String FindMake(String carMake){
        // Created a StringBuilder instance to convey messages or values to the UserInterface class, and appended messages or values to it.
        StringBuilder msg = new StringBuilder();
        boolean flag = false; // Variable to determine if the car is found, initialized to false.
        LocalDateTime time = LocalDateTime.now(); // Store the current time.

        for (Car car : carsArray) {
            if (car.getMake().equals(carMake)) {
                msg.append("Car found.\nSlot ID: ").append(car.getSlotID())
                        .append("\nRegistration: ").append(car.getRegistration())
                        .append("\nMake: ").append(car.getMake())
                        .append("\nModel: ").append(car.getModel())
                        .append("\nYear: ").append(car.getYear())
                        .append("\n")
                        .append(TimeLength(time, car.getTime())) // Call the TimeLength() method to display the parking time.
                        .append("\n-------------------------------------------------\n");
                flag = true;
            }
        }
        if (!flag) {
            msg.append("Car not found.");
        }
        return msg.toString();
    }

    /**
     * Method to reset all slots.
     *
     * @return msg String variable to convey the results, messages, and errors of methods in the UserInterface class.
     */
    public static String ResetSlots(){
        String msg;

        for (ParkingSpot spot : slotsArray) { // All slots are set to "Empty" using the setter method with a for-each loop.
            spot.setOccupy("Empty");
        }
        carsArray.clear(); // The clear() method removes all attributes of the carsArray.
        msg = "All cars removed from all parking slots.";
        return msg;
    }
}