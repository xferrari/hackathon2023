package at.codingaustria.hackathon.controller;


import at.codingaustria.hackathon.evaluation.RouteCompatator;
import at.codingaustria.hackathon.evaluation.RouteEvaluator;
import at.codingaustria.hackathon.evaluation.RouteNotFoundException;
import at.codingaustria.hackathon.obj.Location;
import at.codingaustria.hackathon.obj.Route;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphhopper.directions.api.client.ApiException;
import java.util.ArrayList;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RouteContoller {

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/api/initial")
    @ResponseBody
    public String getInitData() throws JsonProcessingException, RouteNotFoundException, ApiException {
        List<Location> list1= new ArrayList<>();
        list1.add(new Location(48.21612, 16.373137));
        list1.add(new Location(48.4017869, 15.9847379));
        list1.add(new Location(48.3739927, 16.6357775));
        list1.add(new Location(48.1245335, 14.8823511));
        Route route1  = RouteEvaluator.getFullRouteInformation(list1);

        List<Location> list2= new ArrayList<>();
        list2.add(new Location(48.4017869, 15.9847379));
        list2.add(new Location(48.3739927, 16.6357775));
        list2.add(new Location(48.1245335, 14.8823511));
        Route route2  = RouteEvaluator.getFullRouteInformation(list2);

        List<Location> list3= new ArrayList<>();
        list3.add(new Location(48.0053016, 16.231961));
        list3.add(new Location(48.4017869, 15.9847379));
        list3.add(new Location(48.1245335, 14.8823511));
        Route route3  = RouteEvaluator.getFullRouteInformation(list3);

        List<Route> listOfTheList=new ArrayList<>();
        listOfTheList.add(route1);
        listOfTheList.add(route2);
        listOfTheList.add(route3);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return mapper.writeValueAsString(listOfTheList);
    }

    @GetMapping("/api/route")
    @ResponseBody
    public String getObject() throws JsonProcessingException {
//        RouteObj routeObj = new RouteObj(Arrays.asList("Wien", "St. Pölten"));
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
//        return mapper.writeValueAsString(routeObj);
        return null;
    }

    @PostMapping("api/mergeRoutes")
    public ResponseEntity<List<Route>> mergeRoutes(@RequestBody List<Route> routes)
        throws RouteNotFoundException, ApiException {

        var routesWithCosts = new ArrayList<Route>();
        for (var route : routes) {
            routesWithCosts.add(RouteEvaluator.getFullRouteInformation(route.getTargets()));
        }

        var size = routesWithCosts.size();
        do {
            mergeRoutes1(routesWithCosts);
        } while (size > routesWithCosts.size());


        return new ResponseEntity<>(routesWithCosts, HttpStatus.OK);
    }

    private void mergeRoutes1(List<Route> routes) throws RouteNotFoundException, ApiException {
        Route mergedRoute = null;
        Route route1Merged = null;
        Route route2Merged = null;
        for (var route1 : routes) {
            for (var route2 : routes) {
                var merged = RouteEvaluator.getFullRouteInformation(RouteCompatator.compareRoutes(route1.getTargets(), route2.getTargets()));

                if (merged.getCosts() < (route1.getCosts() + route2.getCosts())) {
                    mergedRoute = merged;
                    route1Merged = route1;
                    route2Merged = route2;
                    break;
                }
            }
        }
        if (mergedRoute != null) {
            routes.remove(route1Merged);
            routes.remove(route2Merged);
            routes.add(mergedRoute);
        }
    }

}