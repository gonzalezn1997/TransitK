package transit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RouteTest {

    private Route route;
    @BeforeEach
    void setUp() {
        route = new Route("route_id","agency_id","route_short_name","route_long_name","route_desc","route_type","route_url","route_color","route_text_color");
    }
    @Test
    void getTripIdsInRoute() {
        Trips trips = new Trips(new ArrayList<>());
        trips.importFile(new File("tripsTest.txt"));
        route.setTripsInRoute(trips);
        assertEquals(trips.getColOfTrips().size(),route.getTripIdsInRoute().size());
        ArrayList<String> ids = new ArrayList<>();
        for (Trip trip:trips.getColOfTrips()){
            ids.add(trip.getTripId());
        }
        assertEquals(ids,route.getTripIdsInRoute());
    }


}