/**
 * Represents a parking spot with specific details such as spot ID and occupy.
 *
 * @author: Chaeyeon Im(104532390)
 * @version 1.0 (8.Apr.2024)
 *          1.1 (12.Apr.2024)
 *          1.2 (13.Apr.2024)
 *          2.3 (17.May.2024)
 */
public class ParkingSpot {
    // Encapsulated variables for the slot's ID, occupy. Used to store parking slot information.
    private String spotID;
    private String occupy;

    /**
     * Constructor for the ParkingSpot class.
     *
     * @param spotID The spot ID of the parking spot, provided as a String.
     * @param occupy The occupancy state of the parking spot, provided as a String.
     */
    public ParkingSpot(String spotID, String occupy){
        this.spotID = spotID;
        this.occupy = occupy;
    }

    /**
     * Gets the spot ID of the parking spot.
     *
     * @return The spot ID of the parking spot as a String.
     */
    public String getSpotID(){
        return spotID;
    }

    /**
     * Gets the occupancy state of the parking spot.
     *
     * @return The occupancy state of the parking spot as a String.
     */
    public String getOccupy(){
        return occupy;
    }

    /**
     * Sets the occupancy state of the parking spot.
     *
     * @param changedOccupy The new occupancy state of the parking spot, provided as a String.
     */
    public void setOccupy(String changedOccupy) {
        this.occupy = changedOccupy;
    }

    /**
     * Overrides the default toString method to display the spot ID and occupancy state of the parking spot.
     *
     * @return A string representation of the parking spot, including spot ID and occupancy.
     */
    public String toString() {
        return "Slot ID: "+spotID+" "+occupy;
    }
}
