package Google;

import Data.FiresData.Fire;
import Data.FiresData.Fires;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.Duration;

import java.io.IOException;

/**
 * Created by tyler on 4/29/2017.
 */
public class Directions {

    public long duration(GeoApiContext ctx, Coordinate origin, Coordinate dest) throws InterruptedException, ApiException, IOException {
        /*DirectionsResult aResult = ( DirectionsApi.newRequest(ctx) ).origin(origin.lat+","+origin.lon).
        destination(dest.lat+","+dest.lon).await();*/

        try {
            DirectionsResult aResult = DirectionsApi.getDirections(ctx, origin.getCoords(), dest.getCoords()).await();
            Duration duration = aResult.routes[0].legs[0].duration;
            return duration.inSeconds;
        } catch(Exception e) {
            return Long.MAX_VALUE;
        }

    }

    public DirectionsRoute[] getMultipleRoutes(GeoApiContext ctx, Coordinate origin, Coordinate dest) throws InterruptedException, ApiException, IOException {
        DirectionsResult aResult = DirectionsApi.getDirections(ctx, origin.getCoords(), dest.getCoords()).alternatives(true).await();
        return aResult.routes;
    }

    public boolean routeHasFires(Fires fires, DirectionsRoute route) {

        for(Fire aFire : fires) {
            for(int i=0;i<route.legs[0].steps.length;i++) {
                if(distFrom(new Float(aFire.lat), new Float(aFire.lon), new Double(route.legs[0].steps[i].endLocation.lat).longValue(),new Double(route.legs[0].steps[i].endLocation.lng).longValue())<1)
                    return true;
            }
        }
        return false;
    }

    public static float distFrom(float lat1, float lng1, float lat2, float lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return dist;
    }
}
