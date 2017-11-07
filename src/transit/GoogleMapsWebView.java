/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <gnabasikat@msoe.edu, gonzalezn@msoe.edu, galluntf@msoe.edu> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy us a beer in return Alexander Gnabasik, Noe Gonzalez, Trey Gallun.
 * ----------------------------------------------------------------------------
 */
package transit;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.*;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Is the GoogleMapsWebView
 *
 * @author Alexander Gnabasik
 * @created 11/4/2017
 */
public class GoogleMapsWebView implements Observer {
    private GoogleMapView googleMapView;
    private Stops allStops;
    private GoogleMap googleMap;
    private LinkedHashSet<Marker> markers;

    /**
     * Constructs the GoogleMapWebView.
     *
     * @param googleMapView The JavaFX GoogleMapWebView
     * @param allStops      The Stops object containing all Stops.
     * @author Alexander Gnabasik
     */
    GoogleMapsWebView(GoogleMapView googleMapView, Stops allStops) {
        this.googleMapView = googleMapView;
        this.allStops = allStops;
        this.googleMap = createMap(new LatLong(43.039160, -87.897019), 8);
        markers = new LinkedHashSet<>();
    }

    /**
     * Updates the Observer from the subject.
     *
     * @param obj The subject it is attached to.
     * @author Alexander Gnabasik
     */
    @Override
    public void update(Object obj) {
        this.allStops = (Stops) obj;
        updateCoordinateData();
        updateMarkers();
    }

    /**
     * Updates the coordinate markers data for the googleMap.
     *
     * @author Alexander Gnabasik
     */
    public void updateCoordinateData() {
        Collection<Stop> stops = allStops.getColOfStops();
        Marker marker;
        LatLong latLong;
        MarkerOptions markerOptions;
        double lat;
        double lon;
        for (Stop s : stops) {
            markerOptions = new MarkerOptions();
            lat = s.getStopLat();
            lon = s.getStopLon();
            latLong = new LatLong(lat, lon);
            markerOptions.position(latLong);
            marker = new Marker(markerOptions);
            marker.setTitle("Short Name: " + s.getStopName() + "\r\nStopId: " + s.getStopId() + "\r\nLatitude: " +
                    lat + "\r\nLongitude: " + lon + "\r\nDescription: " + s.getStopDesc());
            markers.add(marker);
        }
    }

    /**
     * Updates the Markers in the googleMap.
     *
     * @author Alexander Gnabasik
     */
    public void updateMarkers() {

        LatLong c = googleMap.getCenter();
        Integer z = googleMap.getZoom();
        googleMap = createMap(c, z);
        googleMap.addMarkers(markers);
        displayGMap(googleMap);
    }

    private void displayGMap(GoogleMap gMap) {
        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
        InfoWindow infoWindow = new InfoWindow(infoWindowOptions);
        infoWindow.open(gMap);
    }

    /**
     * Creates a new GoogleMap to display.
     *
     * @return The new GoogleMap object.
     * @author Alexander Gnabasik.
     */
    public GoogleMap createMap(LatLong center, Integer zoom) {
        MapOptions mapOptions = new MapOptions();
        mapOptions.center(center)
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(true)
                .rotateControl(false)
                .scaleControl(true)
                .streetViewControl(false)
                .zoomControl(true)
                .zoom(zoom);
        return googleMapView.createMap(mapOptions);
    }

}
