package com.company;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
public class Solution {
    private static int m;

    private static void initialize(int m) {
        // TODO - finish the function
    }

    private static void insert(double key, int value) {
        // TODO - finish the function
        // Search for the item - search should return [Node node, boolean isPresent}
        // Case 1 - insert into underfull leaf
        // All we need to do is insert the pair into the arraylist of pairs of
        // the leaf node. children will be null
        // Case 2 - Leaf gets full
        // Split the node: Left half pairs stay in the old leaf, Right half pairs go
        // to new node. In between key has to go to the parent.
        // Insert the in between key into parent, then order them properly,
        // On the way down, store the nodes in stack, on the way up, pop them and
        // update them
        System.out.println("Inserting Key : " + key + "; value : " + value);
    }

    /**
     * Returns all values associated with the key
     * @param key {double} Key we need to search for
     * @return values {int[]} All the values associated with the key.
     */
    private static int[] search(double key) {
        // TODO - finish the function
        // TODO - finish the function
        // TODO - print to a file
        int[] values = {};
        System.out.println("Searching for key : " + key);
        return values;
    }

    /**
     * Returns all values associated with the key
     * @param key1 {double} Starting key
     * @param key2 {double} Ending key
     * @return values {int[]} All the values between the two given keys
     */
    private static int[] search(double key1, double key2) {
        // TODO - finish the function
        // TODO - print to a file
        int[] values = {};
        System.out.println("Searching between keys : " + key1 + " and " + key2);
        return values;
    }

    public static void main(String[] args) {
        String fileName = args[0];
        String s;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((s = bufferedReader.readLine()) != null) {
                // TODO: use regex
                if(s.indexOf("Insert(") != -1) {
                    double key = Double.parseDouble(s.split("Insert\\(")[1].split(",")[0]);
                    int val = Integer.parseInt(s.split("Value")[1].split("\\)")[0]);
                    insert(key, val);
                } else if(s.indexOf("Search(") != -1) {
                    if(s.indexOf(",") == -1) {
                        double key = Double.parseDouble(s.split("Search\\(")[1].split("\\)")[0]);
                        search(key);
                    } else {
                        double key1 = Double.parseDouble(s.split("Search\\(")[1].split(",")[0]);
                        double key2 = Double.parseDouble(s.split("Search\\(")[1].split(",")[1].split("\\)")[0]);
                        search(key1, key2);
                    }
                }
            }
            bufferedReader.close();
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }
}