package lib;

/**
 * Created by Koustubh on 5/1/2014.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.*;

/**
 * Class to read documents
 *
 * @author Mubin Shrestha
 */
public class DocumentParser {

    //This variable will hold all terms of each document in an array.
    private List<List<String>> termsDocsArray = new ArrayList();
    private List<String> allTerms = new ArrayList(); //to hold all terms
    private List<double[]> tfidfDocsVector = new ArrayList();
    HashMap<Double, String> scoreMapper= new HashMap<Double, String>();
    HashMap<String, Double> idfMapper= new HashMap<String, Double>();
    PriorityQueue<Double> priorityQueue= new PriorityQueue<Double>(20,Collections.reverseOrder());

    /**
     * Method to read files and store in array.
     * @param filePath : source file path
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void parseFiles(String filePath, String query) throws FileNotFoundException, IOException {
        File[] allfiles = new File(filePath).listFiles();
        BufferedReader in = null;
        for (File f : allfiles) {
            if (f.getName().endsWith(".txt")) {
                in = new BufferedReader(new FileReader(f));
                StringBuilder sb = new StringBuilder();
                String s = null;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                }

                List<String> sentences=new ArrayList<String>();
                BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
                String source = sb.toString();
                iterator.setText(source);
                int start = iterator.first();
                for (int end = iterator.next();
                     end != BreakIterator.DONE;
                     start = end, end = iterator.next()) {
                    sentences.add(source.substring(start, end));
                }


                //String[] tokenizedTerms = sb.toString().replaceAll("[\\W&&[^\\s]]", "").split("\\W+");   //to get individual terms
                String[] queryTerms= query.replaceAll("[\\W&&[^\\s]]", "").split("\\W+");

                for (String term : queryTerms) {
                    if (!allTerms.contains(term)) {  //avoid duplicate entry
                        allTerms.add(term);
                    }
                }
                termsDocsArray.add(sentences);
            }
        }

    }

    /**
     * Method to create termVector according to its tfidf score.
     */
    public void tfIdfCalculator() {
        double tf; //term frequency
        double idf; //inverse document frequency
        double tfidf; //term requency inverse document frequency


        for (String terms : allTerms) {
            idf = new TfIdf().idfCalculator(termsDocsArray, terms);
            idfMapper.put(terms, idf);
            System.out.println(terms+" "+ idf);
        }

        for (List<String> docTermsArray : termsDocsArray) {

            for (String sentence : docTermsArray) {

                String[] sentenceArray = sentence.replaceAll("[\\W&&[^\\s]]", "").split("\\W+");
                double[] tfidfvectors = new double[allTerms.size()];
                double totalScore = 0.0;
                int count = 0;
                for (String terms : allTerms) {
                    tf = new TfIdf().tfCalculator(sentenceArray, terms);
                    idf = idfMapper.get(terms);
                    tfidf = tf * idf;
                    totalScore += tfidf;
                    tfidfvectors[count] = tfidf;
                    //System.out.println(tfidf);
                    count++;
                }
                scoreMapper.put(totalScore, sentence);
                priorityQueue.offer(totalScore);
                tfidfDocsVector.add(tfidfvectors);  //storing document vectors;
            }
        }
        getBestResult(5);
    }

    private void getBestResult(int i) {
        for(int j=0;j<i; j++){
            double score= priorityQueue.poll();
            String sentence=scoreMapper.get(score);
            System.out.println(sentence);
        }
    }

    /**
     * Method to calculate cosine similarity between all the documents.
     */
    public void getCosineSimilarity() {
        for (int i = 0; i < tfidfDocsVector.size(); i++) {
            for (int j = 0; j < tfidfDocsVector.size(); j++) {
//                System.out.println("between " + i + " and " + j + "  =  "
//                                + new CosineSimilarity().cosineSimilarity
//                                (
//                                        tfidfDocsVector.get(i),
//                                        tfidfDocsVector.get(j)
//                                )
//                );
            }
        }
    }
}