package coding.exercise.mapssearch.util;

import coding.exercise.mapssearch.transport.model.Connection;
import coding.exercise.mapssearch.transport.model.Station;
import coding.exercise.mapssearch.transport.queries.NearbyQuery;
import coding.exercise.mapssearch.transport.queries.RoutingQuery;
import coding.exercise.mapssearch.transport.queries.SearchQuery;

/**
 * Util library which provides functions to parse the user input, to ensure its validity, and to create the
 * corresponding objects
 */
public class UserInputParser
{
    // Regular expressions which are used to ensure the validity of user input
    private final static String CONNECTION_LINE_REGEX = "\\w+\\s*(->)\\s*\\w+\\s*(:)\\s*\\d+";
    private final static String ROUTING_QUERY_REGEX = "(route)\\s+\\w+\\s*(->)\\s*\\w+";
    private final static String NEARBY_QUERY_REGEX = "(nearby)\\s+\\w+\\s*(,)\\s*\\d+";

    /**
     * util function to parse the connection line between two stations
     *
     * @param connectionLine
     * @return
     */
    public static Connection parseConnection( String connectionLine )
    {
        if( connectionLine == null )
            throw new IllegalArgumentException("the entered connection line is null");

        connectionLine = connectionLine.trim();

        if( !connectionLine.matches(CONNECTION_LINE_REGEX) )
            throw new IllegalArgumentException(String.format(
                "the connection line \"%s\" is not properly formatted, it should be similar to \"<source> -> <destination>: <travel time>\"",
                connectionLine));

        String[] connectionLineSplits = connectionLine.split(":");

        int travelTimeInSecs = Integer.parseInt(connectionLineSplits[1].trim());

        String[] stationsNames = connectionLineSplits[0].split("->");

        String sourceStationName = stationsNames[0].trim().toUpperCase();
        validateStationName(sourceStationName);

        String destinationStationName = stationsNames[1].trim().toUpperCase();
        validateStationName(destinationStationName);

        return new Connection(new Station(sourceStationName), new Station(destinationStationName), travelTimeInSecs);
    }

    /**
     * util function to parse the search query, and to determine the corresponding query type
     *
     * @param queryLine
     * @return
     */
    public static SearchQuery parseQuery( String queryLine )
    {
        if( queryLine == null )
            throw new IllegalArgumentException("the entered query line is null");

        queryLine = queryLine.trim();

        if( !queryLine.matches(ROUTING_QUERY_REGEX) && !queryLine.matches(NEARBY_QUERY_REGEX) ) {
            throw new IllegalArgumentException(String.format(
                "the query line \"%s\" is not properly formatted, it should be similar to \"route <source> -> <destination>\""
                    + " or \"nearby <source>, <maximum travel time>\"",
                queryLine));
        }

        if( queryLine.toLowerCase().startsWith("route ") ) {
            String[] routeQuerySplits = queryLine.replace("route ", "").split("->");

            String sourceStationName = routeQuerySplits[0].trim().toUpperCase();
            validateStationName(sourceStationName);

            String destinationStationName = routeQuerySplits[1].trim().toUpperCase();
            validateStationName(destinationStationName);

            return new RoutingQuery(new Station(sourceStationName), new Station(destinationStationName));

        } else if( queryLine.toLowerCase().startsWith("nearby ") ) {
            String[] nearbyQuerySplits = queryLine.replace("nearby ", "").split(",");

            String sourceStationName = nearbyQuerySplits[0].trim().toUpperCase();
            validateStationName(sourceStationName);

            int maxTravelTime = Integer.parseInt(nearbyQuerySplits[1].trim());

            return new NearbyQuery(new Station(sourceStationName), maxTravelTime);
        } else {
            throw new IllegalArgumentException("Search queries should start with either \"route\" or \"nearby\" ");
        }
    }

    /**
     * checks if a given string contains only alphanumeric characters
     * Note: this method is available in the library *Apache Commons Lang*. However, for the sake of the coding
     * challenge it's not imported
     *
     * @param string string to be checked
     * @return
     */
    private static boolean isAlphanumeric( String string )
    {
        for( Character character : string.toCharArray() ) {
            if( !Character.isLetterOrDigit(character) ) {
                return false;
            }
        }
        return true;
    }

    /**
     * validates the given station station to ensure it holds only alphanumeric chars and it doesn't contain reserved keywords
     *
     * @param stationName
     */
    private static void validateStationName( String stationName )
    {
        if( !isAlphanumeric(stationName) )
            throw new IllegalArgumentException(
                "Only letters and digits are allowed in the station station. Please check the station " + stationName);
        if( stationName.toLowerCase().startsWith("route ") )
            throw new IllegalArgumentException("station names shouldn't start with the reserved word \"route\"");
        else if( stationName.toLowerCase().startsWith("nearby ") )
            throw new IllegalArgumentException("station names shouldn't start with the reserved word \"nearby\"");
    }

}
