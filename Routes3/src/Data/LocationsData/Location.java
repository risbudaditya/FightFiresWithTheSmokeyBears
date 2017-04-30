package Data.LocationsData;

import Google.Coordinate;

/**
 * Created by tyler on 4/30/2017.
 */
public class Location implements Coordinate {
    public String lat;
    public String lon;
    public String num;

    @Override
    public String getLat() {
        return lat;
    }

    @Override
    public String getLon() {
        return lon;
    }

    public String getCoords() {
        return lat+","+lon;
    }

}
