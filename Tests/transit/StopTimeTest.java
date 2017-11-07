package transit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Created by gnabasikat on 10/14/2017.
 *
 * @author Alexander Gnabasik
 */
class StopTimeTest {

    private StopTime stopTime;

    @BeforeEach
    void setUp() {
        stopTime = new StopTime("09:00:00", "9:05:00", "StopId", 1, "TripId",
                "HeadSign", "PickUpType", "DropOffType");
    }

    @Test
    void export() {

    }

    @Test
    void getArrivalTimeInSeconds() {
        assertEquals(stopTime.getArrivalTimeInSeconds(), 9 * 3600);
    }
}