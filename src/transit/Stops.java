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
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * A collection of stops
 *
 * @author Trey Gallun
 * @version 1.0
 * @created 05-Oct-2017 12:10:56 PM
 */

public class Stops implements Subject, ExportImport {

    private Collection<Stop> colOfStops;
    private Collection<Observer> observers;

    /**
     * default constructor
     */
    public Stops() {
        this(new ArrayList<>());
    }

    /**
     * main constructor
     *
     * @param colOfStops: stop collection
     */
    public Stops(Collection<Stop> colOfStops) {
        this.colOfStops = colOfStops;
        observers = new ArrayList<>();
    }

    public Collection<Stop> getColOfStops() {
        return colOfStops;
    }

    /**
     * adds a stop to the collection
     *
     * @param stop: stop
     * @author Trey Gallun
     */
    public void add(Stop stop) {
        colOfStops.add(stop);
        update(this);
    }

    /**
     * removes a stop from the collection using given stopID.
     *
     * @param stopId: stop_ID
     * @return the stop removed
     * @author Trey Gallun
     */
    public Stop remove(String stopId) {
        Stop stop = searchStopId(stopId);
        colOfStops.remove(stop);
        update(this);
        return stop;
    }

    /**
     * attaches an observer
     *
     * @param observer: an observer
     * @author Trey Gallun
     */
    public void attach(Observer observer) {
        observers.add(observer);
        update(this);
    }

    /**
     * detaches and observer
     *
     * @param observer: the observer to detach
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
     * @param obj: an object to satisfy the interface, does nothing currently
     * @author Trey Gallun
     */
    @Override
    public void update(Object obj) {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    /**
     * Searches for a stop using given stopID
     *
     * @param stopId: stop_ID
     * @return the stop with the stopID
     * @author Trey Gallun
     */
    public Stop searchStopId(String stopId) {
        for (Stop stop : colOfStops) {
            if (stop.getStopId().equals(stopId))
                return stop;
        }
        return null;
    }

    /**
     * exports stop collection to given file
     *
     * @param file this is the file that will be written to
     * @return return the file written
     * @author Trey Gallun
     */
    //TODO throw exceptions
    @Override
    public File export(File file) {
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write("stop_id,stop_name,stop_desc,stop_lat,stop_lon\n");
            for (Stop stop : colOfStops) {
                stop.export(printWriter);
            }
            printWriter.close();
        } catch (FileNotFoundException e) {
            System.err.println("File: " + file.getName() + " could not be found!");
        }
        return file;
    }

    /**
     * Imports the file to individual Stop objects
     *
     * @param file The .GTFS file
     * @return If the import was successful.
     * @author Trey Gallun
     */
    //TODO throw exceptions to controller
    public boolean importFile(File file) {
        try {
            Scanner in = new Scanner(file);
            in.nextLine();
            while (in.hasNextLine()) {
                ArrayList<String> data = splitAttributes(in.nextLine());
                if (checkLat(Double.parseDouble(data.get(3))) && checkLon(Double.parseDouble(data.get(4))))
                    colOfStops.add(new Stop(data.get(0), data.get(1), data.get(2), Double.parseDouble(data.get(3)), Double.parseDouble(data.get(4))));
                else
                    System.out.println("INVALID LAT OR LON");
            }
            update(this);
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File: " + file.getName() + " could not be found!");
            return false;
        }
    }

    /**
     * Checks latitude for proper value
     *
     * @param latitude: the latitude
     * @return boolean if latitude is valid
     * @author Trey Gallun
     */
    public boolean checkLat(double latitude) {
        return latitude <= 90 && latitude >= -90;
    }

    /**
     * Checks longitude for proper value
     *
     * @param longitude: the longitude
     * @return boolean if longitude is valid
     * @author Trey Gallun
     */
    public boolean checkLon(double longitude) {
        return longitude <= 180 && longitude >= -180;
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
        while (data.size() != 5) {
            data.set(2, data.get(2) + "," + data.get(3));
            data.remove(3);
        }
        return data;
    }
}