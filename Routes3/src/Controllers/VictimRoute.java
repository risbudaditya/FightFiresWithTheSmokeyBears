package Controllers;

import Data.ClustersData.Cluster;
import Data.ClustersData.Clusters;
import Data.Data;
import Data.FiresData.Fires;
import Data.LocationsData.Location;
import Google.Coordinate;
import Google.Directions;
import Google.Route;
import com.google.maps.OkHttpRequestHandler;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsRoute;

import java.io.IOException;
import java.time.Duration;

/**
 * Created by tyler on 4/29/2017.
 */
public class VictimRoute {



    public VictimRoute() throws Exception {
    }


    public String getRouteToNearestCluster(Data data, Location location) throws InterruptedException, ApiException, IOException {

        Cluster cluster = getClosestCluster(data, location);
        return getRouteToClusterURL(data, location, cluster);
    }

    public Cluster getClosestCluster(Data data, Location location) throws InterruptedException, ApiException, IOException {
        Directions aDirections = new Directions();
        long shortest = Long.MAX_VALUE;
        Cluster closest = null;
        for(Cluster c : data.clusters) {
            long aDuration = aDirections.duration(data.aGCTX, location, c);
            if(closest==null||aDuration<shortest) {
                shortest = aDuration;
                closest = c;
            }
        }
        return closest;
    }

    public String getRouteToClusterURL(Data data, Location location, Cluster cluster) {
        Route aRoute = new Route();
        aRoute.add(location);
        aRoute.add(cluster);
        return aRoute.getURL();
    }

    public String getRouteToClusterURLBeta(Data data, Location location, Cluster cluster) throws InterruptedException, ApiException, IOException {
        Route aRoute = new Route();
        aRoute.add(location);
        Directions aDirections = new Directions();
        DirectionsRoute[] routes = aDirections.getMultipleRoutes(data.aGCTX, location, cluster);
        for(int i = 0;i<routes.length;i++) {
            if(!aDirections.routeHasFires(data.fires, routes[i])) {
                for(int j=0;j<routes[i].legs[0].steps.length;j++) {
                    aRoute.addLatLong(routes[i].legs[0].steps[j].endLocation);
                }
                break;
            }
        }
        aRoute.add(cluster);
        return aRoute.getURL();
    }
}