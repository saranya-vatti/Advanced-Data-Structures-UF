package com.company;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
public class Solution {

    public static void main(String[] args) {
        BPlusTree tree = new BPlusTree(4);
        tree.insert(1, 1.0);
        tree.insert(2, 2.0);
        tree.insert(3, 3.0);
        tree.insert(4, 4.0);
        tree.insert(5, 5.0);
        tree.insert(6, 6.0);
        tree.insert(7, 7.0);
        tree.insert(8, 8.0);
    }
}