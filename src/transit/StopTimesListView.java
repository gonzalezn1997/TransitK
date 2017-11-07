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
 * @created 30-Oct-2017 12:11:01 PM
 * Creates a list view to display stop times
 */
public class StopTimesListView extends ListView implements Observer {
    private ListView listView;
    private StopTimes stopTimes;

    /**
     * Constructs the StopTimesListView.
     *
     * @param listView The listView to be displayed to.
     * @author Noe Gonzalez
     */
    public StopTimesListView(ListView listView) {
        this.listView = listView;
    }

    /**
     * Updates the observer from the subject.
     *
     * @param obj The subject it is observing.
     * @author Noe Gonzalez
     */
    @Override
    public void update(Object obj) {
        this.stopTimes = (StopTimes) obj;
    }

    /**
     * Displays the data when viewing stop times
     *
     * @author Alexander Gnabasik
     */
    public void display() {
        try {
            ObservableList<String> emptyList = FXCollections.observableArrayList();
            listView.setItems(emptyList);
            ArrayList<String> data = new ArrayList<>();
            ObservableList<String> observableData;
            Collection<StopTime> stopTimesCol = stopTimes.getColOfStopTimes();
            String datum;
            for (StopTime s : stopTimesCol) {
                datum = s.getStopId() + " " + s.getTripId() + " " + s.getArrivalTime() + " " + s.getDepartureTime() + " "
                        + s.getSequence() + " " + s.getStopHeadsign() + " " + s.getPickUpType() + " " + s.getDropOffType();
                data.add(datum);
            }
            observableData = FXCollections.observableArrayList(data);
            listView.getItems().addAll(observableData);
        } catch (OutOfMemoryError e) {
            System.err.println("Could not display StopTimes, Out of Memory.");
        }
    }
}
