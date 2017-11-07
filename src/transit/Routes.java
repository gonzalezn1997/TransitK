/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <gnabasikat@msoe.edu, gonzalezn@msoe.edu, galluntf@msoe.edu> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy us a beer in return Alexander Gnabasik, Noe Gonzalez, Trey Gallun.
 * ----------------------------------------------------------------------------
 */

package transit;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

/**
 * A collection of routes and associated methods
 *
 * @author Trey Gallun
 * @version 1.0
 * @created 05-Oct-2017 12:10:53 PM
 */

public class Routes implements Subject, ExportImport {

    private Stops allStops;
    private Trips allTrips;
    private StopTimes allStopTimes;
    private Collection<Route> colOfRoutes;
    private Collection<Observer> observers;

    /**
     * Constructs the Routes object
     *
     * @param colOfRoutes  The collection of Route objects.
     * @param allStops     The Stops object containing all Stops.
     * @param allTrips     The Trips object containing all Trips.
     * @param allStopTimes The StopTimes object containing all StopTimes.
     * @author Trey Gallun
     */
    public Routes(Collection<Route> colOfRoutes, Stops allStops, Trips allTrips, StopTimes allStopTimes) {
        this(colOfRoutes);
        this.allStops = allStops;
        this.allTrips = allTrips;
        this.allStopTimes = allStopTimes;
    }

    /**
     * main constructor
     *
     * @param colOfRoutes: collection of routes
     * @author Trey Gallun
     */
    public Routes(Collection<Route> colOfRoutes) {
        this.colOfRoutes = colOfRoutes;
        observers = new ArrayList<>();
    }

    public Collection<Route> getColOfRoutes() {
        return colOfRoutes;
    }

    /**
     * adds a route to collection
     *
     * @param route: a route
     * @author Trey Gallun
     */
    public void add(Route route) {
        colOfRoutes.add(route);
        update(this);
    }

    /**
     * removes a route from collection
     *
     * @param routeId: route_ID
     * @return route removed from collection
     * @author Trey Gallun
     */
    public Route remove(String routeId) {
        Route route = searchRouteId(routeId);
        colOfRoutes.remove(route);
        update(this);
        return route;
    }

    /**
     * attaches an observer
     *
     * @param observer: the observer to attach
     * @author Trey Gallun
     */
    @Override
    public void attach(Observer observer) {
        observers.add(observer);
        update(this);
    }

    /**
     * detach an observer
     *
     * @param observer: observer to detach
     * @return the observer that was detached
     * @author Trey Gallun
     */
    @Override
    public Observer detach(Observer observer) {
        observers.remove(observer);
        return observer;
    }

    /**
     * updates the observers
     *
     * @param object this does nothing yet, satisfies interface
     * @author Trey Gallun
     */
    public void update(Object object) {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    /**
     * updates the routes
     *
     * @author Trey Gallun
     */
    public void updateRoutes() {
        int i = 0;
        for (Route route : colOfRoutes) {
            route.updateRoute();
        }
        update(this);
    }

    /**
     * searches for a route with given id
     *
     * @param routeId: route_ID
     * @return route with id, null if not present
     * @author Trey Gallun
     */
    public Route searchRouteId(String routeId) {
        for (Route route : colOfRoutes) {
            if (route.getId().equals(routeId))
                return route;
        }
        return null;
    }

    /**
     * searches routes in collection for a stop with given stop id
     *
     * @param stopId: stop_ID
     * @return a collection of routes containing the stop given the stop id
     * @author Trey Gallun
     */
    public Routes searchRoutesForStop(String stopId) {
        Routes routes = new Routes(new ArrayList<>());
        for (Route route : colOfRoutes) {
            if (route.getStopsInRoute().searchStopId(stopId) != null)
                routes.add(route);
        }
        return routes;
    }

    /**
     * exports route collection to given file
     *
     * @param file this is the file that will be written to
     * @return return the file written
     */
    @Override
    //TODO throw exceptions
    public File export(File file) {
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write("route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color\n");
            for (Route route : colOfRoutes) {
                route.export(printWriter);
            }
            printWriter.close();
        } catch (FileNotFoundException e) {
            System.err.println(("File: " + file.getName() + " could not be found!"));
        }
        return file;
    }


    /**
     * Imports the file to individual Route objects and populates the collection
     *
     * @param file The .GTFS file
     * @return boolean if the import was successful.
     * @author Trey Gallun
     */
    //TODO throw exceptions to controller
    public boolean importFile(File file) {
        try {
            Scanner in = new Scanner(file);
            in.nextLine();
            while (in.hasNextLine()) {
                ArrayList<String> data = splitAttributes(in.nextLine());
                colOfRoutes.add(new Route(data.get(0), data.get(1), data.get(2), data.get(3), data.get(4), data.get(5),
                        data.get(6), data.get(7), data.get(8), allStops, allTrips, allStopTimes));
            }
            update(this);
            return true;
        } catch (FileNotFoundException e) {
            System.err.println("File: " + file.getName() + " could not be found!");
            return false;
        }
    }

    /**
     * Splits the attributes into an ArrayList
     *
     * @param line: the line to split
     * @return the ArrayList of the split data
     * @author Trey Gallun
     */
    public ArrayList<String> splitAttributes(String line) {
        ArrayList<String> data = new ArrayList<>(Arrays.asList(line.split(",")));
        while (data.size() != 9) {
            data.set(4, data.get(4) + "," + data.get(5));
            data.remove(5);
        }
        return data;
    }
}