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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * A ListView to display Routes.
 *
 * @author Alexander Gnabasik & Noe Gonzalez
 * @version 1.0
 * @created 30-Oct-2017 12:11:01 PM
 */
public class RoutesListView extends ListView implements Observer {
    private ListView listView;
    private Routes routes;

    /**
     * Constructs the RoutesListView
     *
     * @param listView the ListView to be displayed in
     * @author Noe Gonzalez
     */
    public RoutesListView(ListView listView) {
        this.listView = listView;
    }

    /**
     * Updates the observer from the Subject.
     *
     * @param obj The subject to be updated from.
     * @author Noe Gonzalez
     */
    @Override
    public void update(Object obj) {
        this.routes = (Routes) obj;
    }

    /**
     * Displays routes with a given stop
     *
     * @param stop The stop to look in routes for.
     * @author Noe Gonzalez
     */
    public void display(Stop stop) {
        ObservableList<String> emptyList = FXCollections.observableArrayList();
        listView.setItems(emptyList);
        ArrayList<String> data = new ArrayList<>();
        ObservableList<String> observableData;
        Iterator<Route> routesIterator = routes.getColOfRoutes().iterator();
        Route currentRoute;
        while (routesIterator.hasNext()) {
            currentRoute = routesIterator.next();
            if (currentRoute.getStopsInRoute().searchStopId(stop.getStopId()) != null) {
                data.add(currentRoute.getId());
            }
        }

        observableData = FXCollections.observableArrayList(data);
        listView.getItems().addAll(observableData);
    }

    /**
     * Displays upcoming Trips for a given Route.
     *
     * @param route The route the user is interested in.
     * @author Noe Gonzalez
     */
    public void display(Route route, StopTimes stopTimes) {
        double currentTime = (System.nanoTime() / 100000000000.0);
        ObservableList<String> emptyList = FXCollections.observableArrayList();
        listView.setItems(emptyList);
        ArrayList<String> data = new ArrayList<>();
        ObservableList<String> observableData;
        Collection<String> tripIds = route.getTripIdsInRoute();
        String tempTripId;
        StopTimes stInTrip;
        Collection<StopTime> stopTimesWithTrip = new ArrayList<>();
        Iterator<String> tripIdsIterator = tripIds.iterator();
        while (tripIdsIterator.hasNext()) {
            tempTripId = tripIdsIterator.next();
            stInTrip = stopTimes.searchTripIdTimes(tempTripId);
            if (stInTrip != null) {
                stopTimesWithTrip.addAll(stInTrip.getColOfStopTimes());
            }
        }
        StopTime temp;
        Iterator<StopTime> stopIt = stopTimesWithTrip.iterator();
        while (stopIt.hasNext()) {
            temp = stopIt.next();
            if ((double) temp.getArrivalTimeInSeconds() > currentTime) {
                data.add(temp.getTripId());
            }
        }
        observableData = FXCollections.observableArrayList(data);
        listView.getItems().addAll(observableData);
    }

    /**
     * Displays all routes.
     *
     * @author Alexander Gnabasik
     */
    public void display() {
        ObservableList<String> emptyList = FXCollections.observableArrayList();
        listView.setItems(emptyList);
        ArrayList<String> data = new ArrayList<>();
        ObservableList<String> observableData;
        Collection<Route> routesCol = routes.getColOfRoutes();
        String datum;
        for (Route r : routesCol) {
            datum = r.getRouteId() + " " + r.getAgencyID() + " " + r.getShortName() + " " + r.getLongName() + " "
                    + r.getRouteDesc() + " " + r.getRouteType() + " " + r.getRouteURL() + " " + r.getRouteColor() + " "
                    + r.getRouteTextColor();
            data.add(datum);
        }
        observableData = FXCollections.observableArrayList(data);
        listView.getItems().addAll(observableData);
    }
}
