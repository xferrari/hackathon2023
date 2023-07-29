package at.codingaustria.hackathon.controller;


import at.codingaustria.hackathon.evaluation.RouteEvaluator;
import at.codingaustria.hackathon.evaluation.RouteNotFoundException;
import at.codingaustria.hackathon.obj.Route;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphhopper.directions.api.client.ApiException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class RouteContoller {

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
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

        var result = List.of(RouteEvaluator.getFullRouteInformation(routes.get(0).getTargets()));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}