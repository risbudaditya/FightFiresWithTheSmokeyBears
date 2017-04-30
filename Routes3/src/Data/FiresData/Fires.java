package Data.FiresData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

/**
 * Created by tyler on 4/29/2017.
 */
public class Fires extends ArrayList<Fire> {
    public static final String FIRES_ENDPOINT = "http://172.16.32.153:5000/route_info";



    public Fires() throws Exception {
        /*URL url = new URL(FIRES_ENDPOINT);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");

        if(con.getResponseCode()!=200) throw new Exception("source not there!" + con.getResponseCode());

        BufferedReader br = new BufferedReader(new InputStreamReader( con.getInputStream()));

        StringBuffer out = new StringBuffer();

        String line;
        while((line = br.readLine())!=null) {
            out.append(line);
        }
        con.disconnect();
        loadFires ((JSONObject) new JSONParser().parse(out.toString()));*/

    }

    public void loadFires(JSONObject json) {
        JSONArray anArray = (JSONArray)json.get("fires");
        Iterator<JSONArray> it = anArray.iterator();
        while(it.hasNext()) {
            JSONArray obj = it.next();
            Fire aFire = new Fire();
            aFire.lat = new Double((Double)obj.get(0)).toString();
            aFire.lon = new Double((Double)obj.get(1)).toString();
            aFire.intensity = new Long((Long)obj.get(2)).toString();
            aFire.detected = (String) obj.get(3);
            aFire.time = (String) obj.get(4);
            add(aFire);
        }
    }
}
