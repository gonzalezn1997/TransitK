/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <gnabasikat@msoe.edu, gonzalezn@msoe.edu, galluntf@msoe.edu> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy us a beer in return Alexander Gnabasik, Noe Gonzalez, Trey Gallun.
 * ----------------------------------------------------------------------------
 */

package transit;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.text.LabelView;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.sun.prism.PhongMaterial;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The controller for the project and the FXML file
 *
 * @author Noe Gonzalez, Alex Gnabasik, and Trey Gallun
 * @version 1.3
 * @created 09-Oct-2017 12:10:42 PM
 */
public class Controller implements Initializable, MapComponentInitializedListener {
    private LinkedHashSet<String> tripIds;
    private Routes allRoutes;
    private Stops allStops;
    private StopTimes allStopTimes;
    private Trips allTrips;
    public TextField area1;
    public TextField area2;
    public TextField area3;
    public TextField area4;
    public TextField area5;
    public TextField area6;
    public TextField area7;
    public MenuButton updateMenu;
    public Button updateButton;
    public Button updateSearch;
    private FileChooser chooser;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private MenuButton viewButton;
    @FXML
    private MenuButton searchItem;
    @FXML
    private TextField searchBox;
    @FXML
    private ListView<String> listView;
    @FXML
    private ListView<String> scheduleList;
    @FXML
    private GoogleMapView googleMapView;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label5;
    @FXML
    private Label label6;
    @FXML
    private Label label7;

    @FXML
    private Button exportTripsButton;
    @FXML
    private Button exportRoutesButton;
    @FXML
    private Button exportStopTimesButton;
    @FXML
    private Button exportStopsButton;
    private boolean stopsImported;
    private boolean routesImported;
    private boolean tripsImported;
    private boolean stopTimesImported;
    private ObservableList<MenuItem> list;
    private boolean searchForRoutes;
    private boolean searchForStops;
    private boolean searchForTrips;
    private RoutesListView oRoutesListView;
    private StopListView oStopListView;
    private StopTimesListView oStopTimes;
    private TripListView oTrips;
    private StopListView oStops;
    private RoutesListView oRoutes;
    private boolean searchForNextTrip;
    public GoogleMapsWebView googleMapsWebView;

    public Controller() {
        stopsImported = false;
        routesImported = false;
        tripsImported = false;
        stopTimesImported = false;
        chooser = new FileChooser();
        searchForRoutes = false;
        searchForStops = false;
        searchForTrips = false;
    }

