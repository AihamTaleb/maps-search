package coding.exercise.mapssearch;

import coding.exercise.mapssearch.transport.model.Connection;
import coding.exercise.mapssearch.transport.model.TransportNetwork;
import coding.exercise.mapssearch.transport.queries.SearchQuery;
import coding.exercise.mapssearch.util.OutputFormatter;
import coding.exercise.mapssearch.util.UserInputParser;
import coding.exercise.mapssearch.util.graphutil.Route;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Represents the entry point of the program
 */
public class SearchLauncher
{

    public static void main( String[] args )
    {
        // the network being created using the user input
        TransportNetwork transportNetwork = new TransportNetwork();

        // reading the input from stdin
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String line;
        try {
            int numberOfConnections = -1;
            int parsedConnections = 0;

            while( (line = stdin.readLine()) != null && line.length() != 0 ) {
                if( numberOfConnections == -1 ) {
                    numberOfConnections = Integer.parseInt(line);
                } else {

                    try {
                        if( parsedConnections < numberOfConnections ) {
                            // parsing a connection
                            Connection connection = UserInputParser.parseConnection(line);
                            transportNetwork.createConnection(connection);
                            parsedConnections++;
                        } else {
                            // parsing a query
                            SearchQuery searchQuery = UserInputParser.parseQuery(line);
                            Route route = searchQuery.answerQuery(transportNetwork);

                            // printing the output in the desired format
                            System.out.println(OutputFormatter.formatQueryResult(searchQuery, route));
                        }
                    }
                    catch( IllegalArgumentException e ) {
                        System.out.println(e.getMessage());
                    }
                }
            }

            stdin.close();
        }
        catch( IOException e ) {
            e.printStackTrace();
        }

    }
}
