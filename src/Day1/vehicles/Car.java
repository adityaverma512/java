package Day1.vehicles;

import java.util.logging.Logger;

/**
 * Car class that extends AbstractVehicle.
 * This represents a specific type of vehicle with its own unique implementations
 * of abstract methods and additional car-specific features.
 */
public class Car extends AbstractVehicles {

    // Logger to log the car operations
    private static final Logger LOGGER = Logger.getLogger(Car.class.getName());

    private final int numDoors;          // Number of doors the car has
    private final String transmissionType; // Type of transmission (automatic, manual, etc.)

    /**
     * Constructor for Car class.
     *
     * @param make The manufacturer of the car
     * @param model The model name of the car
     * @param year The year the car was manufactured
     * @param numDoors Number of doors the car has
     * @param transmissionType Type of transmission (automatic, manual, etc.)
     */
    public Car(String make, String model, int year, int numDoors, String transmissionType) {
        super(make, model, year);
        this.numDoors = numDoors;
        this.transmissionType = transmissionType;
        LOGGER.info("Car created: " + make + " " + model + " (" + transmissionType + " transmission, " + numDoors + "-door)");
    }

    /**
     * Accelerates the car by a specified amount. 
     *
     * @param amount The amount to accelerate by
     * @return A message indicating the car's new speed after accelerating.
     */
    @Override
    public String accelerate(int amount) {
        if (!isRunning()) {
            LOGGER.warning("Cannot accelerate: Engine is not running.");
            return "Cannot accelerate: Engine is not running.";
        }

        int currentSpeed = getCurrentSpeed();
        int newSpeed = Math.min(currentSpeed + amount, getMaxSpeed());
        setCurrentSpeed(newSpeed);

        LOGGER.info("Car accelerated: Current speed is " + newSpeed + " km/h.");
        return "The car accelerates to " + newSpeed + " km/h.";
    }

    /**
     * Applies the brakes to the car. 
     * Cars have a quick braking effect, reducing speed by up to 20 km/h.
     *
     * @return A message indicating the new speed after braking.
     */
    @Override
    public String brake() {
        int currentSpeed = getCurrentSpeed();

        if (currentSpeed == Constants.DEFAULT_SPEED) {
            LOGGER.info("Brake applied but car is already stopped.");
            return "The car is already stopped.";
        }

        // Calculate braking effect - cars can brake quickly
        int reduction = Math.min(currentSpeed, 20);
        setCurrentSpeed(currentSpeed - reduction);

        LOGGER.info("Car slowed down: Current speed is " + getCurrentSpeed() + " km/h.");
        return "The car slows down to " + getCurrentSpeed() + " km/h.";
    }

    /**
     * Returns the maximum speed of the car.
     *
     * @return The maximum speed of the car (e.g., 220 km/h)
     */
    @Override
    protected int getMaxSpeed() {
        // Log the max speed of the car
        LOGGER.info("Max speed for car is " + Constants.MAX_SPEED3 + " km/h.");
        return Constants.MAX_SPEED3; // Most cars have a max speed of about 220 km/h
    }

    /**
     * Returns the number of doors on the car.
     *
     * @return The number of doors the car has
     */
    public int getNumDoors() {
        LOGGER.info("Number of doors: " + numDoors);
        return numDoors;
    }

    /**
     * Returns the type of transmission in the car (automatic, manual, etc.)
     *
     * @return The transmission type of the car
     */
    public String getTransmissionType() {
        LOGGER.info("Transmission type: " + transmissionType);
        return transmissionType;
    }

    /**
     * Returns a string representation of the car, including its make, model, number of doors,
     * transmission type, and current speed.
     *
     * @return A string representation of the car
     */
    @Override
    public String toString() {
        return super.toString() + " | " + numDoors + "-door, " +
                transmissionType + " transmission";
    }
}
