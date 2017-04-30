package Data.ClustersData;

import Data.FiresData.Fire;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by tyler on 4/29/2017.
 */
public class Clusters extends ArrayList<Cluster> {

    public void loadClusters(JSONObject json) {
        JSONArray anArray = (JSONArray)json.get("clusters");
        Iterator<JSONArray> it = anArray.iterator();
        while(it.hasNext()) {
            JSONArray obj = it.next();
            Cluster aCluster = new Cluster();
            aCluster.lat = new Double((Double)obj.get(0)).toString();
            aCluster.lon = new Double((Double)obj.get(1)).toString();
            aCluster.size = (Long)obj.get(2);
            add(aCluster);
        }
    }
}
