package com.company;
import java.io.*;
import java.util.*;
public class Solution {

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        String fileName = args[0];
        String s;
        boolean isDebugMode = true;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            BPlusTree tree = new BPlusTree(Integer.parseInt(bufferedReader.readLine()));
            File outputFile = new File("output_" + fileName.split("_name")[0] +"" +
                    ".txt");
            File debugFile = new File("debug_" + fileName.split("_name")[0] +"" +
                    ".txt");
            outputFile.createNewFile();
            debugFile.createNewFile();
            FileWriter outputWriter = new FileWriter(outputFile, true);
            FileWriter debugWriter = new FileWriter(outputFile, true);
            while((s = bufferedReader.readLine()) != null) {
                if(s.contains("Insert(")) {
                    double key = Double.parseDouble(s.split("Insert\\(")[1].split
                            (",")[0]);
                    String val = s.split(",")[1].split("\\)")[0];
                    tree.insert(key, val);
                } else if(s.contains("Search(")) {
                    if(s.contains(",")) {
                        double key1 = Double.parseDouble(s.split("Search\\(")[1].split(",")[0]);
                        double key2 = Double.parseDouble(s.split("Search\\(")[1].split(",")[1].split("\\)")[0]);
                        ArrayList<Pair> pairs = tree.search(key1, key2);
                        if(pairs.isEmpty()) {
                            outputWriter.write("Null\n");
                        } else {
                            StringJoiner out = new StringJoiner(",");
                            for(Pair pair:pairs) {
                                out.add(pair.toString());
                            }
                            outputWriter.write(out.toString() + "\n");
                        }
                    } else {
                        double key = Double.parseDouble(s.split("Search\\(")[1].split
                                ("\\)")[0]);
                        ArrayList<Pair> pairs = tree.search(key);
                        if(pairs.isEmpty()) {
                            outputWriter.write("Null\n");
                        } else {
                            StringJoiner out = new StringJoiner(",");
                            for (Pair pair : pairs) {
                                out.add(pair.val);
                            }
                            outputWriter.write(out.toString() + "\n");
                        }
                    }
                }
            }
            if(isDebugMode)  debugWriter.write(tree.toString() + "\n");
            outputWriter.flush();
            debugWriter.flush();
            outputWriter.close();
            debugWriter.close();
            bufferedReader.close();
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        // System.out.println(totalTime);
    }
}