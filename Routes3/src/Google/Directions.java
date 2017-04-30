package Google;

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


        DirectionsResult aResult = DirectionsApi.getDirections(ctx, origin.getCoords(), dest.getCoords()).await();
        Duration duration = aResult.routes[0].legs[0].duration;
        return duration.inSeconds;

    }

    public DirectionsRoute[] getMultipleRoutes(Coordinate origin, Coordinate dest) {
        DirectionsResult aResult = DirectionsApi.getDirections(ctx, origin.getCoords(), dest.getCoords()).alternatives(true).await();
        return aResult.routes;
    }

    public boolean coordinateIsOnRoute(Coordinate aCoord, DirectionsRoute route) {
        
    }
}
