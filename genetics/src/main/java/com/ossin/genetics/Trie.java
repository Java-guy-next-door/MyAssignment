package com.ossin.genetics;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Trie {
    private final Node root;
    private Node curser;
    private int nodes;

    public Trie() {
        root = new Node();
        curser = root;
        nodes = 1;
    }

//    public void add(DnaDataService.DnaCharacter character) {
    public void add(char character) {
//        Node node = root;
//        for (char c : value.toCharArray()) {
//            BooleanPair charRepresentation = getCharRepresentation(c);
//            node.children.putIfAbsent(charRepresentation, new Node());
//            node = node.children.get(charRepresentation);
        curser = createChildIfAbsent(curser, character);
//        }
    }

    private BooleanPair getCharRepresentation(char c) {
        BooleanPair pair = null;
        switch(c) {
            case 'A':
                pair = new BooleanPair(false, false);
                break;
            case 'C':
                pair = new BooleanPair(false, true);
                break;
            case 'G':
                pair = new BooleanPair(true, false);
                break;
            case 'T':
                pair = new BooleanPair(true, true);
                break;
        }
        return pair;
    }

//    public void add(byte[] value) {
//        Node node = root;
//        Character c;
//        for (int i = 0; i < value.length(); i++) {
//            c = value.charAt(i);
//            node.children.putIfAbsent(c, new Node(i == value.length()-1));
//            node = node.children.get(c);
//        }
//    }
    public int getNodes() {
        return nodes++;
    }

    public boolean exists(char[] gene) {
//    public boolean exists(DnaDataService.DnaCharacter[] gene) {
        Node node = root;

        for (char c : gene) {
//        for (DnaDataService.DnaCharacter character : gene) {
//            BooleanPair charRepresentation = getCharRepresentation(c);
//            if (!node.children.containsKey(charRepresentation)) {
//                return false;
//            }
            node = getChild(node, c);
//            node = getChild(node, character);
            if (node == null) {
                return false;
            }
        }
        return true;
    }

//    private Node createChildIfAbsent(Node node, DnaDataService.DnaCharacter character) {
    private Node createChildIfAbsent(Node node, char character) {
        Node result = null;
        switch (character) {
            case 'A':
                if (node.childA == null) {
                    node.childA = new Node();
                    nodes++;
                }
                result = node.childA;
                break;
            case 'C':
                if (node.childC == null) {
                    node.childC = new Node();
                    nodes++;
                }
                result = node.childC;
                break;
            case 'G':
                if (node.childG == null) {
                    node.childG = new Node();
                    nodes++;
                }
                result = node.childG;
                break;
            case 'T':
                if (node.childT == null) {
                    node.childT = new Node();
                    nodes++;
                }
                result = node.childT;
                break;
        }
        return result;
    }

    private Node getChild(Node node, char character) {
//    private Node getChild(Node node, DnaDataService.DnaCharacter character) {
        Node result = null;
        switch (character) {
            case 'A':
                result = node.childA;
                break;
            case 'C':
                result = node.childC;
                break;
            case 'G':
                result = node.childG;
                break;
            case 'T':
                result = node.childT;
                break;
        }
        return result;
    }

    public void startGeneSquence() {
        this.curser = root;
    }

//    public int nodes() {
//        return nodes(root);
//    }
//    private int nodes(Node node) {
//        int result = 1;
//        for (Node child : node.children.values()) {
//            result += nodes(child);
//        }
//        return result;
//    }

    private static class Node {
//        private final boolean isCompleteValue;
//        private final Map<Character, Node> children;
//        Map<BooleanPair, Node> children;
        Node childA = null;
        Node childC = null;
        Node childG = null;
        Node childT = null;

        Node() {
//            this.isCompleteValue = isCompleteValue;
//            children = new HashMap<>();
        }
    }

    private static class BooleanPair {
        boolean b1;
        boolean b2;
        BooleanPair(boolean b1, boolean b2) {
            this.b1 = b1;
            this.b2 = b2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BooleanPair that = (BooleanPair) o;
            return b1 == that.b1 &&
                    b2 == that.b2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(b1, b2);
        }
    }
}
