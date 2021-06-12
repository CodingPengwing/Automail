package costs;

import automail.MailItem;

import java.util.HashMap;

// Team: Workshop 16, Team 06
/**
 * Responsible for getting the service fee for a floor
 */
public class ServiceFeeCalculator implements CostCalculator {
    // The default service fee, to be used if modem lookup fails and no previous value is known
    private static final double DEFAULT_SERVICE_FEE = 0.0D;
    // A map of the last known service fee for each floor
    private final HashMap<Integer, Double> prevLookups = new HashMap<>();
    // The current service fee
    private double serviceFee;

    /**
     * Attempts to retrieve the service fee for the given floor. If the lookup fails, the last known service fee for
     * that floor is returned, and that does not exist, a default value is returned
     * @param item The MailItem for which the service fee we are calculating
     */
    @Override
    public void calculateCost(MailItem item) {
        // Attempt to to get the latest service fee
        serviceFee = ModemAdaptor.lookupServiceFee(item.getDestFloor());

        if (serviceFee < 0) {
            // Modem lookup failed, try to get the last known service for the given floor
            if (prevLookups.get(item.getDestFloor()) == null) {
                // No known service fee for this floor. Just use a default value
                serviceFee = DEFAULT_SERVICE_FEE;
            } else {
                serviceFee = prevLookups.get(item.getDestFloor());
            }
        } else {
            // Modem lookup succeeded. Update the last known service fee for the given floor
            prevLookups.put(item.getDestFloor(), serviceFee);
        }
    }

    /**
     * @return serviceFee The service fee for the given floor
     */
    public double getServiceFee() {
        return serviceFee;
    }
}
