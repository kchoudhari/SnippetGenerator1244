package SnippetGenerator;

/**
 * Created by Koustubh on 4/28/2014.
 */
import org.apache.lucene.index.*;

/**
 * @author Xu Fei for ITCS 6265/8265 Advanced Topics in KDD: Information
 *         Retrieval & Web Mining (Fall 09)
 */
public class dump {

    public String index;
    public IndexReader ir;

    public static void main(String args[]) throws Exception {
        // term you want to search
        String term = "people";

        dump t = new dump();

        t.open();
        // get all terms and df
        t.AllTerms(t.ir);
        // get one term information
        //t.Term(term);
        t.close();
    }

    public void open() throws Exception {
        // directory for indexing files
        this.index = "index";
        // create IndexReader
        this.ir = IndexReader.open(this.index);
    }

    public void close() throws Exception {
        // close IndexReader
        this.ir.close();
    }

    // get all terms and d-f (no sorted)
    public void AllTerms(IndexReader ir) throws Exception {
        // TermEnum terms = ir.terms(new Term("contents", "achieve"));
        TermEnum terms = ir.terms();
        //int i = 0;
        while (terms.next()) {
            //i++;
            System.out.println(terms.term().text() + "\t" + terms.docFreq());
        }
        // System.out.println(i);
    }

    // get term d-f (no sorted)
    public void getTerm(IndexReader ir, String term) throws Exception {
        TermEnum terms = ir.terms();
        System.out.println("Term: " + term + "\n");
        while (terms.next()) {
            if (terms.term().text() == term) {
                System.out.println(terms.term().text() + " =>"
                        + terms.docFreq());
            }
        }
    }

    public void Term(String term) throws Exception {
        this.getTerm(this.ir, term);
        TermDocs td = this.ir.termDocs(new Term("contents", term));
        System.out.println("Doc ID\t=> Term frequency:");
        int i = 0;
        while (td.next()) {
            System.out.println(td.doc() + "\t=> " + td.freq());
            i++;
        }
        System.out.println("\nInverse document frequency is: " + i);
    }

}