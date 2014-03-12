package SnippetGenerator;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: harsh
 * Date: 3/11/14
 * Time: 5:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class data {

    HashMap<Object, String> data = new HashMap<Object, String>();

    data(String query) {

        data = (HashMap<Object, String>) JSONParser(query);

    }

    public HashMap getMap() throws MalformedURLException {
        return data;
    }

    Map JSONParser(String query) {
        Map<Object, String> genData=new HashMap<Object, String>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("/Users/harsh/IdeaProjects/SnippetGenerator/src/main/java/data.json"));
            JSONObject jsonObject = (JSONObject) obj;

            String cricketString = String.valueOf((JSONObject) jsonObject.get(query));

            System.out.println(cricketString);

            // convert user object to json string, and save to a file
            genData = mapper.readValue(String.valueOf(cricketString).replace("\\", ""), Map.class);
            // display to console


        } catch (JsonGenerationException e) {

            e.printStackTrace();

        } catch (JsonMappingException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return genData;
    }
}


