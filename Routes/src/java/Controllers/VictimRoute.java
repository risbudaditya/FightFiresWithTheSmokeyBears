import Data.ClustersData.Cluster;
import Data.ClustersData.Clusters;
import Data.Data;
import Data.FiresData.Fires;
import Google.Directions;
import com.google.maps.errors.ApiException;

import java.io.IOException;

/**
 * Created by tyler on 4/29/2017.
 */
public class VictimRoute {

    public Data data = new Data();

    public VictimRoute() throws Exception {
    }


    public String getRouteToNearestCluster() throws InterruptedException, ApiException, IOException {

        Cluster cluster = getClosestCluster();
        return "";
        //return getRouteToClusterURL();
    }

    public Cluster getClosestCluster() throws InterruptedException, ApiException, IOException {
        Directions aDirections = new Directions();
        for(Cluster c : data.clusters) {
            aDirections.distance(data.aGCTX, c, c);

        }
        return data.clusters.get(0);
    }
}