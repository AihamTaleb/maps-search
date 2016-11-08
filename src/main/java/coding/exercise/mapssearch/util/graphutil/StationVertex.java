package coding.exercise.mapssearch.util.graphutil;

import coding.exercise.mapssearch.transport.model.Station;

/**
 * an auxiliary class that enhances the station object with specific fields useful for Dijkstra algorithm, and for
 * the resulting routes
 */
class StationVertex implements Comparable<StationVertex>
{
    /**
     * a reference to the station which this vertex object is enhancing with more data
     */
    private Station station;

    /**
     * the minimum distance to this vertex in the graph starting from a source station which is stored in Dijkstra's
     * algorithm
     */
    private int minDistance = Integer.MAX_VALUE;

    /**
     * a reference to the previous vertex in the shortest route calculated by Dijkstra
     */
    private StationVertex previous;

    StationVertex( Station station )
    {
        this.station = station;
    }

    int getMinDistance()
    {
        return minDistance;
    }

    Station getStation()
    {
        return station;
    }

    StationVertex getPrevious()
    {
        return previous;
    }

    void setMinDistance( int minDistance )
    {
        this.minDistance = minDistance;
    }

    void setPrevious( StationVertex previous )
    {
        this.previous = previous;
    }

    void setStation( Station station )
    {
        this.station = station;
    }

    public String toString()
    {
        return "Vertex for " + station.toString();
    }

    public int compareTo( StationVertex other )
    {
        return Integer.compare(minDistance, other.minDistance);
    }

}
