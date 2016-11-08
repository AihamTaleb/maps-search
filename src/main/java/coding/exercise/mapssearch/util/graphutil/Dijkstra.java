package coding.exercise.mapssearch.util.graphutil;

import coding.exercise.mapssearch.transport.model.Connection;
import coding.exercise.mapssearch.transport.model.Station;

import java.util.*;

/**
 * A helper class that calculates the shortest paths from a given source station in the directed graph in which it
 * belongs. Once instantiated, it can be used to compute the shortest route from the source station to a given
 * destination station. In addition, it can provide the set of nearby stations from the source station.
 */
public class Dijkstra
{
    /**
     * a local auxiliary map which maps the stations to their corresponding vertices, this is constructed to make it
     * easier and more efficient to retrieve the vertex for a given station
     */
    private Map<Station, StationVertex> localStationVertexMap;

    /**
     * the station from which we would like to calculate the shortest path or the nearby stations
     */
    private Station sourceStation;

    /**
     * creates a new instance from Dijkstra's algorithm, and computes the shortest paths from the given source station
     *
     * @param transportGraph
     * @param sourceStation
     */
    public Dijkstra( HashMap<Station, HashSet<Connection>> transportGraph, Station sourceStation )
    {
        this.sourceStation = sourceStation;

        this.localStationVertexMap = new HashMap<Station, StationVertex>();
        for( Station station : transportGraph.keySet() ) {
            this.localStationVertexMap.put(station, new StationVertex(station));
        }

        this.localStationVertexMap.get(sourceStation).setMinDistance(0);
        PriorityQueue<StationVertex> stationVertexQueue = new PriorityQueue<StationVertex>();
        stationVertexQueue.add(this.localStationVertexMap.get(sourceStation));

        while( !stationVertexQueue.isEmpty() ) {
            StationVertex u = stationVertexQueue.poll();

            // Visit each connection exiting from u
            for( Connection connection : transportGraph.get(u.getStation()) ) {
                StationVertex v = this.localStationVertexMap.get(connection.getDestinationStation());
                int weight = connection.getTravelTimeInSeconds();
                int distanceThroughU = u.getMinDistance() + weight;
                if( distanceThroughU < v.getMinDistance() ) {
                    stationVertexQueue.remove(v);

                    v.setMinDistance(distanceThroughU);
                    v.setPrevious(u);
                    stationVertexQueue.add(v);
                }
            }
        }
    }

    /**
     * computes the shortest route to a given destination station
     *
     * @param destinationStation
     * @return
     */
    public Route getShortestPathToStation( Station destinationStation )
    {
        Route route = new Route();

        for( StationVertex stationVertex = this.localStationVertexMap.get(destinationStation);
             stationVertex != null; stationVertex = stationVertex.getPrevious() ) {
            route.addStationToRoute(stationVertex.getStation(), stationVertex.getMinDistance());
        }

        if( !route.getStationVertexList().isEmpty() ) {
            Collections.reverse(route.getStationVertexList());
        }

        return route;
    }

    /**
     * computes the nearby stations of the source station, by filtering the vertices whose required minimum time to
     * reach them is less than the travelTimeLimit parameter
     *
     * @param travelTimeLimit
     * @return
     */
    public Route getNearbyStationsWithinLimit( int travelTimeLimit )
    {
        Route route = new Route();

        for( Station station : this.localStationVertexMap.keySet() ) {
            if( !station.equals(sourceStation) ) {
                int travelTime = this.localStationVertexMap.get(station).getMinDistance();
                if( this.localStationVertexMap.get(station).getMinDistance() <= travelTimeLimit ) {
                    route.addStationToRoute(station, travelTime);
                }
            }
        }

        if( !route.getStationVertexList().isEmpty() ) {
            Collections.sort(route.getStationVertexList());
        }

        return route;
    }
}

