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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RouteContoller {

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/api/initial3")
    @ResponseBody
    public ResponseEntity<List<Route>> getInitData3() throws RouteNotFoundException, ApiException {
        List<Location> innsbruckBregenz = new ArrayList<>();
        innsbruckBregenz.add(new Location(47.259659, 11.400375));
        innsbruckBregenz.add(new Location(47.50311, 9.7471));
        Route route1 = RouteEvaluator.getFullRouteInformation(innsbruckBregenz);

        var locations = List.of(
                new Location(48.21612, 16.373137),
                new Location(48.203530, 15.638170)
        );
        Route route2 = RouteEvaluator.getFullRouteInformation(innsbruckBregenz);

        List<Route> listOfTheList = new ArrayList<>();
        listOfTheList.add(route1);
        listOfTheList.add(route2);


        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        // return mapper.writeValueAsString(listOfTheList);
        return new ResponseEntity<>(listOfTheList, HttpStatus.OK);
    }

    @GetMapping("/api/initial")
    @ResponseBody
    public ResponseEntity<List<Route>> getInitData() throws RouteNotFoundException, ApiException {
        List<Location> list1 = new ArrayList<>();
        list1.add(new Location(48.21612, 16.373137));
        list1.add(new Location(48.4017869, 15.9847379));
        list1.add(new Location(48.3739927, 16.6357775));
        list1.add(new Location(48.1245335, 14.8823511));
        Route route1 = RouteEvaluator.getFullRouteInformation(list1);

        List<Location> list2 = new ArrayList<>();
        list2.add(new Location(48.4017869, 15.9847379));
        list2.add(new Location(48.3739927, 16.6357775));
        list2.add(new Location(48.1245335, 14.8823511));
        Route route2 = RouteEvaluator.getFullRouteInformation(list2);

        List<Location> list3 = new ArrayList<>();
        list3.add(new Location(48.0053016, 16.231961));
        list3.add(new Location(48.4017869, 15.9847379));
        list3.add(new Location(48.1245335, 14.8823511));
        Route route3 = RouteEvaluator.getFullRouteInformation(list3);

        List<Route> listOfTheList = new ArrayList<>();
        listOfTheList.add(route1);
        listOfTheList.add(route2);
        listOfTheList.add(route3);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        // return mapper.writeValueAsString(listOfTheList);
        return new ResponseEntity<>(listOfTheList, HttpStatus.OK);
    }

    @GetMapping("/api/initial2")
    @ResponseBody
    public ResponseEntity<List<Route>> getInitData2() throws RouteNotFoundException, ApiException {
        List<Location> list2 = new ArrayList<>();
        list2.add(new Location(48.4017869, 15.9847379));
        list2.add(new Location(48.3739927, 16.6357775));
        list2.add(new Location(48.1245335, 14.8823511));
        Route route2 = RouteEvaluator.getFullRouteInformation(list2);

        List<Location> list3 = new ArrayList<>();
        list3.add(new Location(48.0053016, 16.231961));
        list3.add(new Location(48.4017869, 15.9847379));
        list3.add(new Location(48.1245335, 14.8823511));
        Route route3 = RouteEvaluator.getFullRouteInformation(list3);

        List<Location> list1 = new ArrayList<>();
        list1.add(new Location(48.21612, 16.373137));
        list1.add(new Location(48.4017869, 15.9847379));
        list1.add(new Location(48.3739927, 16.6357775));
        list1.add(new Location(48.1245335, 14.8823511));
        Route route1 = RouteEvaluator.getFullRouteInformation(list1);

        List<Location> list4 = new ArrayList<>();
        list4.add(new Location(47.845516, 16.516668));
        list4.add(new Location(47.7766024, 17.0326694));
        list4.add(new Location(47.6000949, 16.6252523));
        list4.add(new Location(47.8966677, 16.6438987));
        Route route4 = RouteEvaluator.getFullRouteInformation(list4);

        List<Route> listOfTheList = new ArrayList<>();

        listOfTheList.add(route2);
        listOfTheList.add(route3);
        listOfTheList.add(route1);
        listOfTheList.add(route4);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return new ResponseEntity<>(listOfTheList, HttpStatus.OK);
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

        var size = 0;
        var counter = 0;
        do {
            counter++;
            size = routesWithCosts.size();
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
                if (route1.equals(route2))
                    continue;
                var merged = RouteCompatator.compareRoutes(route1.getTargets(), route2.getTargets());

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