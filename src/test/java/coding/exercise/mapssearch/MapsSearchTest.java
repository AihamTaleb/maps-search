package coding.exercise.mapssearch;

import coding.exercise.mapssearch.transport.model.Station;
import coding.exercise.mapssearch.transport.model.TransportNetwork;
import coding.exercise.mapssearch.transport.queries.NearbyQuery;
import coding.exercise.mapssearch.transport.queries.RoutingQuery;
import coding.exercise.mapssearch.util.UserInputParser;
import coding.exercise.mapssearch.util.graphutil.Route;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * End to end test scenarios which start from the query and produces the desired result using the transport network
 */
public class MapsSearchTest
{
    private static TransportNetwork transportNetwork;

    @BeforeClass
    public static void setUpTest()
    {
        transportNetwork = new TransportNetwork();

        String[] connectionLines =
            new String[] { "A -> B: 240", "A -> C: 70", "A -> D: 120", "C -> B: 60", "D -> E: 480", "C -> E: 240",
                "B -> E: 210", "E -> A: 300" };

        for( String connectionLine : connectionLines ) {
            transportNetwork.createConnection(UserInputParser.parseConnection(connectionLine));
        }
    }

    @Test
    public void testRoutingQueryE2E()
    {
        String routingQueryString = "route A -> B";
        RoutingQuery routingQuery = (RoutingQuery) UserInputParser.parseQuery(routingQueryString);
        Route shortestRoute = routingQuery.answerQuery(transportNetwork);

        List<Station> actualStationsList = shortestRoute.getStations();

        List<Station> expectedStationsList = new ArrayList<Station>();
        expectedStationsList.add(new Station("A"));
        expectedStationsList.add(new Station("C"));
        expectedStationsList.add(new Station("B"));

        assertEquals(actualStationsList, expectedStationsList);
        assertEquals(shortestRoute.getTravelTimeToStation(routingQuery.getDestinationStation()), 130);
    }

    @Test
    public void testNearbyQueryE2E()
    {
        String nearbyQueryString = "nearby A, 130";
        NearbyQuery nearbyQuery = (NearbyQuery) UserInputParser.parseQuery(nearbyQueryString);
        Route nearbyStations = nearbyQuery.answerQuery(transportNetwork);

        LinkedHashMap<Station, Integer> expectedNearbyStations = new LinkedHashMap<Station, Integer>();
        expectedNearbyStations.put(new Station("C"), 70);
        expectedNearbyStations.put(new Station("D"), 120);
        expectedNearbyStations.put(new Station("B"), 130);

        assertEquals(nearbyStations.getStationsWithTravelTimes(), expectedNearbyStations);
    }
}
