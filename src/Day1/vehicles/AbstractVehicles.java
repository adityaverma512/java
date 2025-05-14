package Day1.vehicles;

import java.util.logging.Logger;

/**
 * AbstractVehicles provides a partial implementation of the {@link Vehicles} interface.
 * <p>
 * This abstract class encapsulates shared vehicle data and behaviors such as make, model, year,
 * engine state, and current speed. It defines base logic for starting and stopping a vehicle,
 * while deferring vehicle-specific behavior (e.g., maximum speed, acceleration) to concrete subclasses.
 * </p>
 */
public abstract class AbstractVehicles implements Vehicles {

    // Logger for logging vehicle actions
    private static final Logger LOGGER = Logger.getLogger(AbstractVehicles.class.getName());

    // Encapsulated fields to protect internal state
    private String make;
    private String model;
    private int year;
    private int currentSpeed;
    private boolean isRunning;

    /**
     * Constructs a vehicle with basic identifying information.
     *
     * @param make  The manufacturer of the vehicle
     * @param model The model of the vehicle
     * @param year  The year the vehicle was manufactured
     */
    public AbstractVehicles(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.currentSpeed = Constants.DEFAULT_SPEED;
        this.isRunning = false;
    }

    /**
     * Start the vehicle if it is not already running.
     *
     * @return A message indicating the vehicle has started or is already running
     */
    @Override
    public String start() {
        if (!isRunning) {
            isRunning = true;
            LOGGER.info("Vehicle started: " + make + " " + model);
            return "The " + make + " " + model + " has started.";
        } else {
            LOGGER.warning("Attempted to start already running vehicle: " + make + " " + model);
            return "The vehicle is already running.";
        }
    }

    /**
     * Stop the vehicle and reset its speed to 0.
     *
     * @return A message indicating the vehicle has stopped
     */
    @Override
    public String stop() {
        if (isRunning) {
            isRunning = false;
            currentSpeed = Constants.DEFAULT_SPEED;
            LOGGER.info("Vehicle stopped: " + make + " " + model);
            return "The " + make + " " + model + " has stopped.";
        } else {
            LOGGER.warning("Attempted to stop an already stopped vehicle: " + make + " " + model);
            return "The vehicle is already stopped.";
        }
    }

    /**
     * Get the current speed of the vehicle.
     *
     * @return The current speed in km/h
     */
    @Override
    public int getCurrentSpeed() {
        return currentSpeed;
    }

    /**
     * Returns a string containing the year, make, model, and speed of the vehicle.
     *
     * @return A human-readable representation of the vehicle
     */
    @Override
    public String toString() {
        return year + " " + make + " " + model +
                " (Speed: " + currentSpeed + " km/h)";
    }

    /**
     * Set the current speed of the vehicle.
     * Used internally by subclasses to modify speed based on acceleration or braking.
     *
     * @param speed The new speed to set (must be non-negative)
     */
    protected void setCurrentSpeed(int speed) {
        if (speed >= Constants.DEFAULT_SPEED) {
            LOGGER.fine("Speed changed from " + this.currentSpeed + " to " + speed);
            this.currentSpeed = speed;
        }
    }

    /**
     * Check if the vehicle is currently running.
     *
     * @return true if the vehicle is running, false otherwise
     */
    protected boolean isRunning() {
        return isRunning;
    }

    /**
     * Get the maximum speed allowed for the vehicle.
     * This method must be implemented by concrete subclasses.
     *
     * @return The maximum speed in km/h
     */
    protected abstract int getMaxSpeed();

    /**
     * Get the manufacturer of the vehicle.
     *
     * @return The vehicle's make
     */
    public String getMake() {
        return make;
    }

    /**
     * Get the model of the vehicle.
     *
     * @return The vehicle's model name
     */
    public String getModel() {
        return model;
    }

    /**
     * Get the manufacturing year of the vehicle.
     *
     * @return The year the vehicle was made
     */
    public int getYear() {
        return year;
    }
}
