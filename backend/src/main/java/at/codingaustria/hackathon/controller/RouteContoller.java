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

    @GetMapping("/api/initial3")
    @ResponseBody
    public ResponseEntity<List<Route>> getInitData3() throws RouteNotFoundException, ApiException {
        List<Location> innsbruckBregenz = new ArrayList<>();
        innsbruckBregenz.add(new Location(47.259659, 11.400375));
        innsbruckBregenz.add(new Location(47.50311, 9.7471));
        Route route1 = RouteEvaluator.getFullRouteInformation(innsbruckBregenz);

        var viennaStPoelten = List.of(
                new Location(48.21612, 16.373137),
                new Location(48.203530, 15.638170)
        );
        Route route2 = RouteEvaluator.getFullRouteInformation(viennaStPoelten);

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
        list1.add(new Location(48.21612, 16.373137, "RAIFFEISENLANDESBANK NIEDERÖSTERREICH-WIEN AG"));
        list1.add(new Location(48.527794, 16.3619866, "Raiffeisenkasse Ernstbrunn eGen"));
        Route route1 = RouteEvaluator.getFullRouteInformation(list1);

        List<Location> list2 = new ArrayList<>();
        list2.add(new Location(48.21612, 16.373137, "RAIFFEISENLANDESBANK NIEDERÖSTERREICH-WIEN AG"));
        list2.add(new Location(48.0863213, 16.291321, "Raiffeisen Regionalbank Mödling eGen"));
        Route route2 = RouteEvaluator.getFullRouteInformation(list2);

        List<Location> list3 = new ArrayList<>();
        list3.add(new Location(48.21612, 16.373137, "RAIFFEISENLANDESBANK NIEDERÖSTERREICH-WIEN AG"));
        list3.add(new Location(48.56526, 16.0799974, "Raiffeisen Regionalbank Mödling eGen"));
        Route route3 = RouteEvaluator.getFullRouteInformation(list3);

        List<Location> list4 = new ArrayList<>();
        list4.add(new Location(48.21612, 16.373137, "RAIFFEISENLANDESBANK NIEDERÖSTERREICH-WIEN AG"));
        list4.add(new Location(48.3738038, 16.3137991, "Raiffeisenbank Korneuburg eGen"));
        Route route4 = RouteEvaluator.getFullRouteInformation(list4);

        List<Location> list5 = new ArrayList<>();
        list5.add(new Location(48.21612, 16.373137, "RAIFFEISENLANDESBANK NIEDERÖSTERREICH-WIEN AG"));
        list5.add(new Location(48.1464987, 16.7032167, "Raiffeisenkasse Orth a.d. Donau eGen"));
        Route route5 = RouteEvaluator.getFullRouteInformation(list5);

        List<Location> list6 = new ArrayList<>();
        list6.add(new Location(48.21612, 16.373137, "RAIFFEISENLANDESBANK NIEDERÖSTERREICH-WIEN AG"));
        list6.add(new Location(48.1784607, 16.0761195, "Raiffeisenbank Wienerwald eGen"));
        Route route6 = RouteEvaluator.getFullRouteInformation(list6);


        List<Route> listOfTheList = new ArrayList<>();
        listOfTheList.add(route1);
        listOfTheList.add(route2);
        listOfTheList.add(route3);
        listOfTheList.add(route4);
        listOfTheList.add(route5);
        listOfTheList.add(route6);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        // return mapper.writeValueAsString(listOfTheList);
        return new ResponseEntity<>(listOfTheList, HttpStatus.OK);
    }

    @GetMapping("/api/initial2")
    @ResponseBody
    public ResponseEntity<List<Route>> getInitData2() throws RouteNotFoundException, ApiException {
        List<Location> list2 = new ArrayList<>();
        list2.add(new Location(48.4017869, 15.9847379, "Raiffeisenbank Region Wagram eGen"));
        list2.add(new Location(48.3739927, 16.6357775, "Raiffeisenbank Auersthal-Bockfließ-Groß Schweinbarth eGen"));
        list2.add(new Location(48.1245335, 14.8823511, "Raiffeisenbank Region Amstetten eGen"));
        Route route2 = RouteEvaluator.getFullRouteInformation(list2);

        List<Location> list3 = new ArrayList<>();
        list3.add(new Location(48.0053016, 16.231961, "Raiffeisenbank Region Baden eGen"));
        list3.add(new Location(48.4017869, 15.9847379, "Raiffeisenbank Region Wagram eGen"));
        list3.add(new Location(48.1245335, 14.8823511, "Raiffeisenbank Region Amstetten eGen"));
        Route route3 = RouteEvaluator.getFullRouteInformation(list3);

        List<Location> list1 = new ArrayList<>();
        list1.add(new Location(48.21612, 16.373137, "RAIFFEISENLANDESBANK NIEDERÖSTERREICH-WIEN AG"));
        list1.add(new Location(48.4017869, 15.9847379, "Raiffeisenbank Region Wagram eGen"));
        list1.add(new Location(48.3739927, 16.6357775, "Raiffeisenbank Auersthal-Bockfließ-Groß Schweinbarth eGen"));
        list1.add(new Location(48.1245335, 14.8823511, "Raiffeisenbank Region Amstetten eGen"));
        Route route1 = RouteEvaluator.getFullRouteInformation(list1);

        List<Location> list4 = new ArrayList<>();
        list4.add(new Location(47.845516, 16.516668, "Raiffeisenlandesbank Burgenland und Revisionsverband eGen"));
        list4.add(new Location(47.7766024, 17.0326694, "Raiffeisenbank Seewinkel-Hansag eGen"));
        list4.add(new Location(47.6000949, 16.6252523, "Raiffeisenbank Region Deutschkreutz-Horitschon eGen"));
        list4.add(new Location(47.8966677, 16.6438987, "Raiffeisenbank Neusiedlersee-HÃ¼gelland eGen"));
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

    @PostMapping("api/mergeRoutes")
    public ResponseEntity<List<Route>> mergeRoutes(@RequestBody List<Route> routes)
            throws RouteNotFoundException, ApiException {

        var initialLocations = getAllLocations(routes);

        var routesWithCosts = new ArrayList<Route>();
        for (var route : routes) {
            routesWithCosts.add(RouteEvaluator.getFullRouteInformation(route.getTargets()));
        }


        var size = 0;
        var counter = 0;
        do {
            counter++;
            size = routesWithCosts.size();
            mergeRoutes1(routesWithCosts, initialLocations);
        } while (size > routesWithCosts.size());

        return new ResponseEntity<>(routesWithCosts, HttpStatus.OK);
    }

    private List<Location> getAllLocations(List<Route> routes) {
        var allLocations = new ArrayList<Location>();
        for (var route : routes) {
            allLocations.addAll(route.getTargets());
        }
        return allLocations;
    }

    private void mergeRoutes1(List<Route> routes, List<Location> initial) throws RouteNotFoundException, ApiException {
        Route mergedRoute = null;
        Route route1Merged = null;
        Route route2Merged = null;
        var counter = 0;
        for (var route1 : routes) {
            for (var route2 : routes) {
                counter++;
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
            if (mergedRoute != null)
                break;
        }
        if (mergedRoute != null) {
            routes.remove(route1Merged);
            routes.remove(route2Merged);
            routes.add(mergedRoute);
        }
    }

}