    /**
     * Upon creating the GUI, initializes a multitude of attributes.
     *
     * @param location  The URL of the FXML file
     * @param resources The FXML resources.
     * @authon Noe Gonzalez Alex Gnabasik and Trey Gallun
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        googleMapView.addMapInializedListener(this);
        oRoutesListView = new RoutesListView(listView);
        oStopListView = new StopListView(listView);
        oTrips = new TripListView(scheduleList);
        oStopTimes = new StopTimesListView(scheduleList);
        oStops = new StopListView(scheduleList);
        oRoutes = new RoutesListView(scheduleList);
        allStopTimes = new StopTimes(new ArrayList<>());
        allTrips = new Trips(allStopTimes, new ArrayList<>());
        allStops = new Stops(new ArrayList<>());
        allRoutes = new Routes(new ArrayList<>(), allStops, allTrips, allStopTimes);
        searchForRoutes = false;
        searchForStops = false;
        searchForTrips = false;
        list = FXCollections.observableArrayList();
        searchItem.getItems().setAll(list);
        viewButton.setDisable(true);
        searchBox.setDisable(true);
        allRoutes.attach(oRoutesListView);
        allStops.attach(oStopListView);
        allStops.attach(oStops);
        allRoutes.attach(oRoutes);
        allStopTimes.attach(oStopTimes);
        allTrips.attach(oTrips);
        exportRoutesButton.setDisable(true);
        exportStopsButton.setDisable(true);
        exportStopTimesButton.setDisable(true);
        exportTripsButton.setDisable(true);


        MenuItem item1 = new MenuItem("Search for stops by Route_ID");
        item1.setOnAction((event) -> {
            searchItem.setText("Search: Stops by Route ID");
            searchForStops = true;
            searchForRoutes = false;
            searchForTrips = false;
            searchForNextTrip = false;
            searchBox.setDisable(false);
        });
        MenuItem item2 = new MenuItem("Search for routes by Stop_ID");
        item2.setOnAction((event) -> {
            searchItem.setText("Search: Routes by Stop ID");
            searchForRoutes = true;
            searchForStops = false;
            searchForTrips = false;
            searchForNextTrip = false;
            searchBox.setDisable(false);
        });
        MenuItem item3 = new MenuItem("Search for trips by Route_ID");
        item3.setOnAction((event) -> {
            searchItem.setText("Search: Trips by Route ID");
            searchForTrips = true;
            searchForStops = false;
            searchForRoutes = false;
            searchForNextTrip = false;
            searchBox.setDisable(false);
        });
        MenuItem item4 = new MenuItem("Search for next Trip by Stop_ID");
        item4.setOnAction((event) -> {
            searchItem.setText("Search: next Trip by Stop_ID");
            searchForTrips = false;
            searchForStops = false;
            searchForRoutes = false;
            searchForNextTrip = true;
            searchBox.setDisable(false);
        });
        list.addAll(item1, item2, item3, item4);
        searchItem.getItems().addAll(list);
        searchBox.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                search();
            }
        });

        MenuItem u1 = new MenuItem("Update Stop");
        u1.setOnAction((event) -> {
            updateButton.setDisable(true);
            updateMenu.setText("Update Stop");
            updateSearch.setDisable(false);
            area1.setDisable(false);
            area2.setDisable(true);
            area3.setDisable(true);
            area4.setDisable(true);
            area5.setDisable(true);
            area6.setDisable(true);
            area7.setDisable(true);
            area1.clear();
            area2.clear();
            area3.clear();
            area4.clear();
            area5.clear();
            area6.clear();
            area7.clear();
            label1.setText("Stop ID");
            label2.setText("Stop Description");
            label3.setText("Stop Name");
            label4.setText("");
            label5.setText("Latitude");
            label6.setText("Longitude");
            label7.setText("");

        });
        MenuItem u2 = new MenuItem("Update Route");
        u2.setOnAction((event) -> {
            updateButton.setDisable(true);
            updateMenu.setText("Update Route");
            updateSearch.setDisable(false);
            area1.setDisable(false);
            area2.setDisable(true);
            area3.setDisable(true);
            area4.setDisable(true);
            area5.setDisable(true);
            area6.setDisable(true);
            area7.setDisable(true);
            area1.clear();
            area2.clear();
            area3.clear();
            area4.clear();
            area5.clear();
            area6.clear();
            area7.clear();
            label1.setText("Route ID");
            label2.setText("Agency ID");
            label3.setText("Route Short Name");
            label4.setText("Route Long Name");
            label5.setText("Route Description");
            label6.setText("Route Color");
            label7.setText("Route Text Color");
        });
        MenuItem u3 = new MenuItem("Update Trip");
        u3.setOnAction((event) -> {
            updateButton.setDisable(true);
            updateMenu.setText("Update Trip");
            updateSearch.setDisable(false);
            area1.setDisable(false);
            area2.setDisable(true);
            area3.setDisable(true);
            area4.setDisable(true);
            area5.setDisable(true);
            area6.setDisable(true);
            area7.setDisable(true);
            area1.clear();
            area2.clear();
            area3.clear();
            area4.clear();
            area5.clear();
            area6.clear();
            area7.clear();
            label1.setText("Trip ID");
            label2.setText("Route ID");
            label3.setText("Block ID");
            label4.setText("Service ID");
            label5.setText("Trip Headsign");
            label6.setText("Shape ID");
            label7.setText("Direction ID");
        });
        MenuItem u4 = new MenuItem("Update Stop Time");
        u4.setOnAction((event) -> {
            updateButton.setDisable(true);
            updateMenu.setText("Update Stop Time");
            updateSearch.setDisable(false);
            area1.setDisable(false);
            area5.setDisable(false);
            area2.setDisable(true);
            area3.setDisable(true);
            area4.setDisable(true);
            area6.setDisable(true);
            area7.setDisable(true);
            area1.clear();
            area2.clear();
            area3.clear();
            area4.clear();
            area5.clear();
            area6.clear();
            area7.clear();
            label1.setText("Stop ID");
            label2.setText("Arrival Time");
            label3.setText("Departure Time");
            label4.setText("Route Long Name");
            label5.setText("Trip ID");
            label6.setText("top Sequence");
            label7.setText("Stop Headsign");
        });
        updateButton.setOnAction((event) -> {
            if (updateMenu.getText().equals("Update Stop")) {
                Stop stop = allStops.searchStopId(area1.getText());
                stop.setStopDesc(area2.getText());
                stop.setStopId(area1.getText());
                stop.setStopLat(Double.parseDouble(area5.getText()));
                stop.setStopLon(Double.parseDouble(area6.getText()));
                stop.setStopName(area3.getText());
                oStops.display();
            } else if (updateMenu.getText().equals("Update Route")) {
                Route route = allRoutes.searchRouteId(area1.getText());
                route.setAgencyID(area4.getText());
                route.setRouteId(area1.getText());
                route.setRouteShortName(area2.getText());
                route.setRouteLongName(area3.getText());
                route.setRouteDesc(area5.getText());
                route.setRouteColor(area6.getText());
                route.setRouteTextColor(area7.getText());
                oRoutes.display();
            } else if (updateMenu.getText().equals("Update Trip")) {
                Trip trip = allTrips.searchTripId(area1.getText());
                trip.setTripId(area1.getText());
                trip.setRouteId(area2.getText());
                trip.setBlockId(area3.getText());
                trip.setServiceId(area4.getText());
                trip.setTripHeadsign(area5.getText());
                trip.setShapeId(area6.getText());
                trip.setDirectionId(area7.getText());
                oTrips.display();
            } else if (updateMenu.getText().equals("Update Stop Time")) {
                String stopId = area1.getText();
                String tripId = area5.getText();
                Trip trip = allTrips.searchTripId(tripId);
                StopTimes st = trip.getStopTimesInTrip();
                StopTime s = st.searchStopIdTimes(stopId);
                s.setStopId(area1.getText());
                s.setArrivalTime(area2.getText());
                s.setDepartureTime(area3.getText());
                s.setStopHeadsign(area4.getText());
                s.setTripId(area5.getText());
                s.setDropOffType(area6.getText());
                s.setPickUpType(area7.getText());
                oStopTimes.display();
            }

            label1.setText("");
            label2.setText("");
            label3.setText("");
            label4.setText("");
            label5.setText("");
            label6.setText("");
            label7.setText("");
            area1.setText("");
            area2.setText("");
            area3.setText("");
            area4.setText("");
            area5.setText("");
            area6.setText("");
            area7.setText("");
            area1.setDisable(false);
            area2.setDisable(false);
            area3.setDisable(false);
            area4.setDisable(false);
            area5.setDisable(false);
            area6.setDisable(false);
            area7.setDisable(false);
        });
        ObservableList<MenuItem> uList = FXCollections.observableArrayList();
        uList.addAll(u1, u2, u3, u4);
        updateMenu.getItems().addAll(uList);

        MenuItem view1 = new MenuItem("View Routes");
        view1.setOnAction((event) -> {
            oRoutes.display();
            viewButton.setText("View Routes");
        });
        MenuItem view2 = new MenuItem("View Trips");
        view2.setOnAction((event) -> {
            oTrips.display();
            viewButton.setText("View Trips");
        });
        MenuItem view3 = new MenuItem("View StopTimes");
        view3.setOnAction((event) -> {
            oStopTimes.display();
            viewButton.setText("View StopTimes");
        });
        MenuItem view4 = new MenuItem("View Stops");
        view4.setOnAction((event) -> {
            oStops.display();
            viewButton.setText("View Stops");
        });
        ObservableList<MenuItem> vList = FXCollections.observableArrayList();
        vList.addAll(view1, view2, view3, view4);
        viewButton.getItems().addAll(vList);

        searchBox.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                search();
            }
        });

        anchorPane.setBackground(new Background
                (new BackgroundFill
                        (Color.color(.7490196078, .7490196078, .7490196078), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    /**
     * Exports the Trips to the file.
     *
     * @param e The button being pressed.
     * @author Alexander Gnabasik
     */
    @FXML
    private void exportTrips(ActionEvent e) {
        try {
            File file;
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.txt"));
            file = chooser.showSaveDialog(anchorPane.getScene().getWindow());
            allTrips.export(file);
        } catch (NullPointerException f) {

        }
    }

