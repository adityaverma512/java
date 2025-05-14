package Day1.vehicles;

import java.util.logging.Logger;

/**
 * VehicleManagementDemo is a simple demonstration class that showcases
 * the core functionality of the vehicle hierarchy system.
 * <p>
 * This class creates one car and one bike instance and demonstrates
 * their behaviors including starting, accelerating, braking, and stopping.
 * </p>
 */
public class VehiclesDemo {

    // Logger for this demo class
    private static final Logger LOGGER = Logger.getLogger(VehiclesDemo.class.getName());

    /**
     * Main method that runs the vehicle demonstration.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        LOGGER.info("Starting Vehicle Management Demo");

        // Create one Car and one Bike
        Car car = new Car("Toyota", "Camry", 2023, 4, "Automatic");
        Bike bike = new Bike("Kawasaki", "Ninja", 2023, "Sport");

        LOGGER.info("Created vehicles: " + car.toString());
        LOGGER.info("Created vehicles: " + bike.toString());

        // Demonstrate bike-specific feature: kickstand
        LOGGER.info("Setting kickstand on bike: " + bike.setKickstand(false));

        // Start vehicles
        LOGGER.info("Starting car: " + car.start());
        LOGGER.info("Starting bike: " + bike.start());

        // Demonstrate acceleration
        LOGGER.info("Car acceleration: " + car.accelerate(30));
        LOGGER.info("Bike acceleration: " + bike.accelerate(40));

        // Show current status
        LOGGER.info("Current car state: " + car);
        LOGGER.info("Current bike state: " + bike);

        // Demonstrate braking
        LOGGER.info("Car braking: " + car.brake());
        LOGGER.info("Bike braking: " + bike.brake());

        // Access vehicle-specific features
        LOGGER.info("Car doors: " + car.getNumDoors());
        LOGGER.info("Car transmission: " + car.getTransmissionType());
        LOGGER.info("Bike type: " + bike.getBikeType());

        // Try to set kickstand while moving (should fail)
        LOGGER.info("Attempt to lower kickstand while moving: " + bike.setKickstand(true));

        // Stop all vehicles
        LOGGER.info("Stopping car: " + car.stop());
        LOGGER.info("Stopping bike: " + bike.stop());

        // Final state
        LOGGER.info("Final car state: " + car);
        LOGGER.info("Final bike state: " + bike);

        LOGGER.info("Vehicle Management Demo completed");
    }
}