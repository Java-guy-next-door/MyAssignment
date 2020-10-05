package com.ossin.genetics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DnaDataService {
    private final DnaSequenceTrie genePool;
    private final char[] PREFIX;
    private int prefixIndex;
    private int nodes;

    @Autowired
    public DnaDataService(@Value("${gene-prefix}") String genePrefix) {
        this.genePool = new DnaSequenceTrie();
        this.PREFIX = new char[genePrefix.length()];
        for (int i = 0; i < genePrefix.length(); i++) {
            PREFIX[i] = genePrefix.charAt(i);
        }
        prefixIndex = 0;
        nodes = 0;
    }

    public void addCharacter(char dnaCharacter) {
        if (PREFIX[prefixIndex] == dnaCharacter) {
            prefixIndex++;
            if (prefixIndex == PREFIX.length) {
                genePool.startGeneSequence();
                prefixIndex = 0;
                nodes++;
            }
        } else {
            for (int i = 0; i < prefixIndex; i++) {
                genePool.add(PREFIX[i]);
            }
            prefixIndex = 0;
            genePool.add(dnaCharacter);
        }
    }

    public boolean exist(char[] gene) {
        if (gene.length < PREFIX.length) {
            return false;
        }

        for (int i = 0 ; i < PREFIX.length; i++) {
            if (PREFIX[i] != gene[i]) {
                return false;
            }
        }
        if (gene.length == PREFIX.length) {
            return true;
        }

        char[] geneTail = new char[gene.length - PREFIX.length];
        for (int i = PREFIX.length; i < gene.length; i++) {
            geneTail[i - PREFIX.length] = gene[i];
        }
        return genePool.exists(geneTail);
    }

    public int getNodes() {
        return nodes;
    }
}
