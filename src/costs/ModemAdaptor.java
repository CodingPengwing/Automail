package costs;

import com.unimelb.swen30006.wifimodem.WifiModem;
import simulation.Building;
import simulation.Statistics;

// Team: Workshop 16, Team 06
/**
 * Protected variation class, responsible for being the interface between the system and the WifiModem device
 */
public class ModemAdaptor {
    /**
     * Returns the service fee for the given floor by interfacing with the modem. If the lookup failed, -1.0 is returned
     * @param destFloor The destination floor to check
     * @return The service fee for the given floor, or -1.0 id the lookup failed
     */
    public static double lookupServiceFee(int destFloor) {
        double serviceFee = -1.0D;
        try {
            serviceFee = WifiModem.getInstance(Building.MAILROOM_LOCATION).forwardCallToAPI_LookupPrice(destFloor);

            // Keep track of lookup statistics
            if (serviceFee < 0.0) {
                // Lookup failed
                Statistics.getInstance().incrementNumFailedLookups();
            } else {
                // Lookup succeeded
                Statistics.getInstance().incrementNumSuccessfulLookups();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return serviceFee;
    }
}
