package costs;

// Team: Workshop 16, Team 06

import automail.MailItem;

/**
 * A cost calculator can calculate the cost of some aspect of delivering mail
 */
public interface CostCalculator {
    /**
     * Calculates part of the cost of delivering to the given floor
     * @param item The MailItem for which the delivery cost we are calculating
     * @return Part of the cost of delivering there
     */
    void calculateCost(MailItem item);
}
