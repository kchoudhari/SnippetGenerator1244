package SnippetGenerator;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: harsh
 * Date: 3/11/14
 * Time: 5:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class data {

    HashMap<Object, HashMap<String, String>> data = new HashMap<Object, HashMap<String, String>>();

    data(String query) {

        data = (HashMap<Object, HashMap<String, String>>) Parser(query);

    }

    public HashMap getMap() throws MalformedURLException {
        return data;
    }

    Map Parser(String query) {
        Map<String, Map<String, String>> genData = new HashMap<String, Map<String, String>>();

        try {

            // Get your query string posted by the user
            //  String query = request.getParameter("query");
            //Instantiate a Customsearch object using NetHttpTransport and the JacksonFactory (JSON library)
            HttpRequestInitializer httpRequestInitializer = new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest httpRequest) throws IOException {

                }
            };
            System.out.println("hi");
            Customsearch customsearch = new Customsearch(new NetHttpTransport(), new JacksonFactory(), httpRequestInitializer);
            // Instantiate a Customsearch.Cse.List object using your query string
            com.google.api.services.customsearch.Customsearch.Cse.List list = customsearch.cse().list(query);
            // Set your API KEY
            list.setKey("AIzaSyCNW_RkKOLUdKMtcszzibzALEkoHazrJyw");
            // Set your custom search engine ID
            list.setCx("016346005891615725002:bgiac4u5emc");
            // Execute the search
            Search results = list.execute();
            // Get the result items

            List<Result> items = results.getItems();
            // Loop through your result items and stream them to the client
            for (Result result : items) {
                Map<String, String> innerMap = new HashMap<String, String>();
                innerMap.put(result.getTitle(), result.getSnippet());
                genData.put(result.getLink(), innerMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return genData;

    }
}