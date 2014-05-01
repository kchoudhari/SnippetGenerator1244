package lib;

/**
 * Created by Koustubh on 5/1/2014.
 */

import java.util.List;

//
/**
 * Class to calculate TfIdf of term.
 * @author Mubin Shrestha
 */
public class TfIdf {
    //
    /**
     * Calculated the tf of term termToCheck
     * @param totalterms : Array of all the words under processing document
     * @param termToCheck : term of which tf is to be calculated.
     * @return tf(term frequency) of term termToCheck
     */
    public double tfCalculator(String[] totalterms, String termToCheck) {
        double count = 0;  //to count the overall occurrence of the term termToCheck
        for (String s : totalterms) {
            if (s.equalsIgnoreCase(termToCheck)) {
                count++;
            }
        }
        return count / totalterms.length;
    }
    //

    //
    /**
     * Calculated idf of term termToCheck
     * @param allTerms : all the terms of all the documents
     * @param termToCheck
     * @return idf(inverse document frequency) score
     */
    public double idfCalculator(List<List<String>> allTerms, String termToCheck) {
        double count = 0;
        for (List<String> ss : allTerms) {
            for (String s : ss) {
                String[] test= s.replaceAll("[\\W&&[^\\s]]", "").split("\\W+");
                for(String test2: test) {
                    if (test2.equalsIgnoreCase(termToCheck)) {
                        count++;
                        break;
                    }
                }
            }
        }
        return Math.log(allTerms.size() / count);
    }
//
}
//
