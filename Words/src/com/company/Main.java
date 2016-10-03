package com.company;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import com.company.LoadWords;

public class Main {

    public static void main(String[] args) {
        com.company.LoadWords loadWords = new com.company.LoadWords();
        try
        {
            loadWords.LoadDictionary(args[0]);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
