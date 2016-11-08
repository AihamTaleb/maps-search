package coding.exercise.mapssearch.transport.model;

/**
 * this represents a direct connection between two stations in the transportation graph (network)
 */
public class Connection
{
    /**
     * the station from which the connection starts
     */
    private Station sourceStation;

    /**
     * the station at which the connection ends
     */
    private Station destinationStation;

    /**
     * the cost of travel (in terms of time cost on seconds)
     */
    private int travelTimeInSeconds;

    public Connection( Station sourceStation, Station destinationStation, int travelTimeInSeconds )
    {
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.travelTimeInSeconds = travelTimeInSeconds;
    }

    public void setTravelTimeInSeconds( int travelTimeInSeconds )
    {
        this.travelTimeInSeconds = travelTimeInSeconds;
    }

    public void setDestinationStation( Station destinationStation )
    {
        this.destinationStation = destinationStation;
    }

    public void setSourceStation( Station sourceStation )
    {
        this.sourceStation = sourceStation;
    }

    public Station getSourceStation()
    {
        return sourceStation;
    }

    public Station getDestinationStation()
    {
        return destinationStation;
    }

    public int getTravelTimeInSeconds()
    {
        return travelTimeInSeconds;
    }

    /**
     * the equality of two connections depend on the equality operation of their stations as well as the travel time
     * required between their stations
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals( Object obj )
    {
        if( obj == null || !(obj instanceof Connection) ) {
            return false;
        } else {
            Connection other = (Connection) obj;
            return other.getSourceStation().equals(this.getSourceStation()) &&
                other.getDestinationStation().equals(this.getDestinationStation()) &&
                other.getTravelTimeInSeconds() == this.getTravelTimeInSeconds();
        }
    }

    @Override
    public String toString()
    {
        return String.format("Connection: %s -> %s (with travel time %d)",
            this.sourceStation.toString(),
            this.destinationStation.toString(),
            this.travelTimeInSeconds);
    }

    @Override
    public int hashCode()
    {
        return this.sourceStation.hashCode() + this.destinationStation.hashCode();
    }
}
