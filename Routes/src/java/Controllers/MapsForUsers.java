package Controllers;

import FiresData.Fires;

/**
 * Created by tyler on 4/29/2017.
 */
public class MapsForUsers {
    public static String mapsForUsers() throws Exception {
        //return "{map:[{num:\"7035172175\", url:\"www.google.com\"}]}";
        //return new Fires().get(0).toString();
        Fires aFires = new Fires();
        return aFires.get(0).toString();
    }
}
