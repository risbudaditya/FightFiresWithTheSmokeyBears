package Data;

import Data.ClustersData.Clusters;
import Data.FiresData.Fires;
import Data.LocationsData.Locations;
import com.google.maps.GeoApiContext;
import org.json.simple.JSONObject;

/**
 * Created by tyler on 4/29/2017.
 */
public class Data {

    public static final String FIRES_CLUSTERS_ENDPOINT = "http://40a72073.ngrok.io/route_info";

    public static final String API_KEY = "AIzaSyC7bSWxKk7Lt6SAKu0vQPC_UDDj9KFssmk";


    RESTClient RESTClient = new RESTClient();
    public GeoApiContext aGCTX ;

    public Fires fires;
    public Clusters clusters;
    public Locations locations;

    public Data() throws Exception {
        aGCTX = new GeoApiContext();
        aGCTX.setApiKey(API_KEY);
        JSONObject aFiresClusters = RESTClient.getJSON(FIRES_CLUSTERS_ENDPOINT);
        fires = new Fires();
        fires.loadFires(aFiresClusters);
        clusters = new Clusters();
        clusters.loadClusters(aFiresClusters);
        locations = new Locations();
        locations.load(RESTClient.getJSON("other"));
    }
}
