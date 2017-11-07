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
import java.util.*;

/**
 * @author Alexander Gnabasik
 * @version 1.0
 * @created 05-Oct-2017 12:10:58 PM
 * <p>
 * A collection of StopTime objects.
 */
public class StopTimes implements Subject, ExportImport {

    private Collection<Observer> observers;
    private Collection<StopTime> colOfStopTimes;

    /**
     * Constructs the StopTimes object
     *
     * @param colOfStopTimes a collection of stoptimes.
     * @author Alexander Gnabasik
     */
    public StopTimes(Collection<StopTime> colOfStopTimes) {
        this.colOfStopTimes = colOfStopTimes;
        observers = new ArrayList<>();
    }

    public Collection<StopTime> getColOfStopTimes() {
        return colOfStopTimes;
    }

    /**
     * Adds a stopTime to the collection of stopTimes
     *
     * @param stopTime stopTime to be added to the collection of stopTimes
     * @author Alexander Gnabasik
     */
    public void add(StopTime stopTime) {
        colOfStopTimes.add(stopTime);
        update(this);
    }


    /**
     * Removes a StopTime associated with a specified stop.
     *
     * @param stopId the ID of the StopTime of a specified stop to be removed.
     * @return StopTime removed if found, or null if not found.
     */
    public StopTime removeByStopId(String stopId) {
        StopTime stopTime = searchStopIdTimes(stopId);
        if (stopTime != null) {
            if (colOfStopTimes.remove(stopTime)) {
                update(this);
                return stopTime;
            }
        }
        return null;
    }

