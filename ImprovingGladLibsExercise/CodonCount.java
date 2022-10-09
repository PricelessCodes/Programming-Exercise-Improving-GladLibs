
/**
 * Write a description of CodonCount here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;

public class CodonCount {
    
    private HashMap<String,Integer> DNACodons;
    
    public CodonCount()
    {
        DNACodons = new HashMap<String,Integer>();
    }

    private void buildCodonMap(int start, String dna)
    {
        DNACodons.clear();
        
        for (int i = start; i < dna.length() - 2; i += 3)
        {
            String codon = dna.substring(i, i + 3);
            if (!DNACodons.containsKey(codon))
            {
                DNACodons.put(codon,1);
            }
            else
            {
                DNACodons.put(codon, DNACodons.get(codon) + 1);
            }
        }
    }
    
    private String getMostCommonCodon()
    {
        int max = -1;
        String maxCodon = "";
        for (String codon : DNACodons.keySet())
        {
            if (DNACodons.get(codon) > max)
            {
                max = DNACodons.get(codon);
                maxCodon = codon;
            }
        }
        return maxCodon;
    }
    
    private void printCodonCounts(int start, int end)
    {
        for (String codon : DNACodons.keySet())
        {
            int value = DNACodons.get(codon);
            if (value >= start && value <= end)
            {
                System.out.println("total count: " + value + " of codon: " + codon);
            }
        }
    }
    
    public void tester()
    {
        FileResource fr = new FileResource();
        String dna = fr.asString().toUpperCase().trim();
        System.out.println("the dna is: " + dna);
        buildCodonMap(0, dna);
        System.out.println("the total number of unique codons is: " + DNACodons.size());
        
        String mostCommonCodon = getMostCommonCodon();
        System.out.println("the most common codon is: " + mostCommonCodon + " and its count is: " + DNACodons.get(mostCommonCodon));
        
        int start = 7, end = 7;
        System.out.println("the codons and their number of occurrences for those codons whose number of occurrences are between (" + start + " and " + end + ") inclusive: ");
        printCodonCounts(start, end);
    }
}
