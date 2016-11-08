package coding.exercise.mapssearch.util.graphutil;

import coding.exercise.mapssearch.transport.model.Station;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * this represents a full route between two stations, it can hold single or multiple stations in it.
 * Can be used to hold the full shortest path result or nearby query result
 */
public class Route
{
    private List<StationVertex> stationVertexList;

    public Route()
    {
        this.stationVertexList = new ArrayList<StationVertex>();
    }

    void addStationToRoute( Station station, int cost )
    {
        StationVertex vertex = new StationVertex(station);
        vertex.setMinDistance(cost);
        this.stationVertexList.add(vertex);
    }

    public int getTravelTimeToStation( Station station )
    {
        StationVertex vertex = findVertexForStation(station);
        if( vertex != null )
            return vertex.getMinDistance();
        else
            return -1;
    }

    List<StationVertex> getStationVertexList()
    {
        return stationVertexList;
    }

    public List<Station> getStations()
    {
        List<Station> stations = new ArrayList<Station>();
        for( StationVertex vertex : this.stationVertexList ) {
            stations.add(vertex.getStation());
        }
        return stations;
    }

    public LinkedHashMap<Station, Integer> getStationsWithTravelTimes()
    {
        LinkedHashMap<Station, Integer> stationsWithTimes = new LinkedHashMap<Station, Integer>();
        for( StationVertex vertex : this.stationVertexList ) {
            stationsWithTimes.put(vertex.getStation(), vertex.getMinDistance());
        }
        return stationsWithTimes;
    }

    private StationVertex findVertexForStation( Station station )
    {
        for( StationVertex vertex : this.stationVertexList ) {
            if( vertex.getStation().equals(station) ) {
                return vertex;
            }
        }
        return null;
    }
}
