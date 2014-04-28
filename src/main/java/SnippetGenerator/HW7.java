package SnippetGenerator;

import gate.*;
import gate.creole.SerialAnalyserController;

import java.io.File;
import java.net.URL;

/**
 * Created by Koustubh on 4/15/2014.
 */
public class HW7 {
    public static String HOME_PATH = "";
    public HW7() throws Exception{
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
    public static void main( String[] args ) throws Exception
    {
       new HW7();
        SerialAnalyserController annieController =
                (SerialAnalyserController) Factory.createResource(
                        "gate.creole.SerialAnalyserController", Factory.newFeatureMap(),
                        Factory.newFeatureMap(), "ANNIE");
        FeatureMap params = Factory.newFeatureMap();
        ProcessingResource pr = (ProcessingResource)
                Factory.createResource("gate.creole.annotdelete.AnnotationDeletePR", params);
        annieController.add(pr);

        params.put("tokeniserRulesURL", "file:" + HW7.HOME_PATH + "plugins" + File.separator + "ANNIE" + File.separator + "resources" + File.separator + "tokeniser" + File.separator + "DefaultTokeniser.rules");
        params.put("transducerGrammarURL", "file:"+  HW7.HOME_PATH+ "plugins" + File.separator + "ANNIE" + File.separator + "resources" + File.separator + "tokeniser" + File.separator + "postprocess.jape");
        pr = (ProcessingResource)
                Factory.createResource("gate.creole.tokeniser.DefaultTokeniser", params);
        annieController.add(pr);

        params = Factory.newFeatureMap();
        params.put("listsURL", "file:"+ HW7.HOME_PATH + "plugins" +File.separator + "ANNIE" + File.separator + "resources" + File.separator  + "gazetteer" + File.separator + "lists.def");
        params.put("caseSensitive", true);
        params.put("encoding", "UTF-8");
        pr = (ProcessingResource)
                Factory.createResource("gate.creole.gazetteer.DefaultGazetteer", params);
        annieController.add(pr);

        params = Factory.newFeatureMap();

        params.put("listsURL", "file:"+ HW7.HOME_PATH + "japegaz" + File.separator + "gaz" + File.separator +  "/lists.def");

        params.put("caseSensitive", false);
        params.put("encoding", "UTF-8");
        params.put("gazetteerFeatureSeparator", "$");
        pr = (ProcessingResource)
                Factory.createResource("gate.creole.gazetteer.DefaultGazetteer", params);
        annieController.add(pr);

        params = Factory.newFeatureMap();
        params.put("gazetteerListsURL", "file:" + HW7.HOME_PATH + "plugins" + File.separator + "ANNIE" + File.separator + "resources" + File.separator + "sentenceSplitter" + File.separator + "gazetteer" + File.separator + "lists.def");
        params.put("transducerURL", "file:" + HW7.HOME_PATH + "plugins" + File.separator + "ANNIE" + File.separator + "resources" + File.separator + "sentenceSplitter" + File.separator + "grammar" + File.separator + "main.jape");
        pr = (ProcessingResource)
                Factory.createResource("gate.creole.splitter.SentenceSplitter", params);
        annieController.add(pr);

        params = Factory.newFeatureMap();
        params.put("rulesURL", "file:" + HW7.HOME_PATH +"plugins" + File.separator + "ANNIE" + File.separator + "resources" + File.separator + "heptag" + File.separator + "ruleset");
        params.put("lexiconURL", "file:"+HW7.HOME_PATH +"plugins" + File.separator + "ANNIE" + File.separator + "resources" + File.separator + "heptag" + File.separator + "lexicon");
        pr = (ProcessingResource)
                Factory.createResource("gate.creole.POSTagger", params);
        annieController.add(pr);



        params = Factory.newFeatureMap();
        params.put("grammarURL", "file:"+HW7.HOME_PATH+"plugins" + File.separator + "ANNIE" + File.separator + "resources" + File.separator + "NE" + File.separator + "main.jape");
        pr = (ProcessingResource)
                Factory.createResource("gate.creole.ANNIETransducer", params);
        annieController.add(pr);

        /***
         params = Factory.newFeatureMap();
         params.put("grammarURL", "file:" + Main.HOME_PATH + "plugins" + File.separator + "ANNIE" + File.separator + "resources" + File.separator + "VP" + File.separator + "VerbGroups.jape");
         pr = (ProcessingResource)
         Factory.createResource("gate.creole.VPChunker", params);
         annieController.add(pr);


         params = Factory.newFeatureMap();
         params.put("posTagURL", "file:" + Main.HOME_PATH + "plugins" + File.separator + "Tagger_NP_Chunking" + File.separator + "pos_tag_dict");
         params.put("rulesURL", "file:" + Main.HOME_PATH + "plugins" + File.separator + "Tagger_NP_Chunking" + File.separator + "rules");
         pr = (ProcessingResource)
         Factory.createResource("mark.chunking.GATEWrapper", params);
         annieController.add(pr);
         ****/

        params = Factory.newFeatureMap();
        params.put("grammarURL", "file:" + HW7.HOME_PATH + "japegaz" + File.separator + "jape" + File.separator + "main.jape");
        pr = (ProcessingResource)
                Factory.createResource("gate.creole.Transducer", params);
        annieController.add(pr);

        Corpus corpus = (Corpus) Factory.createResource("gate.corpora.CorpusImpl");
        //Point it to whichever folder contains your documents
        URL dir = new File("C:/Users/Shekhar/Documents/Courses/NLPF13/HW2/ShekharHW2/docs").toURI().toURL();
        corpus.populate(dir, null, "UTF-8", false); //set the encoding to whatever is the encoding of your files

        annieController.setCorpus(corpus);
        annieController.execute();

        Document tempDoc = null;
        //This is how you can access the annotations created by your gate application
        for (int i = 0; i < corpus.size(); i++)
        {
            tempDoc = (Document)corpus.get(i);
            for(Annotation a: tempDoc.getAnnotations().get("OrgTitle"))
            {

                System.out.print("Org: "+a.getFeatures().get("org"));
                System.out.print("     Title: "+a.getFeatures().get("title"));
                System.out.println();
            }

            //Really important to release these resources or you'll soon run out of working memory
            Factory.deleteResource(tempDoc);

        }

        //Release resource--important
        Factory.deleteResource(corpus);

    }
}
