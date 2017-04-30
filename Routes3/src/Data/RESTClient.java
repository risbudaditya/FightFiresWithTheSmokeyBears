package Data;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by tyler on 4/29/2017.
 */
public class RESTClient {



    public JSONObject getJSON(String URL) throws Exception {
        /*java.net.URL url = new URL(URL);
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
        return (JSONObject) new JSONParser().parse(out.toString());*/
        return (JSONObject) new JSONParser().parse(URL==Data.FIRES_CLUSTERS_ENDPOINT?"{\n" +
                "  \"clusters\": [\n" +
                "    [\n" +
                "      36.297, \n" +
                "      -119.156, \n" +
                "      15\n" +
                "    ]\n" +
                "  ], \n" +
                "  \"destinations\": [\n" +
                "    [\n" +
                "      35.26, \n" +
                "      -119.673\n" +
                "    ], \n" +
                "    [\n" +
                "      36.705, \n" +
                "      -119.673\n" +
                "    ], \n" +
                "    [\n" +
                "      36.297, \n" +
                "      -119.156\n" +
                "    ]\n" +
                "  ], \n" +
                "  \"fires\": [\n" +
                "    [\n" +
                "      36.297, \n" +
                "      -119.156, \n" +
                "      3, \n" +
                "      \"2017-04-28\", \n" +
                "      \"08:30PM\"\n" +
                "    ]\n" +
                "  ]\n" +
                "}" : "{\"locations\": [\n" +
                "[\n" +
                "  36.247, \n" +
                "  -119.126, \n" +
                "  \"123-456-7890\"\n" +
                "],\n" +
                "[\n" +
                "  36.307, \n" +
                "  -119.166, \n" +
                "  \"555-456-7890\"\n" +
                "]\n" +
                "]}");
    }
}
