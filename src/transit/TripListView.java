/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <gnabasikat@msoe.edu, gonzalezn@msoe.edu, galluntf@msoe.edu> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy us a beer in return Alexander Gnabasik, Noe Gonzalez, Trey Gallun.
 * ----------------------------------------------------------------------------
 */

package transit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Alexander Gnabasik
 * @version 1.0
 * @created 05-Oct-2017 12:11:01 PM
 * <p>
 * Creates a listview to display Trips.
 */
public class TripListView extends ListView implements Observer {
    private ListView listView;
    private Trips trips;

    /**
     * Constructs a TripListView
     *
     * @param listView The ListView to be displayed in.
     * @author Alexander Gnabasik
     */
    public TripListView(ListView listView) {
        this.listView = listView;
    }

    /**
     * Updates the observer from the Subject.
     *
     * @param obj The subject to be updated from.
     * @author Alexander Gnabasik
     */
    @Override
    public void update(Object obj) {
        this.trips = (Trips) obj;
    }


    /**
     * Diplayes all of the Trips
     *
     * @author Alexander Gnabasik
     */
    public void display() {
        ObservableList<String> emptyList = FXCollections.observableArrayList();
        listView.setItems(emptyList);
        ArrayList<String> data = new ArrayList<>();
        ObservableList<String> observableData;
        Collection<Trip> tripsCol = trips.getColOfTrips();
        String datum;
        for (Trip t : tripsCol) {
            datum = t.getRouteId() + " " + t.getServiceId() + " " + t.getTripId() + " " + t.getTripHeadsign() + " "
                    + t.getDirectionId() + " " + t.getBlockId() + " " + t.getShapeId();
            data.add(datum);
        }
        observableData = FXCollections.observableArrayList(data);
        listView.getItems().addAll(observableData);
    }

    /**
     * Displays a trip
     * param t: a trip to display
     *
     * @author Trey Gallun
     */
    public void display(Trip t) {
        ObservableList<String> emptyList = FXCollections.observableArrayList();
        listView.setItems(emptyList);
        ArrayList<String> data = new ArrayList<>();
        ObservableList<String> observableData;
        String datum;
        datum = t.getRouteId() + " " + t.getServiceId() + " " + t.getTripId() + " " + t.getTripHeadsign() + " "
                + t.getDirectionId() + " " + t.getBlockId() + " " + t.getShapeId();
        data.add(datum);
        observableData = FXCollections.observableArrayList(data);
        listView.getItems().addAll(observableData);
    }
}
