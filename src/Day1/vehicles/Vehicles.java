package Day1.vehicles;

/**
 * Vehicle interface defines the contract that all vehicle types must follow.
 * This is a form of abstraction that specifies what operations all vehicles should support
 * without dictating how they should be implemented.
 */

public interface Vehicles {

    /**
     * Start the vehicle's engine or motor.
     * @return A message indicating the vehicle has started
     */
    String start();

    /**
     * Stop the vehicle's engine or motor.
     * @return A message indicating the vehicle has stopped
     */
    String stop();

    /**
     * Accelerate the vehicle.
     * @param amount The amount to accelerate by
     * @return A message describing the acceleration
     */

    String accelerate(int amount);

    /**
     * Apply brakes to slow down or stop the vehicle.
     * @return A message indicating braking action
     */
    String brake();

    /**
     * Get current speed of the vehicle.
     * @return Current speed in km/h
     */
    int getCurrentSpeed();

}
