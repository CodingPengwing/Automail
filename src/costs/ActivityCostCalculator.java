package costs;

// Team: Workshop 16, Team 06

import automail.MailItem;

/**
 * Responsible for calculating the activity cost of delivery
 */
public class ActivityCostCalculator implements CostCalculator {
    // The activity cost of looking up a service fee
    private static final double REMOTE_LOOKUP_COST = 0.1D;
    // The activity cost of moving up/down one floor
    private static final double MOVEMENT_COST = 5.0D;
    // The price in AUD of each unit of activity
    private static final double ACTIVITY_UNIT_PRICE = 0.224D;

    // Total units of activity used to charge tenant
    private double chargedActivity;
    // Total activity units in the delivery
    private double totalActivity;
    // The cost of activity included in the charge
    private double chargedActivityCost;
    // Total cost of all activity units in the delivery
    private double totalActivityCost;

    /**
     * Calculates the activity units used to calculate the charge of a delivery
     * @param item The MailItem for which the activity we are calculating
     */
    private void calculateChargedActivity(MailItem item) {
        // Total chargeable activity to a floor is the cost of going up there (destFloor * MOVEMENT_COST) and back (* 2)
        // plus the additional costs of doing the delivery itself and lookup up the service fee once (+ DELIVERY_COST +
        // REMOTE_LOOKUP_COST)
        double roundTripCost = item.getDestFloor() * MOVEMENT_COST * 2;
        chargedActivity = roundTripCost + REMOTE_LOOKUP_COST;
    }

    /**
     * Calculates the total (including unbillable) activity units involved in delivering to the given floor
     * @param item The MailItem for which the activity cost we are calculating
     */
    private void calculateTotalActivity(MailItem item) {
        Cost cost = item.getCost();
        // If the Cost objects hasn't been created, then we have no unbillable activity yet
        double unchargedActivity = 0.0D;
        // Otherwise calculate uncharged activity
        if (cost != null) {
            unchargedActivity = (item.getCost().getNumberOfLookups() - 1) * REMOTE_LOOKUP_COST;
        }
        calculateChargedActivity(item);
        totalActivity = chargedActivity + unchargedActivity;
    }

    // Returns the cost of the given activity units
    private double costOf(double activityUnits) {
        return activityUnits * ACTIVITY_UNIT_PRICE;
    }

    /**
     * Calculates the activity cost included in the charge for the given delivery item
     * @param item The MailItem for which t activity cost is being calculated
     */
    private void calculateChargedActivityCost(MailItem item) {
        calculateChargedActivity(item);
        chargedActivityCost = costOf(chargedActivity);
    }

    /**
     * Calculates the total activity units (including unbillable activity) for the given MailItem
     * @param item The MailItem for which the activity cost is being calculated
     */
    private void calculateTotalActivityCost(MailItem item) {
        calculateTotalActivity(item);
        totalActivityCost = costOf(totalActivity);
    }

    /**
     * @return The number of activity units that are included in the charge
     */
    public double getChargedActivity() {
        return chargedActivity;
    }

    /**
     * @return The total number of activity units
     */
    public double getTotalActivity() {
        return totalActivity;
    }

    /**
     * @return The activity cost included in the charge
     */
    public double getChargedActivityCost() {
        return chargedActivityCost;
    }

    /**
     * @return The total activity cost
     */
    public double getTotalActivityCost() {
        return totalActivityCost;
    }

    /**
     * Calculates and stores all aspects of activity relating to delivering the given MailItem
     * @param item The MailItem for which the activity costs we are calculating
     */
    @Override
    public void calculateCost(MailItem item) {
        calculateChargedActivityCost(item);
        calculateTotalActivityCost(item);
    }
}
