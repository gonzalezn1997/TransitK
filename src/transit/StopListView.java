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
 * @author Alexander Gnabasik & Noe Gonzalez
 * @version 1.0
 * @created 05-Oct-2017 12:11:01 PM
 * <p>
 * Creates a list view to display stops
 */
public class StopListView extends ListView implements Observer {
    private ListView listView;
    private Stops stop;

    /**
     * Constructs the StopListView
     *
     * @param listView The Listview to display to.
     * @author Noe Gonzalez
     */
    public StopListView(ListView listView) {
        this.listView = listView;
    }

    /**
     * Updates the Listview.
     *
     * @param obj The Subject it is attached too.
     */
    @Override
    public void update(Object obj) {
        this.stop = (Stops) obj;
    }

    /**
     * Displays stops within a given route
     *
     * @param route The route where the stops should be within.
     * @author Noe Gonzalez
     */
    public void display(Route route) {
        ObservableList<String> emptyList = FXCollections.observableArrayList();
        listView.setItems(emptyList);
        ArrayList<String> data = new ArrayList<>();
        ObservableList<String> observableData;
        ArrayList<Stop> stopsInRoute;
        stopsInRoute = new ArrayList(route.getStopsInRoute().getColOfStops());
        for (int i = 0; i < stopsInRoute.size(); i++) {
            data.add(stopsInRoute.get(i).getStopId());
        }
        observableData = FXCollections.observableArrayList(data);
        listView.getItems().addAll(observableData);
    }

    /**
     * Displays all stops in the program.
     *
     * @author Alexander Gnabasik
     */
    public void display() {
        ObservableList<String> emptyList = FXCollections.observableArrayList();
        listView.setItems(emptyList);
        ArrayList<String> data = new ArrayList<>();
        ObservableList<String> observableData;
        Collection<Stop> stopsCol = stop.getColOfStops();
        String datum;
        for (Stop s : stopsCol) {
            datum = s.getStopId() + " " + s.getStopName() + " " + s.getStopDesc() + " " + s.getStopLat() + " "
                    + s.getStopLon();
            data.add(datum);
        }
        observableData = FXCollections.observableArrayList(data);
        listView.getItems().addAll(observableData);
    }
}
