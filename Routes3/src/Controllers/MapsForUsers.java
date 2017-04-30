package Controllers;

import Data.Data;
import Data.FiresData.Fires;
import Data.LocationsData.Location;
import org.json.simple.ItemList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by tyler on 4/29/2017.
 */
public class MapsForUsers {
    public static String mapsForUsers() throws Exception {
        //return "{map:[{num:\"7035172175\", url:\"www.google.com\"}]}";
        //return new Fires().get(0).toString();
        Data data = new Data();

        JSONArray urls = new JSONArray();

        for(Location aLocation : data.locations) {
            VictimRoute aRoute = new VictimRoute();
            String url = aRoute.getRouteToNearestCluster(data, aLocation);
            JSONObject aObj = new JSONObject();
            aObj.put("num", aLocation.num);
            aObj.put("url", url);
            urls.add(aObj);
        }

        return urls.toJSONString();
    }
}