    /**
     * Exports the Routes to the file.
     *
     * @param e The button being pressed.
     * @author Alexander Gnabasik
     */
    @FXML
    private void exportRoutes(ActionEvent e) {
        try {
            File file;
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.txt"));
            file = chooser.showSaveDialog(anchorPane.getScene().getWindow());
            allRoutes.export(file);
        } catch (NullPointerException f) {

        }
    }

    /**
     * Exports the Stops to the file.
     *
     * @param e The button being pressed.
     * @author Alexander Gnabasik
     */
    @FXML
    private void exportStops(ActionEvent e) {
        try {
            File file;
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.txt"));
            file = chooser.showSaveDialog(anchorPane.getScene().getWindow());
            allStops.export(file);
        } catch (NullPointerException f) {

        }
    }

    /**
     * Exports all the StopTimes to the file
     *
     * @param e The button being pressed.
     * @author Alexander Gnabasik
     */
    @FXML
    private void exportStopTimes(ActionEvent e) {
        try {
            File file;
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.txt"));
            file = chooser.showSaveDialog(anchorPane.getScene().getWindow());
            allStopTimes.export(file);
        } catch (NullPointerException f) {

        }
    }

    /**
     * filters the routes for the routes containing a specific stop
     *
     * @param stopId the ID of the stop to be searched for.
     * @author Alexander Gnabasik
     */
    public Routes filterRoutes(String stopId) {
        return allRoutes.searchRoutesForStop(stopId);
    }

