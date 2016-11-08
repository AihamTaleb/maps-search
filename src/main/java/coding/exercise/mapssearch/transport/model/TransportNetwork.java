package coding.exercise.mapssearch.transport.model;

import coding.exercise.mapssearch.util.graphutil.Dijkstra;
import coding.exercise.mapssearch.util.graphutil.Route;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * this represents the full network of transportation and it's stored in a directed graph
 */
public class TransportNetwork
{
    /**
     * the main data structure which stores the directed graph which is an (adjacency map)
     */
    private HashMap<Station, HashSet<Connection>> transportGraph;

    public TransportNetwork()
    {
        this.transportGraph = new HashMap<Station, HashSet<Connection>>();
    }

    /**
     * creates a new station in the transportation network with an empty connection set
     *
     * @param station
     */
    public void createStation( Station station )
    {
        this.transportGraph.put(station, new HashSet<Connection>());
    }

    /**
     * creates a new connection in this network between two stations. It creates the stations in case they don't exist
     * in the network
     *
     * @param connection
     */
    public void createConnection( Connection connection )
    {
        if( !this.transportGraph.containsKey(connection.getSourceStation()) ) {
            this.createStation(connection.getSourceStation());
        }

        if( !this.transportGraph.containsKey(connection.getDestinationStation()) ) {
            this.createStation(connection.getDestinationStation());
        }

        // adding the connection in the set of routes that go out from the source station
        this.transportGraph.get(connection.getSourceStation()).add(connection);
    }

    /**
     * calculates the shortest route between the given two stations using Dijkstra's algorithm
     *
     * @param sourceStation
     * @param destinationStation
     * @return a Route instance which stores the stations in the shortest path
     */
    public Route getShortestRoute( Station sourceStation, Station destinationStation )
    {
        Dijkstra dijkstra = new Dijkstra(this.transportGraph, sourceStation);
        return dijkstra.getShortestPathToStation(destinationStation);
    }

    /**
     * searches for the nearby stations within the given travel time limit. It reuses the Dijkstra algorithm to
     * compute these costs
     *
     * @param sourceStation
     * @param maxTravelTime
     * @return
     */
    public Route getNearbyStations( Station sourceStation, int maxTravelTime )
    {
        Dijkstra dijkstra = new Dijkstra(this.transportGraph, sourceStation);
        return dijkstra.getNearbyStationsWithinLimit(maxTravelTime);
    }

    /**
     * checks if the given station exists in the transport network
     *
     * @param station
     * @return
     */
    public boolean containsStation( Station station )
    {
        return this.transportGraph != null && this.transportGraph.containsKey(station);
    }

    /**
     * gets all the outgoing connections from the given station
     *
     * @param station
     * @return
     */
    public Set<Connection> getConnectionsFromStation( Station station )
    {
        if( this.transportGraph != null && !this.transportGraph.isEmpty() )
            return this.transportGraph.get(station);
        else
            return null;
    }

    public Set<Station> getStationsSet()
    {
        if( this.transportGraph != null )
            return this.transportGraph.keySet();
        else
            return null;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();

        for( Station station : this.transportGraph.keySet() ) {
            stringBuilder.append(station);
            stringBuilder.append(" = ");
            stringBuilder.append(this.transportGraph.get(station));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
