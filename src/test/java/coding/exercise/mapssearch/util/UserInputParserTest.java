package coding.exercise.mapssearch.util;

import coding.exercise.mapssearch.transport.model.Connection;
import coding.exercise.mapssearch.transport.model.Station;
import coding.exercise.mapssearch.transport.queries.NearbyQuery;
import coding.exercise.mapssearch.transport.queries.RoutingQuery;
import coding.exercise.mapssearch.transport.queries.SearchQuery;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the user input parser
 */
public class UserInputParserTest
{
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testParseValidConnectionLine()
    {
        Connection createdConnection = UserInputParser.parseConnection("B -> E: 210");
        assertEquals(createdConnection.getSourceStation(), new Station("B"));
        assertEquals(createdConnection.getDestinationStation(), new Station("E"));
        assertEquals(createdConnection.getTravelTimeInSeconds(), 210);
    }

    @Test
    public void testParseInvalidConnectionLine()
    {
        String connectionLine = "B -> : 210";
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(String.format(
            "the connection line \"%s\" is not properly formatted, it should be similar to \"<source> -> <destination>: <travel time>\"",
            connectionLine));
        Connection createdConnection = UserInputParser.parseConnection(connectionLine);
    }

    @Test
    public void testParseValidRoutingQueryLine()
    {
        RoutingQuery createdRoutingQuery = (RoutingQuery) UserInputParser.parseQuery("route A -> B");
        assertEquals(createdRoutingQuery.getSourceStation(), new Station("A"));
        assertEquals(createdRoutingQuery.getDestinationStation(), new Station("B"));
    }

    @Test
    public void testParseValidNearbyQueryLine()
    {
        NearbyQuery createdNearbyQuery = (NearbyQuery) UserInputParser.parseQuery("nearby A, 130");
        assertEquals(createdNearbyQuery.getSourceStation(), new Station("A"));
        assertEquals(createdNearbyQuery.getMaxTravelTimeAllowed(), 130);
    }

    @Test
    public void testParseInvalidQueryLine()
    {
        String queryLine = "rout A -> B";
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(String.format(
            "the query line \"%s\" is not properly formatted, it should be similar to \"route <source> -> <destination>\""
                + " or \"nearby <source>, <maximum travel time>\"",
            queryLine));

        SearchQuery searchQuery = UserInputParser.parseQuery(queryLine);
    }
}
