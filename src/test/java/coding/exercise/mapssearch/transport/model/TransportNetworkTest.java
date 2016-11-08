package coding.exercise.mapssearch.transport.model;

import coding.exercise.mapssearch.util.UserInputParser;
import coding.exercise.mapssearch.util.graphutil.Route;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * contains unit tests for the transport network class
 */
public class TransportNetworkTest
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
    public void testStationsCreation()
    {
        Set<Station> stations = transportNetwork.getStationsSet();
        assertEquals(stations.size(), 5);
        assertTrue(transportNetwork.containsStation(new Station("E")));
    }

    @Test
    public void testCorrectCreationOfConnections()
    {
        Set<Connection> connectionsOfStationA = transportNetwork.getConnectionsFromStation(new Station("A"));
        assertEquals(connectionsOfStationA.size(), 3);
        assertTrue(connectionsOfStationA.contains(new Connection(new Station("A"), new Station("C"), 70)));
    }

    @Test
    public void testShortestRoute()
    {
        Station sourceStation = new Station("A");
        Station destinationStation = new Station("B");
        Route shortestRoute = transportNetwork.getShortestRoute(sourceStation, destinationStation);

        List<Station> actualStationsList = shortestRoute.getStations();

        List<Station> expectedStationsList = new ArrayList<Station>();
        expectedStationsList.add(new Station("A"));
        expectedStationsList.add(new Station("C"));
        expectedStationsList.add(new Station("B"));

        assertEquals(actualStationsList, expectedStationsList);
        assertEquals(shortestRoute.getTravelTimeToStation(destinationStation), 130);
    }

    @Test
    public void testNearbyStations()
    {
        Route nearbyStations = transportNetwork.getNearbyStations(new Station("A"), 130);

        LinkedHashMap<Station, Integer> expectedNearbyStations = new LinkedHashMap<Station, Integer>();
        expectedNearbyStations.put(new Station("C"), 70);
        expectedNearbyStations.put(new Station("D"), 120);
        expectedNearbyStations.put(new Station("B"), 130);

        assertEquals(nearbyStations.getStationsWithTravelTimes(), expectedNearbyStations);
    }
}
