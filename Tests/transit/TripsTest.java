package transit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the StopTimes object.
 * @author Alexander Gnabasik
 */
class TripsTest {
    Trips trips;

    /**
     * Sets up a new Trips object before each test
     * @author Alexander Gnabasik
     */
    @BeforeEach
    void setUp() {
        trips = new Trips(new ArrayList<>());
    }

    /**
     * Tests the size and if a Trips object contains an object after adding
     * @author Alexander Gnabasik
     */
    @Test
    void add() {
        Trip t = new Trip("RouteID", "ServiceID", "TripID",
                "TripHeadsign", "DirectionID", "BlockID", "ShapeID");
        trips.add(t);
        assertEquals(1,trips.getColOfTrips().size());
        assertTrue(trips.contains(t));
    }

    /**
     * Tests the remove method after adding a Trip and before. It checks to make sure the Trip
     * that is returned is the same Trip.
     * @author Alexander Gnabasik
     */
    @Test
    void remove() {
        Trip removed = trips.remove("Hi");
        assertEquals(0,trips.getColOfTrips().size());
        assertEquals(null, removed);
        Trip t = new Trip("RouteID", "ServiceID", "TripID",
                "TripHeadsign", "DirectionID", "BlockID", "ShapeID");
        trips.add(t);
        assertEquals(1,trips.getColOfTrips().size());
        removed = trips.remove("");
        assertEquals(null, removed);
        assertEquals(1, trips.getColOfTrips().size());
        assertTrue(trips.contains(t));
        removed = trips.remove("TripID");
        assertEquals(t, removed);
        assertEquals(0, trips.getColOfTrips().size());
        assertFalse(trips.contains(t));
    }

    /**
     * Tests the searchTripId() method with a null String ID and a proper TripID in Trips.
     * @author Alexander Gnabasik
     */
    @Test
    void searchTripId() {
        Trip t = new Trip("RouteID", "ServiceID", "TripID",
                "TripHeadsign", "DirectionID", "BlockID", "ShapeID");
        trips.add(t);
        Trip searched = trips.searchTripId("");
        assertEquals(null, searched);
        searched = trips.searchTripId("TripID");
        assertEquals(t, searched);
    }

    /**
     * Tests the searchByRouteId in trips with null string RouteIds and a proper routeID in Trips
     * @author Alexander Gnabasik
     */
    @Test
    void searchByRouteId() {
        Trip t = new Trip("RouteID", "ServiceID", "TripID",
                "TripHeadsign", "DirectionID", "BlockID", "ShapeID");
        trips.add(t);
        Trips searched = trips.searchByRouteId("");
        assertEquals(null, searched);
        searched = trips.searchByRouteId("RouteID");
        assertTrue(trips.contains(t));
        assertTrue(searched.contains(t));
        assertEquals(1, searched.getColOfTrips().size());
    }

    /**
     * tests the idChecker() method with a null string and strings.
     * @author Alexander Gnabasik
     */
    @Test
    void idChecker() {
        assertTrue(trips.idChecker("Hi"));
        assertFalse(trips.idChecker(""));
        assertTrue(trips.idChecker("NO STOP"));
    }

    @Test
    void export() {

    }


    @Test
    void attach() {

    }

    @Test
    void detach() {

    }

    @Test
    void update() {

    }

    /**
     * Tests where checkAttributes with fully test for all the Attributes.
     * @author Noe Gonzalez
     */
    @Test
    void checkAttributes() {
        assertTrue(trips.checkAttributes("route_id,service_id,trip_id,trip_headsign,direction_id," +
                "block_id,shape_id"));
        assertFalse(trips.checkAttributes(","));
        assertFalse(trips.checkAttributes(""));
        assertFalse(trips.checkAttributes("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_i"));
        assertFalse(trips.checkAttributes("route_id,service_id,trip_id,trip_headsign,direction_idblock_idshape_id"));
        assertFalse(trips.checkAttributes("Hello goodbye"));
    }

   /**
     * Tests the importFile() method with a blank file, a null file, and a proper Trips file.
     * @author Alexander Gnabasik
     */
   /*
    @Test
    void importFile() {
        File none = new File("");
        File bF = new File("C:\\Users\\Gnabasikat\\IdeaProjects\\transitk\\Tests\\transit\\blankFileTest.txt");
        File tF = new File("C:\\Users\\Gnabasikat\\IdeaProjects\\transitk\\Tests\\transit\\tripsTest.txt");
        assertFalse(trips.importFile(none));
        assertFalse(trips.importFile(bF));
        assertTrue(trips.importFile(tF));
    } */
}