    /**
     * Imports the GTFS file and filters them to call their respective import methods
     *
     * @author Noe Gonzalez
     */
    @FXML
    private void importFile() {
        File file;
        String identifierOne = "";
        String identifierTwo = "";
        int i = 1;
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.txt"));
        try {
            file = chooser.showOpenDialog(anchorPane.getScene().getWindow());
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter(",");
            while (scanner.hasNext() && i <= 2) {
                if (i == 1) {
                    identifierOne = scanner.next();
                } else if (i == 2) {
                    identifierTwo = scanner.next();
                }
                i++;
            }
            if (identifierOne.equalsIgnoreCase("stop_id")) {
                importStop(file);
                stopsImported = true;
                exportStopsButton.setDisable(false);
            } else if (identifierOne.equalsIgnoreCase("route_id") && identifierTwo.equalsIgnoreCase("service_id")) {
                importTrip(file);
                tripsImported = true;
                exportTripsButton.setDisable(false);
            } else if (identifierOne.equalsIgnoreCase("route_id") && identifierTwo.equalsIgnoreCase("agency_id")) {
                importRoute(file);
                routesImported = true;
                exportRoutesButton.setDisable(false);
            } else if (identifierOne.equalsIgnoreCase("trip_id") && identifierTwo.equalsIgnoreCase("arrival_time")) {
                importStopTime(file);
                stopTimesImported = true;
                exportStopTimesButton.setDisable(false);
            } else {
                JOptionPane.showMessageDialog(null, "We are currently not accepting this file type");
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Please select a valid file");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Invalid file used");
        }
        if (stopsImported && tripsImported && routesImported && stopTimesImported) {
            searchItem.setDisable(false);
            searchBox.setDisable(false);
            updateMenu.setDisable(false);
            viewButton.setDisable(false);
            allRoutes.updateRoutes();
        }
    }

