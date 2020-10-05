package com.ossin.genetics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DnaParsingServiceTest {

    static final String GENE_PREFIX = "AAA";

    @Test
    public void feedCharactersOf1Gene() {
        DnaDataService serviceUnderTest = new DnaDataService(GENE_PREFIX);
        char[] gene = new char[]{'A','A','A','C','G','T'};
        for (int i = 0; i < gene.length; i++) {
            serviceUnderTest.addCharacter(gene[i]);
        }

        char[] partialGene1 = new char[]{'A','A','A','C'};
        char[] partialGene2 = new char[]{'A','A','A','C','G'};
        char[] gene2 = new char[]{'A','A','A','C','G','T','A'};

        Assertions.assertTrue(serviceUnderTest.exist(gene));
        Assertions.assertTrue(serviceUnderTest.exist(partialGene1));
        Assertions.assertTrue(serviceUnderTest.exist(partialGene2));
        Assertions.assertFalse(serviceUnderTest.exist(gene2));
    }

    @Test
    public void feedCharactersOf2Genes() {
        DnaDataService serviceUnderTest = new DnaDataService(GENE_PREFIX);
        char[] sequence =
                new char[]{'A','A','A','C','G','T','A','A','A','A','A','T'};
        for (int i = 0; i < sequence.length; i++) {
            serviceUnderTest.addCharacter(sequence[i]);
        }

        char[] gene1 = new char[]{'A','A','A','C','G','T'};
        char[] partialGene1 = new char[]{'A','A','A','C'};
        char[] partialGene2 = new char[]{'A','A','A','C','G'};

        char[] gene2 = new char[]{'A','A','A','A','A','T'};
        char[] partialGene3 = new char[]{'A','A','A'};
        char[] partialGene4 = new char[]{'A','A','A','A'};

        Assertions.assertFalse(serviceUnderTest.exist(sequence));
        Assertions.assertTrue(serviceUnderTest.exist(gene1));
        Assertions.assertTrue(serviceUnderTest.exist(partialGene1));
        Assertions.assertTrue(serviceUnderTest.exist(partialGene2));

        Assertions.assertTrue(serviceUnderTest.exist(gene2));
        Assertions.assertTrue(serviceUnderTest.exist(partialGene3));
        Assertions.assertTrue(serviceUnderTest.exist(partialGene4));
    }

    @Test
    public void feedLotsOfGenes() {
        DnaDataService serviceUnderTest = new DnaDataService(GENE_PREFIX);
        char[] gene = new char[]{'A','A','A','C','G','T'};
        for (int itr = 0; itr < 10000000; itr++) {
            for (int i = 0; i < gene.length; i++) {
                serviceUnderTest.addCharacter(gene[i]);
            }
        }

        char[] gene2 = new char[]{'A','A','A','C','G','T','A'};

        Assertions.assertTrue(serviceUnderTest.exist(gene));
        Assertions.assertFalse(serviceUnderTest.exist(gene2));
    }
}