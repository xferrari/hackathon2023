package at.codingaustria.hackathon.evaluation;

import at.codingaustria.hackathon.obj.Location;
import at.codingaustria.hackathon.obj.Route;
import com.graphhopper.directions.api.client.ApiClient;
import com.graphhopper.directions.api.client.ApiException;
import com.graphhopper.directions.api.client.api.RoutingApi;
import com.graphhopper.directions.api.client.model.RouteResponse;
import com.graphhopper.directions.api.client.model.RouteResponsePath;
import com.graphhopper.directions.api.client.model.VehicleProfileId;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class RouteEvaluator {

  private static List<String> generatePoints(List<Double> latitudes, List<Double> longitudes) {
    return IntStream
        .range(0, latitudes.size())
        .mapToObj(i -> latitudes.get(i) + "," + longitudes.get(i))
        .toList();
  }

  public static double distance(double latitude1, double latitude2, double longitude1,
      double longitude2)
      throws RouteNotFoundException, ApiException {
    return getFullRouteInformation(List.of(new Location(latitude1, longitude1),
        new Location(latitude2, longitude2))).getCosts();
  }

  public static Route getFullRouteInformation(List<Location> stops)
      throws RouteNotFoundException, ApiException {
    String apiKey = "bf00158f-7ee7-46b6-a1ba-7b1669cc4c43"; // Replace with your actual API key

    // Create the GraphHopper API client
    ApiClient apiClient = new ApiClient();
    apiClient.setApiKey(apiKey);

    //Some doc: https://github.com/graphhopper/directions-api-clients/blob/master/java/docs/RoutingApi.md
    // Create the RoutingApi instance
    RoutingApi routingApi = new RoutingApi(apiClient);
    List<String> point = generatePoints(stops.stream().map(Location::getLatitude).toList(),
        stops.stream().map(Location::getLongitude)
            .toList()); // List<String> | The points for which the route should be calculated. Format: `[latitude,longitude]`. Specify at least an origin and a destination. Via points are possible. The maximum number depends on your plan.
    List<String> pointHint = null; // List<String> | The `point_hint` is typically a road name to which the associated `point` parameter should be snapped to. Specify no `point_hint` parameter or the same number as you have `point` parameters.
    List<String> snapPrevention = null; // List<String> | Optional parameter to avoid snapping to a certain road class or road environment. Currently supported values are `motorway`, `trunk`, `ferry`, `tunnel`, `bridge` and `ford`. Multiple values are specified like `snap_prevention=ferry&snap_prevention=motorway`.
    VehicleProfileId vehicle = VehicleProfileId.CAR; // VehicleProfileId | The vehicle profile for which the route should be calculated.
    String locale = "de"; // String | The locale of the resulting turn instructions. E.g. `pt_PT` for Portuguese or `de` for German.
    Boolean elevation = null; // Boolean | If `true`, a third coordinate, the altitude, is included with all positions in the response. This changes the format of the `points` and `snapped_waypoints` fields of the response, in both their encodings. Unless you switch off the `points_encoded` parameter, you need special code on the client side that can handle three-dimensional coordinates. A request can fail if the vehicle profile does not support elevation. See the features object for every vehicle profile.
    List<String> details = null; // List<String> | Optional parameter to retrieve path details. You can request additional details for the route: `street_name` and `time`. For all motor vehicle profiles, we additionally support `max_speed`, `toll`, `road_class`, `road_environment`, and `surface`.
    Boolean optimize = null; // Boolean | Normally, the calculated route will visit the points in the order you specified them. If you have more than two points, you can set this parameter to `true` and the points may be re-ordered to minimize the total travel time. Keep in mind that the limits on the number of locations of the Route Optimization applies, and the request is more expensive.
    Boolean instructions = false; // Boolean | If instructions should be calculated and returned
    Boolean calcPoints = false; // Boolean | If the points for the route should be calculated at all.
    Boolean debug = true; // Boolean | If `true`, the output will be formatted.
    Boolean pointsEncoded = false; // Boolean | Allows changing the encoding of location data in the response. The default is polyline encoding, which is compact but requires special client code to unpack. (We provide it in our JavaScript client library!) Set this parameter to `false` to switch the encoding to simple coordinate pairs like `[lon,lat]`, or `[lon,lat,elevation]`. See the description of the response format for more information.
    String type = "json"; // String | Specifies the media type for the response. For `json`, it will be `application/json`. For `gpx`, it will be `application/gpx+xml`.
    Boolean chDisable = false; // Boolean | Use this parameter in combination with one or more parameters from below.
    String weighting = null; // String | Determines the way the ''best'' route is calculated. Default is `fastest`. Other options are `shortest` (e.g. for `vehicle=foot` or `bike`) and `short_fastest` which finds a reasonable balance between `shortest` and `fastest`. Requires `ch.disable=true`.
    List<Integer> heading = null; // List<Integer> | Favour a heading direction for a certain point. Specify either one heading for the start point or as many as there are points. In this case headings are associated by their order to the specific points. Headings are given as north based clockwise angle between 0 and 360 degree. This parameter also influences the tour generated with `algorithm=round_trip` and forces the initial direction.  Requires `ch.disable=true`.
    Integer headingPenalty = null; // Integer | Time penalty in seconds for not obeying a specified heading. Requires `ch.disable=true`.
    Boolean passThrough = null; // Boolean | If `true`, u-turns are avoided at via-points with regard to the `heading_penalty`. Requires `ch.disable=true`.
    String blockArea = null;//"blockArea_example"; // String | Block road access via a point with the format `latitude,longitude` or an area defined by a circle `lat,lon,radius` or a rectangle `lat1,lon1,lat2,lon2`. Separate several values with `;`. Requires `ch.disable=true`.
    String avoid = null;//"avoid_example"; // String | Specify which road classes and environments you would like to avoid.  Possible values are `motorway`, `primary`, `secondary`, `tertiary`, `trunk`, `residential`, `steps`, `living_street`, `track`, `toll`, `ferry`, `tunnel`, `bridge` and `ford`. Separate several values with `;`. Obviously not all the values make sense for all vehicle profiles e.g. `bike` is already forbidden on a `motorway`. Requires `ch.disable=true`.
    String algorithm = null;//"algorithm_example"; // String | Rather than looking for the shortest or fastest path, this lets you solve two different problems related to routing: With `round_trip`, the route will get you back to where you started. This is meant for fun (think of a bike trip), so we will add some randomness. With `alternative_route`, we give you not one but several routes that are close to optimal, but not too similar to each other. You can control both of these features with additional parameters, see below. Requires `ch.disable=true`.
    Integer roundTripDistance = null; // Integer | If `algorithm=round_trip`, this parameter configures approximative length of the resulting round trip. Requires `ch.disable=true`.
    Long roundTripSeed = null; // Long | If `algorithm=round_trip`, this sets the random seed. Change this to get a different tour for each value.
    Integer alternativeRouteMaxPaths = null; // Integer | If `algorithm=alternative_route`, this parameter sets the number of maximum paths which should be calculated. Increasing can lead to worse alternatives.
    BigDecimal alternativeRouteMaxWeightFactor = null;//new BigDecimal(); // BigDecimal | If `algorithm=alternative_route`, this parameter sets the factor by which the alternatives routes can be longer than the optimal route. Increasing can lead to worse alternatives.
    BigDecimal alternativeRouteMaxShareFactor = null;//new BigDecimal(); // BigDecimal | If `algorithm=alternative_route`, this parameter specifies how similar an alternative route can be to the optimal route. Increasing can lead to worse alternatives.
    // Make the API call and get the route response
    RouteResponse result = routingApi.getRoute(point, pointHint, snapPrevention, vehicle, locale,
        elevation, details, optimize, instructions, calcPoints, debug, pointsEncoded, type,
        chDisable, weighting, heading, headingPenalty, passThrough, blockArea, avoid, algorithm,
        roundTripDistance, roundTripSeed, alternativeRouteMaxPaths,
        alternativeRouteMaxWeightFactor, alternativeRouteMaxShareFactor);

    // Process the route response
    if (result.getPaths() != null && !result.getPaths().isEmpty()) {
      // Get the first route (best route)
      RouteResponsePath route = result.getPaths().get(0);

      // Access route information like distance, time, and geometry
      return new Route(
          stops,
          route.getDistance()
      );
    }

    throw new RouteNotFoundException();
  }

}

