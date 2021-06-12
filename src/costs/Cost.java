package costs;

// Team: Workshop 16, Team 06

import automail.MailItem;

/**
 * Responsible for calculating and storing delivery costs to a destination floor
 */
public class Cost {
    // Markup margin
    private static final double MARKUP_PERCENTAGE = 0.059D;
    // Cost needed to exceed for an item to be classified as a priority delivery. When set to 0, priority is turned off
    private static double PRIORITY_THRESHOLD = 0;

    // Cost calculators
    private static final ActivityCostCalculator activityCostCalculator = new ActivityCostCalculator();
    private static final ServiceFeeCalculator serviceFeeCalculator = new ServiceFeeCalculator();
    
    // The cost used for charging the delivery
    private double cost;

    // The number of lookups that this Cost object has made to calculate its cost
    private int numberOfLookups = 0;

    /**
     * Creates a new Cost objects, calculating and storing all of the cost information of delivering to the given
     * destination floor
     * @param item The MailItem for which the delivery charge we are calculating
     */
    public Cost(MailItem item) {
        calculateCost(item);
    }

    /**
     * Calculates all elements of a charge for a delivery item to the given destination floor
     * @param item The MailItem for which the delivery charge we are calculating
     */
    public void calculateCost(MailItem item) {
        serviceFeeCalculator.calculateCost(item);
        numberOfLookups++;
        activityCostCalculator.calculateCost(item);
        
        cost = serviceFeeCalculator.getServiceFee() + activityCostCalculator.getChargedActivityCost();
    }

    /**
     * Overridden toString() changed so that it returns a string representation of the Cost object in an appropriately
     * formatted way for the logging statements of the simulation
     * @return The string representation of the Cost object
     */
    @Override
    public String toString() {
        return String.format("Charge: %.2f|Cost: %.2f|Fee: %.2f|Activity: %.2f", getCharge(), cost, getServiceFee(), activityCostCalculator.getChargedActivity());
    }



    // Getters ---------------------------------------------------------------------------------------------------------
    /**
     * @return The priority threshold
     */
    public static double getPriorityThreshold() {
        return PRIORITY_THRESHOLD;
    }

    /**
     * @return The total charge of the delivery
     */
    public double getCharge() {
        return cost * (1 + MARKUP_PERCENTAGE);
    }

    /**
     * @return The service fee incurred for the delivery
     */
    public double getServiceFee() {
        return serviceFeeCalculator.getServiceFee();
    }

    /**
     * @return The activity cost of the delivery
     */
    public double getTotalActivityCost() {
        return activityCostCalculator.getTotalActivityCost();
    }

    /**
     * @return The total activity (including unbillable) units of the delivery
     */
    public double getTotalActivityUnits() {
        return activityCostCalculator.getTotalActivity();
    }

    /**
     * @return The number of lookups this Cost object has made to calculate its cost
     */
    public int getNumberOfLookups() {
        return numberOfLookups;
    }

    // Setters ---------------------------------------------------------------------------------------------------------
    /**
     * Sets the priority threshold. Items with a delivery charge over this threshold will be treated as priority items.
     * If set to 0, priority will be turned off
     * @param threshold The new threshold
     */
    public static void setPriorityThreshold(double threshold) {
        PRIORITY_THRESHOLD = threshold;
    }
}
