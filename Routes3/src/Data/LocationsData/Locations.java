package Data.LocationsData;

import Data.FiresData.Fire;
import Google.Coordinate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by tyler on 4/30/2017.
 */
public class Locations extends ArrayList<Location>  {
    public void load(JSONObject json) {
        JSONArray anArray = (JSONArray)json.get("locations");
        Iterator<JSONArray> it = anArray.iterator();
        while(it.hasNext()) {
            JSONArray obj = it.next();
            Location aLoc = new Location();
            aLoc.lat = new Double((Double)obj.get(0)).toString();
            aLoc.lon = new Double((Double)obj.get(1)).toString();
            aLoc.num = (String)obj.get(2);
            add(aLoc);
        }
    }
}