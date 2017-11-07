package transit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the StopTimes object
 * @author Alexander Gnabasik
 */
class StopTimesTest {
    private StopTimes stopTimes;

    /**
     * Sets up a new StopTimes object before each test.
     * @author Alexander Gnabasik
     */
    @BeforeEach
    void setUp() {
        stopTimes = new StopTimes(new ArrayList<>());
    }

    /**
     * Tests the add() method by adding and checking whether StopTimes contains the trip that was added.
     * @author Alexander Gnabasik
     */
    @Test
    void add() {
        StopTime s = new StopTime("09:00:00", "09:05:00", "StopId", 1, "TripId",
                "HeadSign", "PickUpType", "DropOffType");
        stopTimes.add(s);
        assertEquals(1, stopTimes.getColOfStopTimes().size());
        assertTrue(stopTimes.contains(s));
    }

    /**
     * Tests the removeByStopId(), by adding and then removing and checking that the StopTime
     * that is returned is the same as what was put in.
     * @author Alexander Gnabasik
     */
    @Test
    void removeByStopId() {
        stopTimes.add(new StopTime("09:00:00", "09:05:00", "StopId", 1, "TripId",
                "HeadSign", "PickUpType", "DropOffType"));
        StopTime response = stopTimes.removeByStopId("");
        assertEquals(response, null);
        response = stopTimes.removeByStopId("StopId");
        assertTrue(response.isEqual(new StopTime("09:00:00", "09:05:00", "StopId", 1, "TripId",
                "HeadSign", "PickUpType", "DropOffType")));
        assertEquals(0, stopTimes.getColOfStopTimes().size());
    }

    /**
     * Tests the searchStopIdTimes() by adding and then searching a null String ID and a proper StopId that was added.
     * @author Alexander Gnabasik
     */
    @Test
    void searchStopIdTimes() {
        stopTimes.add(new StopTime("09:00:00", "09:05:00", "StopId", 1, "TripId",
                "HeadSign", "PickUpType", "DropOffType"));
        StopTime response = stopTimes.searchStopIdTimes("");
        assertSame(null, response);
        response = stopTimes.searchStopIdTimes("StopId");
        assertTrue(response.isEqual(new StopTime("09:00:00", "09:05:00", "StopId", 1, "TripId",
                "HeadSign", "PickUpType", "DropOffType")));
        assertEquals(1, stopTimes.getColOfStopTimes().size());
    }

    /**
     * Tests the timeFormatCorrect() with Strings without ints or colons and some with ints and colons.
     * @author Alexander Gnabasik
     */
    @Test
    void timeFormatCorrect() {
        assertTrue(stopTimes.timeFormatCorrect("0:0:0"));
        assertFalse(stopTimes.timeFormatCorrect("0:00"));
        assertFalse(stopTimes.timeFormatCorrect("0:0"));
        assertFalse(stopTimes.timeFormatCorrect("hello:"));
        assertFalse(stopTimes.timeFormatCorrect(""));
        assertFalse(stopTimes.timeFormatCorrect("2000:10:0"));
    }


    @Test
    void export() {

    }

    /**
     * Tests the searchTripIdTimes() by searching a trip that is contained and a null string tripId
     * @author Alexander Gnabasik
     */
    @Test
    void searchTripIdTimes() {
        StopTime stopTime = new StopTime("09:00:00", "09:05:00", "StopId", 1, "TripId",
                "HeadSign", "PickUpType", "DropOffType");
        stopTimes.add(stopTime);
        StopTimes s = stopTimes.searchTripIdTimes("TripId");
        assertTrue(s.contains(stopTime));
        assertTrue(stopTimes.contains(stopTime));
    }

    /**
     * tests the idChecker() method with a null string and strings.
     * @author Alexander Gnabasik
     */
    @Test
    void idStringCheck() {
        assertTrue(stopTimes.idStringCheck("Hi"));
        assertFalse(stopTimes.idStringCheck(""));
        assertTrue(stopTimes.idStringCheck("NO STOP"));
    }

    /**
     * Checks to make sure the sequence can be parsed to an int and it is above zero.
     * Tests this with Strings and negatives and zero.
     * @author Alexander Gnabasik
     */
    @Test
    void sequenceCheck() {
        assertTrue(stopTimes.sequenceCheck("1"));
        assertTrue(stopTimes.sequenceCheck("1000"));
        assertTrue(stopTimes.sequenceCheck("0"));
        assertFalse(stopTimes.sequenceCheck("-1000"));
        assertFalse(stopTimes.sequenceCheck("Hello"));
        assertFalse(stopTimes.sequenceCheck("100Hi"));
        assertFalse(stopTimes.sequenceCheck(""));
    }

    /**
     * Checks that it properly checks the Attributes on the first line.
     * @author Alexander Gnabasik
     */
    @Test
    void checkAttributes() {
        assertTrue(stopTimes.checkAttributes("trip_id,arrival_time,departure_time,stop_id,stop_sequence," +
                "stop_headsign,pickup_type,drop_off_type"));
        assertFalse(stopTimes.checkAttributes(",,,,"));
        assertFalse(stopTimes.checkAttributes(""));
        assertFalse(stopTimes.checkAttributes("trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_typ"));
        assertFalse(stopTimes.checkAttributes("trip_id,arrival_time,departure_time,stop_idstop_sequence"));
        assertFalse(stopTimes.checkAttributes("Hello goodbye"));
    }

    /**
     * Tests the importFile() method with a blank file, a null file, and a proper StopTimes file.
     * @author Alexander Gnabasik
     */
    @Test
    void importFile() {
        File none = new File("");
        File bF = new File("C:\\Users\\Gnabasikat\\IdeaProjects\\transitk\\Tests\\transit\\blankFileTest.txt");
        File tF = new File("C:\\Users\\Gnabasikat\\IdeaProjects\\transitk\\Tests\\transit\\stopTimesTest.txt");
        assertFalse(stopTimes.importFile(none));
        assertFalse(stopTimes.importFile(bF));
        assertTrue(stopTimes.importFile(tF));
    }

}