    /**
     * Imports the routes GTFS files
     *
     * @param file the Route GTFS file
     * @author Alexander Gnabasik
     */
    private void importRoute(File file) {
        allRoutes.importFile(file);
        oRoutes.display();
    }

    /**
     * Imports the stops objects
     *
     * @param file The GTFS stops file.
     * @author Alexander Gnabasik
     */
    private void importStop(File file) {
        allStops.importFile(file);
        oStops.display();
    }

    /**
     * imports the stoptimes
     *
     * @param file the GTFS stoptimes file.
     * @author Alexander Gnabasik
     */
    private void importStopTime(File file) {
        allStopTimes.importFile(file);
        oStopTimes.display();
    }

    /**
     * imports the trips
     *
     * @param file the GTFS trips file
     * @author Alexander Gnabasik
     */
    private void importTrip(File file) {
        tripIds = allTrips.importFile(file);
        oTrips.display();
    }

    /**
     * Verifies the route is in the allRoutes object
     *
     * @param routeId The route ID to be verified
     * @author Alexander Gnabasik
     */
    private boolean routeVerify(String routeId) {
        return allRoutes.searchRouteId(routeId) != null;
    }


    /**
     * Searches allStops for a stop with stopId
     *
     * @param stopId
     * @author Alexander Gnabasik
     */
    public Stop searchStopId(String stopId) {
        return allStops.searchStopId(stopId);
    }

    /**
     * Searches for the next trip by a stop id
     *
     * @author Trey Gallun
     * @author Trey Gallun
     */
    private Trip searchForNextTrip(String stopID) {
        StopTime nextStopTime = null;
        int currentTime = (int) (System.nanoTime() * 1000000000);
        StopTimes stoptimes = allStopTimes.searchStopTimesForStop(stopID);
        System.out.println("Found stop times: " + stoptimes.getColOfStopTimes().size());
        for (StopTime stopTime : stoptimes.getColOfStopTimes()) {
            if (nextStopTime == null) {
                nextStopTime = stopTime;
            } else if (stopTime.getArrivalTimeInSeconds() > currentTime && nextStopTime.getArrivalTimeInSeconds() > stopTime.getArrivalTimeInSeconds()) {
                nextStopTime = stopTime;
            } else if (nextStopTime.getArrivalTimeInSeconds() < stopTime.getArrivalTimeInSeconds()) {
                nextStopTime = stopTime;
            }
        }
        return allTrips.searchTripId(nextStopTime.getTripId());
    }

    /**
     * Verifies the Stop is in the program.
     *
     * @param stopId the ID to the stop
     * @return if the stop is found.
     * @author Alexander Gnabasik
     */
    private boolean stopVerify(String stopId) {
        return searchStopId(stopId) != null;
    }

    /**
     * Verifies the trip with the tripId is in the program
     *
     * @param tripId the specified tripId
     * @return if the trip is found
     */
    private boolean tripVerify(String tripId) {
        return allTrips.searchTripId(tripId) != null;
    }

    /**
     * Updates the allRoutes Object.
     *
     * @author Noe Gonzalez
     */
    public void update() {
        allRoutes.updateRoutes();
    }

