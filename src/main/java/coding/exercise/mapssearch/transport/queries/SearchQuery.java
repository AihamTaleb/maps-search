package coding.exercise.mapssearch.transport.queries;

import coding.exercise.mapssearch.transport.model.TransportNetwork;
import coding.exercise.mapssearch.util.graphutil.Route;

/**
 * an abstract search query type which is defined to enable the easy addition of new query types by just implementing
 * the interface method(s)
 */
public interface SearchQuery
{
    /**
     * Answers a given query depending on: 1) the query subtype. 2) the transportation network data.
     * By passing the transportation network as a param we ensure the re-usability and testability of this function
     * over multiple networks
     *
     * @param transportNetwork
     * @return the computed route object which holds the query result information
     */
    public Route answerQuery( TransportNetwork transportNetwork );
}
