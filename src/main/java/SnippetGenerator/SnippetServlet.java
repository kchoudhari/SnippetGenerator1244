package SnippetGenerator; /**
 * Created with IntelliJ IDEA.
 * User: harsh
 * Date: 11/27/13
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */

import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@MultipartConfig
public class SnippetServlet extends HttpServlet {
    HashMap<URL, String> dummyData=new HashMap<URL, String>();
    ObjectMapper mapper = new ObjectMapper();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        //Stack<Object> recentUpdates=helper.getUpdates();
        //int size=recentUpdates.size();



        PrintWriter writer = response.getWriter();
        String query= request.getParameter("msg1");

        System.out.println("hi");
        data dummy=new data(query);
        dummyData= dummy.getMap();

        Iterator iterator=dummyData.entrySet().iterator();

        while(iterator.hasNext()){
            Map.Entry pairs = (Map.Entry)iterator.next();
            writer.println("<div class='row-fluid post-river-twocolumn border-bottom id-5294f1e5ecad046801c97170 featurepost'>" +
                    "<a href='"+pairs.getKey()+"' target='_blank'>"+pairs.getKey()+"</a>" +
                    "<br/>"+pairs.getValue()+"</div>"
            );
            writer.flush();
        }

        writer.close();

    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("posting");
    }

    }
