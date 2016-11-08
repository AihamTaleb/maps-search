package coding.exercise.mapssearch.transport.queries;

import coding.exercise.mapssearch.transport.model.Station;
import coding.exercise.mapssearch.transport.model.TransportNetwork;
import coding.exercise.mapssearch.util.graphutil.Route;

/**
 * This query type answers the nearby queries
 */
public class NearbyQuery implements SearchQuery
{
    /**
     * the station which we would like to calculate its neighbors
     */
    private Station sourceStation;

    /**
     * the limit of time travel within which the nearby stations are to be reached
     */
    private int maxTravelTimeAllowed;

    public NearbyQuery( Station sourceStation, int maxTravelTimeAllowed )
    {
        this.sourceStation = sourceStation;
        this.maxTravelTimeAllowed = maxTravelTimeAllowed;
    }

    /**
     * calculates the nearby stations by using the respective transport network in the param
     *
     * @param transportNetwork
     * @return a Route object which holds the nearby stations ordered by time to reach them in an ascending order
     */
    public Route answerQuery( TransportNetwork transportNetwork )
    {
        if( transportNetwork.containsStation(this.sourceStation) ) {
            return transportNetwork.getNearbyStations(this.sourceStation, this.maxTravelTimeAllowed);
        } else
            return new Route();
    }

    public Station getSourceStation()
    {
        return sourceStation;
    }

    public int getMaxTravelTimeAllowed()
    {
        return maxTravelTimeAllowed;
    }

    public void setSourceStation( Station sourceStation )
    {
        this.sourceStation = sourceStation;
    }

    public void setMaxTravelTimeAllowed( int maxTravelTimeAllowed )
    {
        this.maxTravelTimeAllowed = maxTravelTimeAllowed;
    }

    @Override
    public String toString()
    {
        return "Nearby Query: from " + this.sourceStation + " within " + this.maxTravelTimeAllowed;
    }
}
