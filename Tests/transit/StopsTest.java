package transit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StopsTest {
    private Stops stops;
    @BeforeEach
    void setup(){
        stops = new Stops();
    }
    @Test
    void remove() {
        Stop stop = new Stop("stop_id","stop_name","stop_desc",0.0,0.0);
        stops.add(stop);
        assertEquals(stop,stops.remove("stop_id"));
        assertEquals(0,stops.getColOfStops().size());
    }

    @Test
    void add() {
        Stop stop = new Stop("stop_id","stop_name","stop_desc",0.0,0.0);
        stops.add(stop);
        assertTrue(stops.getColOfStops().contains(stop));
        assertEquals(1,stops.getColOfStops().size());
    }

    @Test
    void searchStopId() {
        Stop stop = new Stop("stop_id","stop_name","stop_desc",0.0,0.0);
        stops.add(stop);
        assertEquals(stop, stops.searchStopId("stop_id"));
    }

    @Test
    void checkLat() {
        for(int i = -360; i <= 360; i++){
            if(i >= -90 && i <= 90)
                assertTrue(stops.checkLat(i));
            else
                assertFalse(stops.checkLat(i));
        }
    }

    @Test
    void checkLon() {
        for(int i = -360; i <= 360; i++){
            if(i >= -180 && i <= 180)
                assertTrue(stops.checkLon(i));
            else
                assertFalse(stops.checkLon(i));
        }
    }

    @Test
    void splitAttributes() {
        ArrayList<String> attributes = new ArrayList();
        attributes.add("stop_id");
        attributes.add("stop_name");
        attributes.add("stop_desc");
        attributes.add("stop_lat");
        attributes.add("stop_lon");
        ArrayList<String> splitAttributes = stops.splitAttributes("stop_id,stop_name,stop_desc,stop_lat,stop_lon");
        assertEquals(attributes.size(), splitAttributes.size());
        for (String attribute:attributes){
            assertTrue(splitAttributes.contains(attribute));
        }
        attributes.set(2,"stop,_desc");
        splitAttributes = stops.splitAttributes("stop_id,stop_name,stop,_desc,stop_lat,stop_lon");
        assertEquals(attributes.get(2), splitAttributes.get(2));
        assertEquals(attributes.size(), splitAttributes.size());
    }

}