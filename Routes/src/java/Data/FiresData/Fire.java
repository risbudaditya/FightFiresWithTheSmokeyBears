package FiresData;

import Google.Coordinate;

import java.sql.Time;
import java.util.Date;

/**
 * Created by tyler on 4/29/2017.
 */
public class Fire implements Coordinate {
    public String lat;
    public String lon;
    public String intensity;
    public String detected;
    public String time;

    public String toString() {
        return "Fire object with lat: "+lat+" and long: "+lon+" and intensity: "+intensity+" and detected: "+detected;
    }

}
