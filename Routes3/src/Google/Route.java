package Google;

import Data.LocationsData.Location;
import com.google.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by tyler on 4/30/2017.
 */
public class Route extends ArrayList<Coordinate> {
    public static final String PREFIX = "https://www.google.com/maps/dir";
    public static final String DELIMITER = "/";

    public String getURL() {
        StringBuilder aBuilder = new StringBuilder(PREFIX);
        Iterator<Coordinate> it = this.iterator();
        while(it.hasNext()) {
            Coordinate aCoord = it.next();
            aBuilder.append(DELIMITER+aCoord.getCoords());
        }
        return aBuilder.toString();
    }

    public void addLatLong(LatLng latLong) {
        Location aLocation = new Location();
        aLocation.lat  = new Double(latLong.lat).toString();
        aLocation.lon  = new Double(latLong.lng).toString();
    }

}
