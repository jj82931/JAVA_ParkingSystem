/**
 * Represents a car with specific details such as registration, make, model, and year.
 *
 * @author: Chaeyeon Im(104532390)
 * @version 1.0 (8.Apr.2024)
 *          1.1 (12.Apr.2024)
 *          1.2 (13.Apr.2024)
 *          2.3 (17.May.2024)
 */
import java.time.LocalDateTime;

public class Car {
    private String registration;
    private String make;
    private String model;
    private int year;
    LocalDateTime time; // Reference variable to store the time.
    String slotID; // Variable to store the slot ID where the car is parked.

    /**
     * Constructor for the Car class.
     *
     * @param registration The registration number of the car, provided as a String.
     * @param make The make of the car, provided as a String.
     * @param model The model of the car, provided as a String.
     * @param year The year of the car, provided as an integer.
     */
    public Car (String registration, String make, String model, int year){
        this.registration = registration;
        this.make = make;
        this.model = model;
        this.year = year;
    }

    /**
     * Gets the registration number of the car.
     *
     * @return The registration number of the car as a String.
     */
    public String getRegistration() {
        return registration;
    }

    /**
     * Gets the make of the car.
     *
     * @return The make of the car as a String.
     */
    public String getMake() {
        return make;
    }

    /**
     * Gets the model of the car.
     *
     * @return The model of the car as a String.
     */
    public String getModel() {
        return model;
    }

    /**
     * Gets the year of the car.
     *
     * @return The year of the car as an integer.
     */
    public int getYear() {
        return year;
    }

    /**
     * Gets the slot ID where the car is parked.
     *
     * @return The slot ID where the car is parked as a String.
     */
    public String getSlotID(){
        return slotID;
    }

    /**
     * Gets the time when the car was parked.
     *
     * @return The time when the car was parked as a LocalDateTime object.
     */
    public LocalDateTime getTime(){
        return time;
    }

    /**
     * Sets the slot ID where the car is parked.
     *
     * @param parkingID The slot ID where the car is parked, provided as a String.
     */
    public void SetParkingSlotID(String parkingID){
        this.slotID = parkingID;
    }

    /**
     * Sets the time when the car was parked.
     *
     * @param time The time when the car was parked, provided as a LocalDateTime object.
     */
    public void SetTime(LocalDateTime time) {
        this.time = time;
    }

    /**
     * Generates a string representation of the car's details.
     *
     * @return A string representation of the car's details.
     */
    public String ShowList() {
        return ", Car Registration: " + registration + ", Make: " + make;
    }
}
