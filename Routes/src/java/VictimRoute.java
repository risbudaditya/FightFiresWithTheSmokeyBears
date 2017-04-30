import ClustersData.Cluster;
import ClustersData.Clusters;
import FiresData.Fires;

/**
 * Created by tyler on 4/29/2017.
 */
public class VictimRoute {

    public Fires fires;

    public Clusters clusters;

    public void setFires(Fires aFires) {
        fires = aFires;
    }

    public void setClusters(Clusters aClusters) {
        clusters = aClusters;
    }

    public String getRouteToNearestCluster() {
        Cluster cluster = getClosestCluster();
        String
    }

}