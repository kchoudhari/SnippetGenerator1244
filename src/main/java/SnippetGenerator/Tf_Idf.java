package SnippetGenerator;


import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.*;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.search.similarities.TFIDFSimilarity;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;

/**
 * Created by Koustubh on 4/28/2014.
 */
public class Tf_Idf {
    static float tf = 1;
    static float idf = 0;
    private float tfidf_score;

    static float[] tfidf = null;


    public void scoreCalculator(IndexReader reader,String field,String term) throws IOException
    {
        /** GET TERM FREQUENCY & IDF **/
        TFIDFSimilarity tfidfSIM = new DefaultSimilarity();
        Bits liveDocs = MultiFields.getLiveDocs(reader);
        TermsEnum termEnum = MultiFields.getTerms(reader, field).iterator(null);
        BytesRef bytesRef;
        while ((bytesRef = termEnum.next()) != null)
        {
            if(bytesRef.utf8ToString().trim() == term.trim())
            {
                if (termEnum.seekExact(bytesRef, true))
                {
                    idf = tfidfSIM.idf(termEnum.docFreq(), reader.numDocs());
                    DocsEnum docsEnum = termEnum.docs(liveDocs, null);
                    if (docsEnum != null)
                    {
                        int doc;
                        while((doc = docsEnum.nextDoc())!=DocIdSetIterator.NO_MORE_DOCS)
                        {
                            tf = tfidfSIM.tf(docsEnum.freq());
                            tfidf_score = tf*idf;
                            System.out.println(" -tfidf_score- " + tfidf_score);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Tf_Idf test=new Tf_Idf();
        IndexReader reader= DirectoryReader.open(FSDirectory.open(new File("index")));
        test.scoreCalculator(reader,"My name is harsh","name");
    }
}
