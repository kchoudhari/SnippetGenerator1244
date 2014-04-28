package SnippetGenerator;

import de.tudarmstadt.ukp.jwktl.JWKTL;
import de.tudarmstadt.ukp.jwktl.api.*;

import java.io.File;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by Koustubh on 4/28/2014.
 */
public class TestWiktionary {
    public static void main(String[] args) {
        File WIKTIONARY_DIRECTORY = new File("C:\\Data\\wiktionary\\parsed");
        IWiktionaryEdition wkt = JWKTL.openEdition(WIKTIONARY_DIRECTORY);
        IWiktionaryPage page = wkt.getPageForWord("beautiful");
        IWiktionaryEntry entry = page.getEntry(0);
        IWiktionarySense sense = entry.getSense(1);
        for (IWiktionaryRelation relation : sense.getRelations(RelationType.SYNONYM))
            System.out.println(relation.getTarget());
        wkt.close();

    }
}
