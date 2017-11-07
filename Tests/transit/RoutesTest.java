package transit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class RoutesTest {
    private Routes routes;

    @BeforeEach
    void setUp() {
        routes = new Routes(new ArrayList<>());
    }

    @Test
    void remove() {
        Route route = new Route("route_id","agency_id","route_short_name","route_long_name","route_desc","route_type","route_url","route_color","route_text_color");
        routes.add(route);
        assertEquals(route,routes.remove("route_id"));
        assertEquals(0,routes.getColOfRoutes().size());
    }

    @Test
    void add() {
        Route route = new Route("route_id","agency_id","route_short_name","route_long_name","route_desc","route_type","route_url","route_color","route_text_color");
        routes.add(route);
        assertTrue(routes.getColOfRoutes().contains(route));
        assertEquals(1,routes.getColOfRoutes().size());
    }

    @Test
    void searchRouteId() {
        Route route = new Route("route_id","agency_id","route_short_name","route_long_name","route_desc","route_type","route_url","route_color","route_text_color");
        routes.add(route);
        assertEquals(route, routes.searchRouteId("route_id"));
    }

    @Test
    void searchRoutesForStop() {
        Route route = new Route("route_id","agency_id","route_short_name","route_long_name","route_desc","route_type","route_url","route_color","route_text_color");
        Stops stops = new Stops();
        Stop stop = new Stop("stop_id","stop_name","stop_desc",0.0,0.0);
        stops.add(stop);
        route.setStopsInRoute(stops);
        routes.add(route);
        Collection<Route> collection = new ArrayList<>();
        collection.add(route);
        assertEquals(collection,routes.searchRoutesForStop("stop_id").getColOfRoutes());
    }

    @Test
    void splitAttributes() {
        ArrayList<String> attributes = new ArrayList();
        attributes.add("route_id");
        attributes.add("agency_id");
        attributes.add("route_short_name");
        attributes.add("route_long_name");
        attributes.add("route_desc");
        attributes.add("route_type");
        attributes.add("route_url");
        attributes.add("route_color");
        attributes.add("route_text_color");
        ArrayList<String> splitAttributes = routes.splitAttributes("route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color");
        assertEquals(attributes.size(), splitAttributes.size());
        for (String attribute:attributes){
            assertTrue(splitAttributes.contains(attribute));
        }
        attributes.set(4,"route,_desc");
        splitAttributes = routes.splitAttributes("route_id,agency_id,route_short_name,route_long_name,route,_desc,route_type,route_url,route_color,route_text_color");
        assertEquals(attributes.get(4), splitAttributes.get(4));
        assertEquals(attributes.size(), splitAttributes.size());
    }
}