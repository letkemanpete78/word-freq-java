package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by pete on 9/20/2016.
 */
public class LoadWords
{
    public LoadWords() {}

    public LinkedHashMap<String,Integer> LoadDictionary(String filename) throws IOException
    {
        Hashtable words = new Hashtable();
        File dir = new File(".");
        File fin = new File(dir.getCanonicalPath() + File.separator + filename);
        BufferedReader br = new BufferedReader(new FileReader(fin));

        String line = null;
        int itemCounter = 0;
        while ((line = br.readLine()) != null) {
            line = line.replace("----","--").replace("---","--").replace("--"," -- ").replace("`"," ` ").replace("!"," ! ").replace("@"," @ ").replace("#"," # ").replace("$"," $ ").replace("%"," % ");
            line = line.replace("^"," ^ ").replace("*"," * ").replace("("," ( ").replace(")"," ) ").replace("_"," _ ").replace("{"," { ").replace("}"," } ").replace("["," [ ");
            line = line.replace("]"," ] ").replace(";"," ; ").replace(":"," : ").replace(","," , ").replace("."," . ").replace("<"," < ").replace(">"," > ").replace("?"," ? ").replace("+"," + ");
            line = line.replace("="," = ").replace("/"," / ").replace("~"," ~ ").replace("\\"," \\ ").replace("'"," ' ").replace("\""," \" ");
            String[] result = line.toLowerCase().split("\\s");
            for (int x=0; x<result.length; x++) {
                if (words.containsKey(result[x].trim()))
                {
                    itemCounter = Integer.parseInt(words.get(result[x].trim()).toString());
                    words.remove(result[x].trim());
                }
                itemCounter++;
                words.put(result[x].trim(),itemCounter);
            }
        }
        br.close();

        Map<String,Integer> aMap2 = new LinkedHashMap<String, Integer>();

        aMap2 = sortMapByValues(words,Direction.Decending,Element.Value);

        // printing values after soring of map
        System.out.println("Value " + " - " + "Key");
        int wordCount = 0;
        int maxValue = 0;
        for(Map.Entry<String,Integer> entry : aMap2.entrySet())
        {
            if (wordCount == 0)
            {
                maxValue = entry.getValue();
                wordCount++;
            }
            if ((int)maxValue != (int)entry.getValue())
            {
                maxValue = entry.getValue();
                wordCount++;
            }
            if (wordCount <= 30)
            {
                System.out.println(wordCount + ": " + entry.getValue() + " - " + entry.getKey());
            }
        }
        return (LinkedHashMap) aMap2;
    }



    public enum Direction {
        Ascending,
        Decending
    }

    public enum Element {
        Key,
        Value
    }

    private Map sortMapByValues(Map<String, Integer> aMap, Direction direction, Element element) {


        Set<Map.Entry<String,Integer>> mapEntries = aMap.entrySet();

        // used linked list to sort, because insertion of elements in linked list is faster than an array list.
        List<Map.Entry<String,Integer>> aList = new LinkedList<Map.Entry<String,Integer>>(mapEntries);

        // sorting the List
        Collections.sort(aList, new Comparator<Map.Entry<String,Integer>>() {

            @Override
            public int compare(Map.Entry<String, Integer> ele1, Map.Entry<String, Integer> ele2)
            {
                if (element == Element.Value)
                {
                    if (direction == Direction.Ascending) {
                        return ele1.getValue().compareTo(ele2.getValue());
                    } else {
                        return ele2.getValue().compareTo(ele1.getValue());
                    }
                }
                else
                {
                    if (direction == Direction.Ascending) {
                        return ele1.getKey().compareTo(ele2.getKey());
                    } else {
                        return ele2.getKey().compareTo(ele1.getKey());
                    }
                }
            }
        });

        // Storing the list into Linked HashMap to preserve the order of insertion.
        Map<String,Integer> aMap2 = new LinkedHashMap<String, Integer>();
        for(Map.Entry<String,Integer> entry: aList) {
            aMap2.put(entry.getKey(), entry.getValue());
        }


        return  aMap2;
    }
}