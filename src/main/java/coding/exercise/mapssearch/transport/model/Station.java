package coding.exercise.mapssearch.transport.model;

/**
 * this represents the station node in the transportation network (graph)
 */
public class Station
{
    /**
     * the station name is a unique case insensitive alphanumeric string
     */
    private String stationName;

    public Station( String stationName )
    {
        this.stationName = stationName;
    }

    public String getStationName()
    {
        return stationName;
    }

    public void setStationName( String stationName )
    {
        this.stationName = stationName;
    }

    /**
     * the equality of two stations is defined on their names (case insensitive strings)
     * @param obj
     * @return
     */
    @Override
    public boolean equals( Object obj )
    {
        if( obj == null || !(obj instanceof Station) ) {
            return false;
        } else {
            Station other = (Station) obj;
            return other.getStationName().equalsIgnoreCase(this.getStationName());
        }
    }

    @Override
    public String toString()
    {
        return stationName;
    }

    @Override
    public int hashCode()
    {
        return this.stationName.hashCode();
    }
}
