
/**
 * Write a description of WordsInFiles here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;
import java.io.*;

public class WordsInFiles {

    private HashMap<String,ArrayList<String>> words;
    
    public WordsInFiles()
    {
        words = new HashMap<String,ArrayList<String>>();
    }
    
    private void addWordsFromFile(File f)
    {
        FileResource fr = new FileResource(f);
        
        for (String word: fr.words())
        {
            word = word.toLowerCase().trim();
            
            if (!words.containsKey(word))
            {
                ArrayList<String> files = new ArrayList<String>();
                files.add(f.getName());
                words.put(word, files);
            }
            else
            {
                ArrayList<String> files = words.get(word);
                if (!files.contains(f.getName()))
                {
                    files.add(f.getName());
                    words.put(word, files);
                }
            }
        }
    }
    
    private void buildWordFileMap()
    {
        words.clear();
        
        DirectoryResource dr = new DirectoryResource();
        // iterate over files
        for (File f : dr.selectedFiles())
        {
            addWordsFromFile(f);
        }
    }
    
    private int maxNumber()
    {
        int max = -1;
        String maxWord = "";
        for (String word : words.keySet())
        {
            int length = words.get(word).size();
            if (length > max)
            {
                max = length;
                maxWord = word;
            }
        }
        System.out.println("the most common word in files is: (" + maxWord + ") and it is occuranced inside (" + max + ") files");
        return max;
    }
    
    private ArrayList<String> wordsInNumFiles(int number)
    {
        ArrayList<String> wordsList = new ArrayList<String>();
        int count = 0; 
        
        for (String word : words.keySet())
        {
            ArrayList<String> currentList = words.get(word);
            int length = currentList.size();
            if (length == number)
            {
                wordsList.add(word);
                count++;
            }
        }
        System.out.println("countcountcount : " + count);
        return wordsList;
    }
    
    private void printFilesIn(String word)
    {
        String print = word + " exists in (";
        ArrayList<String> files = words.get(word);
        
        for (int i = 0; i < files.size(); i++)
        {
            String file = files.get(i);
            print += (file + ", ");
        }
        print += ")";
        System.out.println(print);
    }
    
    private void printAll()
    {
        System.out.println("Print all inside hashmap");
        for (String word : words.keySet())
        {
            printFilesIn(word);
        }
    }
    
    public void tester()
    {
        buildWordFileMap();
        
        printAll();
        System.out.println("\n");
        
        int occurrance = maxNumber();
        System.out.println("\n");
        
        int number = 4;
        ArrayList<String> wordList = wordsInNumFiles(number);
        System.out.println("Words that occurred in (" + number + ") files");
        for (int i = 0; i < wordList.size(); i++)
        {
            printFilesIn(wordList.get(i));
        }
        System.out.println("\n");
        System.out.println(wordList.size());
        printFilesIn("tree");
    }
}
