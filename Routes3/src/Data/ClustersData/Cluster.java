package Data.ClustersData;

import Google.Coordinate;

/**
 * Created by tyler on 4/29/2017.
 */
public class Cluster implements Coordinate {
    public String lat;
    public String lon;
    public Long size;

    @Override
    public String getLon() {
        return lon;
    }

    public String getLat() {
        return lat;
    }

    public String toString() {
        return "lat: "+lat+" lon: "+lon+" size: "+size;
    }

    public String getCoords() {
        return lat+","+lon;
    }
}
