package SnippetGenerator;

import de.tudarmstadt.ukp.jwktl.JWKTL;

import java.io.File;

/**
 * Created by Koustubh on 4/28/2014.
 */
public class CreateWiktionary {
    public static void main(String[] args) {
        File dumpFile = new File("C:\\Data\\wiktionary\\enwiktionary-20140415-pages-articles-multistream.xml");
        File outputDirectory = new File("C:\\Data\\wiktionary\\parsed");
        boolean overwriteExisting = false;

        JWKTL.parseWiktionaryDump(dumpFile, outputDirectory, overwriteExisting);
    }
}