    /**
     * Checks to see which search type was selected, then searches through appropriate data,
     * then populates listView.
     *
     * @author Noe Gonzalez
     */
    @FXML
    public void search() {
        if (searchForRoutes) {
            if (stopVerify(searchBox.getText())) {
                Stop stop = allStops.searchStopId((searchBox.getText()));
                oRoutesListView.display(stop);
            }
        } else if (searchForStops) {
            if (routeVerify(searchBox.getText())) {
                Route temp = allRoutes.searchRouteId(searchBox.getText());
                oStopListView.display(temp);
            }
        } else if (searchForTrips) {
            if (routeVerify(searchBox.getText())) {
                Route temp = allRoutes.searchRouteId(searchBox.getText());
                oRoutesListView.display(temp, allStopTimes);
            }
        } else if (searchForNextTrip) {
            if (stopVerify(searchBox.getText())) {
                oTrips.display(searchForNextTrip(searchBox.getText()));
            }
        }
    }

    /**
     * Controller for the update search
     *
     * @param actionEvent the Button
     * @author Alexander Gnabasik
     */
    public void updateSearch(ActionEvent actionEvent) {
        updateButton.setDisable(false);
        try {
            String t = updateMenu.getText();
            if (t.equals("Update Stop")) {
                String stopId = area1.getText();
                Stop st = allStops.searchStopId(stopId);
                area1.setText(stopId);
                area2.setText(st.getStopDesc());
                area3.setText(st.getStopName());
                area4.setText("");
                area5.setText(Double.toString(st.getStopLat()));
                area6.setText(Double.toString(st.getStopLon()));
                area7.setText("");
                area2.setDisable(false);
                area3.setDisable(false);
                area4.setDisable(true);
                area5.setDisable(false);
                area6.setDisable(false);
                area7.setDisable(true);
                updateButton.setDisable(false);

            } else if (t.equals("Update Trip")) {
                String tripId = area1.getText();
                Trip tr = allTrips.searchTripId(tripId);
                area1.setText(tripId);
                area2.setText(tr.getRouteId());
                area3.setText(tr.getBlockId());
                area4.setText(tr.getServiceId());
                area5.setText(tr.getTripHeadsign());
                area6.setText(tr.getShapeId());
                area7.setText(tr.getDirectionId());
                area2.setDisable(false);
                area3.setDisable(false);
                area4.setDisable(false);
                area5.setDisable(false);
                area6.setDisable(false);
                area7.setDisable(false);

            } else if (t.equals("Update Route")) {
                String routeId = area1.getText();
                Route r = allRoutes.searchRouteId(routeId);
                area1.setText(routeId);
                area2.setText(r.getShortName());
                area3.setText(r.getLongName());
                area4.setText(r.getAgencyID());
                area5.setText(r.getRouteDesc());
                area6.setText(r.getRouteColor());
                area7.setText(r.getRouteTextColor());
                area2.setDisable(false);
                area3.setDisable(false);
                area4.setDisable(false);
                area5.setDisable(false);
                area6.setDisable(false);
                area7.setDisable(false);

            } else if (t.equals("Update Stop Time")) {
                String stopId = area1.getText();
                String tripId = area5.getText();
                Trip trip = allTrips.searchTripId(tripId);
                StopTimes st = trip.getStopTimesInTrip();
                StopTime s = st.searchStopIdTimes(stopId);
                area1.setText(stopId);
                area2.setText(s.getArrivalTime());
                area3.setText(s.getDepartureTime());
                area4.setText(s.getStopHeadsign());
                area5.setText(tripId);
                area6.setText(s.getDropOffType());
                area7.setText(s.getPickUpType());
                area2.setDisable(false);
                area3.setDisable(false);
                area4.setDisable(false);
                area6.setDisable(false);
                area7.setDisable(false);
            }
        } catch (NullPointerException e) {
            System.err.println("Could not find an item with the specified attributes");
            JOptionPane.showMessageDialog(null, "Could not find an item with the specified attributes");
        }
    }

    /**
     * Initializes the GoogleMap object and passes it into googleMapsWebView.
     *
     * @author Alexander Gnabasik.
     */
    @Override
    public void mapInitialized() {
        googleMapsWebView = new GoogleMapsWebView(googleMapView, allStops);
        allStops.attach(googleMapsWebView);
    }
}// end Controller