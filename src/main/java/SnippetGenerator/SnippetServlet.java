package SnippetGenerator; /**
 * Created with IntelliJ IDEA.
 * User: harsh
 * Date: 11/27/13
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */

import de.tudarmstadt.ukp.jwktl.JWKTL;
import de.tudarmstadt.ukp.jwktl.api.*;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.*;


@MultipartConfig
public class SnippetServlet extends HttpServlet {
    HashMap<String, HashMap<String, String>> dummyData = new HashMap<String, HashMap<String, String>>();
    ObjectMapper mapper = new ObjectMapper();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        //Stack<Object> recentUpdates=helper.getUpdates();
        //int size=recentUpdates.size();


        PrintWriter writer = response.getWriter();
        //get input string for search
        String query = request.getParameter("msg1");
        //tokenize the input string
        StringTokenizer tokens = new StringTokenizer(query);
        //load stopwords
        Set<String> stopWords = new LinkedHashSet<String>();
        BufferedReader SW= new BufferedReader(new FileReader("C:\\Data\\wiktionary\\stopwords.txt"));
        for(String line;(line = SW.readLine()) != null;)
            stopWords.add(line.trim());
        SW.close();
        //remove stopwords from the token list
        String updatedQuery = new String();
        while(tokens.hasMoreTokens()){
            String temp = tokens.nextToken();
            if(!stopWords.contains(temp))
            {
                updatedQuery=updatedQuery.concat(temp);
            }
        }
        StringTokenizer updatedTokens = new StringTokenizer(updatedQuery);

        File WIKTIONARY_DIRECTORY = new File("C:\\Data\\wiktionary\\parsed");
        IWiktionaryEdition wkt = JWKTL.openEdition(WIKTIONARY_DIRECTORY);
        while(updatedTokens.hasMoreTokens()) {
            IWiktionaryPage page = wkt.getPageForWord("tokens");
            IWiktionaryEntry entry = page.getEntry(0);
            IWiktionarySense sense = entry.getSense(1);
            for (IWiktionaryRelation relation : sense.getRelations(RelationType.SYNONYM))
                System.out.println(relation.getTarget());
            wkt.close();
        }
        data dummy = new data(query);
        dummyData = dummy.getMap();

        Iterator iterator = dummyData.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry pairs = (Map.Entry) iterator.next();
            Map<String, String> innerMap = new HashMap<String, String>();
            innerMap = (Map)pairs.getValue();
            Iterator iterator1 = innerMap.entrySet().iterator();

            while (iterator1.hasNext()) {
                Map.Entry innerPairs = (Map.Entry) iterator1.next();

                writer.println("<div class='row-fluid post-river-twocolumn border-bottom id-5294f1e5ecad046801c97170 featurepost'>" +
                                "<a href='" + pairs.getKey() + "' target='_blank'>" + innerPairs.getKey() + "</a>" +
                                "<br/>" + innerPairs.getValue() + "</div>"
                );
                writer.flush();
            }

        }

        writer.close();

    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("posting");
    }

}







