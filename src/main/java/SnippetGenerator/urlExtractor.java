package SnippetGenerator;

/**
 * Created by Koustubh on 4/3/2014.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class urlExtractor {

    String getContent(URL url) throws URISyntaxException, IOException {
        Document doc = Jsoup.connect(url.toString()).get();
        //Elements newsHeadlines = doc.select("#mp-itn b a");
        String content=Jsoup.parseBodyFragment(doc.html()).getElementsByTag("p").text();
        System.out.println(content);

        return content;
    }
}