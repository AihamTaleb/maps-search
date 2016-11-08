package coding.exercise.mapssearch.transport.queries;

import coding.exercise.mapssearch.transport.model.Station;
import coding.exercise.mapssearch.transport.model.TransportNetwork;
import coding.exercise.mapssearch.util.graphutil.Route;

/**
 * This query type answers the routing queries
 */
public class RoutingQuery implements SearchQuery
{
    /**
     * the station from which the routing query starts
     */
    private Station sourceStation;

    /**
     * the station to which at routing query ends
     */
    private Station destinationStation;

    public RoutingQuery( Station sourceStation, Station destinationStation )
    {
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
    }

    /**
     * calculates the shortest route between the two given stations by using the respective transport network in
     * the param
     *
     * @param transportNetwork
     * @return a Route object that stores all the stations in the shortest path as well as the total time required
     * to reach them
     */
    public Route answerQuery( TransportNetwork transportNetwork )
    {
        if( transportNetwork.containsStation(this.sourceStation)
            && transportNetwork.containsStation(this.destinationStation) ) {
            return transportNetwork.getShortestRoute(this.sourceStation, this.destinationStation);
        } else {
            return new Route();
        }
    }

    public Station getDestinationStation()
    {
        return destinationStation;
    }

    public Station getSourceStation()
    {
        return sourceStation;
    }

    public void setSourceStation( Station sourceStation )
    {
        this.sourceStation = sourceStation;
    }

    public void setDestinationStation( Station destinationStation )
    {
        this.destinationStation = destinationStation;
    }

    @Override
    public String toString()
    {
        return "Routing Query: from " + this.sourceStation + " to " + this.destinationStation;
    }

}
