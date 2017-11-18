package com.company;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
public class Solution {

    public static void main(String[] args) throws IOException {

        String fileName = args[0];
        String s;
        boolean isDebugMode = false;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            BPlusTree tree = new BPlusTree(Integer.parseInt(bufferedReader.readLine
                    ()), isDebugMode);
            while((s = bufferedReader.readLine()) != null) {
                if(s.contains("Insert(")) {
                    double key = Double.parseDouble(s.split("Insert\\(")[1].split
                            (",")[0]);
                    int val = Integer.parseInt(s.split("Value")[1].split("\\)")[0]);
                    tree.insert(key, val);
                    if(isDebugMode)  System.out.println(tree.toString());
                } else if(s.contains("Search(")) {
                    if(s.contains(",")) {
                        double key1 = Double.parseDouble(s.split("Search\\(")[1].split(",")[0]);
                        double key2 = Double.parseDouble(s.split("Search\\(")[1].split(",")[1].split("\\)")[0]);
                        System.out.println(Arrays.toString(tree.search(key1, key2).toArray
                                ()));
                    } else {
                        double key = Double.parseDouble(s.split("Search\\(")[1].split
                                ("\\)")[0]);
                        System.out.println(Arrays.toString(tree.search(key).toArray
                                ()));
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