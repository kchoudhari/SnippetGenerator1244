package SnippetGenerator;

/**
 * Created by Koustubh on 4/2/2014.
 */

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GoogleSearchServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GoogleSearchServlet</title>");
            out.println("</head>");
            out.println("<body>");

            // Get your query string posted by the user
          //  String query = request.getParameter("query");
            String query = "cricket";
            //Instantiate a Customsearch object using NetHttpTransport and the JacksonFactory (JSON library)
            HttpRequestInitializer httpRequestInitializer = new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest httpRequest) throws IOException {

                }
            };

            Customsearch customsearch = new Customsearch(new NetHttpTransport(), new JacksonFactory(),httpRequestInitializer);
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
            for(Result result : items){
                out.println("<br/>");
                out.println("<b><a href='"+result.getLink() + "'>"+result.getTitle()+"</a></b>");
                out.println("<br/>");
                out.println("<b>" + result.getSnippet()+"</b>");
            }
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


}
