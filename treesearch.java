/*
 * treesearch
 * Name:    Saranya Vatti
 * UFID:    29842706
 * UF mail: saranyavatti@ufl.edu
 * In partial fulfillment of course: Advanced Data Structures at University of Florida
 *
 *
 * Description: Creates a B Plus tree and performs search and inserts from a given
 * file in a
 * given format
 */
import java.io.*;
import java.util.*;
public class treesearch {

    public static void main(String[] args) throws IOException {
        if(args.length == 0) {
            System.out.println("Please enter a valid filename.");
            return;
        }
        String fileName = args[0];
        String s;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            BPlusTree tree = new BPlusTree(Integer.parseInt(bufferedReader.readLine()));
            File outputFile = new File("output_file.txt");
            outputFile.createNewFile();
            FileWriter outputWriter = new FileWriter(outputFile, false);
            while ((s = bufferedReader.readLine()) != null) {
                if (s.contains("Insert(")) {
                    double key = Double.parseDouble(s.split("Insert\\(")[1].split
                            (",")[0]);
                    String val = s.split(",")[1].split("\\)")[0];
                    tree.insert(key, val);
                } else if (s.contains("Search(")) {
                    if (s.contains(",")) {
                        double startKey = Double.parseDouble(s.split("Search\\(")[1]
                                .split(",")[0]);
                        double endKey = Double.parseDouble(s.split("Search\\(")[1]
                                .split(",")[1].split("\\)")[0]);
                        ArrayList<Pair> pairs = tree.search(startKey, endKey);
                        if (pairs.isEmpty()) {
                            outputWriter.write("Null\n");
                        } else {
                            StringBuilder out = new StringBuilder("");
                            for (Pair pair : pairs) {
                                out.append(pair.toString());
                                out.append(",");
                            }
                            outputWriter.write(out.deleteCharAt(out.length() - 1).toString()
                                    + "\n");
                        }
                    } else {
                        double key = Double.parseDouble(s.split("Search\\(")[1].split
                                ("\\)")[0]);
                        ArrayList<Pair> pairs = tree.search(key);
                        if (pairs.isEmpty()) {
                            outputWriter.write("Null\n");
                        } else {
                            StringBuilder out = new StringBuilder("");
                            for (Pair pair : pairs) {
                                out.append(pair.val);
                                out.append(",");
                            }
                            outputWriter.write(out.deleteCharAt(out.length() - 1).toString()
                                    + "\n");
                        }
                    }
                }
            }
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