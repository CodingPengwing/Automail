package simulation;

import costs.Cost;

// Team: Workshop 16, Team 06
/**
 * Singleton for keeping track of various statistics of the simulation
 */
public class Statistics {
    private static Statistics instance = null;

    // The total number of items delivered
    private int numItemsDelivered = 0;
    // Total activity units done by all robots
    private double totalActivityCost = 0.0D;
    // Total activity done by all robots that can be billed to the residents
    private double totalBillableActivity = 0.0D;
    // Total service cost
    private double totalServiceCost = 0.0D;
    // Total number of failed lookups of the service fee
    private int numFailedLookups = 0;
    // Total number of successful lookups of the service fee
    private int numSuccessfulLookups = 0;

    private Statistics() {
        // Empty private singleton constructor, does nothing
    }

    /**
     * Singleton getInstance method. Returns the singleton instance, creating it if it does not exist yet
     * @return The singleton instance
     */
    public static Statistics getInstance() {
        if (instance == null) {
            instance = new Statistics();
        }

        return instance;
    }

    /**
     * Adds relevant information from the given Cost object to the statistics of the simulation
     * @param cost The Cost object with relevant charge information
     */
    public void addCostToStats(Cost cost) {
        totalBillableActivity += cost.getTotalActivityUnits();
        totalActivityCost += cost.getTotalActivityCost();
        totalServiceCost += cost.getServiceFee();
    }

    /**
     * Prints the statistics of the simulation to stdout
     */
    public void printStats() {
        System.out.println("Total number of items delivered: " + numItemsDelivered);
        System.out.printf("Total billable activity: %.2f%n", totalBillableActivity);
        System.out.printf("Total activity cost: %.2f%n", totalActivityCost);
        System.out.printf("Total service cost: %.2f%n", totalServiceCost);
        System.out.println("Total number of lookups: " + (numFailedLookups + numSuccessfulLookups));
        System.out.println("Total number of successful lookups: " + numSuccessfulLookups);
        System.out.println("Total number of failed lookups: " + numFailedLookups);
    }

    // Setters ---------------------------------------------------------------------------------------------------------
    /**
     * Increments the total number of items delivered by 1
     */
    public void incrementNumItemsDelivered() {
        instance.numItemsDelivered++;
    }

    /**
     * Increments the total number of failed service fee lookups by 1
     */
    public void incrementNumFailedLookups() {
        instance.numFailedLookups++;
    }

    /**
     * Increments the total number of successful service fee lookups by 1
     */
    public void incrementNumSuccessfulLookups() {
        instance.numSuccessfulLookups++;
    }
}
