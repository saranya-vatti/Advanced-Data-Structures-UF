/*
 * treesearch
 * Name:    Saranya Vatti
 * UFID:    29842706
 * UF mail: saranyavatti@ufl.edu
 * In partial fulfillment of course: Advanced Data Structures at University of Florida
 *
 *
 * Description: Creates a tree and performs search and inserts from a given file in a
 * given format
 */
package com.ufl;
import java.io.*;
import java.util.*;
public class treesearch {

    public static void main(String[] args) throws IOException {
        String fileName = args[0];
        String s;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            BPlusTree tree = new BPlusTree(Integer.parseInt(bufferedReader.readLine()));
            File outputFile = new File("output_file.txt");
            outputFile.createNewFile();
            FileWriter outputWriter = new FileWriter(outputFile, false);
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
            System.out.println(tree);
            outputWriter.flush();
            outputWriter.close();
            bufferedReader.close();
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }
}