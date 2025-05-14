package Day1.vehicles;

import java.util.logging.Logger;

/**
 * Bike class that extends AbstractVehicle.
 * This represents a motorcycle with unique implementations
 * for the abstract methods and bike-specific features.
 */
public class Bike extends AbstractVehicles {

    // Logger to log the bike operations
    private static final Logger LOGGER = Logger.getLogger(Bike.class.getName());

    private final String bikeType;  // e.g., "Sport", "Cruiser", "Touring"
    private boolean standDown;      // Kickstand status

    /**
     * Constructor for Bike class.
     *
     * @param make     The manufacturer of the bike
     * @param model    The model name of the bike
     * @param year     The year the bike was manufactured
     * @param bikeType The type of motorcycle (e.g., Sport, Cruiser)
     */
    public Bike(String make, String model, int year, String bikeType) {
        super(make, model, year);
        this.bikeType = bikeType;
        this.standDown = true; // Kickstand starts down when bike is parked
        LOGGER.info("Bike created: " + make + " " + model + " (" + bikeType + ")");
    }

    /**
     * Starts the motorcycle's engine if the kickstand is up.
     *
     * @return A message indicating if the bike has started or if the kickstand is down.
     */
    @Override
    public String start() {
        if (standDown) {
            LOGGER.warning("Cannot start bike: Kickstand is down.");
            return "Cannot start: Please raise the kickstand first.";
        }
        LOGGER.info("Bike started: " + getMake() + " " + getModel());
        return super.start();
    }

    /**
     * Accelerates the bike by a specified amount.
     * Bikes accelerate faster than cars, so the amount is multiplied by a factor of 1.5.
     *
     * @param amount The amount to accelerate by
     * @return A message indicating the bike's new speed after accelerating.
     */
    @Override
    public String accelerate(int amount) {
        if (!isRunning()) {
            LOGGER.warning("Cannot accelerate: Engine is not running.");
            return "Cannot accelerate: Engine is not running.";
        }

        int currentSpeed = getCurrentSpeed();
        int effectiveAmount = (int) (amount * 1.5); // Bikes accelerate faster
        int newSpeed = Math.min(currentSpeed + effectiveAmount, getMaxSpeed());
        setCurrentSpeed(newSpeed);

        LOGGER.info("Bike accelerated: Current speed is " + newSpeed + " km/h.");
        return "The motorcycle accelerates rapidly to " + newSpeed + " km/h.";
    }

    /**
     * Applies the brakes to the bike.
     * The bike can brake harder than a car, so it will reduce speed by up to 25 km/h.
     *
     * @return A message indicating the new speed after braking.
     */
    @Override
    public String brake() {
        int currentSpeed = getCurrentSpeed();

        if (currentSpeed == Constants.DEFAULT_SPEED) {
            LOGGER.info("Brake applied but motorcycle is already stopped.");
            return "The motorcycle is already stopped.";
        }

        // Calculate braking effect - bikes can brake harder than cars
        int reduction = Math.min(currentSpeed, 25);
        setCurrentSpeed(currentSpeed - reduction);

        LOGGER.info("Bike slowed down: Current speed is " + getCurrentSpeed() + " km/h.");
        return "The motorcycle slows down to " + getCurrentSpeed() + " km/h.";
    }

    /**
     * Determines the maximum speed of the bike based on its type.
     *
     * @return The maximum speed of the bike (e.g., 300 km/h for sport bikes, 180 km/h for cruisers)
     */
    @Override
    protected int getMaxSpeed() {
        // Log the maximum speed selection based on bike type
        int maxSpeed;
        if (bikeType.equalsIgnoreCase("Sport")) {
            maxSpeed = Constants.MAX_SPEED1;
        } else if (bikeType.equalsIgnoreCase("Cruiser")) {
            maxSpeed = Constants.MAX_SPEED2;
        } else {
            maxSpeed = Constants.MAX_SPEED3; // Default for other types
        }
        LOGGER.info("Max speed for " + bikeType + " bike is " + maxSpeed + " km/h.");
        return maxSpeed;
    }

    /**
     * Sets the kickstand status of the bike.
     *
     * @param down true to put the kickstand down, false to raise it
     * @return A message indicating the current kickstand status
     */
    public String setKickstand(boolean down) {
        if (getCurrentSpeed() > Constants.DEFAULT_SPEED && down) {
            LOGGER.warning("Cannot lower kickstand while moving.");
            return "Cannot lower kickstand while moving!";
        }

        this.standDown = down;

        if (down) {
            // Stop the engine if kickstand is down
            if (isRunning()) {
                super.stop();
                LOGGER.info("Kickstand lowered — engine stopped for safety.");
                return "Kickstand down. Engine stopped for safety.";
            }
            LOGGER.info("Kickstand lowered.");
            return "Kickstand down.";
        } else {
            LOGGER.info("Kickstand raised.");
            return "Kickstand up.";
        }
    }

    /**
     * Checks if the kickstand is down.
     *
     * @return true if the kickstand is down, false otherwise
     */
    public boolean isStandDown() {
        return standDown;
    }

    /**
     * Returns the type of the bike (Sport, Cruiser, etc.)
     *
     * @return The type of the motorcycle (e.g., "Sport", "Cruiser")
     */
    public String getBikeType() {
        return bikeType;
    }

    /**
     * Returns a string representation of the bike, including its make, model, current speed, and kickstand status.
     *
     * @return A string representing the bike.
     */
    @Override
    public String toString() {
        return super.toString() + " | " + bikeType + " motorcycle" +
                (standDown ? " (Kickstand DOWN)" : " (Kickstand UP)");
    }
}
