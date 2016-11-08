package coding.exercise.mapssearch.util;

import coding.exercise.mapssearch.transport.model.Station;
import coding.exercise.mapssearch.transport.queries.NearbyQuery;
import coding.exercise.mapssearch.transport.queries.RoutingQuery;
import coding.exercise.mapssearch.transport.queries.SearchQuery;
import coding.exercise.mapssearch.util.graphutil.Route;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Util library which provides functions to correctly format the search query outputs
 */
public class OutputFormatter
{
    public static String formatQueryResult( SearchQuery searchQuery, Route route )
    {
        if( searchQuery == null )
            throw new IllegalArgumentException("the passed query is null");
        if( route == null )
            throw new IllegalArgumentException("the passed route is null");

        StringBuilder resultStringBuilder = new StringBuilder();

        if( searchQuery instanceof RoutingQuery ) {
            RoutingQuery routingQuery = (RoutingQuery) searchQuery;

            List<Station> routeStations = route.getStations();
            if( !routeStations.isEmpty() ) {
                Iterator<Station> routeIterator = routeStations.iterator();
                while( routeIterator.hasNext() ) {
                    resultStringBuilder.append(routeIterator.next());
                    if( routeIterator.hasNext() ) {
                        resultStringBuilder.append(" -> ");
                    }
                }
                resultStringBuilder.append(": ");
                resultStringBuilder.append(route.getTravelTimeToStation(routingQuery.getDestinationStation()));
            } else {
                resultStringBuilder.append(String.format("Error: No route from %s to %s",
                    routingQuery.getSourceStation(),
                    routingQuery.getDestinationStation()));
            }

        } else if( searchQuery instanceof NearbyQuery ) {
            NearbyQuery nearbyQuery = (NearbyQuery) searchQuery;

            LinkedHashMap<Station, Integer> stationsWithTravelTimes = route.getStationsWithTravelTimes();
            if( !stationsWithTravelTimes.isEmpty() ) {
                int index = 0;
                for( Map.Entry<Station, Integer> entry : stationsWithTravelTimes.entrySet() ) {
                    resultStringBuilder.append(entry.getKey());
                    resultStringBuilder.append(": ");
                    resultStringBuilder.append(entry.getValue());
                    if( index++ < stationsWithTravelTimes.entrySet().size() - 1 ) {
                        resultStringBuilder.append(", ");
                    }
                }
            } else {
                resultStringBuilder.append(String.format("Error: No nearby stations around %s within travel time %d",
                    nearbyQuery.getSourceStation(),
                    nearbyQuery.getMaxTravelTimeAllowed()));
            }

        } else {
            throw new IllegalArgumentException("passed unknown query type " + searchQuery.getClass());
        }

        return resultStringBuilder.toString();
    }
}
