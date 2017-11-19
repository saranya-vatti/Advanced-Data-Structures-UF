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
            File outputFile = new File("output_" + fileName.split("_name")[0] +"" +
                    ".txt");
            outputFile.createNewFile();
            FileWriter writer = new FileWriter(outputFile, true);
            while((s = bufferedReader.readLine()) != null) {
                if(s.contains("Insert(")) {
                    double key = Double.parseDouble(s.split("Insert\\(")[1].split
                            (",")[0]);
                    String val = s.split(",")[1].split("\\)")[0];
                    tree.insert(key, val);
                    if(isDebugMode)  System.out.println(tree.toString());
                } else if(s.contains("Search(")) {
                    // writer.write(tree.toString() + "\n");
                    if(s.contains(",")) {
                        double key1 = Double.parseDouble(s.split("Search\\(")[1].split(",")[0]);
                        double key2 = Double.parseDouble(s.split("Search\\(")[1].split(",")[1].split("\\)")[0]);
                        ArrayList<Pair> pairs = tree.search(key1, key2);
                        if(pairs.isEmpty()) {
                            writer.write("Null\n");
                        } else {
                            StringJoiner out = new StringJoiner(",");
                            for(Pair pair:pairs) {
                                out.add(pair.toString());
                            }
                            writer.write(out.toString() + "\n");
                        }
                    } else {
                        double key = Double.parseDouble(s.split("Search\\(")[1].split
                                ("\\)")[0]);
                        ArrayList<Pair> pairs = tree.search(key);
                        if(pairs.isEmpty()) {
                            writer.write("Null\n");
                        } else {
                            StringJoiner out = new StringJoiner(",");
                            for (Pair pair : pairs) {
                                out.add(pair.val);
                            }
                            writer.write(out.toString() + "\n");
                        }
                    }
                }
            }
            writer.flush();
            writer.close();
            bufferedReader.close();
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }
}