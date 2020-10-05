package com.ossin.genetics;

import org.springframework.stereotype.Service;

@Service
public class DnaSequenceTrie {
    private final Node root;
    private Node cursor;

    public DnaSequenceTrie() {
        root = new Node();
        cursor = root;
    }

    public void add(char character) {
        cursor = createChildIfAbsent(cursor, character);
    }

    public boolean exists(char[] gene) {
        Node node = root;

        for (char c : gene) {
            node = getChild(node, c);
            if (node == null) {
                return false;
            }
        }
        return true;
    }

    private Node createChildIfAbsent(Node node, char character) {
        Node result = null;
        switch (character) {
            case 'A':
                if (node.childA == null) {
                    node.childA = new Node();
                }
                result = node.childA;
                break;
            case 'C':
                if (node.childC == null) {
                    node.childC = new Node();
                }
                result = node.childC;
                break;
            case 'G':
                if (node.childG == null) {
                    node.childG = new Node();
                }
                result = node.childG;
                break;
            case 'T':
                if (node.childT == null) {
                    node.childT = new Node();
                }
                result = node.childT;
                break;
        }
        return result;
    }

    private Node getChild(Node node, char character) {
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

    public void startGeneSequence() {
        this.cursor = root;
    }

    private static class Node {
        Node childA = null;
        Node childC = null;
        Node childG = null;
        Node childT = null;
    }
}