    /**
     * Searches the StopTimes for a specified stop.
     *
     * @param stopId the ID of the stop
     * @return null if not found, StopTime if found
     * @author Alexander Gnabasik
     */
    public StopTime searchStopIdTimes(String stopId) {
        for (StopTime s : colOfStopTimes) {
            if (s.getStopId().equals(stopId)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Searches the StopTimes for a specified stop and returns all stop times with that stop.
     *
     * @param stopID the ID of the stop
     * @return a stoptimes of all stoptime objects
     * @author Trey Gallun
     */
    public StopTimes searchStopTimesForStop(String stopID) {
        StopTimes stopTimes = new StopTimes(new LinkedList<>());
        for (StopTime stopTime : colOfStopTimes) {
            if (stopTime.getStopId().equals(stopID)) {
                stopTimes.add(stopTime);
            }
        }
        return stopTimes;
    }

    /**
     * Imports the file into a collection of StopTimes objects
     *
     * @param file The StopTimes file to be read.
     * @return false if the file is ill-formatted, but true if successful at putting the data into the StopTimes object.
     * @author Alexander Gnabasik
     */
    public boolean importFile(File file) {
        try {
            Scanner in = new Scanner(file);
            if (in.hasNextLine()) {
                String attributes = in.nextLine();
                if (checkAttributes(attributes)) {
                    while (in.hasNextLine()) {
                        String stopHeadSign = "";
                        String pickUpType = "";
                        String dropOffType = "";
                        String allData = in.nextLine();
                        String[] data = allData.split(",");
                        String tripId = data[0];
                        String arrivalTime = data[1];
                        String departureTime = data[2];
                        String stopId = data[3];
                        String stopSequence = data[4];
                        if (data.length > 5) {
                            stopHeadSign = data[5];
                            if (data.length > 6) {
                                pickUpType = data[6];
                                if (data.length > 7) {
                                    dropOffType = data[7];
                                }
                            }
                        }
                        if (timeFormatCorrect(arrivalTime) && timeFormatCorrect(departureTime) && idStringCheck(stopId)
                                && sequenceCheck(stopSequence) && idStringCheck(tripId)) {
                            StopTime stopTime = new StopTime(arrivalTime, departureTime, stopId, Integer.parseInt(stopSequence), tripId,
                                    stopHeadSign, pickUpType, dropOffType);
                            colOfStopTimes.add(stopTime);
                            update(this);
                        } else {
                            System.err.println("Could not import StopTime; StopTime Attribute Malformed");
                        }
                    }
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            System.err.println("Could not import file; File Malformed");
        }
        return false;
    }

    /**
     * Exports the entire collection of StopTimes.
     *
     * @param file the file to export to.
     * @return the file that the stopTimes were saved to.
     * @author Alexander Gnabasik
     */
    public File export(File file) {
        try {
            Writer writer = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.write("trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type\r\n");
            Iterator i = colOfStopTimes.iterator();
            while (i.hasNext()) {
                StopTime stopTime = (StopTime) i.next();
                stopTime.export(printWriter);
            }
            printWriter.close();
        } catch (IOException e) {
            System.err.println("Could not export file.");
        }
        return file;
    }

    /**
     * Searches for all StopTimes that contain a specified tripID.
     *
     * @param tripId tripID to be searched for
     * @return a StopTimes object containing all the stoptimes within a specific trip.
     * @author Alexander Gnabasik
     */
    public StopTimes searchTripIdTimes(String tripId) {
        ArrayList<StopTime> a = new ArrayList<>();
        StopTimes stopTimes = new StopTimes(a);
        Iterator i = colOfStopTimes.iterator();
        while (i.hasNext()) {
            StopTime stopTime = (StopTime) i.next();
            if (stopTime.getTripId().equals(tripId)) {
                stopTimes.add(stopTime);
            }
        }
        if (stopTimes.getColOfStopTimes().size() > 0) {
            return stopTimes;
        } else {
            return null;
        }
    }

    /**
     * Adds an observer
     *
     * @param observer An observer of the StopTimes
     * @author Alexander Gnabasik
     */
    @Override
    public void attach(Observer observer) {
        observers.add(observer);
        update(this);
    }

    /**
     * Removes an observer
     *
     * @param observer The observer to remove.
     * @return The observer that was removed.
     * @author Alexander Gnabasik
     */
    @Override
    public Observer detach(Observer observer) {
        if (observers.contains(observer)) {
            observers.remove(observer);
            return observer;
        } else {
            return null;
        }
    }

    /**
     * Updates the observer.
     *
     * @param obj Itself
     * @author Alexander Gnabasik
     */
    @Override
    public void update(Object obj) {
        Iterator i = observers.iterator();
        while (i.hasNext()) {
            Observer o = (Observer) i.next();
            o.update(this);
        }
    }

    /**
     * Checks to see if the arrivalTime and departureTime are formatted correctly.
     *
     * @param time The time to be checked for proper format.
     * @return Whether the time String is formatted correctly.
     * @author Alexander Gnabasik
     */
    public boolean timeFormatCorrect(String time) {
        if (time.contains(":")) {
            String[] timeString = time.split(":");
            if (timeString.length == 3) {
                int hour = Integer.parseInt(timeString[0]);
                int minute = Integer.parseInt(timeString[1]);
                int second = Integer.parseInt(timeString[2]);
                if (hour <= 26 && hour >= 0 && minute < 60 && minute >= 0 &&
                        second < 60 && second >= 0) {
                    return true;
                }
            }
        }
        System.out.println("Malformed Time in StopTimes: " + time);
        return false;
    }

    /**
     * Checks an ID String to see if it has a length greater than 0.
     *
     * @param id the ID String
     * @return if id has a length greater than 0.
     * @author Alexander Gnabasik
     */
    public boolean idStringCheck(String id) {
        return id.length() > 0;
    }

    /**
     * Checks to see if the sequence is a int number greater than 0.
     *
     * @param sequence A string of the sequence number.
     * @return Whether the sequence is a an int greater than 0.
     * @author Alexander Gnabasik
     */
    public boolean sequenceCheck(String sequence) {
        try {
            int s = Integer.parseInt(sequence);
            return s >= 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks to see if a StopTime is contained in the StopTimes.
     *
     * @param stopTime a StopTime object to be checked.
     * @return if the StopTime object is contained in StopTimes.
     * @author Alexander Gnabasik
     */
    public boolean contains(StopTime stopTime) {
        return colOfStopTimes.contains(stopTime);
    }

    /**
     * Checks to make sure the attributes are correct for a StopTimes GTFS file.
     *
     * @param attributes The String of the first line of a StopTimes GTFS file.
     * @return If the attributes are correct for a StopTimes GTFS file.
     * @author Alexander Gnabasik
     */
    public boolean checkAttributes(String attributes) {
        if (attributes.contains(",")) {
            String[] a = attributes.split(",");
            if (a.length == 8) {
                if (a[0].equals("trip_id") && a[1].equals("arrival_time") && a[2].equals("departure_time")
                        && a[3].equals("stop_id") && a[4].equals("stop_sequence") && a[5].equals("stop_headsign")
                        && a[6].equals("pickup_type") && a[7].equals("drop_off_type")) {
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }


}//end StopTimes