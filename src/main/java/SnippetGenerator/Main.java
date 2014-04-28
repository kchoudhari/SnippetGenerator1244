package SnippetGenerator;

/**
 * Created by Koustubh on 4/16/2014.
 */import java.io.File;
import java.net.URL;

import gate.*;
import static gate.util.persistence.PersistenceManager.loadObjectFromFile;

public class Main {
    public static String HOME_PATH = "";
    public Main() throws Exception{
        System.setProperty("gate.home", "C:\\Program Files\\GATE_Developer_7.1");
        Gate.init();
        File gateHome = Gate.getGateHome();
        HOME_PATH = gateHome.getCanonicalPath() + "/";

        System.out.println("Home path: "+ HOME_PATH);
        if (Gate.getGateHome() == null)
            Gate.setGateHome(gateHome);


        File pluginsHome = new File(gateHome, "plugins");
        //Register all the plugins that your program will need
        Gate.getCreoleRegister().registerDirectories(
                new File(pluginsHome, "ANNIE")
                        .toURI().toURL());
    }

    public static void main(String[] ars)throws Exception
    {
        new Main();

        //Point it to where your gapp file resides on your hard drive
        gate.CorpusController c = ((gate.CorpusController)loadObjectFromFile(new java.io.File("C:\\Data\\Nyu Poly\\koustubhhw7\\hw7.gapp")));
        Corpus corpus = (Corpus) Factory.createResource("gate.corpora.CorpusImpl");
        //Point it to whichever folder contains your documents
        URL dir = new File("C:\\Data\\Nyu Poly\\Sem 4\\NLP\\embeddedjape\\input").toURI().toURL();
        corpus.populate(dir, null, "UTF-8", false); //set the encoding to whatever is the encoding of your files

        c.setCorpus(corpus);
        c.execute();

        gate.Document tempDoc = null;
        //This is how you can access the annotations created by your gate application
        //This is how you can access the annotations created by your gate application
        for (int i = 0; i < corpus.size(); i++){
            tempDoc = (gate.Document)corpus.get(i);
            for(gate.Annotation a: tempDoc.getAnnotations().get("Name")){

                System.out.println("First Name: "+a.getFeatures().get("first"));
                System.out.println("Last Name: "+a.getFeatures().get("last"));
            }

            //Really important to release these resources or you'll soon run out of working memory
            Factory.deleteResource(tempDoc);

        }

        //Release resource--important
        Factory.deleteResource(corpus);
    }
}

