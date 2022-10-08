
/**
 * Write a description of GladLibMap here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;
import java.io.*;

public class GladLibMap {
    
    private HashMap<String,ArrayList<String>> myMap;
    private ArrayList<String> trackWords;
    private ArrayList<String> trackCategories;
    
    private Random myRandom;
    
    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "data";
    
    public GladLibMap(){
        myMap = new HashMap<String,ArrayList<String>>();
        initializeFromSource(dataSourceDirectory);
        myRandom = new Random();
    }
    
    public GladLibMap(String source){
        myMap = new HashMap<String,ArrayList<String>>();
        initializeFromSource(source);
        myRandom = new Random();
    }
    
    private void initializeFromSource(String source) {
        String[] labels = {"adjective", "noun", "color", "country", "name", "animal", "timeframe", "verb", "fruit"};
        
        for (String label : labels)
        {
            ArrayList<String> labelWords = readIt(source + "/" + label + ".txt");
            myMap.put(label, labelWords);
        }
        
        trackWords = new ArrayList<String>();
        trackCategories = new ArrayList<String>();
    }
    
    private String randomFrom(ArrayList<String> source){
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }
    
    private String getSubstitute(String label) {
        if (label.equals("number")){
            return ""+myRandom.nextInt(50)+5;
        }
        if (myMap.containsKey(label)){
            return randomFrom(myMap.get(label));
        }
        return "**UNKNOWN**";
    }
    
    private String processWord(String w){
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);
        if (first == -1 || last == -1){
            return w;
        }
        String prefix = w.substring(0,first);
        String suffix = w.substring(last+1);
        String sub = getSubstitute(w.substring(first+1,last));
        
        while (trackWords != null && trackWords.contains(sub))
        {
            sub = getSubstitute(w.substring(first+1,last));
        }
        trackWords.add(sub);
        
        if (!trackCategories.contains(w.substring(first+1,last))){
            trackCategories.add(w.substring(first+1,last));
        }
        
        return prefix+sub+suffix;
    }
    
    private void printOut(String s, int lineWidth){
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
    }
    
    private String fromTemplate(String source){
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        return story;
    }
    
    private ArrayList<String> readIt(String source){
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        return list;
    }
    
    private int totalWordsInMap()
    {
        int total = 0;
        for (String label : myMap.keySet())
        {
            total += myMap.get(label).size();
        }
        return total;
    }
    
    private int totalWordsConsidered()
    {
        int total = 0;
        
        if (trackCategories.size() != 3)
        {
            return -1;
        }
        
        boolean isNoun = trackCategories.contains("noun");
        boolean isColor = trackCategories.contains("color");
        boolean isAadjective = trackCategories.contains("adjective");
        
        if (isNoun && isColor && isAadjective)
        {
            total += myMap.get("noun").size() + myMap.get("color").size() + myMap.get("adjective").size();
            
            return total;
        }
        return -1;
    }
    
    public void makeStory(){
        trackWords.clear();
        trackCategories.clear();
        System.out.println("\n");
        String story = fromTemplate("data/madtemplate4.txt");
        printOut(story, 60);
        System.out.println("\n");
        
        int totalWords = totalWordsInMap();
        System.out.println("the total number of words that were possible to pick from is: " + totalWords);
        
        int totalWordsCons = totalWordsConsidered();
        System.out.println("the total number of words that were only noun, color, and adjective: " + totalWordsCons);
    }
}
